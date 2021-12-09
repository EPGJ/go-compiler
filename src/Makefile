# Makefile
JAVA=java
JAVAC=javac

ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=java org.antlr.v4.gui.TestRig

GEN_PATH=src/lexer

all: antlr javac 
	@echo "Done."

antlr: GoLexer.g4 GoParser.g4
	$(ANTLR4)  GoLexer.g4 GoParser.g4

javac:
	javac -cp /usr/local/lib/antlr-4.9.2-complete.jar *.java 

run:
	$(GRUN) Go sourceFile ../tests/examples/anonymousMethods.go -gui

baixe:
	wget https://raw.githubusercontent.com/antlr/grammars-v4/master/golang/Java/GoParserBase.java  

baixeteste:
	wget https://raw.githubusercontent.com/antlr/grammars-v4/master/golang/examples/anonymousMethods.go

clean:
	@rm *.class *.java *.tokens  *.interp

#all: 

