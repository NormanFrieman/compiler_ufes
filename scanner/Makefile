JAVAC=javac

JAVA=java

ROOT=/home-temp/aluno/Documents/trab/scanner

ANTLR_PATH=${ROOT}/tools/antlr-4.13.2-complete.jar

CLASS_PATH_OPTION=-cp .:${ANTLR_PATH}

ANTLR4=${JAVA} -jar ${ANTLR_PATH}

GRUN=${JAVA} ${CLASS_PATH_OPTION} org.antlr.v4.gui.TestRig

GRAMMAR_NAME=jvm

GEN_PATH=out

all: antlr javac
	@echo "Done."

antlr: ${GRAMMAR_NAME}.g
	${ANTLR4} -o ${GEN_PATH} ${GRAMMAR_NAME}.g

javac:
	${JAVAC} ${CLASS_PATH_OPTION} ${GEN_PATH}/*.java

run:
	cd ${GEN_PATH} && ${GRUN} ${GRAMMAR_NAME} tokens -tokens ../in/${FILE}

clean:
	@rm -rf ${GEN_PATH}