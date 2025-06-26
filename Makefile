JAVAC=javac

JAVA=java

ROOT:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

ANTLR_PATH=${ROOT}/tools/antlr-4.13.2-complete.jar

CLASS_PATH_OPTION=-cp .:${ANTLR_PATH}

ANTLR4=${JAVA} -jar ${ANTLR_PATH}

GRUN=${JAVA} ${CLASS_PATH_OPTION} org.antlr.v4.gui.TestRig

GRAMMAR_NAME=jvm

LEXER=${ROOT}/${GRAMMAR_NAME}Lexer.g

PARSER=${ROOT}/${GRAMMAR_NAME}Parser.g

IN=${ROOT}/tests/casetests

GEN_PATH=generated

BIN_PATH=bin

all: antlr javac
	@echo "Done."

antlr: ${LEXER} ${PARSER}
	${ANTLR4} -no-listener -visitor -o ${GEN_PATH} ${LEXER} ${PARSER}

javac:
	rm -rf ${BIN_PATH}
	mkdir ${BIN_PATH}
	${JAVAC} ${CLASS_PATH_OPTION} -d ${BIN_PATH} */*.java Main.java

# Executar o lexer
lexer:
	cd ${GEN_PATH} && ${GRUN} ${GRAMMAR_NAME} tokens -tokens ${IN}/${FILE}

# Executa o parser
parser:
	cd $(GEN_PATH) && $(GRUN) $(GRAMMAR_NAME) program ${IN}/$(FILE)

# Exibe a parse tree
parserGui:
	cd $(GEN_PATH) && $(GRUN) $(GRAMMAR_NAME) program ${IN}/$(FILE) -gui

# Executa o main
run:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) Main $(IN)/$(FILE)

# Remove os arquivos gerados
clean:
	@rm -rf ${GEN_PATH} ${BIN_PATH}