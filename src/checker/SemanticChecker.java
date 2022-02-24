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

// TODOs:
// Must do:
// 	-
// Would be great if done:
// - implement tables using hash
// - maybe move the typeErrors and other functions to typing package
// - 



public class SemanticChecker extends GoParserBaseVisitor<AST> {

	private StrTable st = new StrTable();
	private VarTable vt = new VarTable();
	private FuncTable ft = new FuncTable();

	Type lastDeclType; // Global variable with the last declared var type 
	Type lastDeclFuncType; // Global variable with the last declared func type 
	String lastDeclFuncName; // Global variable with the last declared func name 
	int lastDeclFuncArgsSize; // Global variable with the last declared FUNC argsSize 
	int lastDeclArrayArgsSize; // Global variable with the last declared ARRAY argsSize 
	int lastExpressionListSize;

	AST root;

	void printTables() {
		System.out.print("\n\n");
		System.out.print(st);
		System.out.print("\n\n");
		System.out.print(vt);
		System.out.print("\n\n");
		System.out.print(ft);
		System.out.print("\n\n");
	}

    void printAST() {
    	AST.printDot(root, vt);
    }

	/*------------------------------------------------------------------------------*
	 *	Var checking and declaration.
	 *------------------------------------------------------------------------------*/

	// Checks whether the variable was previously declared
	AST checkVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = vt.lookupVar(text, lastDeclFuncName);
		if (idx == -1) {
			System.out.printf("SEMANTIC ERROR (%d): variable '%s' was not declared.\n", line, text);
			System.exit(1);
		}
		return new AST(NodeKind.VAR_USE_NODE, idx, vt.getType(idx));
	}

	// Creates a new variable from token
	AST newVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = vt.lookupVar(text, lastDeclFuncName);
		if (idx != -1) {
			System.out.printf("SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n",
				line, text, vt.getLine(idx)
			);
			System.exit(1);
		}
		idx = vt.addVar(text, lastDeclFuncName, line, lastDeclType, lastDeclArrayArgsSize);
		return new AST(NodeKind.VAR_DECL_NODE, idx, lastDeclType);
	}

	/*------------------------------------------------------------------------------*
	 *	Function checking and declaration.
	 *------------------------------------------------------------------------------*/

	// Checks whether the function was previously declared
	AST checkFunc(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = ft.lookupFunc(text);
		if (idx == -1) {
			System.out.printf("SEMANTIC ERROR (%d): function '%s' was not declared.\n", line, text);
			System.exit(1);
		}
		return new AST(NodeKind.FUNC_CALL_NODE, idx, ft.getType(idx));
	}

	// Creates a new function from token
	AST newFunc(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = ft.lookupFunc(text);
		if (idx != -1) {
			System.out.printf("SEMANTIC ERROR (%d): function '%s' already declared at line %d.\n",
				line, text, ft.getLine(idx)
			);
			System.exit(1);
		}
		idx = ft.addFunc(text, line, lastDeclFuncType, lastDeclFuncArgsSize);
		return new AST(NodeKind.FUNC_DECL_NODE, idx, lastDeclFuncType);
	}

	// Checks if the function was called with the right amount of arguments
	// Warning: this function should only be called after an explict call of the checkFunc
	void checkFuncCall(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = ft.lookupFunc(token.getText());

		// Doesnt show 'function not declared' error
		if(idx != -1) {
			int argsSize = ft.getArgsSize(idx);
	
			if(argsSize != lastExpressionListSize) {
				System.out.printf("SEMANTIC ERROR (%d): function '%s' expected %d arguments but received '%d'.\n",
					line, text, argsSize, lastExpressionListSize
				);
				System.exit(1);
			}
		}
	}

	// Checks if a return statement has a compatible type with the function
	void checkFuncReturnType(int lineNo, Type t) {
		if(t != lastDeclFuncType) {
			System.out.printf(
				"SEMANTIC ERROR (%d): Return statement type incompatible with function type. Expected '%s' but received '%s'.\n",
				lineNo, lastDeclFuncType, t
			);
			System.exit(1);
		}
	}

    /*------------------------------------------------------------------------------*
	 *	Type, operations and expression checking
	 *------------------------------------------------------------------------------*/
	
    private void typeError(int lineNo, String op, Type t1, Type t2) {
    	System.out.printf(
			"SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
			lineNo, op, t1.toString(), t2.toString()
		);
		System.exit(1);
    }

	private void checkUnaryOp(int lineNo, String op, Type t) {
		if (t != Type.INT_TYPE && t != Type.FLOAT32_TYPE) {
			System.out.printf(
				"SEMANTIC ERROR (%d): type '%s' not suported for unary operator '%s'.\n",
				lineNo, t.toString(), op
			);
			System.exit(1);
		}
	}

	private void checkAssign(int lineNo, String op,Type l, Type r) {
        if (l != r) {
			typeError(lineNo, op, l, r);
			System.exit(1);
		} 
    }

	private void checkBoolExpr(int lineNo, String cmd, Type t) {
		if (t != Type.BOOL_TYPE) {
			System.out.printf(
				"SEMANTIC ERROR (%d): conditional expression in '%s' is '%s' instead of '%s'.\n",
				lineNo, cmd, t.toString(), Type.BOOL_TYPE.toString()
			);
			System.exit(1);
		}
	}

	private void checkIndex(int lineNo, Type t) {
		if(t != Type.INT_TYPE) {
			System.out.printf(
				"SEMANTIC ERROR (%d): incompatible type '%s' at array index.\n",
				lineNo, t.toString()
			);
			System.exit(1);
		}
    }

	private void checkCase(int lineNo, Type t1, Type t2) {
		if(t1 != t2) {
			System.out.printf(
				"SEMANTIC ERROR (%d): incompatible types for case, expected '%s' but expression is '%s'.\n",
				lineNo, t1.toString(), t2.toString()
			);
			System.exit(1);
		}
	}

	// ----- Specific for when declaring variables

	private void typeInitError(int lineNo, String varName, Type t1, Type t2) {
    	System.out.printf(
			"SEMANTIC ERROR (%d): incompatible types when declaring variable '%s', var type is '%s' and expression type is '%s'.\n",
			lineNo, varName, t1.toString(), t2.toString()
		);
		System.exit(1);
    }

	private void checkInitAssign(int lineNo, String varName, Type l, Type r) {
        if (l != r ) {
			typeInitError(lineNo, varName, l, r);
			System.exit(1);
		}
    }
    
	private void checkArrayInit(int lineNo, String varName) {
		if(lastDeclArrayArgsSize != lastExpressionListSize) {
			System.out.printf(
				"SEMANTIC ERROR (%d): Array '%s' declared with size %d but initialized with %d arguments.\n",
				lineNo, varName, lastDeclArrayArgsSize, lastExpressionListSize
			);
			System.exit(1);
		}
	}

	/*------------------------------------------------------------------------------*
	 *	Visitor for program rule
	 *------------------------------------------------------------------------------*/

	// Visits the rule program: PACKAGE MAIN import_section? func_section
    @Override
	public AST visitProgram(GoParser.ProgramContext ctx) {
		AST funcSection = visit(ctx.func_section());

		// Creates the root node for the program
		this.root = AST.newSubtree(NodeKind.PROGRAM_NODE, Type.NO_TYPE, funcSection);

		return this.root;
	}

	/*------------------------------------------------------------------------------*
	 *	Visitors for var_types rule
	 *------------------------------------------------------------------------------*/

	// Visits the rule var_types: INT
	@Override
	public AST visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		return null;
	}

	// Visits the rule var_types: STRING
	@Override
	public AST visitStringType(GoParser.StringTypeContext ctx) {
		this.lastDeclType = Type.STRING_TYPE;
		return null;
	}

	// Visits the rule var_types: BOOL
	@Override
	public AST visitBoolType(GoParser.BoolTypeContext ctx) {
		this.lastDeclType = Type.BOOL_TYPE;
		return null;
	}
	
	// Visits the rule var_types: FLOAT32
	@Override
	public AST visitFloat32Type(GoParser.Float32TypeContext ctx) {
		this.lastDeclType = Type.FLOAT32_TYPE;
		return null;
	}

	/*------------------------------------------------------------------------------*
	 *Visitors for var_declaration and declare_assign rules
	 *------------------------------------------------------------------------------*/

	// Visits the rule var_declaration: VAR IDENTIFIER (var_types | var_types? ASSIGN  expression | array_declaration) SEMI?
	@Override
	public AST visitVar_declaration(GoParser.Var_declarationContext ctx) {	
		// Check wheter the variable is an array or a normal variable
		if(ctx.array_declaration() != null) {
			// Recursively visits the array_declaration to define the array type
			visit(ctx.array_declaration());
		} else {
			// Not an array
			lastDeclArrayArgsSize = 0;

			// Defines the var type based on the explicit type declaration or 
			// the given initial expression.
			// e.g: var x int
			// 		var x = 10
			if(ctx.var_types() != null) {
				// Defines lastDeclType with explicit var type declaration
				visit(ctx.var_types());
			} else {
				// Defines lastDeclType based on expression type
				lastDeclType = visit(ctx.expression()).type;
			}
		}

		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Checks if the variable was previously declared
		AST varDecl = newVar(identifierToken);

		boolean hasAssign = ctx.ASSIGN() != null;
		// Checks if the identifier type and expression type match
		if(hasAssign) {
			AST identifier = checkVar(identifierToken);
			AST expression = visit(ctx.expression());

			checkInitAssign(identifierToken.getLine(), identifierToken.getText(), identifier.type, expression.type);
			
			varDecl.addChild(expression);
		}

		return varDecl;
	}


	// Visits the rule declare_assign: IDENTIFIER DECLARE_ASSIGN ( array_init | expression) SEMI?
	@Override
	public AST visitDeclare_assign(GoParser.Declare_assignContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		// Defines lastDeclType based on expression type or array initialization
		AST expression = null;
		if(ctx.expression() != null) {
			expression = visit(ctx.expression());
			lastDeclType = expression.type;
			lastDeclArrayArgsSize = 0;
		}

		AST arrayInit = null;
		if(ctx.array_init() != null) {
			arrayInit = visit(ctx.array_init());

			// Checks if the array was initialized with the correct amount of indexes
			checkArrayInit(identifierToken.getLine(), identifierToken.getText());
		}

		// Checks if the variable was previously declared
		AST node = newVar(identifierToken);

		node.addChild(expression);
		node.addChild(arrayInit);

		return node;
	}

	// /*------------------------------------------------------------------------------*
	//  *Visitor for array_declaration and array_ags rules
	//  *------------------------------------------------------------------------------*/

	// Visits the rule array_declaration: L_BRACKET DECIMAL_LIT R_BRACKET var_types
	@Override
	public AST visitArray_declaration(GoParser.Array_declarationContext ctx) {
		// Defines the array size
		lastDeclArrayArgsSize = Integer.parseInt(ctx.DECIMAL_LIT().getText());

		// Defines lastDeclType 
		visit(ctx.var_types());

		return null;
	}

	// Visits the rule array_init: array_declaration L_CURLY expression_list? R_CURLY
	@Override
	public AST visitArray_init(GoParser.Array_initContext ctx) {
		// Recursively visits the rule for error checking
		visit(ctx.array_declaration());

		AST expressionList = null;
		if(ctx.expression_list() != null) {
			// Recursively visits the rule for error checking
			expressionList = visit(ctx.expression_list());
		} 

		return expressionList;
	}

	/*------------------------------------------------------------------------------*
	 *	Visitors for input and output
	 *------------------------------------------------------------------------------*/

	//  Visits the rule input: INPUT L_PAREN AMPERSAND id R_PAREN
	@Override
	public AST visitInput(GoParser.InputContext ctx) {
		// Recursively visits the rule for error checking
		AST identifier = visit(ctx.id());

		return AST.newSubtree(NodeKind.INPUT_NODE, Type.NO_TYPE, identifier);
	}

	// Visits the rule output: OUTPUT L_PAREN expression_list? R_PAREN
	@Override
	public AST visitOutput(GoParser.OutputContext ctx) {
		// Recursively visits the rule for error checking
		AST expressionList = visit(ctx.expression_list());

		return AST.newSubtree(NodeKind.OUTPUT_NODE, Type.NO_TYPE, expressionList);
	}

	/*------------------------------------------------------------------------------*
	 *	Visitors for functions related rules
	 *------------------------------------------------------------------------------*/
	
	// Visits the rule func_declaration: FUNC IDENTIFIER L_PAREN func_args? R_PAREN var_types? statement_section
	@Override
	public AST visitFunc_declaration(GoParser.Func_declarationContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();
		
		if(ctx.var_types() != null) {
			// Defines lastDeclType 
			visit(ctx.var_types());
		} else {
			// Function has no return type
			lastDeclType = Type.NO_TYPE;
		}

		// Saves the func type before the lastDeclType is overwritten
		// by the args declaration
		lastDeclFuncType = lastDeclType;

		// Defines the lastDeclFuncName for future var scope checking
		lastDeclFuncName = identifierToken.getText();

		AST funcArgs = null;
		if(ctx.func_args() != null) {
			// Defines lastDeclFuncArgsSize and adds the args to var table
			funcArgs = visit(ctx.func_args());
		} else {
			// Function has no args
			lastDeclFuncArgsSize = 0;
		}
		
		// Checks if the function was previously declared		
		AST node = newFunc(identifierToken);

		// Recursively visits rule for error checking
		AST statements = visit(ctx.statement_section());
		
		// Adds the statements and args as function's children
		node.addChild(funcArgs);
		node.addChild(statements);

		return node;
	}

	// Visits the rule func_args: id var_types (COMMA id var_types)*
	@Override
	public AST visitFunc_args(GoParser.Func_argsContext ctx) {
		lastDeclFuncArgsSize = ctx.id().size();

		AST node = AST.newSubtree(NodeKind.FUNC_ARGS_NODE, Type.NO_TYPE);

		// Adds every argument into the var table
		for(int i = 0; i < ctx.id().size(); i++) {
			// Defines lastDeclType
			visit(ctx.var_types(i));

			// Recursively visits the rule for error checking
			AST child = visit(ctx.id(i));

			node.addChild(child);
		}

		return node;
	}

	// Visits the rule func_section: func_declaration* func_main func_declaration*
	@Override
	public AST visitFunc_section(GoParser.Func_sectionContext ctx) {
		// Creates the func_list node
		AST node = AST.newSubtree(NodeKind.FUNC_LIST_NODE, Type.NO_TYPE);

		// Checks if there is any other function declaration besides the main function
		if(ctx.func_declaration() != null) {
			// Recursively visits the functions for error checking
			for (GoParser.Func_declarationContext funcDecl : ctx.func_declaration()) {
				AST child = visit(funcDecl);
				node.addChild(child);
			}
		}

		// Visits the main function for error checking
		AST mainFunc = visit(ctx.func_main());

		// Adds the main function as func_section's child
		node.addChild(mainFunc);

		return node;
	}

	// Visits the rule func_main: FUNC MAIN L_PAREN func_args? R_PAREN var_types? statement_section
	@Override
	public AST visitFunc_main(GoParser.Func_mainContext ctx) {
		if(ctx.var_types() != null) {
			// Defines lastDeclType 
			visit(ctx.var_types());
		} else {
			// Function has no return type
			lastDeclType = Type.NO_TYPE;
		}

		// Saves the func type before the lastDeclType be overwritten
		// by the args declaration
		lastDeclFuncType = lastDeclType;

		AST funcArgs = null;
		if(ctx.func_args() != null) {
			// Defines lastDeclFuncArgsSize
			funcArgs = visit(ctx.func_args());
		} else {
			// Function has no args
			lastDeclFuncArgsSize = 0;
		}
		
		// Defines the lastDeclFuncName for future var scope checking
		lastDeclFuncName = "main";

		// Recursively visits rule for error checking
		AST statements = visit(ctx.statement_section());

		Token mainToken = ctx.MAIN().getSymbol();

		// Adds the main function into the functions table
		int idx = ft.addFunc(mainToken.getText(), mainToken.getLine(), lastDeclFuncType, lastDeclFuncArgsSize);		 

		// Creates the node for the main function
		AST mainFunc = new AST(NodeKind.FUNC_MAIN_NODE, idx, lastDeclFuncType);

		// Adds the statements as function's child
		mainFunc.addChild(funcArgs);
		mainFunc.addChild(statements);

		return mainFunc;
	}

	/*------------------------------------------------------------------------------*
	 *	Visitors for statements rule
	 *------------------------------------------------------------------------------*/

	// Visits the rule statement_section: L_CURLY statement* return_statement? R_CURLY
	@Override
	public AST visitStatement_section(GoParser.Statement_sectionContext ctx) {
		AST node = AST.newSubtree(NodeKind.STATEMENT_SECTION_NODE, Type.NO_TYPE);

		// Recursively visits every statement for error checking
		for (GoParser.StatementContext stmt : ctx.statement()) {
			AST child = visit(stmt);
			node.addChild(child);
		}

		if(ctx.return_statement() != null) {
			// Recursively visits the rule for error checking
			AST child = visit(ctx.return_statement());
			node.addChild(child);
		}

		return node;
	}

	// Visits the rule return_statement: RETURN expression SEMI?
	@Override
	public AST visitReturn_statement(GoParser.Return_statementContext ctx) {
		Type expressionType = Type.NO_TYPE;
		AST expression = null;
		
		if(ctx.expression() != null) {
			expression = visit(ctx.expression());
			expressionType = expression.type;
		}

		// Checks if the function return type and expression type match
		checkFuncReturnType(ctx.RETURN().getSymbol().getLine(), expressionType);
		
		// Creates the return node
		AST node = AST.newSubtree(NodeKind.RETURN_NODE, expressionType);

		node.addChild(expression);

		return node;
	}

	// Visits the rule if_statement: IF expression statement_section (ELSE statement_section)?
	@Override
	public AST visitIf_statement(GoParser.If_statementContext ctx) {
		AST expression = visit(ctx.expression());

		// Checks expression to see if it is bool type 
		checkBoolExpr(ctx.IF().getSymbol().getLine(), "if", expression.type);

		// Recursively visits the statement_section from the if block for error checking
		AST ifStatements = visit(ctx.statement_section(0));

		// Recursively visits the statement_section from the else block for error checking
		AST elseNode = null;
		if(ctx.ELSE() != null) {
			AST elseStatements = visit(ctx.statement_section(1));
			elseNode = AST.newSubtree(NodeKind.ELSE_NODE, Type.NO_TYPE, elseStatements);
		} 

		return AST.newSubtree(NodeKind.IF_NODE, Type.NO_TYPE, expression, ifStatements, elseNode);
	}

	// Visits the rule for_statement: FOR expression? statement_section
	@Override
	public AST visitWhile(GoParser.WhileContext ctx) {
		// Checks if there is a expression
		AST expression = null;
		if(ctx.expression() != null) {
			expression = visit(ctx.expression());
	
			// Checks if the expression has bool type
			checkBoolExpr(ctx.FOR().getSymbol().getLine(), "for", expression.type);
		}

		// Recursively visits the statement_section for error checking
		AST statements = visit(ctx.statement_section());

		return AST.newSubtree(NodeKind.WHILE_NODE, Type.NO_TYPE, expression, statements);
	}

	// Visits the rule or_statement: FOR declare_assign SEMI expression SEMI assign_statement statement_section
	@Override
	public AST visitFor(GoParser.ForContext ctx) {
		// Recursively visits rules for error checking 
		AST declareAssign = visit(ctx.declare_assign());
		AST expression = visit(ctx.expression());
		AST assign = visit(ctx.assign_statement());
		AST statements = visit(ctx.statement_section());
		
		// Checks if the expression has bool type
		checkBoolExpr(ctx.FOR().getSymbol().getLine(), "for", expression.type);

		return AST.newSubtree(NodeKind.FOR_NODE, Type.NO_TYPE, declareAssign, expression, assign, statements);
	}

	// Visits the rule assign_statement: id op=(ASSIGN | MINUS_ASSIGN | PLUS_ASSIGN) expression SEMI?
	@Override
	public AST visitAssignExpression(GoParser.AssignExpressionContext ctx) {
		AST expression = visit(ctx.expression());
		
		// Checks if the variable was previously declared
		AST identifier = visit(ctx.id());
		
		Token identifierToken = ctx.id().IDENTIFIER().getSymbol();

		// Checks if the assign operation is suported
		checkAssign(identifierToken.getLine(), ctx.op.getText(), identifier.type, expression.type);

		// Defines which node kind the assign has
		NodeKind kind = null;
		int op = ctx.op.getType();
		if(op == GoParser.ASSIGN)		kind = NodeKind.ASSIGN_NODE;
		if(op == GoParser.PLUS_ASSIGN)	kind = NodeKind.PLUS_ASSIGN_NODE;
		if(op == GoParser.MINUS_ASSIGN)	kind = NodeKind.MINUS_ASSIGN_NODE;

		return AST.newSubtree(kind, Type.NO_TYPE, identifier, expression);
	}

	// Visits the rule assign_statement: IDENTIFIER op=(PLUS_PLUS | MINUS_MINUS) SEMI?
	@Override
	public AST visitAssignPPMM(GoParser.AssignPPMMContext ctx) {
		// Checks if the variable was previously declared
		AST identifier = visit(ctx.id());

		Token identifierToken = ctx.id().IDENTIFIER().getSymbol();

		// Checks if the operation is suported
		checkUnaryOp(identifierToken.getLine(), ctx.op.getText(), identifier.type);
		
		// Since the assignment wont change the var type, there is no need 
		// to call the checkAssign function
		if(ctx.op.getType() == GoParser.PLUS_PLUS) {
			return AST.newSubtree(NodeKind.PLUS_PLUS_NODE, Type.NO_TYPE, identifier);
		} else { // Minus-minus
			return AST.newSubtree(NodeKind.MINUS_MINUS_NODE, Type.NO_TYPE, identifier);
		}
	}
	
	//  Visits the rule func_call: IDENTIFIER L_PAREN expression_list? R_PAREN 
	@Override
	public AST visitFunc_call(GoParser.Func_callContext ctx) {
		Token funcToken = ctx.IDENTIFIER().getSymbol();

		// Checks if the function was previously declared
		AST node = checkFunc(funcToken);

		// Checks if the function call has any parameters
		if(ctx.expression_list() != null) {
			// Recursively visits rule for error checking
			AST child = visit(ctx.expression_list());
			node.addChild(child);
		} else {
			lastExpressionListSize = 0;
		}

		// Checks if the expressionListSize is the same size as what the function expects
		checkFuncCall(funcToken);

		return node;
	}


	// Visits the rule expression_list: expression (COMMA expression)*
	@Override
	public AST visitExpression_list(GoParser.Expression_listContext ctx) {
		lastExpressionListSize = ctx.expression().size();

		AST node = AST.newSubtree(NodeKind.EXPRESSION_LIST_NODE, Type.NO_TYPE);

		// Recursively visits each expression for error checking
		for(GoParser.ExpressionContext expr : ctx.expression()) {
			AST child = visit(expr);
			node.addChild(child);
		}

		return node;
	}


	/*------------------------------------------------------------------------------*
	 *	Visitors for expression rule
	 *------------------------------------------------------------------------------*/

	// Visits the rule expression: expression op=(STAR | DIV | MOD) expression
	@Override
	public AST visitStarDivMod(GoParser.StarDivModContext ctx) {
		// Visits both operands to check their types
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Unify the types from both operands
		Type lt = l.type;
		Type rt = r.type;
		Type unif = lt.unifyMathOps(rt);

		// Operation not allowed
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}
		
		// Defines which node kind the expression has
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

	// Visits the rule expression: expression op=(PLUS | MINUS) expression
	@Override
	public AST visitPlusMinus(GoParser.PlusMinusContext ctx) {
		// Visits both operands to check their types
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));
		
		// Unify the types from both operands
		Type lt = l.type;
		Type rt = r.type;
		Type unif = lt.unifyMathOps(rt);

		// Operation not suported
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}

		if (ctx.op.getType() == GoParser.PLUS) {
			return AST.newSubtree(NodeKind.PLUS_NODE, unif, l, r);
		} else { // MINUS
			return AST.newSubtree(NodeKind.MINUS_NODE, unif, l, r);
		}
	}

	// Visits the rule expression: expression op=( EQUALS | NOT_EQUALS | LESS | LESS_OR_EQUALS | GREATER | GREATER_OR_EQUALS) expression
	@Override
	public AST visitRelationalOperators(GoParser.RelationalOperatorsContext ctx) {
		// Visits both operands to check their types
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));
		
		// Unify the types from both operands
		Type lt = l.type;
		Type rt = r.type;
		Type unif;

		int op = ctx.op.getType();
		if(op == GoParser.EQUALS || op == GoParser.NOT_EQUALS){
			unif = lt.unifyCompare(rt);
		} else {
			unif = lt.unifyCompare2(rt);
		}

		// Operation not suported
		if (unif == Type.NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), lt, rt);
		}

		// Defines which node kind the expression has
		NodeKind kind = null;
		if (op == GoParser.EQUALS) 				kind = NodeKind.EQUALS_NODE;
		if (op == GoParser.NOT_EQUALS) 			kind = NodeKind.NOT_EQUALS_NODE;
		if (op == GoParser.LESS) 				kind = NodeKind.LESS_NODE;
		if (op == GoParser.LESS_OR_EQUALS) 		kind = NodeKind.LESS_OR_EQUALS_NODE;
		if (op == GoParser.GREATER) 			kind = NodeKind.GREATER_NODE;
		if (op == GoParser.GREATER_OR_EQUALS) 	kind = NodeKind.GREATER_OR_EQUALS_NODE;

		return AST.newSubtree(kind, unif, l, r);
	}

	// Visits the rule expression: L_PAREN expression R_PAREN 
	@Override
	public AST visitExpressionParen(GoParser.ExpressionParenContext ctx) {
		return visit(ctx.expression());
	}

	// Visits the rule expression: id
	@Override
	public AST visitExpressionId(GoParser.ExpressionIdContext ctx) {
		return visit(ctx.id());
	}

	// Visits the rule expression: func_call
	@Override
	public AST visitExpressionFuncCall(GoParser.ExpressionFuncCallContext ctx) {
		return visit(ctx.func_call());
	}

	// Visits the rule expression: DECIMAL_LIT
	@Override
	public AST visitIntVal(GoParser.IntValContext ctx) {
		int intData = Integer.parseInt(ctx.getText());
		return new AST(NodeKind.INT_VAL_NODE, intData, Type.INT_TYPE);
	}

	// Visits the rule expression: FLOAT_LIT
	@Override
	public AST visitFloatVal(GoParser.FloatValContext ctx) {
		float floatData = Float.parseFloat(ctx.getText());
		return new AST(NodeKind.FLOAT32_VAL_NODE, floatData, Type.FLOAT32_TYPE);
	}

	// Visits the rule expression: INTERPRETED_STRING_LIT
	@Override
	public AST visitStringVal(GoParser.StringValContext ctx) {
		// Adds the string to the string table.
		int idx = st.addString(ctx.INTERPRETED_STRING_LIT().getText());

		return new AST(NodeKind.STRING_VAL_NODE, idx, Type.STRING_TYPE);
	}

	// Visits the rule expression: BOOLEAN_LIT
	@Override
	public AST visitBoolVal(GoParser.BoolValContext ctx) {
		if(ctx.getText().equals("true")) {
			return new AST(NodeKind.BOOL_VAL_NODE, 1, Type.BOOL_TYPE);
		} else {
			return new AST(NodeKind.BOOL_VAL_NODE, 0, Type.BOOL_TYPE);
		}
	}

	/*------------------------------------------------------------------------------*
	 *	Visitor for id rule
	 *------------------------------------------------------------------------------*/

	// Visits the rule id: IDENTIFIER (L_BRACKET expression R_BRACKET)?
	@Override
	public AST visitId(GoParser.IdContext ctx) {
		Token identifierToken = ctx.IDENTIFIER().getSymbol();

		AST expression = null;
		// Checks if it has an array index 
		if(ctx.expression() != null) {
			// Recursively visits the expression for error checking
			expression = visit(ctx.expression());
			
			// Checks if the index is valid
			checkIndex(identifierToken.getLine(), expression.type);

			// if the parent is the func_args rule, creates the new var
			if(ctx.parent instanceof GoParser.Func_argsContext) {
				lastDeclArrayArgsSize = expression.intData;
				return newVar(identifierToken);
			}
		}

		// if the parent is the func_args rule, creates the new var
		if(ctx.parent instanceof GoParser.Func_argsContext) {
			lastDeclArrayArgsSize = 0;
			return newVar(identifierToken);
		}

		// Checks if the variable was previously declared
		AST node  = checkVar(identifierToken);
		node.addChild(expression);

		return node;

	}

}
