# Makefile
JAVA=java
JAVAC=javac

ROOT=~/projects/ufes/compilers/go-compiler

ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=java org.antlr.v4.gui.TestRig

GEN_PATH=lexer

DATA=$(ROOT)/tests
IN=$(DATA)/in        

all: antlr javac 
	@echo "Done."

antlr: src/GoLexer.g4 src/GoParser.g4 
	cd src && $(ANTLR4) -no-listener -o $(GEN_PATH) GoLexer.g4 GoParser.g4

javac:
	cd src && $(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java  

run:
	cd src/$(GEN_PATH) && $(GRUN) Go sourceFile $(FILE) -gui

#runall:
#	-for FILE in $(IN)/*.go; do \
#		cd src/$(GEN_PATH) && \
#		echo -e "\Running $${FILE}" && \
#		$(GRUN) Go sourceFile $${FILE} -gui && \
#		cd ../.. ; \
#	done;

clean: 
	find src/lexer -type f -not -name 'GoParserBase.java' -print0 | xargs -0  -I {} rm -v {}

