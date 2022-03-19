package code;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

import static code.OpCode.*;
import static code.Instruction.INSTR_MEM_SIZE;

public final class CodeGen extends ASTBaseVisitor<Integer> {
    private final Instruction code[]; // Code memory
	private final StrTable st;
	private final VarTable vt;

	private static int nextInstr;
	private static int intRegsCount;
	private static int floatRegsCount;
	
	public CodeGen(StrTable st, VarTable vt) {
		this.code = new Instruction[INSTR_MEM_SIZE];
		this.st = st;
		this.vt = vt;
	}
	
	@Override
	public void execute(AST root) {
		nextInstr = 0;
		intRegsCount = 0;
		floatRegsCount = 0;
	    dumpStrTable();
	    visit(root);
	    dumpProgram();
	}

    /*------------------------------------------------------------------------------*
	 *	Prints
	 *------------------------------------------------------------------------------*/

	void dumpProgram() {
	    for (int addr = 0; addr < nextInstr; addr++) {
	    	System.out.printf("--%s\n", code[addr].toString());
	    }
	}

	void dumpStrTable() {
	    for (int i = 0; i < st.size(); i++) {
	        System.out.printf("SSTR %s\n", st.get(i));
	    }
	}

	@Override
	protected Integer visitBoolVal(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitIntVal(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitFloatVal(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitStringVal(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitInput(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitOutput(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitEquals(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitNotEquals(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitLess(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitLessOrEquals(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitGreater(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitGreaterOrEquals(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitStar(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitDiv(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitMod(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitPlus(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitMinus(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitStatementSection(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitReturn(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitVarDecl(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitAssign(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitPlusAssign(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitMinusAssign(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitPlusPlus(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitMinusMinus(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitIf(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitElse(AST node) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitFuncMain(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitFuncDecl(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitFuncArgs(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitExpressionList(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitProgram(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitFuncList(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer visitVarUse(AST node) {
		// TODO Auto-generated method stub
		return null;
	}

}
