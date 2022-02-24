package checker;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import parser.GoLexer;
import parser.GoParser;

public class Main {
	//  TODO: args error checking
	public static void main(String[] args) throws IOException {
		CharStream input = CharStreams.fromFileName(args[0]);

		GoLexer lexer = new GoLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		GoParser parser = new GoParser(tokens);

		ParseTree tree = parser.program();

		if (parser.getNumberOfSyntaxErrors() != 0) {
			return;
		}

		SemanticChecker checker = new SemanticChecker();
		checker.visit(tree);

		checker.printAST();
		
	}

}
