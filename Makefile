
JAVA=java
JAVAC=javac

# Eu uso ROOT como o diretório raiz para os meus labs.
YEAR=$(shell pwd | grep -o '20..-.')
ROOT=~/projects/ufes/compilers/

ANTLR_PATH=/usr/local/lib/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=$(JAVA)

# Diretório para aonde vão os arquivos gerados.
GEN_PATH=lexer

# Diretório para os casos de teste
DATA=$(ROOT)/tests
IN=$(DATA)/in

all: antlr javac
	@echo "Done."

# Opção -no-listener foi usada para que o ANTLR não gere alguns arquivos
# desnecessários para o momento. Isto será explicado melhor nos próximos labs.
antlr: PhpLexer.g4 PhpParser.g4
	$(ANTLR4) -no-listener -o $(GEN_PATH) PhpLexer.g4 PhpParser.g4

javac:
	$(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java

# 'EZ' é o prefixo comum das duas gramáticas (EZLexer e EZParser).
# 'program' é a regra inicial de EZParser.
run:
	cd $(GEN_PATH) && $(GRUN) PhpParser $(FILE)

runall:
	-for FILE in $(IN)/*.php; do \
	 	cd $(GEN_PATH) && \
	 	echo -e "\nRunning $${FILE}" && \
	 	$(GRUN)  PhpParser $${FILE} && \
	 	cd .. ; \
	done;

clean:
	@rm -rf $(GEN_PATH)
