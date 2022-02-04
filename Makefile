# Makefile
JAVA=java
JAVAC=javac

ROOT=~/projects/ufes/compilers/go-compiler

ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=java org.antlr.v4.gui.TestRig

GEN_PATH=lexer

# Diretório aonde está a classe com a função main.
MAIN_PATH=checker

# Diretório para os arquivos .class
BIN_PATH=bin

# Diretório para os casos de teste
DATA=$(ROOT)/tests
IN=$(DATA)/in        

all: antlr javac 
	@echo "Done."

antlr: src/GoLexer.g4 src/GoParser.g4 
	cd src && $(ANTLR4) -no-listener -visitor -o $(GEN_PATH) GoLexer.g4 GoParser.g4

javac:
	cd src && rm -rf $(BIN_PATH)
	cd src && mkdir $(BIN_PATH)
	cd src && $(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java  

#run:
#	cd src/$(GEN_PATH) && $(GRUN) Go sourceFile ../../$(FILE) -gui

run:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH)/Main $(FILE)

runall:
	-for FILE in $(IN)/*.go; do \
	 	echo -e "\nRunning $${FILE}" && \
	 	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH)/Main $${FILE}; \
	done;


clean: 
	find src/lexer -type f -not -name 'GoParserBase.java' -print0 | xargs -0  -I {} rm -v {}

