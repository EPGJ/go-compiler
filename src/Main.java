

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import checker.SemanticChecker;

import parser.GoLexer;
import parser.GoParser;
import code.Interpreter;
import code.LLVMGenerator;


public class Main {
	public static void main(String[] args) throws IOException {
		// Cria um CharStream que lê os caracteres de um arquivo.
		// O livro do ANTLR fala para criar um ANTLRInputStream,
		// mas a partir da versão 4.7 essa classe foi deprecada.
		// Esta é a forma atual para criação do stream.
		CharStream input = CharStreams.fromFileName(args[0]);
		String flag = args[1];

		// Cria um lexer que consome a entrada do CharStream.
		GoLexer lexer = new GoLexer(input);

		// Cria um buffer de tokens vindos do lexer.
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// Cria um parser que consome os tokens do buffer.
		GoParser parser = new GoParser(tokens);

		// Começa o processo de parsing na regra 'program'.
		ParseTree tree = parser.program();

		if (parser.getNumberOfSyntaxErrors() != 0) {
			// Houve algum erro sintático. Termina a compilação aqui.
			return;
		}

		// Cria o analisador semântico e visita a ParseTree para
		// fazer a análise.
		SemanticChecker checker = new SemanticChecker();
		checker.visit(tree);

		

		if(flag.equals("-i")){
			Interpreter interpreter = new Interpreter(checker.st, checker.vt, checker.ft);
			interpreter.execute(checker.root);
		} else {
			LLVMGenerator codeGen = new LLVMGenerator(checker.st, checker.vt, checker.ft);
			codeGen.execute(checker.root);
		}

		
	}

}
