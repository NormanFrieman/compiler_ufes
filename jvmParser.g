parser grammar jvmParser;

options {
    tokenVocab = jvmLexer;
}

program:
    init
    stmt_sect
;

init:
    (PACKAGE ID)*
    (IMPORT STRING_VALUE)*
;

stmt_sect:
    FUNCTION STRING_VALUE PAREN_LEFT PAREN_RIGHT BRACE_LEFT

    BRACE_RIGHT
;