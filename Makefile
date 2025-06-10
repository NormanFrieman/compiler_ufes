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

all: antlr javac
	@echo "Done."

antlr: ${LEXER} ${PARSER}
	${ANTLR4} -no-listener -o ${GEN_PATH} ${LEXER} ${PARSER}

javac:
	${JAVAC} ${CLASS_PATH_OPTION} ${GEN_PATH}/*.java

lexer:
	cd ${GEN_PATH} && ${GRUN} ${GRAMMAR_NAME} tokens -tokens ${IN}/${FILE}

parser:
	cd $(GEN_PATH) && $(GRUN) $(GRAMMAR_NAME) program ${IN}/$(FILE)

parserGui:
	cd $(GEN_PATH) && $(GRUN) $(GRAMMAR_NAME) program ${IN}/$(FILE) -gui

clean:
	@rm -rf ${GEN_PATH}