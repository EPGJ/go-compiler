package code;

import java.util.Scanner;
import java.util.Stack;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

public class LLVMGenerator extends ASTBaseVisitor<Void>{

    static int reg = 1;

    private static String header_text = "";
    private static String main_text = "";
    private static String buffer = "";
    private static int str_i = 0;
    private static int main_reg = 1;
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
        main_text += buffer;
        formatMainText();
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @scanf(i8*, ...)\n";
        text += "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";
        text += "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";
        text += "@strps = constant [4 x i8] c\"%s\\0A\\00\"\n";
        text += "@strsi = constant [3 x i8] c\"%d\\00\"\n";
        text += "@strsd = constant [4 x i8] c\"%lf\\00\"\n";
        text += "\n";
        text += header_text;
        text += "define i32 @main() nounwind {\n";
        text += main_text;
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
    protected Void visitBoolVal(AST node) {
        buffer += node.intData;
        return null;
    }

    @Override
    protected Void visitIntVal(AST node) {
        buffer += node.intData;
        return null;
    }

    @Override
    protected Void visitFloatVal(AST node) {
        buffer += node.floatData;
        return null;
    }

    @Override
    protected Void visitStringVal(AST node) {
        buffer += node.intData;
        return null;
    }

    /*------------------------------------------------------------------------------*
	 *	Input
	 *------------------------------------------------------------------------------*/


    @Override
    protected Void visitInput(AST node) {
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
    protected Void visitOutput(AST node) {
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
    protected Void visitEquals(AST node) {
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
    protected Void visitLess(AST node) {
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
    protected Void visitLessOrEquals(AST node) {
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
    protected Void visitGreater(AST node) {
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
    protected Void visitGreaterOrEquals(AST node) {
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
	protected Void visitNotEquals(AST node) {
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
    protected Void visitStar(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'multiply' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = sitofp i32 %6 to float
            %8 = fmul float %5, %7
            store float %8, float* %3, align 4 
            */
	    } else {
	        /* codigo llvm 
            %5 = load i32, i32* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = mul nsw i32 %5, %6
            store i32 %7, i32* %3, align 4
            */
	    }

        return null;
    }

    @Override
    protected Void visitDiv(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'div' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = sitofp i32 %6 to float
            %8 = fdiv float %5, %7
            store float %8, float* %3, align 4
            */
	    } else {
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
    protected Void visitMod(AST node) {
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
    protected Void visitPlus(AST node) {
        AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		// Emits the 'sum' for the corresponding type
	    if (node.type == Type.FLOAT32_TYPE) {
	        /* codigo llvm 
            %5 = load float, float* %4, align 4
            %6 = load float, float* %2, align 4
            %7 = fadd float %5, %6
            store float %7, float* %3, align 4
            */
	    } else {
	        /* codigo llvm 
            %5 = load i32, i32* %4, align 4
            %6 = load i32, i32* %2, align 4
            %7 = add nsw i32 %5, %6
            store i32 %7, i32* %3, align 4
            */
	    }
        return null;
    }

    @Override
    protected Void visitMinus(AST node) {
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
    protected Void visitStatementSection(AST node) {
        for (AST child : node.getChildren()) {
			visit(child);
		}
		return null; 
    }

    @Override
    protected Void visitReturn(AST node) {
        // Checks if there is an expression node child
		if (node.getChildren().size() == 1) {
			// Visits the expression
			visit(node.getChild(0));
		}

		return null; 
    }

    @Override
    protected Void visitVarDecl(AST node) {
        // Checks if the variable was assigned a value at declaration
		if(node.getChildren().size() > 0) {
			// Visits the expression to push its value to the stack
			visit(node.getChild(0));
	
			// Get the var index and type 
			int varIdx = node.intData;
			Type varType = vt.getType(varIdx);
	
			// Emits the 'store word' for the corresponding type
			if (varType == Type.FLOAT32_TYPE) {
				/*  llvm code
                %1 = alloca i32, align 4
                %2 = alloca float, align 4
                store i32 0, i32* %1, align 4
                ret i32 0
                */
			} else {
				/* llvm code
                %1 = alloca i32, align 4
                %2 = alloca i32, align 4
                store i32 0, i32* %1, align 4
                ret i32 0
                */
			}
		}

		return null;
    }

    @Override
    protected Void visitAssign(AST node) {
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
            ret i32 0
           */
	    } else {
	        /*
            %1 = alloca i32, align 4
            %2 = alloca i32, align 4
            store i32 0, i32* %1, align 4
            store i32 1, i32* %2, align 4
            ret i32 0
            */
	    }

        return null;
    }

    @Override
    protected Void visitPlusAssign(AST node) {
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
    protected Void visitMinusAssign(AST node) {
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
    protected Void visitPlusPlus(AST node) {
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
    protected Void visitMinusMinus(AST node) {
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
    protected Void visitIf(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitElse(AST node) {
        // Visits the statement section
		visit(node.getChild(0));

		return null;
    }

    @Override
    protected Void visitWhile(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFor(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFuncCall(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFuncMain(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFuncDecl(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFuncArgs(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitExpressionList(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitProgram(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitFuncList(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitVarUse(AST node) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
