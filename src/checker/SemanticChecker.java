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

	// Visits the rule var_types: INT
	@Override
	public Type visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		return Type.NO_TYPE;
	}

	// Visits the rule var_types: STRING
	@Override
	public Type visitStringType(GoParser.StringTypeContext ctx) {
		this.lastDeclType = Type.STR_TYPE;
		return Type.NO_TYPE;
	}

	// Visits the rule var_types: BOOL
	@Override
	public Type visitBoolType(GoParser.BoolTypeContext ctx) {
		this.lastDeclType = Type.BOOL_TYPE;
		return Type.NO_TYPE;
	}

	// Visits the rule var_types: FLOAT32
	@Override
	public Type visitFloat32Type(GoParser.Float32TypeContext ctx) {
		this.lastDeclType = Type.FLOAT32_TYPE;
		return Type.NO_TYPE;
	}

	// Visita a regra var_declaration: VAR IDENTIFIER (var_types | var_types? ASSIGN  expression | array_declaration) SEMI?
	@Override
	public Type visitVar_declaration(GoParser.Var_declarationContext ctx) {
		// Checa se a variável é um array ou uma variável normal
		if (ctx.array_declaration() != null) {
			// Recursively visits the array_declaration to define the array type
			visit(ctx.array_declaration());
		} else {
			boolean hasVarType = ctx.var_types() != null;

			// Defines the var type based on the explicit type declaration or
			// the given initial expression.
			// e.g: var x int
			// var x = 10
			if (hasVarType) {
				// Define lastDeclType com a declaração explícita do tipo da variável
				visit(ctx.var_types());
			} else {
				// Define lastDeclType baseado no tipo da expressão
				lastDeclType = visit(ctx.expression());
			}
		}

		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Checks if the variable was previously declared
		newVar(identifierToken);

		boolean hasAssign = ctx.ASSIGN() != null;
		// Checks if the identifier type and expression type match
		if (hasAssign) {
			Type identifierType = checkVar(identifierToken);
			Type expressionType = visit(ctx.expression());

			checkInitAssign(identifierToken.getLine(), identifierToken.getText(), identifierType, expressionType);
		}

		return Type.NO_TYPE;
	}

}
