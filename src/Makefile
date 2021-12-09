# Makefile
JAVA=java
JAVAC=javac

ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=java org.antlr.v4.gui.TestRig

GEN_PATH=lexer

all: antlr javac 
	@echo "Done."

antlr: GoLexer.g4 GoParser.g4 
	$(ANTLR4) -no-listener -o $(GEN_PATH) GoLexer.g4 GoParser.g4

javac:
	$(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java  

run:
	cd $(GEN_PATH) && $(GRUN) Go sourceFile ../../tests/examples/anonymousMethods.go -gui

baixe:
	wget https://raw.githubusercontent.com/antlr/grammars-v4/master/golang/Java/GoParserBase.java  

baixeteste:
	wget https://raw.githubusercontent.com/antlr/grammars-v4/master/golang/examples/anonymousMethods.go

clean: 
	find lexer -type f -not -name 'GoParserBase.java' -print0 | xargs -0  -I {} rm -v {}

