ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ANTLR_PATH=$ROOT/tools/antlr-4.13.2-complete.jar

LEXER=$ROOT/jvmLexer.g
PARSER=$ROOT/jvmParser.g

CLASS_PATH_OPTION="-cp .:'$ANTLR_PATH'"

GRAMMAR_NAME=jvm
GEN_PATH=$ROOT/generated
BIN_PATH=$ROOT/bin
IN=$ROOT/tests/casetests

ANTLR="java -jar '$ANTLR_PATH' -no-listener -visitor -o '$GEN_PATH' '$LEXER' '$PARSER'"

JAVAC="javac $CLASS_PATH_OPTION -d '$BIN_PATH' */*.java Main.java"

FILE=array1.go
PARSER="cd '$BIN_PATH' && java $CLASS_PATH_OPTION org.antlr.v4.gui.TestRig generated.$GRAMMAR_NAME program '$IN'/$1 -gui"

echo ${ROOT}
echo ${ANTLR}
echo ${JAVAC}
echo ${PARSER}

eval "$ANTLR"
rm -rf $BIN_PATH
eval "$JAVAC"
eval "$PARSER"
