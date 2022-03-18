JAVA=java
JAVAC=javac

# O ANTLR deve estar instalado numa pasta chamada tools na raiz do projeto
ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)

# Diretório com o código fonte.
SOURCE_PATH=src

# Diretório para aonde vão os arquivos gerados pelo ANTLR.
GEN_PATH=$(SOURCE_PATH)/parser

# Diretório aonde está a classe com a função main.
MAIN_PATH=$(SOURCE_PATH)/checker

# Diretório para os arquivos .class
BIN_PATH=$(SOURCE_PATH)/bin

# Diretório para os casos de teste
IN=tests

all: antlr javac
	@echo -e "\nDone."

antlr:
	@echo -e "Generating parser with ANTLR..."
	$(ANTLR4) -no-listener -visitor -package parser -Xexact-output-dir -o $(GEN_PATH) $(SOURCE_PATH)/GoLexer.g4 $(SOURCE_PATH)/GoParser.g4

javac:
	@echo -e "\nCompiling checker..."
	@rm -rf $(BIN_PATH)
	@mkdir $(BIN_PATH)
	$(JAVAC) $(CLASS_PATH_OPTION) -d $(BIN_PATH)  $(SOURCE_PATH)/Main.java  $(SOURCE_PATH)/*/*.java

run:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) Main $(file) $(flag)

simulator:
	@echo -e "\nCompiling the NSTM Simulator..."
	gcc -Wall -Wconversion -o NSTMsimulator $(SOURCE_PATH)/nstm/*.c 

runsim:
	./NSTMsimulator < $(file)

clean:
	@rm -rf $(GEN_PATH) $(BIN_PATH) $(SOURCE_PATH)/.antlr target/ tests/*/*.pdf *.dot *.pdf