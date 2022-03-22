package code;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

public class LLVMGenerator extends ASTBaseVisitor<Integer>{

    static int reg = 2;

    private static String header_text = "";
    private static String main_text = "";
    private static String buffer = "";
    private static int buffer_var_int =0;
    private static int buffer_var_int_flag =1;
    private static float buffer_var_float = 0;
    private static int str_i = 0;
    private static int main_reg = 2;
    private static int br = 0;
    private final StrTable st;
	private final VarTable vt;
    // private final Scanner in;

    static Stack<Integer> br_stack = new Stack<>();


    public LLVMGenerator(StrTable st, VarTable vt) {
		this.st = st;
		this.vt = vt;
	}

    @Override 
    public void execute(AST root) {
        visit(root);
        main_text += buffer;
        formatMainText();
        String text = "";
        // text += "declare i32 @printf(i8*, ...)\n";
        // text += "declare i32 @scanf(i8*, ...)\n";
        // text += "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";
        // text += "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";
        // text += "@strps = constant [4 x i8] c\"%s\\0A\\00\"\n";
        // text += "@strsi = constant [3 x i8] c\"%d\\00\"\n";
        // text += "@strsd = constant [4 x i8] c\"%lf\\00\"\n";
        text += "\n";
        text += header_text;
        // text += "define i32 @main() nounwind {\n";
        text += "define dso_local i32 @main() #0{\n  %1 = alloca i32, align 4\n  store i32 0, i32* %1, align 4\n";
        text += buffer;
        text += "  ret i32 0\n";
        text += "}\n";
        
        System.out.println(text);
    }


    private static void formatMainText() {
        String[] lines = main_text.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append("  ").append(line).append("\n");
        }
        main_text = sb.toString();
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
	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load float, float* %2, align 4
            %7 = fadd float %5, %6
            store float %7, float* %3, align 4
            */
	    } else {
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxl + 2) + ", align 4\n"; 
            buffer+= "  %" + reg++ + " = load i32, i32* %" + (varIdxr + 2) + ", align 4\n";
            buffer+= "  %" + reg++ + " = add nsw i32 %"+ (varIdxl + 2) + ", %"+ (varIdxr + 2)+"\n";
            buffer_var_int_flag = 2;
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
                //buffer += "  store i32 0, i32* %1, align 4\n"; // TODO: Ver como vai colocar isso de verdade

                BigDecimal float_value = new BigDecimal(Float.toString(buffer_var_float));

                if (buffer_var_float != 0){ //Verifica se a declaração já possui o valor
                    buffer += "  store float " + float_value + ", float* %" + (varIdx +2) + ", align 4\n";
                }
                

			} else {
                buffer += "  %" + reg++ + " = alloca i32, align 4\n";
                //buffer += "  store i32 0, i32* %1, align 4\n"; // TODO: Ver como vai colocar isso de verdade
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
	        buffer += "  store float " + buffer_var_float + ", float* %" + (varIdx +2) + ", align 4\n";
	    } else {
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
    protected Integer visitBoolVal(AST node) {
        // buffer += node.intData;
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
                // buffer+= "%" + reg++ +" = alloca bool, align 4";
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
				case INT_TYPE:  	/*buffer+=x;*/		break;
				case FLOAT32_TYPE: 	/*buffer+=x;*/		break;
				case BOOL_TYPE: 	/*buffer+=x;*/   	break;
				case STRING_TYPE:  	/*buffer+=x;*/		break;
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

		// Emits the 'equals operation' for the corresponding type
		switch (l.type) {
			case INT_TYPE:			/*codigo llvm icmp */	break;
			case FLOAT32_TYPE:		/*codigo llvm icmp */	break;
			case BOOL_TYPE:			/*codigo llvm icmp */	break;
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

		switch (l.type) {
			case INT_TYPE:			/*codigo llvm slt */	break;
			case FLOAT32_TYPE:		/*codigo llvm slt */	break;
			case BOOL_TYPE:			/*codigo llvm slt */	break;
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

		switch (l.type) {
			case INT_TYPE:			/*codigo llvm sle */	break;
			case FLOAT32_TYPE:		/*codigo llvm sle */	break;
			case BOOL_TYPE:			/*codigo llvm sle */	break;
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

		switch (l.type) {
			case INT_TYPE:			/*codigo llvm sgt */	break;
			case FLOAT32_TYPE:		/*codigo llvm sgt */	break;
			case BOOL_TYPE:			/*codigo llvm sgt */	break;
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

		switch (l.type) {
			case INT_TYPE:			/*codigo llvm sge */	break;
			case FLOAT32_TYPE:		/*codigo llvm sge */	break;
			case BOOL_TYPE:			/*codigo llvm sge */	break;
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

		switch (l.type) {
			case INT_TYPE:			/*codigo llvm ne */	break;
			case FLOAT32_TYPE:		/*codigo llvm ne */	break;
			case BOOL_TYPE:			/*codigo llvm ne */	break;
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

		// Emits the 'multiply' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {

            buffer+= "%" + reg++ +"= load float, float* %" + (reg - 3) +", align 4\n";
			buffer+= "%" + reg++ +"= load float, float* %" + (reg - 3) +", align 4\n";
			buffer+= "%" + reg++ +"= fmul float %" + (reg - 3) +", %" + (reg - 2) + "\n";
			
            /* codigo llvm 
            %4 = load float, float* %2, align 4
            %5 = load float, float* %3, align 4
            %6 = fmul float %4, %5
            */
	    } else {

            buffer+= "%" + reg++ +"= load i32, i32* %" + (reg - 3) +", align 4\n";
			buffer+= "%" + reg++ +"= load i32, i32* %" + (reg - 3) +", align 4\n";
			buffer+= "%" + reg++ +" mul nsw i32 %" + (reg - 3) +", %" + (reg - 2) + "\n";

	        /* codigo llvm 
                %4 = load i32, i32* %2, align 4
                %5 = load i32, i32* %3, align 4
                %6 = mul nsw i32 %4, %5
            */
	    }
        return null;
    }

    @Override
    protected Integer visitDiv(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'div' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
            buffer+= "%" + reg++ +"= load float, float* %" + (reg - 2) +", align 4\n";
			buffer+= "%" + reg++ +"= load i32, i32* %" + (reg - 5) +", align 4\n";
            buffer+= "%" + reg++ +"= sitofp i32 %" + (reg - 2) +" to float\n";
			buffer+= "%" + reg++ +"= fdiv float %" + (reg - 4) +", %" + (reg - 2) + "\n";
			buffer+= "store float %" + (reg - 1) +", float %" + (reg - 6) + ", align 4\n";

	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = sitofp i32 %6 to float
            %8 = fdiv float %5, %7
            store float %8, float* %3, align 4
            */
	    } else {
            buffer+= "%" + reg++ +"= load i32, i32* %" + (reg - 2) +", align 4\n";
			buffer+= "%" + reg++ +"= load i32, i32* %" + (reg - 5) +", align 4\n";
			buffer+= "%" + reg++ +"= sdiv i32 %" + (reg - 3) +", %" + (reg - 2) + "\n";
			buffer+= "store i32 %" + (reg - 1) +", i32 %" + (reg - 5) + ", align 4\n";
            
	        /* codigo llvm 
            %5 = load i32, i32* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = sdiv i32 %5, %6
            store i32 %7, i32* %3, align 4
            */
	    }

        return null;
    }

    @Override
    protected Integer visitMod(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'mod' for the corresponding type

        /* codigo llvm 
        %5 = load i32, i32* %4, align 4
        %6 = load i32, i32* %2, align 4
        %7 = srem i32 %5, %6
        store i32 %7, i32* %3, align 4
        */
	    
        return null;
    }

   

    @Override
    protected Integer visitMinus(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'minus' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load float, float* %2, align 4
            %7 = fsub float %5, %6
            store float %7, float* %3, align 4
            */
	    } else {
	        /* codigo llvm 
            %5 = load i32, i32* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = sub nsw i32 %5, %6
            store i32 %7, i32* %3, align 4
            */
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
    protected Integer visitPlusAssign(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		if (varType == Type.FLOAT32_TYPE) {
			/*
            %1 = alloca i32, align 4
            %2 = alloca float, align 4
            store i32 0, i32* %1, align 4
            store float 1.000000e+00, float* %2, align 4
            %3 = load float, float* %2, align 4
            %4 = fadd float %3, 2.000000e+00
            store float %4, float* %2, align 4
            ret i32 0
            */
	    } else {
			/*
            %1 = alloca i32, align 4
            %2 = alloca i32, align 4
            store i32 0, i32* %1, align 4
            store i32 1, i32* %2, align 4
            %3 = load i32, i32* %2, align 4
            %4 = add nsw i32 %3, 2
            store i32 %4, i32* %2, align 4
            ret i32 0
            */
	    }

		return null;
    }

    @Override
    protected Integer visitMinusAssign(AST node) {
        // Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		if (varType == Type.FLOAT32_TYPE) {
			/*
            %1 = alloca i32, align 4
            %2 = alloca float, align 4
            store i32 0, i32* %1, align 4
            store float 1.000000e+00, float* %2, align 4
            %3 = load float, float* %2, align 4
            %4 = fsub float %3, 2.000000e+00
            store float %4, float* %2, align 4
            ret i32 0
            */
	    } else {
			/*
            %1 = alloca i32, align 4
            %2 = alloca i32, align 4
            store i32 0, i32* %1, align 4
            store i32 1, i32* %2, align 4
            %3 = load i32, i32* %2, align 4
            %4 = sub nsw i32 %3, 2
            store i32 %4, i32* %2, align 4
            ret i32 0
            */
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
			/*
            %1 = alloca i32, align 4
            %2 = alloca float, align 4
            store i32 0, i32* %1, align 4
            store float 1.000000e+00, float* %2, align 4
            %3 = load float, float* %2, align 4
            %4 = fadd float %3, 1.000000e+00
            store float %4, float* %2, align 4
            ret i32 0
            */
	    } else {
			/*
            %1 = alloca i32, align 4
            %2 = alloca i32, align 4
            store i32 0, i32* %1, align 4
            store i32 1, i32* %2, align 4
            %3 = load i32, i32* %2, align 4
            %4 = add nsw i32 %3, 1
            store i32 %4, i32* %2, align 4
            ret i32 0
            */
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
			/*
            %1 = alloca i32, align 4
            %2 = alloca float, align 4
            store i32 0, i32* %1, align 4
            store float 1.000000e+00, float* %2, align 4
            %3 = load float, float* %2, align 4
            %4 = fadd float %3, -1.000000e+00
            store float %4, float* %2, align 4
            ret i32 0
            */
	    } else {
			/*
            %1 = alloca i32, align 4
            %2 = alloca i32, align 4
            store i32 0, i32* %1, align 4
            store i32 1, i32* %2, align 4
            %3 = load i32, i32* %2, align 4
            %4 = add nsw i32 %3, -1
            store i32 %4, i32* %2, align 4
            ret i32 0
            */
	    }
        return null;
    }

    @Override
    protected Integer visitIf(AST node) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Integer visitFor(AST node) {
        // TODO Auto-generated method stub
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
    
}