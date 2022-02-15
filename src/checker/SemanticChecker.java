package checker;

import org.antlr.v4.runtime.Token;

import parser.GoParser;
import parser.GoParserBaseVisitor;
import tables.FuncTable;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

public class SemanticChecker extends GoParserBaseVisitor<Type> {

	private StrTable st = new StrTable(); // Tabela de strings.
	private VarTable vt = new VarTable(); // Tabela de variáveis.
	private FuncTable ft = new FuncTable(); // Table de funcoes.

	Type lastDeclType; // Variável "global" com o último tipo declarado.
	Type lastDeclFuncType; // Global variable with the last declared func type
	int lastDeclArgsSize; // Global variable with the last declared argsSize
	int lastExpressionListSize;

	private boolean passed = true;

	/*
	 *
	 * CRIAÇÃO E VERIFICAÇÃO DE VARIÁVEIS
	 *
	 */

	// Testa se o dado token foi declarado antes.
	Type checkVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = vt.lookupVar(text);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): variable '%s' was not declared.\n", line, text);
			passed = false;
			return Type.NO_TYPE;
		}
		return vt.getType(text);
	}

	// Cria uma nova variável a partir do dado token.
	void newVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = vt.lookupVar(text);
		if (!isInTable) {
			System.err.printf(
					"SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n",
					line, text, vt.getLine(text));
			passed = false;
			return;
		}
		vt.addVar(text, line, lastDeclType);
	}

	/*
	 *
	 * CRIAÇÃO E VERIFICAÇÃO DE FUNÇÕES
	 *
	 */

	// Testa se a função foi declarada antes.
	Type checkFunc(Token token) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = ft.lookupFunc(text);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): function '%s' was not declared.\n", line, text);
			passed = false;
			return Type.NO_TYPE;
		}
		return ft.getType(text);
	}

	// Cria uma nova função a partir do dado token.
	void newFunc(Token token, int argsSize) {
		String text = token.getText();
		int line = token.getLine();
		boolean isInTable = ft.lookupFunc(text);
		if (!isInTable) {
			System.err.printf("SEMANTIC ERROR (%d): function '%s' already declared at line %d.\n",
					line, text, ft.getLine(text));
			passed = false;
			return;
		}
		ft.addFunc(text, line, lastDeclFuncType, argsSize);
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
				passed = false;
			}
		}
	}

	// Checa se o return tem um tipo compatível com a função
	void checkFuncReturnType(int lineNo, Type t) {
		if (t != lastDeclFuncType) {
			System.err.printf(
					"SEMANTIC ERROR (%d): Return statement type incompatible with function type. Expected '%s' but received '%s'.\n",
					lineNo, lastDeclFuncType, t);
			passed = false;
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
		passed = false;
	}

	// Verifica se o tipo da variável é numérico.
	private void checkUnaryOp(int lineNo, String op, Type t) {
		if (t != Type.INT_TYPE && t != Type.FLOAT32_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): type '%s' not suported for unary operator '%s'.\n",
					lineNo, t.toString(), op);
			passed = false;
		}
	}

	// Verifica atribuição de tipos.
	private void checkAssign(int lineNo, String op, Type l, Type r) {
		if (l == Type.BOOL_TYPE && r != Type.BOOL_TYPE)
			typeError(lineNo, op, l, r);
		if (l == Type.STR_TYPE && r != Type.STR_TYPE)
			typeError(lineNo, op, l, r);
		if (l == Type.INT_TYPE && r != Type.INT_TYPE)
			typeError(lineNo, op, l, r);
		if (l == Type.FLOAT32_TYPE && !(r == Type.INT_TYPE || r == Type.FLOAT32_TYPE))
			typeError(lineNo, op, l, r);
	}

	// Verifica se o tipo é booleano
	private void checkBoolExpr(int lineNo, String cmd, Type t) {
		if (t != Type.BOOL_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): conditional expression in '%s' is '%s' instead of '%s'.\n",
					lineNo, cmd, t.toString(), Type.BOOL_TYPE.toString());
			passed = false;
		}
	}

	// Verifica se o tipo é inteiro
	private void checkIndex(int lineNo, Type t) {
		if (t != Type.INT_TYPE) {
			System.out.printf(
					"SEMANTIC ERROR (%d): incompatible type '%s' at array index.\n",
					lineNo, t.toString());
			passed = false;
		}
	}

	// Verifica se os tipos são iguais
	private void checkCase(int lineNo, Type t1, Type t2) {
		if (t1 != t2) {
			System.out.printf(
					"SEMANTIC ERROR (%d): incompatible types for case, expected '%s' but expression is '%s'.\n",
					lineNo, t1.toString(), t2.toString());
			passed = false;
		}
	}

	// Imprime erro de incompatibilidade de tipos na declaração de variáveis
	private void typeInitError(int lineNo, String varName, Type t1, Type t2) {
		System.out.printf(
				"SEMANTIC ERROR (%d): incompatible types when declaring variable '%s', var type is '%s' and expression type is '%s'.\n",
				lineNo, varName, t1.toString(), t2.toString());
		passed = false;
	}

	// Verifica atribuiçao de tipos
	private void checkInitAssign(int lineNo, String varName, Type l, Type r) {
		if (l == Type.BOOL_TYPE && r != Type.BOOL_TYPE)
			typeInitError(lineNo, varName, l, r);
		if (l == Type.STR_TYPE && r != Type.STR_TYPE)
			typeInitError(lineNo, varName, l, r);
		if (l == Type.INT_TYPE && r != Type.INT_TYPE)
			typeInitError(lineNo, varName, l, r);
		if (l == Type.FLOAT32_TYPE && !(r == Type.INT_TYPE || r == Type.FLOAT32_TYPE))
			typeInitError(lineNo, varName, l, r);
	}

	//
	private void checkArrayInit(int lineNo, String varName) {
		if (lastDeclArgsSize != lastExpressionListSize) {
			System.out.printf(
					"SEMANTIC ERROR (%d): Array '%s' declared with size %d but initialized with %d arguments.\n",
					lineNo, varName, lastDeclArgsSize, lastExpressionListSize);
			passed = false;
		}
	}

	// Retorna true se os testes passaram.
	boolean hasPassed() {
		return passed;
	}

	// Exibe o conteúdo das tabelas em stdout.
	void printTables() {
		System.out.print("\n\n");
		System.out.print(st);
		System.out.print("\n\n");
		System.out.print(vt);
		System.out.print("\n\n");
	}

	// Visita a regra var_types: INT
	@Override
	public Type visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		return Type.NO_TYPE;
	}

	// Visita a regra var_types: STRING
	@Override
	public Type visitStringType(GoParser.StringTypeContext ctx) {
		this.lastDeclType = Type.STR_TYPE;
		return Type.NO_TYPE;
	}

	// Visita a regra var_types: BOOL
	@Override
	public Type visitBoolType(GoParser.BoolTypeContext ctx) {
		this.lastDeclType = Type.BOOL_TYPE;
		return Type.NO_TYPE;
	}

	// Visita a regra var_types: FLOAT32
	@Override
	public Type visitFloat32Type(GoParser.Float32TypeContext ctx) {
		this.lastDeclType = Type.FLOAT32_TYPE;
		return Type.NO_TYPE;
	}

	// Visita a regra var_declaration: VAR IDENTIFIER (var_types | var_types? ASSIGN
	// expression | array_declaration) SEMI?
	@Override
	public Type visitVar_declaration(GoParser.Var_declarationContext ctx) {
		// Checa se a variável é um array ou uma variável normal
		if (ctx.array_declaration() != null) {
			// Visita recursivamente array_declaration para definir o tipo do array
			visit(ctx.array_declaration());
		} else {
			boolean hasVarType = ctx.var_types() != null;

			if (hasVarType) {
				// Define lastDeclType com a declaração explícita do tipo da variável
				visit(ctx.var_types());
			} else {
				// Define lastDeclType baseado no tipo da expressão
				lastDeclType = visit(ctx.expression());
			}
		}

		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Verifica se a variável foi previamente declarada
		newVar(identifierToken);

		boolean hasAssign = ctx.ASSIGN() != null;

		if (hasAssign) {
			Type identifierType = checkVar(identifierToken);
			Type expressionType = visit(ctx.expression());

			checkInitAssign(identifierToken.getLine(), identifierToken.getText(), identifierType, expressionType);
		}

		return Type.NO_TYPE;
	}

	// Visita a regra declare_assign: IDENTIFIER DECLARE_ASSIGN ( array_init |
	// expression) SEMI?
	@Override
	public Type visitDeclare_assign(GoParser.Declare_assignContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Define lastDeclType com base no tipo da expressão ou na inicialização do
		// array
		if (ctx.expression() != null)
			lastDeclType = visit(ctx.expression());
		if (ctx.array_init() != null) {
			visit(ctx.array_init());

			// Verifica se o array foi inicializado com a quantidade correta de indices
			checkArrayInit(identifierToken.getLine(), identifierToken.getText());
		}

		// Verifica se a variável foi declarada previamente.
		newVar(identifierToken);

		return Type.NO_TYPE;
	}

	/*
	 *
	 * array_declaration e array_ags
	 *
	 */

	// Visita a regra array_declaration: L_BRACKET DECIMAL_LIT R_BRACKET var_types
	@Override
	public Type visitArray_declaration(GoParser.Array_declarationContext ctx) {
		// Define o tamanho do array.
		lastDeclArgsSize = Integer.parseInt(ctx.DECIMAL_LIT().getText());

		visit(ctx.var_types());

		return Type.NO_TYPE;
	}

	// Visita a regra array_init: array_declaration L_CURLY expression_list? R_CURLY
	@Override
	public Type visitArray_init(GoParser.Array_initContext ctx) {
		// Recursively Visita a regra for error checking
		visit(ctx.array_declaration());

		if (ctx.expression_list() != null) {
			// Recursively Visita a regra for error checking
			visit(ctx.expression_list());
		}

		return Type.NO_TYPE;
	}

	/*
	 *
	 * input e output
	 *
	 */

	// Visita a regra input: INPUT L_PAREN AMPERSAND id R_PAREN
	@Override
	public Type visitInput(GoParser.InputContext ctx) {
		// Visita a regra recursivamente para verificar erros
		visit(ctx.id());

		return Type.NO_TYPE;
	}

	// Visita a regra output: OUTPUT L_PAREN expression_list? R_PAREN
	@Override
	public Type visitOutput(GoParser.OutputContext ctx) {
		visit(ctx.expression_list());

		return Type.NO_TYPE;
	}


	/*
	 *
	 * Funções
	 *
	 */

	// Visita a regra func_declaration: FUNC IDENTIFIER L_PAREN func_args? R_PAREN var_types? statement_section
	@Override
	public Type visitFunc_declaration(GoParser.Func_declarationContext ctx) {

		if (ctx.var_types() != null) {
			// Define lastDeclType
			visit(ctx.var_types());
		} else {
			// Função não retorna nenhum tipo
			lastDeclType = Type.NO_TYPE;
		}

		// Salva o tupo da função antes do lastDeclType se sobrescrito pela declaração dos args
		lastDeclFuncType = lastDeclType;

		if (ctx.func_args() != null) {
			// Define lastDeclArgsSize
			visit(ctx.func_args());
		} else {
			lastDeclArgsSize = 0;
		}

		// Verifica se a função foi previamente declarada
		newFunc(ctx.IDENTIFIER().getSymbol(), lastDeclArgsSize);

		visit(ctx.statement_section());

		return Type.NO_TYPE;
	}


	// Visita a regra func_args: id var_types (COMMA id var_types)*
	@Override
	public Type visitFunc_args(GoParser.Func_argsContext ctx) {
		lastDeclArgsSize = ctx.id().size();

		// Adiciona cada elemento na tabela de variáveis
		for (int i = 0; i < ctx.id().size(); i++) {

			visit(ctx.var_types(i));
			// Verifica se a variável foi previamente declarada
			newVar(ctx.id(i).IDENTIFIER().getSymbol());

			visit(ctx.id(i));
		}

		return Type.NO_TYPE;
	}

	// Visita a regra func_section: func_declaration* func_main func_declaration*
	@Override
	public Type visitFunc_section(GoParser.Func_sectionContext ctx) {
		//  Verifica se tem outra declaração de função além da função main
		if (ctx.func_declaration() != null) {
			// Visita recursivamente as funções para checar erros
			for (GoParser.Func_declarationContext funcDecl : ctx.func_declaration()) {
				visit(funcDecl);
			}
		}

		// Visita a função main para checar erros
		visit(ctx.func_main());

		return Type.NO_TYPE;
	}



	/*
	 *
	 * Statements
	 *
	 */

	// Visita a regra statement_section: L_CURLY statement* return_statement?
	// R_CURLY
	@Override
	public Type visitStatement_section(GoParser.Statement_sectionContext ctx) {
		// Visita cada statement para checar erros
		for (GoParser.StatementContext stmt : ctx.statement()) {
			visit(stmt);
		}

		if (ctx.return_statement() != null) {
			visit(ctx.return_statement());
		}

		return Type.NO_TYPE;
	}
	
	

	// Visita a regra return_statement: RETURN expression SEMI?
	@Override
	public Type visitReturn_statement(GoParser.Return_statementContext ctx) {
		Type expressionType = Type.NO_TYPE;

		if (ctx.expression() != null) {
			expressionType = visit(ctx.expression());
		}

		checkFuncReturnType(ctx.RETURN().getSymbol().getLine(), expressionType);

		return Type.NO_TYPE;
	}

	// Visita a regra if_statement: IF expression statement_section (ELSE statement_section)?
	@Override
	public Type visitIf_statement(GoParser.If_statementContext ctx) {
		Type expressionType = visit(ctx.expression());

		// Varifica a expressão para ver se ela é do tipo bool
		checkBoolExpr(ctx.IF().getSymbol().getLine(), "if", expressionType);

		// Visita recursivamente statment_section do bloco if para checar erros
		visit(ctx.statement_section(0));

		// Visita recursivamente statment_section do bloco else para checar erros
		if (ctx.ELSE() != null) {
			visit(ctx.statement_section(1));
		}

		return Type.NO_TYPE;
	}

	

}
