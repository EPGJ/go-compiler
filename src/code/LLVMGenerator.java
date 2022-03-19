package code;

import java.util.Scanner;

import java.util.HashSet;
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
    private final Scanner in;

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
                buffer+= "%" + reg++ +" = alloca i32, align 4";
				break;
			case STRING_TYPE:
                buffer+= "%" + reg++ +" = alloca i32, align 4";
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitEquals(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitNotEquals(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitLess(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitLessOrEquals(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitGreater(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitGreaterOrEquals(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitStar(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitDiv(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitMod(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitPlus(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitMinus(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitStatementSection(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitReturn(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitVarDecl(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitAssign(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitPlusAssign(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitMinusAssign(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitPlusPlus(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitMinusMinus(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitIf(AST node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Void visitElse(AST node) {
        // TODO Auto-generated method stub
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
