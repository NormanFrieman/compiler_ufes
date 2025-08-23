#!/bin/bash

ROOT="$(cd .. && cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ANTLR_PATH=$ROOT/tools/antlr-4.13.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"
JASMIN_PATH=$ROOT/tools/jasmin.jar

GRAMMAR_NAME=jvm
GEN_PATH=$ROOT/generated
BIN_PATH=$ROOT/bin

IN=$ROOT/tests/casetests
TEST=$ROOT/tests/expected_checker

GREEN='\e[32m'
RED='\e[31m'
NC='\e[0m'

cd $BIN_PATH
errors=0

# Checks whether it is possible to access the entry codes
verify=$(ls $IN/*go)
if [ $? -ne 0 ]; then
    echo -e ${RED}No such file or directory${NC}
  exit 1
fi

# Checks that the terminal output is the same as the expected output
for infile in `ls $IN/*.go`; do
    echo ${infile}
    base=$(basename $infile)
    name="${base//.go/}"
    outfile=$TEST/${name}_test.j
    expected=$TEST/${name}.j
    echo Running $base
    if java $CLASS_PATH_OPTION:$BIN_PATH Main $infile codegen > $outfile | java -jar $JASMIN_PATH -d $TEST Program | java -cp $TEST Program | diff -w $outfile $expected; then
        echo -e $base ${GREEN}success${NC}
        rm -rf $outfile
    else
        echo -e $base ${RED}error${NC}
        errors=$((errors+1))
    fi
done

if [ $errors -gt 0 ]; then
    echo -e ${RED}${errors} errors${NC}
    exit 1
else
    echo -e ${GREEN}All tests passed${NC}
fi

rm -rf Program.class