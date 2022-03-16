package code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.FuncTable;
import tables.StrTable;
import tables.VarTable;
import typing.Type;


public class Interpreter extends ASTBaseVisitor<Void> {

	private final DataStack stack;
	private final Memory memory;
	private final StrTable st;
	private final VarTable vt;
	private final FuncTable ft;
	private final Scanner in;
	private List<FunctionRef> functionRefs;

	public Interpreter(StrTable st, VarTable vt, FuncTable ft) {
		this.stack = new DataStack();
		this.memory = new Memory(vt);
		this.st = st;
		this.vt = vt;
		this.ft = ft;
		this.in = new Scanner(System.in);
		this.functionRefs = new ArrayList<FunctionRef>();
	}

	// Helper method to find the function reference when the function is called
	FunctionRef findFuncRef(String name) {
		for (FunctionRef ref : functionRefs) {
			if (ref.name == name) return ref;
		}
		return null;
	}

	/*------------------------------------------------------------------------------*
	 *	Var values
	 *------------------------------------------------------------------------------*/

	@Override
	protected Void visitBoolVal(AST node) {
		// recursion base
		stack.pushInt(node.intData);
		return null; 
	}

	@Override
	protected Void visitIntVal(AST node) {
		// recursion base
		stack.pushInt(node.intData);
		return null; 
	}

	@Override
	protected Void visitFloatVal(AST node) {
		// recursion base
		stack.pushFloat(node.floatData);
		return null; 
	}

	@Override
	protected Void visitStringVal(AST node) {
		// recursion base
		stack.pushInt(node.intData);
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
			case INT_TYPE:  		readInt(varIdx);    	break;
	        case FLOAT32_TYPE: 		readFloat32(varIdx);   	break;
			case BOOL_TYPE: 		readBool(varIdx);   	break;
			case STRING_TYPE:  		readString(varIdx); 	break;
			case NO_TYPE:
		    default:
	            System.err.printf("Invalid input type: %s!\n", varType.toString());
	            System.exit(1);
		}
		return null;
	}

	private Void readInt(int varIdx) {
		System.out.printf("read (int): ");
		int value = in.nextInt();
		memory.storeInt(varIdx, value);
		return null; 
	}

	private Void readFloat32(int varIdx) {
		System.out.printf("read (float32): ");
		float value = in.nextFloat();
		memory.storeFloat(varIdx, value);
		return null;
	}

	private Void readBool(int varIdx) {
		int value;
	    do {
	        System.out.printf("read (bool - 0 = false, 1 = true): ");
	        value = in.nextInt();
	    } while (value != 0 && value != 1);
	    memory.storeInt(varIdx, value);
	    return null;
	}

	private Void readString(int varIdx) {
		System.out.printf("read (str): ");

		// Changes the default delimiter to read senteces with spaces
		// then resets it
		in.useDelimiter("\n");
		String s = in.next();
		in.reset();

		int strIdx = st.addString(s);
		memory.storeInt(varIdx, strIdx);
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
				case INT_TYPE:  	writeInt();			break;
				case FLOAT32_TYPE: 	writeFloat32();		break;
				case BOOL_TYPE: 	writeBool();   		break;
				case STRING_TYPE:  	writeString();		break;
				case NO_TYPE:
				default:
					System.err.printf("Invalid output type: %s!\n", expression.type.toString());
					System.exit(1);
			}
			System.out.print(" ");
		}
		System.out.println();

		return null;
	}

	private Void writeInt() {
		System.out.print(stack.popInt());
		return null;
	}

	private Void writeFloat32() {
		System.out.print(stack.popFloat());
		return null;
	}

	private Void writeBool() {
		if (stack.popInt() == 0) {
			System.out.print("false");
		} else {
			System.out.print("true");
		}
		return null;
	}

	private Void writeString() {
		int stringIdx = stack.popInt();
		String originalString = st.get(stringIdx);
		System.out.print(originalString.replace("\"", ""));
		return null;
	}

	/*------------------------------------------------------------------------------*
	 *	Relational operations
	 *------------------------------------------------------------------------------*/

	//  Helper method to execute the '==', '!=', '<', '<=', '>', '>=' comparison operations
	private void execComparison(Type t, String op) {
		// Required to initialize, but the if clauses will overwrite the value
		boolean result = false;

		// Compares INT and BOOL (represented as int) values 
		if(t == Type.INT_TYPE || t == Type.BOOL_TYPE) {
			int r = stack.popInt();
			int l = stack.popInt();

			// Some of these operations are not valid for BOOL_TYPE, 
			// but the semantic checker handles it
			switch (op) {
				case "==":		result = l == r; 	break;
				case "!=":		result = l != r;	break;
				case "<":		result = l < r;		break;
				case "<=":		result = l <= r;	break;
				case ">":		result = l > r;		break;
				case ">=":		result = l >= r;	break;
			}
		}

		// Compares strings
		if(t == Type.STRING_TYPE) {
			String rString = st.get(stack.popInt());
			String lString = st.get(stack.popInt());

			switch (op) {
				case "==":		result = lString.compareTo(rString) == 0;		break;
				case "!=":		result = lString.compareTo(rString) != 0;		break;
				case "<":		result = lString.compareTo(rString) < 0;		break;
				case "<=":		result = lString.compareTo(rString) <= 0;		break;
				case ">":		result = lString.compareTo(rString) > 0;		break;
				case ">=":		result = lString.compareTo(rString) >= 0;		break;
			}
		}

		// Compares FLOAT32 values
		if(t == Type.FLOAT32_TYPE) {
			float r = stack.popFloat();
			float l = stack.popFloat();

			switch (op) {
				case "==":		result = l == r;	break;
				case "!=":		result = l != r;	break;
				case "<":		result = l < r;		break;
				case "<=":		result = l <= r;	break;
				case ">":		result = l > r;		break;
				case ">=":		result = l >= r;	break;
			}
		}

		// Pushes the result to the stack
		if (result) {
			stack.pushInt(1); // true
		} else {
			stack.pushInt(0); // false
		}

	}

	@Override
	protected Void visitEquals(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, "==");

		return null; 
	}

	@Override
	protected Void visitNotEquals(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, "!=");

		return null;
	}

	@Override
	protected Void visitLess(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, "<");

		return null;
	}

	@Override
	protected Void visitLessOrEquals(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, "<=");

		return null; 
	}

	@Override
	protected Void visitGreater(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, ">");

		return null;  
	}

	@Override
	protected Void visitGreaterOrEquals(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execComparison(lNode.type, ">=");

		return null; 
	}

	/*------------------------------------------------------------------------------*
	 *	Arithmetic operations
	 *------------------------------------------------------------------------------*/

	//  Helper method to execute the '+', '-', '*', '/', '%' operations
	private void execArithmetic(Type t, String op) {
		// Executes arithmetic operations with INT values
		if(t == Type.INT_TYPE) {
			int r = stack.popInt();
			int l = stack.popInt();

			int result = 0;
			switch (op) {
				case "+":		result = l + r;		break;
				case "-":		result = l - r;		break;
				case "*":		result = l * r;		break;
				case "/":		result = l / r;		break;
				case "%":		result = l % r;		break;
			}

			stack.pushInt(result);
		}

		// Executes arithmetic operations with FLOAT32 values
		if(t == Type.FLOAT32_TYPE) {
			float r = stack.popFloat();
			float l = stack.popFloat();

			float result = 0;
			switch (op) {
				case "+":		result = l + r;		break;
				case "-":		result = l - r;		break;
				case "*":		result = l * r;		break;
				case "/":		result = l / r;		break;
				case "%":		result = l % r;		break;
			}

			stack.pushFloat(result);
		}
	}

	@Override
	protected Void visitStar(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execArithmetic(lNode.type, "*");

		return null;
	}

	@Override
	protected Void visitDiv(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execArithmetic(lNode.type, "/");

		return null;
	}

	@Override
	protected Void visitMod(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execArithmetic(lNode.type, "%");

		return null; 
	}

	@Override
	protected Void visitPlus(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execArithmetic(lNode.type, "+");

		return null;
	}

	@Override
	protected Void visitMinus(AST node) {
		AST lNode = node.getChild(0);
		AST rNode = node.getChild(1);

		visit(lNode);
		visit(rNode);

		execArithmetic(lNode.type, "-");

		return null;
	}


	/*------------------------------------------------------------------------------*
	 *	Statements
	 *------------------------------------------------------------------------------*/

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

	// Helper method to execute the '=', '+=' and '-=' assign operations
	private void execAssign(Type t, int varIdx, String op) {
		// Executes the assign operations for INT, BOOL (represented as int) and 
		// STRING (represented as the string index at the string table) types
		if (t == Type.INT_TYPE || t == Type.BOOL_TYPE || t == Type.STRING_TYPE) {
			// Load the var value from memory
			int varValue = memory.loadInt(varIdx);

			int number = stack.popInt();
	
			switch (op) {
				case "=":		varValue = number;		break;
				case "+=":  	varValue += number;		break;
				case "-=":  	varValue -= number;		break;
			}
	
			// Updates the var value in memory
			memory.storeInt(varIdx, varValue);
		}

		// Executes the assign operations for FLOAT32 type
		if (t == Type.FLOAT32_TYPE) {
			// Load the var value from memory
			float varValue = memory.loadFloat(varIdx);

			float number = stack.popFloat();
	
			switch (op) {
				case "=":		varValue = number;		break;
				case "+=":  	varValue += number;		break;
				case "-=":  	varValue -= number;		break;
			}
	
			// Updates the var value in memory
			memory.storeFloat(varIdx, varValue);
		} 	
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
	
			execAssign(varType, varIdx, "=");
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

		execAssign(varType, varIdx, "=");

		return null;
	}

	@Override
	protected Void visitPlusAssign(AST node) {
		// Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		execAssign(varType, varIdx, "+=");

		return null;
	}

	@Override
	protected Void visitMinusAssign(AST node) {
		// Visits the expression to push its value to the stack
		visit(node.getChild(1));

		// Get the var index and type 
		int varIdx = node.getChild(0).intData;
		Type varType = vt.getType(varIdx);

		execAssign(varType, varIdx, "-=");

		return null;
	}

	// Helper method to execute the '++' and '--' unary operations
	private void execUnary(Type t, int varIdx, String op) {
		if (t == Type.INT_TYPE) {
			int number = stack.popInt();

			switch (op) {
				case "++":  number++; 	break;
				case "--":  number--; 	break;
			}

			// Updates the var value in memory
			memory.storeInt(varIdx, number);

			stack.pushInt(number);
		}

		if (t == Type.FLOAT32_TYPE) {
			float number = stack.popInt();

			switch (op) {
				case "++":  number++; 	break;
				case "--":  number--; 	break;
			}

			// Updates the var value in memory
			memory.storeFloat(varIdx, number);

			stack.pushFloat(number);
		}
	}

	@Override
	protected Void visitPlusPlus(AST node) {
		AST child = node.getChild(0);

		visit(child);

		execUnary(child.type, child.intData, "++");

		return null;
	}

	@Override
	protected Void visitMinusMinus(AST node) {
		AST child = node.getChild(0);

		visit(child);

		execUnary(child.type, child.intData, "--");

		return null;
	}

	@Override
	protected Void visitIf(AST node) {
		// Visits the condition node
		visit(node.getChild(0));

		int condition = stack.popInt();
		
		if (condition == 1) {
			// Visits the IF statement section
			visit(node.getChild(1));
		} else {
			// Checks if there is an ELSE node
			if (condition == 0 && node.getChildren().size() == 3) {
				// Visits the ELSE node
				visit(node.getChild(2));
			}
		}

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
		int size = node.getChildren().size();

		// Has both the condition and statement section
		if (size == 2) {
			// Visits the condition for the first time
			visit(node.getChild(0));

			int condition = stack.popInt();
			
			while (condition == 1) {
				// Visits the statement section
				visit(node.getChild(1));
				
				// Visits the condition again to reevaluate it
				visit(node.getChild(0));
				condition = stack.popInt();
			}
		}

		// Doenst have a condition to be evaluated
		if(size == 1) {
			while(true) {
				// Visits the statement section
				visit(node.getChild(0));
			}
		}

		return null; 
	}

	@Override
	protected Void visitFor(AST node) {
		// Visits the var declaration node
		visit(node.getChild(0));

		// Visits the condition for the first time and pops the result from the stack
		visit(node.getChild(1));
		int condition = stack.popInt();
		
		while (condition == 1) {
			// Visits the statement section
			visit(node.getChild(3));
			
			// Visits the var increment node
			visit(node.getChild(2));
			stack.popInt(); // removes the incremented var value from the stack
			
			// Visits the condition again to reevaluate it
			visit(node.getChild(1));
			condition = stack.popInt();
		}

		return null; 
	}
	
	@Override
	protected Void visitFuncCall(AST node) {
		int funcIdx = node.intData;
		String name = ft.getName(funcIdx);

		FunctionRef ref = findFuncRef(name);
		AST expressionList = node.getChild(0);

		if (ref.args != null) {
			// Assigns every value from the expression list to its corresponding arg
			for(int i = 0; i < ref.args.getChildren().size(); i++) {
				// Visits the expression and pushes its value to the stack
				visit(expressionList.getChild(i));
	
				AST arg = ref.args.getChild(i);
	
				execAssign(arg.type, arg.intData, "=");
			}
		}

		visit(ref.statementSection);
		
		return null;
	}

	/*------------------------------------------------------------------------------*
	 *	Functions
	 *------------------------------------------------------------------------------*/

	@Override
	protected Void visitFuncMain(AST node) {
		// Visits the main function's statement section
		visit(node.getChild(0));

		return null; 
	}

	@Override
	protected Void visitFuncDecl(AST node) {
		AST argsNode = null;
		AST stmtNode = null;

		if (node.getChildren().size() == 1) {
			// Func doenst have an arg list
			stmtNode = node.getChild(0);
		} else {
			// Func has both arg list and stmt
			argsNode = node.getChild(0);
			stmtNode = node.getChild(1);
		}

		int funcIdx = node.intData;
		String name = ft.getName(funcIdx);

		// Creates a new function reference
		FunctionRef funcRef = new FunctionRef(name, argsNode, stmtNode);
		
		// Saves the reference for future function calls
		functionRefs.add(funcRef);

		return null;
	}

	@Override
	protected Void visitFuncArgs(AST node) {
		return null; 
	}

	/*------------------------------------------------------------------------------*
	 *	Others
	 *------------------------------------------------------------------------------*/

	@Override
	protected Void visitExpressionList(AST node) {
		return null; 
	}

	@Override
	protected Void visitProgram(AST node) {
		// Visits the function list node
		visit(node.getChild(0));

		// End of program, no need to read from stdin anymore
		in.close();

		return null; 
	}

	@Override
	protected Void visitFuncList(AST node) {
		for (AST child : node.getChildren()) {
			visit(child);
		}
		return null; 
	}

	@Override
	protected Void visitVarUse(AST node) {
		int varIdx = node.intData;
		if (node.type == Type.FLOAT32_TYPE) {
			stack.pushFloat(memory.loadFloat(varIdx));
		} else {
			stack.pushInt(memory.loadInt(varIdx));
		}
		return null; 
	}

	// Helper class to save the statement section and arg list from each function
	private class FunctionRef {
		String name;
		AST args;
		AST statementSection;

		public FunctionRef(String name, AST args, AST stmt) {
			this.name = name;
			this.args = args;
			this.statementSection = stmt;
		}
	}
}
