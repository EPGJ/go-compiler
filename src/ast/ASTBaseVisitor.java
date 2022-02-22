package ast;

public abstract class ASTBaseVisitor<T> {

	public void execute(AST root) {
		visit(root);
	}

	protected T visit(AST node) {
		switch(node.kind) {
			case BOOL_VAL_NODE: 			return visitBoolVal(node);
			case INT_VAL_NODE: 				return visitIntVal(node);
			case FLOAT32_VAL_NODE: 			return visitFloatVal(node);
			case STRING_VAL_NODE: 			return visitStringVal(node);

			case INPUT_NODE: 				return visitInput(node);
			case OUTPUT_NODE: 				return visitOutput(node);

			case EQUALS_NODE: 				return visitEquals(node);
			case NOT_EQUALS_NODE: 			return visitNotEquals(node);
			case LESS_NODE: 				return visitLess(node);
			case LESS_OR_EQUALS_NODE: 		return visitLessOrEquals(node);
			case GREATER_NODE: 				return visitGreater(node);
			case GREATER_OR_EQUALS_NODE: 	return visitGreaterOrEquals(node);

			case STAR_NODE: 				return visitStar(node);
			case DIV_NODE: 					return visitDiv(node);
			case MOD_NODE: 					return visitMod(node);
			case PLUS_NODE: 				return visitPlus(node);
			case MINUS_NODE: 				return visitMinus(node);

			case STATEMENT_SECTION_NODE: 	return visitStatementSection(node);
			case RETURN_NODE: 				return visitReturn(node);
			case VAR_DECL_NODE: 			return visitVarDecl(node);
			case DECLARE_ASSIGN_NODE: 		return visitDeclareAssign(node);
			case ASSIGN_NODE: 				return visitAssign(node);
			case PLUS_ASSIGN_NODE: 			return visitPlusAssign(node);
			case MINUS_ASSIGN_NODE: 		return visitMinusAssign(node);
			case PLUS_PLUS_NODE: 			return visitPlusPlus(node);
			case MINUS_MINUS_NODE: 			return visitMinusMinus(node);
			case IF_NODE: 					return visitIf(node);
			case ELSE_NODE: 				return visitElse(node);
			case WHILE_NODE: 				return visitWhile(node);
			case FOR_NODE: 					return visitFor(node);
			case SWITCH_NODE: 				return visitSwitch(node);
			case CASE_NODE: 				return visitCase(node);
			case DEFAULT_NODE: 				return visitDefault(node);
			case FUNC_CALL_NODE: 			return visitFuncCall(node);
 
			case FUNC_MAIN_NODE: 			return visitFuncMain(node);
			case FUNC_DECL_NODE: 			return visitFuncDecl(node);
			case FUNC_ARGS_NODE: 			return visitFuncArgs(node);
			case EXPRESSION_LIST_NODE: 		return visitExpressionList(node);

			case PROGRAM_NODE: 				return visitProgram(node);
			case FUNC_LIST_NODE: 			return visitFuncList(node);
			case VAR_USE_NODE:				return visitVarUse(node);

	        default:
	            System.err.printf("Invalid kind: %s!\n", node.kind.toString());
	            System.exit(1);
	            return null;
		}
	}
	
	protected abstract T visitBoolVal(AST node);
	protected abstract T visitIntVal(AST node);
	protected abstract T visitFloatVal(AST node);
	protected abstract T visitStringVal(AST node);

	protected abstract T visitInput(AST node);
	protected abstract T visitOutput(AST node);

	protected abstract T visitEquals(AST node);
	protected abstract T visitNotEquals(AST node);
	protected abstract T visitLess(AST node);
	protected abstract T visitLessOrEquals(AST node);
	protected abstract T visitGreater(AST node);
	protected abstract T visitGreaterOrEquals(AST node);

	protected abstract T visitStar(AST node);
	protected abstract T visitDiv(AST node);
	protected abstract T visitMod(AST node);
	protected abstract T visitPlus(AST node);
	protected abstract T visitMinus(AST node);

	protected abstract T visitStatementSection(AST node);
	protected abstract T visitReturn(AST node);
	protected abstract T visitVarDecl(AST node);
	protected abstract T visitDeclareAssign(AST node);
	protected abstract T visitAssign(AST node);
	protected abstract T visitPlusAssign(AST node);
	protected abstract T visitMinusAssign(AST node);
	protected abstract T visitPlusPlus(AST node);
	protected abstract T visitMinusMinus(AST node);
	protected abstract T visitIf(AST node);
	protected abstract T visitElse(AST node);
	protected abstract T visitWhile(AST node);
	protected abstract T visitFor(AST node);
	protected abstract T visitSwitch(AST node);
	protected abstract T visitCase(AST node);
	protected abstract T visitDefault(AST node);
	protected abstract T visitFuncCall(AST node);
	
	protected abstract T visitFuncMain(AST node);
	protected abstract T visitFuncDecl(AST node);
	protected abstract T visitFuncArgs(AST node);
	protected abstract T visitExpressionList(AST node);

	protected abstract T visitProgram(AST node);
	protected abstract T visitFuncList(AST node);
	protected abstract T visitVarUse(AST node);

}
