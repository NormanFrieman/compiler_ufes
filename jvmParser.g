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
    function_declaration
    BRACE_LEFT
    function_call
    BRACE_RIGHT
;

var_declaration:
    VAR ID type
;

param_declaration:
    ID type
;

function_declaration:
    FUNCTION ID PAREN_LEFT (param_declaration (COMMA param_declaration)*)? PAREN_RIGHT type?
;

function_call:
    ID PAREN_LEFT value? PAREN_RIGHT
    | ID (DOT function_call)+
;

type:
    TYPE_UINT
    | TYPE_INT
    | TYPE_INT8
    | TYPE_INT16
    | TYPE_INT32
    | TYPE_INT64
    | TYPE_FLOAT32
    | TYPE_FLOAT64
    | TYPE_STRING
    | TYPE_BOOL
;

value:
    STRING_VALUE
;