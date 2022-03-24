package code;

import java.util.ArrayList;
import java.util.List;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.FuncTable;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

public class LLVMGenerator extends ASTBaseVisitor<Integer>{

    static int reg = 2;

    private static String buffer = "";
    private static int buffer_var_int =0;
    private static int buffer_var_int_flag =1;
    private static float buffer_var_float = 0;
    private static int buffer_var_float_flag =1;
    private static int buffer_var_bool = 2;
    private static int buffer_var_bool_flag =1;
    private final StrTable st;
	private final VarTable vt;
	private final FuncTable ft;
    private List<FunctionRef> functionRefs;



    public LLVMGenerator(StrTable st, VarTable vt, FuncTable ft) {
        this.st = st;
        this.vt = vt;
        this.ft = ft;
        this.functionRefs = new ArrayList<FunctionRef>();
    }
    @Override 
    public void execute(AST root) {
        visit(root);
        String text = "";
        text += "\n";
        text += "define dso_local i32 @main() #0{\n  %1 = alloca i32, align 4\n  store i32 0, i32* %1, align 4\n";
        text += buffer;
        text += "  ret i32 0\n";
        text += "}\n";
        
        System.out.println(text);
    }

    // Helper method to find the function reference when the function is called
	FunctionRef findFuncRef(String name) {
		for (FunctionRef ref : functionRefs) {
			if (ref.name == name) return ref;
		}
		return null;
	}

    @Override
    protected Integer visitBoolVal(AST node) {
        buffer_var_bool = node.intData;
        return null;
    }

    @Override
    protected Integer visitIntVal(AST node) {
        buffer_var_int = node.intData;
        return null;
    }

    @Override
    protected Integer visitFloatVal(AST node) {
        buffer_var_float = node.floatData;
        return null;
    }

    @Override
    protected Integer visitStringVal(AST node) {
        // buffer += node.intData;
        return null;
    }

    /*------------------------------------------------------------------------------*
	 *	Input
	 *------------------------------------------------------------------------------*/


    @Override
    protected Integer visitInput(AST node) {
        int varIdx = node.getChild(0).intData;
        Type varType = vt.getType(varIdx);

        switch(varType) {
			case INT_TYPE:
                buffer+= "%" + reg++ +" = alloca i32, align 4";
				break;
			case FLOAT32_TYPE:
                buffer+= "%" + reg++ +" = alloca float, align 4";
				break;
			case BOOL_TYPE:
                buffer+= "%" + reg++ +" = alloca i8, align 1";
				break;
			case STRING_TYPE:
                // buffer+= "%" + reg++ +" = alloca i32, align 4";
				break;
		    default:
	            System.err.printf("Invalid input type: %s!\n", varType.toString());
	            System.exit(1);
		}
		return null;
    }


    /*------------------------------------------------------------------------------*
	 *	Output
	 *------------------------------------------------------------------------------*/

    @Override
    protected Integer visitOutput(AST node) {
        // Get the expression list node
		AST expressionList = node.getChild(0);

		// Iterates over the expression list's children to print them
		for (AST expression : expressionList.getChildren()) {
			visit(expression);
			
			switch(expression.type) {
				case INT_TYPE:  	buffer+=buffer_var_int;		break;
				case FLOAT32_TYPE: 	buffer+=buffer_var_float;	break;
				case BOOL_TYPE: 	buffer+=buffer_var_bool;  	break;
				case STRING_TYPE:  	/*buffer+=x;*/		        break;
				case NO_TYPE:
				default:
					System.err.printf("Invalid output type: %s!\n", expression.type.toString());
					System.exit(1);
			}
		}

		return null;
    }



    @Override
    protected Integer visitEquals(AST node) {
        AST l = node.getChild(0);
		AST r = node.getChild(1);

		// Visits each expression and get their registers
		visit(l);
		visit(r);

        int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'equals operation' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp eq i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp oeq float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case BOOL_TYPE:
            	    buffer+= "  %" + reg++ + " = load i8, i8* %" + (varIdxl + 2) + ", align 1\n";
                    buffer+= "  %" + reg++ + " = trunc i8 %"+ (reg - 2) + " to i1\n"; 
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i32\n";
                    buffer+= "  %" + reg++ + " = load i8, i8* %" + (varIdxr + 2) + ", align 1\n";
                    buffer+= "  %" + reg++ + " = trunc i8 %"+ (reg - 2) + " to i1\n"; 
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i32\n";
                    buffer+= "  %" + reg++ + " = icmp eq i32 %"+ (reg - 5) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm icmp */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
    }

    @Override
    protected Integer visitLess(AST node) {
        AST l = node.getChild(0);
		AST r = node.getChild(1);

		visit(l);
		visit(r);

        int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'lesser than' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp slt i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp olt float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm slt */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
    }

    @Override
    protected Integer visitLessOrEquals(AST node) {
        AST l = node.getChild(0);
		AST r = node.getChild(1);

		visit(l);
		visit(r);

		int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'less or equals' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp sle i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp ole float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm sle */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
    }

    @Override
    protected Integer visitGreater(AST node) {
        AST l = node.getChild(0);
		AST r = node.getChild(1);

		visit(l);
		visit(r);

		int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'greater than' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp sgt i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    // buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp ogt float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    // buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm sgt */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
    }

    @Override
    protected Integer visitGreaterOrEquals(AST node) {
        AST l = node.getChild(0);
		AST r = node.getChild(1);

		visit(l);
		visit(r);

		int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'greater or equals' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp sge i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp oge float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm sge */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
    }

    @Override
	protected Integer visitNotEquals(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);

		visit(l);
		visit(r);

        int varIdxl = l.intData;
        int varIdxr = r.intData;

		// Emits the 'not equals operation' for the corresponding type
		switch (l.type) {
			case INT_TYPE:	
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = icmp ne i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case FLOAT32_TYPE:		
            	    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
                    buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
                    buffer+= "  %" + reg++ + " = fcmp une float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case BOOL_TYPE:
            	    buffer+= "  %" + reg++ + " = load i8, i8* %" + (varIdxl + 2) + ", align 1\n";
                    buffer+= "  %" + reg++ + " = trunc i8 %"+ (reg - 2) + " to i1\n"; 
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i32\n";
                    buffer+= "  %" + reg++ + " = load i8, i8* %" + (varIdxr + 2) + ", align 1\n";
                    buffer+= "  %" + reg++ + " = trunc i8 %"+ (reg - 2) + " to i1\n"; 
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i32\n";
                    buffer+= "  %" + reg++ + " = icmp ne i32 %"+ (reg - 5) + ", %"+ (reg - 2)+"\n";
                    buffer+= "  %" + reg++ + " = zext i1 %"+ (reg - 2) + " to i8\n";
                    buffer_var_bool_flag = 2;
                break;
			case STRING_TYPE:		/*codigo llvm ne */	break;
			default:
				System.err.printf("Invalid type: %s!\n", l.type.toString());
				System.exit(1);
		}

	    return null;
	}

	/*------------------------------------------------------------------------------*
	 *	Arithmetic operations
	 *------------------------------------------------------------------------------*/

    @Override
    protected Integer visitStar(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		// Emits the 'multiply' for the corresponding type
        if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = fmul float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_float_flag = 2;
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = mul nsw i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_int_flag = 2;
	    }
        return null;
    }

    @Override
    protected Integer visitDiv(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		// Emits the 'div' for the corresponding type
        if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = fdiv float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_float_flag = 2;
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = sdiv i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_int_flag = 2;
	    }
        return null;
    }

    @Override
    protected Integer visitMod(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		
        buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
        buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
        buffer+= "  %" + reg++ + " = mul nsw i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
        buffer_var_int_flag = 2;
	    
	    
        return null;
    }

    @Override
    protected Integer visitPlus(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		// Emits the 'sum' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = fadd float %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_float_flag = 2;
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = add nsw i32 %"+ (reg - 3) + ", %"+ (reg - 2)+"\n";
            buffer_var_int_flag = 2;
	    }
        return null;
    }

    @Override
    protected Integer visitMinus(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		// Emits the 'sum' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = fsub float %"+ (reg - 2) + ", %"+ (reg - 3)+"\n";
            buffer_var_float_flag = 2;
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = sub nsw i32 %"+ (reg - 2) + ", %"+ (reg - 3)+"\n";
            buffer_var_int_flag = 2;
	    }
        return null;

    }

    

    @Override
    protected Integer visitStatementSection(AST node) {
        for (AST child : node.getChildren()) {
			visit(child);
		}
		return null; 
    }

    @Override
    protected Integer visitReturn(AST node) {
        // Checks if there is an expression node child
		if (node.getChildren().size() == 1) {
			// Visits the expression
			visit(node.getChild(0));
		}

		return null; 
    }

    @Override
    protected Integer visitVarDecl(AST node) {
        // Checks if the variable was assigned a value at declaration
		if(node.getChildren().size() > 0) {
			// Visits the expression to push its value to the stack
			visit(node.getChild(0));
	
			// Get the var index and type 
			int varIdx = node.intData;
			Type varType = vt.getType(varIdx);
	
			
			if (varType == Type.FLOAT32_TYPE) {
                buffer += "  %" + reg++ + " = alloca float, align 4\n";
                if (buffer_var_float != 0) //Verifica se a declaração já possui o valor
                    buffer += "  store float " + floatToLLVM(buffer_var_float) + ", float* %" + (varIdx +2) + ", align 4\n";
			} 
            else if (varType == Type.BOOL_TYPE){
                buffer += "  %" + reg++ + " = alloca i8, align 1\n";
                if (buffer_var_bool != 2) //Verifica se a declaração já possui o valor, nesse caso 2, porque bool é 0 ou 1
                    buffer += "  store i8 " + buffer_var_bool + ", i8* %" + (varIdx +2) + ", align 1\n";
			}
             else {
                buffer += "  %" + reg++ + " = alloca i32, align 4\n";
                if (buffer_var_int != 0) //Verifica se a declaração já possui o valor
                    buffer += "  store i32 " + buffer_var_int + ", i32* %" + (varIdx +2) + ", align 4\n";
			}
		}

		return null;
    }

    @Override
    protected Integer visitAssign(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

	    if (varType == Type.FLOAT32_TYPE) {
            if(buffer_var_float_flag == 2){
	            buffer += "  store float %" + (reg - 1) + ", float* %" + (varIdx +2) + ", align 4\n";
                buffer_var_float_flag = 1;    
            }
            else{
                buffer += "  store float " + floatToLLVM(buffer_var_float) + ", float* %" + (varIdx +2) + ", align 4\n";
            }
	    } 
        else if (varType == Type.BOOL_TYPE){
            if(buffer_var_bool_flag == 2){
	            buffer += "  store i8 %" + (reg - 1) + ", i8* %" + (varIdx +2) + ", align 1\n";
                buffer_var_bool_flag = 1;    
            }
            else{
                buffer += "  store i8 " + buffer_var_bool + ", i8* %" + (varIdx +2) + ", align 1\n";
            }
        }
        else {
            if(buffer_var_int_flag == 2){
                buffer += "  store i32 %" + (reg - 1) + ", i32* %" + (varIdx +2) + ", align 4\n";
                buffer_var_int_flag = 1;
            }   
            else{
                buffer += "  store i32 " + buffer_var_int + ", i32* %" + (varIdx +2) + ", align 4\n";
            }
	    }

        return null;
    }

    @Override
    protected Integer visitPlusAssign(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));
        System.out.println("\nvisitPlusAssign\n");
		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		if (varType == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdx + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = fadd float %"+ (reg - 2) + ", "+ floatToLLVM(buffer_var_float)+"\n";
            buffer+= "  store float %" + (reg - 1) + ", float* %" + (varIdx + 2) + ", align 4\n";
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdx + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = add nsw i32 %"+ (reg - 2) + ", "+ (buffer_var_int)+"\n";
            buffer+= "  store i32 %" + (reg - 1) + ", i32* %" + (varIdx + 2) + ", align 4\n";
	    }

		return null;
    }

    @Override
    protected Integer visitMinusAssign(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

        int varIdxl = lNode.intData;
        int varIdxr = rNode.intData;

		// Emits the 'sub' for the corresponding type
	     if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxr + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load float, float* %" + (varIdxl + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = fsub float %"+ (reg - 2) + ", %"+ (reg - 3)+"\n";
            buffer += "  store float %" + (reg - 1) + ", float* %" + (varIdxl +2) + ", align 4\n";
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = sub nsw i32 %"+ (reg - 2) + ", %"+ (reg - 3)+"\n";
            buffer += "  store i32 %" + (reg - 1) + ", i32* %" + (varIdxl +2) + ", align 4\n";
	    }
        return null;

    }

    @Override
    protected Integer visitPlusPlus(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));
		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		if (varType == Type.FLOAT32_TYPE) {
			/* */
	    } else {
			/* */
	    }
        return null;
    }

    @Override
    protected Integer visitMinusMinus(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		if (varType == Type.FLOAT32_TYPE) {
			/* */
	    } else {
			/* */
	    }
        return null;
    }

    @Override
    protected Integer visitIf(AST node) {
        // Visits the condition node
		visit(node.getChild(0));

		// Checks if there is an ELSE
		if (node.getChildren().size() == 3) {
            
            buffer += "  br i1 %" + (reg - 1) + ", label %" + reg++ +", label %"+reg++ +"\n";
            buffer += (reg-2)+":                                                         \n";
            visit(node.getChild(1));
            buffer += "  br label %"+ reg +"\n";
            buffer += (reg-1)+":                                                         \n";
			visit(node.getChild(2));
            buffer += "  br label %"+ reg +"\n";
            buffer += reg+":                                                         \n";

		} else {
            buffer += "  br i1 %" + (reg - 1) + ", label %" + reg++ +", label %"+reg++ +"\n";
            buffer += (reg-2)+":                                                         \n";
            visit(node.getChild(1));
            buffer += (reg-2)+":                                                         \n";           
		}
       

        return null;
    }

    @Override
    protected Integer visitElse(AST node) {
        // Visits the statement section
		visit(node.getChild(0));

		return null;
    }

    @Override
    protected Integer visitWhile(AST node) {
        int size = node.getChildren().size();

		// Has both the condition and statement section
		if (size == 2) {

            buffer += "  br label %" + reg++ + "\n";
            buffer += (reg-1)+":                                                         \n";
			// Visits the condition and get the result resgister
			visit(node.getChild(0));
            buffer += "  br label %"+ (reg + 2) +"\n";

            buffer += (reg - 1)+":                                                        \n";
			visit(node.getChild(1)); // Emit code for body.
            buffer += "  br label %"+ (reg - 1) +"\n";

			buffer += (reg - 1)+":                                                        \n";

		}

		// Doenst have a condition to be evaluated
		if(size == 1) {
			// Visits the statement section
			visit(node.getChild(0));

			// Emits a 'jump' operation back to the start of while block
		}

		return null; 
    }

    @Override
    protected Integer visitFor(AST node) {
        buffer += "  br label %" + reg++ + "\n";
        buffer += (reg-1)+":                                                         \n";
        // Visits the condition and get the result resgister
        visit(node.getChild(0));
        buffer += "  br label %"+ reg +"\n";

        buffer += (reg - 1)+":                                                        \n";
        visit(node.getChild(1)); // Emit code for body.

        buffer += (reg - 1)+":                                                        \n";

		

        return null;
    }

    @Override
    protected Integer visitFuncCall(AST node) {
        return null;
    }

	/*------------------------------------------------------------------------------*
	 *	Functions
	 *------------------------------------------------------------------------------*/

    @Override
    protected Integer visitFuncMain(AST node) {
        visit(node.getChild(0));

		return null; 
    }

    @Override
    protected Integer visitFuncDecl(AST node) {
        return null;
    }

    @Override
    protected Integer visitFuncArgs(AST node) {
        // int funcIdx = node.intData;
		// String name = ft.getName(funcIdx);

		// FunctionRef ref = findFuncRef(name);
		// AST expressionList = node.getChild(0);

		// if (ref.args != null) {
		// 	// Assigns every value from the expression list to its corresponding arg
		// 	for(int i = 0; i < ref.args.getChildren().size(); i++) {
		// 		// Visits the expression and pushes its value to the stack
		// 		visit(expressionList.getChild(i));
	
		// 		AST arg = ref.args.getChild(i);
	
		// 		execAssign(arg.type, arg.intData, "=");
		// 	}
		// }

		// visit(ref.statementSection);
		
        return null;
    }

    /*------------------------------------------------------------------------------*
	 *	Others
	 *------------------------------------------------------------------------------*/

    @Override
    protected Integer visitExpressionList(AST node) {
        return null;
    }

    @Override
    protected Integer visitProgram(AST node) {
        // Visits the function list node
		visit(node.getChild(0));

		// End of program, no need to read from stdin anymore
		return null; 
    }

    @Override
    protected Integer visitFuncList(AST node) {
        for (AST child : node.getChildren()) {
			visit(child);
		}
		return null; 
    }

    @Override
    protected Integer visitVarUse(AST node) {
        int varIdx = node.intData;
		if (node.type == Type.FLOAT32_TYPE) {
			/** code llvm*/
		} else {
			/** code llvm*/
		}
		return null; 
    }
    
    // Helper class to save the statement section and arg list from each function
	private class FunctionRef {
		String name;
		AST args;
		AST statementSection;

		public FunctionRef(String name, AST args, AST stmt) {
			this.name = name;
			this.args = args;
			this.statementSection = stmt;
		}
	}

    /*------------------------------------------------------------------------------*
	 *	Aux Functions
	 *------------------------------------------------------------------------------*/
    public String floatToLLVM(float f) {
		return "0x" + toHexString(Double.doubleToRawLongBits((double) f));
	}
	
	public String doubleToLLVM(double d) {
		return "0x" + toHexString(Double.doubleToRawLongBits(d));
	}
	
	private String toHexString(long l) {
		int count = (l == 0L) ? 1 : ((64 - Long.numberOfLeadingZeros(l)) + 3) / 4;
		StringBuilder buffer = new StringBuilder(count);
		long k = l;
		do {
			long t = k & 15L;
			if (t > 9) {
				t = t - 10 + 'A';
			} else {
				t += '0';
			}
			count -= 1;
			buffer.insert(0, (char) t);
			k = k >> 4;
		} while (count > 0);
		return buffer.toString();
	}	    

}