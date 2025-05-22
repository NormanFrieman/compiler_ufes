#!/bin/bash

ROOT=$(pwd)
ANTLR_PATH=$ROOT/tools/antlr-4.13.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

GRAMMAR_NAME=jvm
GEN_PATH=out

IN=$ROOT/in
TEST=$ROOT/test

cd $GEN_PATH
for infile in `ls $IN/*.go`; do
    base=$(basename $infile)
    name="${base//.go/}"
    outfile=$TEST/${name}.txt
    echo Running $base
    if java $CLASS_PATH_OPTION org.antlr.v4.gui.TestRig $GRAMMAR_NAME tokens -tokens $infile 2>&1 | diff -w $outfile -; then
        echo "Deu bom"
    else
        echo "DEU RUIM"
    fi
done