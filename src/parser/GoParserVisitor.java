// Generated from src/GoParser.g4 by ANTLR 4.9.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GoParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GoParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GoParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#func_section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_section(GoParser.Func_sectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#import_section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_section(GoParser.Import_sectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#package_import}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackage_import(GoParser.Package_importContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#func_main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_main(GoParser.Func_mainContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#func_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_declaration(GoParser.Func_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#var_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_declaration(GoParser.Var_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#declare_assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclare_assign(GoParser.Declare_assignContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#array_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_declaration(GoParser.Array_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#array_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_init(GoParser.Array_initContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(GoParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#output}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput(GoParser.OutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#func_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_args(GoParser.Func_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call(GoParser.Func_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#statement_section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_section(GoParser.Statement_sectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(GoParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GoParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(GoParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link GoParser#for_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(GoParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code for}
	 * labeled alternative in {@link GoParser#for_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(GoParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpression}
	 * labeled alternative in {@link GoParser#assign_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpression(GoParser.AssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignPPMM}
	 * labeled alternative in {@link GoParser#assign_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignPPMM(GoParser.AssignPPMMContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#switch_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitch_statement(GoParser.Switch_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#case_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_statement(GoParser.Case_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#default_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefault_statement(GoParser.Default_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(GoParser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionId}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionId(GoParser.ExpressionIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intVal}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntVal(GoParser.IntValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusMinus}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinus(GoParser.PlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code starDivMod}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStarDivMod(GoParser.StarDivModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionFuncCall}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionFuncCall(GoParser.ExpressionFuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionParen}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionParen(GoParser.ExpressionParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringVal}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringVal(GoParser.StringValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatVal}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatVal(GoParser.FloatValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolVal}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolVal(GoParser.BoolValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationalOperators}
	 * labeled alternative in {@link GoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOperators(GoParser.RelationalOperatorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(GoParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link GoParser#var_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(GoParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringType}
	 * labeled alternative in {@link GoParser#var_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(GoParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link GoParser#var_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(GoParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code float32Type}
	 * labeled alternative in {@link GoParser#var_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat32Type(GoParser.Float32TypeContext ctx);
}