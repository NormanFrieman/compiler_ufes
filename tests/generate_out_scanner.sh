#!/bin/bash

ROOT="$(cd .. && cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ANTLR_PATH=$ROOT/tools/antlr-4.13.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

GRAMMAR_NAME=jvm
GEN_PATH=$ROOT/generated

IN=$ROOT/tests/casetests
TEST=$ROOT/tests/expected_scanner

cd $GEN_PATH
for infile in `ls $IN/*.go`; do
    base=$(basename $infile)
    name="${base//.go/}"
    outfile=$TEST/${name}.txt
    echo Running $base
    java $CLASS_PATH_OPTION org.antlr.v4.gui.TestRig $GRAMMAR_NAME tokens -tokens $infile > $outfile 2>&1
done