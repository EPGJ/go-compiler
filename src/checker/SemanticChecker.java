package checker;

import org.antlr.v4.runtime.Token;

import ast.AST;
import ast.NodeKind;
import parser.GoParser;
import parser.GoParserBaseVisitor;
import tables.FuncTable;
import tables.StrTable;
import tables.VarTable;
import typing.Type;


public class SemanticChecker extends GoParserBaseVisitor<AST> {

	private StrTable st = new StrTable(); // Tabela de strings.
	private VarTable vt = new VarTable(); // Tabela de variáveis.
	private FuncTable ft = new FuncTable(); // Table de funcoes.

	Type lastDeclType; // Variável "global" com o último tipo declarado.
	Type lastDeclFuncType; // Global variable with the last declared func type
	int lastDeclFuncArgsSize; // Global variable with the last declared argsSize
	int lastDeclArrayArgsSize;
	int lastExpressionListSize;
	String lastDeclFuncName;

	AST root;

	/*
	 *
	 * CRIAÇÃO E VERIFICAÇÃO DE VARIÁVEIS
	 *
	 */

	// Testa se o dado token foi declarado antes.
	AST checkVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		String key = text.concat(lastDeclFuncName);
		boolean isInTable = vt.lookupVar(key);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): variable '%s' was not declared.\n", line, text);
			System.exit(1);
		}
		return new AST(NodeKind.VAR_USE_NODE, vt.getType(key), key);
	}

	// Cria uma nova variável a partir do dado token.
	AST newVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		String key = text.concat(lastDeclFuncName);
		boolean isInTable = vt.lookupVar(key);
		if (!isInTable) {
			System.err.printf(
					"SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n",
					line, text, vt.getLine(key));
					System.exit(1);
		}
		vt.addVar(text, line, lastDeclType, lastDeclFuncName,lastDeclArrayArgsSize);
		return new AST(NodeKind.VAR_DECL_NODE, lastDeclType, key);
	}

	/*
	 *
	 * CRIAÇÃO E VERIFICAÇÃO DE FUNÇÕES
	 *
	 */

	// Testa se a função foi declarada antes.
	AST checkFunc(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = ft.lookupFunc(text);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): function '%s' was not declared.\n", line, text);
			System.exit(1);
		}
		return new AST(NodeKind.FUNC_CALL_NODE, ft.getType(text), text);
	}

	// Cria uma nova função a partir do dado token.
	AST newFunc(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = ft.lookupFunc(text);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): function '%s' already declared at line %d.\n",
					line, text, ft.getLine(text));
					System.exit(1);
		}
		ft.addFunc(text, line, lastDeclFuncType, lastDeclFuncArgsSize);
		return new AST(NodeKind.FUNC_DECL_NODE, lastDeclFuncType, text);
	}

	// Verifica se a quantidade de argumentos da função está correta.
	// Atenção: esta função só pode ser chamada após a chamada da função chekFunc
	void checkFuncCall(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = ft.lookupFunc(token.getText());

		if (!isInTable) {
			int argsSize = ft.getArgsSize(text);

			if (argsSize != lastExpressionListSize) {
				System.err.printf("SEMANTIC ERROR (%d): function '%s' expected %d arguments but received '%d'.\n",
						line, text, argsSize, lastExpressionListSize);
						System.exit(1);
			}
		}
	}

	// Checa se o return tem um tipo compatível com a função
	void checkFuncReturnType(int lineNo, Type t) {
		if (t != lastDeclFuncType) {
			System.err.printf(
					"SEMANTIC ERROR (%d): Return statement type incompatible with function type. Expected '%s' but received '%s'.\n",
					lineNo, lastDeclFuncType, t);
					System.exit(1);
		}
	}

	/*
	 *
	 * Tipos e operações
	 *
	 */

	// Imprime quando os tipos são incompatíveis com a operação.
	private void typeError(int lineNo, String op, Type t1, Type t2) {
		System.out.printf(
				"SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
				lineNo, op, t1.toString(), t2.toString());
				System.exit(1);
	}

	// Verifica se o tipo da variável é numérico.
	private void checkUnaryOp(int lineNo, String op, Type t) {
		if (t != Type.INT_TYPE && t != Type.FLOAT32_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): type '%s' not suported for unary operator '%s'.\n",
					lineNo, t.toString(), op);
					System.exit(1);
		}
	}

	private void checkAssign(int lineNo, String op, Type l, Type r) {
		if (l != r) {
			typeError(lineNo, op, l, r);
			System.exit(1);
		} 
	}

	// Verifica se o tipo é booleano
	private void checkBoolExpr(int lineNo, String cmd, Type t) {
		if (t != Type.BOOL_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): conditional expression in '%s' is '%s' instead of '%s'.\n",
					lineNo, cmd, t.toString(), Type.BOOL_TYPE.toString());
					System.exit(1);
		}
	}

	// Verifica se o tipo é inteiro
	private void checkIndex(int lineNo, Type t) {
		if (t != Type.INT_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): incompatible type '%s' at array index.\n",
					lineNo, t.toString());
					System.exit(1);
		}
	}

	// Verifica se os tipos são iguais
	private void checkCase(int lineNo, Type t1, Type t2) {
		if (t1 != t2) {
			System.out.printf(
					"SEMANTIC ERROR (%d): incompatible types for case, expected '%s' but expression is '%s'.\n",
					lineNo, t1.toString(), t2.toString());
					System.exit(1);
		}
	}

	// Imprime erro de incompatibilidade de tipos na declaração de variáveis
	private void typeInitError(int lineNo, String varName, Type t1, Type t2) {
		System.out.printf(
				"SEMANTIC ERROR (%d): incompatible types when declaring variable '%s', var type is '%s' and expression type is '%s'.\n",
				lineNo, varName, t1.toString(), t2.toString());
				System.exit(1);
	}

	// Verifica atribuiçao de tipos
	private void checkInitAssign(int lineNo, String varName, Type l, Type r) {
		if (l != r ) {
			typeInitError(lineNo, varName, l, r);
			System.exit(1);
		}
	}

	//
	private void checkArrayInit(int lineNo, String varName) {
		if (lastDeclArrayArgsSize != lastExpressionListSize) {
			System.out.printf(
					"SEMANTIC ERROR (%d): Array '%s' declared with size %d but initialized with %d arguments.\n",
					lineNo, varName, lastDeclArrayArgsSize, lastExpressionListSize);
					System.exit(1);
		}
	}


	// Exibe o conteúdo das tabelas em stdout.
	void printTables() {
		System.out.print("\n\n");
		System.out.print(st);
		System.out.print("\n\n");
		System.out.print(vt);
		System.out.print("\n\n");
	}

	@Override
	public AST visitProgram(GoParser.ProgramContext ctx) {
		AST funcSection = visit(ctx.func_section());

		this.root = AST.newSubtree(NodeKind.PROGRAM_NODE, Type.NO_TYPE, funcSection);

		return this.root;
	}


	// Visita a regra var_types: INT
	@Override
	public AST visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		return null;
	}

	// Visita a regra var_types: STRING
	@Override
	public AST visitStringType(GoParser.StringTypeContext ctx) {
		this.lastDeclType = Type.STR_TYPE;
		return null;
	}

	// Visita a regra var_types: BOOL
	@Override
	public AST visitBoolType(GoParser.BoolTypeContext ctx) {
		this.lastDeclType = Type.BOOL_TYPE;
		return null;
	}

	// Visita a regra var_types: FLOAT32
	@Override
	public AST visitFloat32Type(GoParser.Float32TypeContext ctx) {
		this.lastDeclType = Type.FLOAT32_TYPE;
		return null;
	}

	// Visita a regra var_declaration: VAR IDENTIFIER (var_types | var_types? ASSIGN expression | array_declaration) SEMI?
	@Override
	public AST visitVar_declaration(GoParser.Var_declarationContext ctx) {
		// Checa se a variável é um array ou uma variável normal
		if (ctx.array_declaration() != null) {
			// Visita recursivamente array_declaration para definir o tipo do array
			visit(ctx.array_declaration());
		} else {
			
			lastDeclArrayArgsSize = 0;

			if(ctx.var_types() != null) {
				// Define lastDeclType com a declaração explícita do tipo da variável
				visit(ctx.var_types());
			} else {
				// Define lastDeclType baseado no tipo da expressão
				lastDeclType = visit(ctx.expression()).type;
			}
		}

		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Verifica se a variável foi previamente declarada
		AST varDecl = newVar(identifierToken);

		boolean hasAssign = ctx.ASSIGN() != null;

		if (hasAssign) {
			AST identifier = checkVar(identifierToken);
			AST expression = visit(ctx.expression());

			checkInitAssign(identifierToken.getLine(), identifierToken.getText(), identifier.type, expression.type);
			varDecl.addChild(expression);
		}

		return varDecl;
	}

	// Visita a regra declare_assign: IDENTIFIER DECLARE_ASSIGN ( array_init |  expression) SEMI?
	@Override
	public AST visitDeclare_assign(GoParser.Declare_assignContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Define lastDeclType com base no tipo da expressão ou na inicialização do
		// array
		AST expression = null;
		if (ctx.expression() != null){
			expression = visit(ctx.expression());
			lastDeclType = expression.type;
			lastDeclArrayArgsSize = 0;
		}
			
		AST arrayInit = null;
		if (ctx.array_init() != null) {
			arrayInit = visit(ctx.array_init());

			// Verifica se o array foi inicializado com a quantidade correta de indices
			checkArrayInit(identifierToken.getLine(), identifierToken.getText());
		}

		// Verifica se a variável foi declarada previamente.
		AST node = newVar(identifierToken);

		node.addChild(expression);
		node.addChild(arrayInit);

		return node;
	}

	/*
	 *
	 * array_declaration e array_ags
	 *
	 */

	// Visita a regra array_declaration: L_BRACKET DECIMAL_LIT R_BRACKET var_types
	@Override
	public AST visitArray_declaration(GoParser.Array_declarationContext ctx) {
		// Define o tamanho do array.
		lastDeclArrayArgsSize = Integer.parseInt(ctx.DECIMAL_LIT().getText());

		visit(ctx.var_types());

		return null;
	}

	// Visita a regra array_init: array_declaration L_CURLY expression_list? R_CURLY
	@Override
	public AST visitArray_init(GoParser.Array_initContext ctx) {
		// Recursively Visita a regra for error checking
		visit(ctx.array_declaration());

		AST expressionList = null;
		if (ctx.expression_list() != null) {
			// Recursively Visita a regra for error checking
			expressionList = visit(ctx.expression_list());
		}

		return expressionList;
	}

	/*
	 *
	 * input e output
	 *
	 */

	// Visita a regra input: INPUT L_PAREN AMPERSAND id R_PAREN
	@Override
	public AST visitInput(GoParser.InputContext ctx) {
		// Visita a regra recursivamente para verificar erros
		AST identifier = visit(ctx.id());

		return AST.newSubtree(NodeKind.INPUT_NODE, Type.NO_TYPE, identifier);
	}

	// Visita a regra output: OUTPUT L_PAREN expression_list? R_PAREN
	@Override
	public AST visitOutput(GoParser.OutputContext ctx) {
		AST expressionList = visit(ctx.expression_list());

		return AST.newSubtree(NodeKind.OUTPUT_NODE, Type.NO_TYPE, expressionList);
	}


	/*
	 *
	 * Funções
	 *
	 */

	// Visita a regra func_declaration: FUNC IDENTIFIER L_PAREN func_args? R_PAREN var_types? statement_section
	@Override
	public AST visitFunc_declaration(GoParser.Func_declarationContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		if (ctx.var_types() != null) {
			// Define lastDeclType
			visit(ctx.var_types());
		} else {
			// Função não retorna nenhum tipo
			lastDeclType = Type.NO_TYPE;
		}

		// Salva o tupo da função antes do lastDeclType se sobrescrito pela declaração dos args
		lastDeclFuncType = lastDeclType;

		// Define a lastDeclFuncName para checagem futura de escopo de variável
		lastDeclFuncName = identifierToken.getText();

		AST funcArgs = null;
		if (ctx.func_args() != null) {
			// Define lastDeclArgsSize
			funcArgs = visit(ctx.func_args());
		} else {
			lastDeclFuncArgsSize = 0;
		}

		// Checa se a função foi previamente declarada
		AST node = newFunc(identifierToken);

		// Visita a regra recursivamente para checagem de erros
		AST statements = visit(ctx.statement_section());
		
		// Adiciona as instruções e argumentos como filhos da função
		node.addChild(funcArgs);
		node.addChild(statements);

		return node;
		
	}


	// Visita a regra func_args: id var_types (COMMA id var_types)*
	@Override
	public AST visitFunc_args(GoParser.Func_argsContext ctx) {
		lastDeclFuncArgsSize = ctx.id().size();

		AST node = AST.newSubtree(NodeKind.FUNC_ARGS_NODE, Type.NO_TYPE);
		// Adiciona cada elemento na tabela de variáveis
		for (int i = 0; i < ctx.id().size(); i++) {

			visit(ctx.var_types(i));
			// Verifica se a variável foi previamente declarada
			AST child = visit(ctx.id(i));

			node.addChild(child);
		}

		return node;
	}

	// Visita a regra func_section: func_declaration* func_main func_declaration*
	@Override
	public AST visitFunc_section(GoParser.Func_sectionContext ctx) {
		// Creates the func_list node
		AST node = AST.newSubtree(NodeKind.FUNC_LIST_NODE, Type.NO_TYPE);
		

		//  Verifica se tem outra declaração de função além da função main
		if (ctx.func_declaration() != null) {
			// Visita recursivamente as funções para checar erros
			for (GoParser.Func_declarationContext funcDecl : ctx.func_declaration()) {
				AST child = visit(funcDecl);
				node.addChild(child);
			}
		}

		// Visita a função main para checar erros
		AST mainFunc = visit(ctx.func_main());

		node.addChild(mainFunc);
		
		return node;
	}


	// Visita a regra func_main: FUNC MAIN L_PAREN func_args? R_PAREN var_types? statement_section
	@Override
	public AST visitFunc_main(GoParser.Func_mainContext ctx) {
		if(ctx.var_types() != null) {
			// Define lastDeclType 
			visit(ctx.var_types());
		} else {
			// Função não retorna nenhum tipo
			lastDeclType = Type.NO_TYPE;
		}

		// Salva o tipo da função antes da lastDeclType ser reescrita pela declaração de argumentos
		lastDeclFuncType = lastDeclType;

		AST funcArgs = null;
		if(ctx.func_args() != null) {
			// Define lastDeclFuncArgsSize
			funcArgs = visit(ctx.func_args());
		} else {
			// Função não tem argumentos
			lastDeclFuncArgsSize = 0;
		}
		
		// Define a lastDeclFuncName para checagem futura de escopo de variável
		lastDeclFuncName = "main";

		// Visita recursivamente a regra para checagem de erros
		AST statements = visit(ctx.statement_section());

		Token mainToken = ctx.MAIN().getSymbol();

		// Adiciona a função principal na tabela de funções
		ft.addFunc(mainToken.getText(), mainToken.getLine(), lastDeclFuncType, lastDeclFuncArgsSize);		 

		// Cria o nó para a função principal
		AST mainFunc = new AST(NodeKind.FUNC_MAIN_NODE, lastDeclFuncType,mainToken.getText());

		// Adiciona as instruções como filho da função
		mainFunc.addChild(funcArgs);
		mainFunc.addChild(statements);

		return mainFunc;
	}

	/*
	 *
	 * Statements
	 *
	 */

	// Visita a regra statement_section: L_CURLY statement* return_statement? R_CURLY
	@Override
	public AST visitStatement_section(GoParser.Statement_sectionContext ctx) {
		AST node = AST.newSubtree(NodeKind.STATEMENT_SECTION_NODE, Type.NO_TYPE);

		// Visita cada statement para checar erros
		for (GoParser.StatementContext stmt : ctx.statement()) {
			AST child = visit(stmt);
			node.addChild(child);
		}

		if(ctx.return_statement() != null) {
			// Visita cada statement para checar erros
			AST child = visit(ctx.return_statement());
			node.addChild(child);
		}

		return node;
	}
	
	

	// Visita a regra return_statement: RETURN expression SEMI?
	@Override
	public AST visitReturn_statement(GoParser.Return_statementContext ctx) {
		Type expressionType = Type.NO_TYPE;
		AST expression = null;
		
		if(ctx.expression() != null) {
			expression = visit(ctx.expression());
			expressionType = expression.type;
		}

		// Checa se o tipo de retorno da função e o tipo da expressão são correspondentes
		checkFuncReturnType(ctx.RETURN().getSymbol().getLine(), expressionType);
		
		// Cria o nó de retorno
		AST node = AST.newSubtree(NodeKind.RETURN_NODE, expressionType);

		node.addChild(expression);

		return node;
	}

	// Visita a regra if_statement: IF expression statement_section (ELSE statement_section)?
	@Override
	public AST visitIf_statement(GoParser.If_statementContext ctx) {
		AST expression = visit(ctx.expression());

		// Varifica a expressão para ver se ela é do tipo bool
		checkBoolExpr(ctx.IF().getSymbol().getLine(), "if", expression.type);

		// Visita recursivamente statment_section do bloco if para checar erros
		AST ifStatements = visit(ctx.statement_section(0));

		// Visita recursivamente statment_section do bloco else para checar erros
		AST elseNode = null;
		if(ctx.ELSE() != null) {
			AST elseStatements = visit(ctx.statement_section(1));
			elseNode = AST.newSubtree(NodeKind.ELSE_NODE, Type.NO_TYPE, elseStatements);
		} 

		return AST.newSubtree(NodeKind.IF_NODE, Type.NO_TYPE, expression, ifStatements, elseNode);
	}



	// Visita a regra for_statement: FOR expression? statement_section
	@Override
	public AST visitWhile(GoParser.WhileContext ctx) {
		// Verifica se existe uma expression
		AST expression = null;
		if (ctx.expression() != null) {
			 expression = visit(ctx.expression());

			// Verifica se a expressão tem o tipo booleano
			checkBoolExpr(ctx.FOR().getSymbol().getLine(), "for", expression.type);
		}

		// Visita recursivamente statement_section para checar erros
		AST statements = visit(ctx.statement_section());

		return AST.newSubtree(NodeKind.WHILE_NODE, Type.NO_TYPE, expression, statements);
	}

	
	// Visita a regra or_statement: FOR declare_assign SEMI expression SEMI assign_statement statement_section
	@Override
	public AST visitFor(GoParser.ForContext ctx) {
		// Visita a regra recursivamente para checar erros
		AST declareAssign = visit(ctx.declare_assign());
		AST expression = visit(ctx.expression());
		AST assign = visit(ctx.assign_statement());
		AST statements = visit(ctx.statement_section());

		// Verifica se a expressão possui o tipo booleano
		checkBoolExpr(ctx.FOR().getSymbol().getLine(), "for", expression.type);

		return AST.newSubtree(NodeKind.FOR_NODE, Type.NO_TYPE, declareAssign, expression, assign, statements);
	}


	// Visita a regra assign_statement: id op=(ASSIGN | MINUS_ASSIGN | PLUS_ASSIGN) expression SEMI?
	@Override
	public AST visitAssignExpression(GoParser.AssignExpressionContext ctx) {
		AST expression = visit(ctx.expression());

		// Verifica se a variável foi previamente declarada
		AST identifier = visit(ctx.id());

		Token identifierToken = ctx.id().IDENTIFIER().getSymbol();

		// Verifica se a operação de atribuição é suportada
		checkAssign(identifierToken.getLine(), ctx.op.getText(), identifier.type, expression.type);

		// Define qual o tipo do node da atribuicao
		NodeKind kind = null;
		int op = ctx.op.getType();
		if(op == GoParser.ASSIGN)		kind = NodeKind.ASSIGN_NODE;
		if(op == GoParser.PLUS_ASSIGN)	kind = NodeKind.PLUS_ASSIGN_NODE;
		if(op == GoParser.MINUS_ASSIGN)	kind = NodeKind.MINUS_ASSIGN_NODE;

		return AST.newSubtree(kind, Type.NO_TYPE, identifier, expression);
	}

	// Visita a regra assign_statement: IDENTIFIER op=(PLUS_PLUS | MINUS_MINUS) SEMI?
	@Override
	public AST visitAssignPPMM(GoParser.AssignPPMMContext ctx) {
		// Verifica se a variavel já foi declarada
		AST identifier = visit(ctx.id());

		Token identifierToken = ctx.id().IDENTIFIER().getSymbol();

		// Verifica se a operação é suportada
		checkUnaryOp(identifierToken.getLine(), ctx.op.getText(), identifier.type);

		if(ctx.op.getType() == GoParser.PLUS_PLUS) {
			return AST.newSubtree(NodeKind.PLUS_PLUS_NODE, Type.NO_TYPE, identifier);
		} else { // Minus-minus
			return AST.newSubtree(NodeKind.MINUS_MINUS_NODE, Type.NO_TYPE, identifier);
		}
	}

	
	// Visita a regra switch_statement: SWITCH id? L_CURLY case_statement R_CURLY
	// @Override
	// public Type visitSwitch_statement(GoParser.Switch_statementContext ctx) {
	// 	if (ctx.id() != null)
	// 		visit(ctx.id());
	// 	if (ctx.func_call() != null)
	// 		visit(ctx.func_call());

	// 	visit(ctx.case_statement());

	// 	return Type.NO_TYPE;
	// }

	// // Visita a regra case_statement: (CASE expression COLON statement*)* (DEFAULT COLON statement*)?
	// @Override
	// public Type visitCase_statement(GoParser.Case_statementContext ctx) {

	// 	GoParser.Switch_statementContext parent = (GoParser.Switch_statementContext) ctx.parent;

	// 	if (parent == null) {
	// 		System.out.println("Case statement has no parent node. Exiting...");
	// 		System.exit(1);
	// 	}

	// 	// Tipo default para a case expression nada está sendo avalidado
	// 	Type caseType = Type.BOOL_TYPE;


	// 	if (parent.id() != null)
	// 		caseType = visit(parent.id());
	// 	if (parent.func_call() != null)
	// 		caseType = visit(parent.func_call());

	// 	// Visita recursivamente cada expression para cada caso para lidar com erros
	// 	for (int i = 0; i < ctx.expression().size(); i++) {
	// 		// Pega a tipo atual da expression
	// 		Type expressionType = visit(ctx.expression(i));

	// 		// Verifica se o tipo da expressão combina com o tipo do case
	// 		checkCase(ctx.CASE().get(i).getSymbol().getLine(), caseType, expressionType);
	// 	}


	// 	// Visita recursivamente cada statement para checar erros
	// 	for (GoParser.StatementContext stmt : ctx.statement()) {
	// 		visit(stmt);
	// 	}

	// 	// Visita a regra recursivamente para checar erros
	// 	if (ctx.default_statement() != null) {
	// 		visit(ctx.default_statement());
	// 	}

	// 	return Type.NO_TYPE;
	// }

	// Visita a regra func_call: IDENTIFIER L_PAREN expression_list? R_PAREN
	@Override
	public AST visitFunc_call(GoParser.Func_callContext ctx) {
		Token funcToken = ctx.IDENTIFIER().getSymbol();

		// Verifica se a função já foi declarada
		AST node = checkFunc(funcToken);

		// Verifica se  a função possui parâmetros
		if (ctx.expression_list() != null) {
			// Visita a regra recursivamente para checar erros
			AST child = visit(ctx.expression_list());
			node.addChild(child);
		} else {
			lastExpressionListSize = 0;
		}

		// Verifica se ListSize possui o mesmo tamanho do que o esperado pela função
		checkFuncCall(funcToken);

		return node;
	}

	// Visita a regra expression_list: expression (COMMA expression)*
	@Override
	public AST visitExpression_list(GoParser.Expression_listContext ctx) {
		lastExpressionListSize = ctx.expression().size();

		AST node = AST.newSubtree(NodeKind.EXPRESSION_LIST_NODE, Type.NO_TYPE);
		// Visita recursivamente cada expression para checar erros
		for (GoParser.ExpressionContext expr : ctx.expression()) {
			AST child = visit(expr);
			node.addChild(child);
		}

		return node;
	}

	/*
	 *
	 * Expression rules
	 *
	 */

	// Visita a regra expression: expression op=(STAR | DIV | MOD) expression
	@Override
	public AST visitStarDivMod(GoParser.StarDivModContext ctx) {
		// Visita ambos os operandos para verificar seus tipos
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Unifica os tipos dos operandos
		Type lt = l.type;
		Type rt = r.type;
		Type unif = lt.unifyMathOps(rt);

		// Operacao nao suportada
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}
		
		// Define qual o tipo do node quel a expressao possui
		NodeKind kind = null;
		switch (ctx.op.getType()) {
			case GoParser.STAR:
				kind = NodeKind.STAR_NODE;
				break;
			case GoParser.DIV:
				kind = NodeKind.DIV_NODE;
				break;
			case GoParser.MOD:
				kind = NodeKind.MOD_NODE;
				break;
		}

		return AST.newSubtree(kind, unif, l, r);
	}


	// Visita a regra expression: expression op=(STAR | DIV | MOD) expression
	// @Override
	// public AST visitStarDivMod(GoParser.StarDivModContext ctx) {
	// 	// Visita os operando para verificar seus tipos
	// 	AST l = visit(ctx.expression(0));
	// 	AST r = visit(ctx.expression(1));

	// 	// Unifica os tipos dos operandos
	// 	Type lt = l.type;
	// 	Type rt = r.type;
	// 	Type unif = lt.unifyMathOps(rt);

	// 	// Operacao nao suportada
	// 	if (unif == Type.NO_TYPE) {
	// 		typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
	// 	}
		
	// 	// Define qual tipo de node a expressao possui
	// 	NodeKind kind = null;
	// 	switch (ctx.op.getType()) {
	// 		case GoParser.STAR:
	// 			kind = NodeKind.STAR_NODE;
	// 			break;
	// 		case GoParser.DIV:
	// 			kind = NodeKind.DIV_NODE;
	// 			break;
	// 		case GoParser.MOD:
	// 			kind = NodeKind.MOD_NODE;
	// 			break;
	// 	}

	// 	return AST.newSubtree(kind, unif, l, r);
	// }

	// Visita a regra expression: expression op=(PLUS | MINUS) expression
	@Override
	public AST visitPlusMinus(GoParser.PlusMinusContext ctx) {
		// Visita os operando para verificar seus tipos
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));
		
		// Unifica os tipos dos operandos
		Type lt = l.type;
		Type rt = r.type;
		Type unif = lt.unifyMathOps(rt);

		// Operacao nao suportada
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}

		if (ctx.op.getType() == GoParser.PLUS) {
			return AST.newSubtree(NodeKind.PLUS_NODE, unif, l, r);
		} else { // MINUS
			return AST.newSubtree(NodeKind.MINUS_NODE, unif, l, r);
		}
	}

	// Visita a regra expression: expression op=( EQUALS | NOT_EQUALS | LESS | LESS_OR_EQUALS | GREATER | GREATER_OR_EQUALS) expression
	@Override
	public AST visitRelationalOperators(GoParser.RelationalOperatorsContext ctx) {
		// Visita os operandos para checar seus tipos
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Unifica os tipos dos operandos
		Type lt = l.type;
		Type rt = r.type;
		Type unif;

		int op = ctx.op.getType();
		if(op == GoParser.EQUALS || op == GoParser.NOT_EQUALS){
			unif = lt.unifyCompare(rt);
		} else {
			unif = lt.unifyCompare2(rt);
		}

		// Operação não suportada
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}

		// Define qual tipo de node a expressao possui
		NodeKind kind = null;
		if (op == GoParser.EQUALS) 				kind = NodeKind.EQUALS_NODE;
		if (op == GoParser.NOT_EQUALS) 			kind = NodeKind.NOT_EQUALS_NODE;
		if (op == GoParser.LESS) 				kind = NodeKind.LESS_NODE;
		if (op == GoParser.LESS_OR_EQUALS) 		kind = NodeKind.LESS_OR_EQUALS_NODE;
		if (op == GoParser.GREATER) 			kind = NodeKind.GREATER_NODE;
		if (op == GoParser.GREATER_OR_EQUALS) 	kind = NodeKind.GREATER_OR_EQUALS_NODE;

		return AST.newSubtree(kind, unif, l, r);
	}

	// Visita a regra expression: L_PAREN expression R_PAREN
	@Override
	public AST visitExpressionParen(GoParser.ExpressionParenContext ctx) {
		return visit(ctx.expression());
	}

	// Visita a regra expression: id
	@Override
	public AST visitExpressionId(GoParser.ExpressionIdContext ctx) {
		return visit(ctx.id());
	}

	// Visita a regra expression: func_call
	@Override
	public AST visitExpressionFuncCall(GoParser.ExpressionFuncCallContext ctx) {
		return visit(ctx.func_call());
	}

	// Visita a regra expression: DECIMAL_LIT
	@Override
	public AST visitIntVal(GoParser.IntValContext ctx) {
		return new AST(NodeKind.INT_VAL_NODE, Type.INT_TYPE, ctx.getText());
	}

	// Visita a regra expression: FLOAT_LIT
	@Override
	public AST visitFloatVal(GoParser.FloatValContext ctx) {
		float floatData = Float.parseFloat(ctx.getText());
		return new AST(NodeKind.FLOAT32_VAL_NODE, Type.FLOAT32_TYPE, floatData);
	}

	// Visita a regra expression: INTERPRETED_STRING_LIT
	@Override
	public AST visitStringVal(GoParser.StringValContext ctx) {
		// Adiciona a string na tabela de strings
		String text = ctx.INTERPRETED_STRING_LIT().getText();
		st.add(text);
		return new AST(NodeKind.STRING_VAL_NODE , Type.STR_TYPE, text);
	}

	// Visita a regra expression: BOOLEAN_LIT
	@Override
	public AST visitBoolVal(GoParser.BoolValContext ctx) {
		if(ctx.getText().equals("true")) {
			return new AST(NodeKind.BOOL_VAL_NODE, Type.BOOL_TYPE,"true");
		} else {
			return new AST(NodeKind.BOOL_VAL_NODE, Type.BOOL_TYPE,"false");
		}
	}

	/*
	 *
	 * Id 
	 *
	 */

	
	

	
	// Visita a regra id: IDENTIFIER (L_BRACKET expression R_BRACKET)?
	@Override
	public AST visitId(GoParser.IdContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		AST expression = null;
		if (ctx.expression() != null) {
			// Visita recursivamente a expressão para checar erro
			expression = visit(ctx.expression());

			// Verifica se o index é valido
			checkIndex(identifierToken.getLine(), expression.type);
		

		// Se o pai é a regra func_args rule, cria a nova variável
			if(ctx.parent instanceof GoParser.Func_argsContext) {
				lastDeclArrayArgsSize = 1; // Retirar depois
				return newVar(identifierToken);
			}
			
		}

		// Se o pai é a regra func_args rule, cria a nova variável
		if(ctx.parent instanceof GoParser.Func_argsContext) {
			lastDeclArrayArgsSize = 0;
			return newVar(identifierToken);
		}
		
		// Checa se a variável foi declarada anteriormente
		AST node  = checkVar(identifierToken);
		node.addChild(expression);

		return node;

	}

	public void printAST() {
    	AST.printDot(root, vt);
	}

}
