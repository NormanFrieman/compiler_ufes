#!/bin/bash

ROOT="$(cd .. && cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ANTLR_PATH=$ROOT/tools/antlr-4.13.2-complete.jar
JASMIN_PATH=$ROOT/tools/jasmin.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

GRAMMAR_NAME=jvm
GEN_PATH=$ROOT/generated
BIN_PATH=$ROOT/bin

IN=$ROOT/tests/casetests
TEST=$ROOT/tests/expected_codegen

cd $BIN_PATH
for infile in `ls $IN/*.go`; do
    base=$(basename $infile)
    name="${base//.go/}"
    outfile=$TEST/${name}.j
    echo Running $base
    java $CLASS_PATH_OPTION:$BIN_PATH Main $infile codegen > $outfile
    java -jar $JASMIN_PATH -d $TEST Program
    java -cp $TEST Program
done

rm -rf Program.class