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
    (function_call | var_declaration)
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
    | BRACKET_LEFT INT_DEC BRACKET_RIGHT type
;

value:
    STRING_VALUE
    | ID
    | value (COMMA value)?
;

var_declaration_composite:
    ID ASSIGN composite
;

composite:
    INT_DEC
    | STRING_VALUE
    | BRACKET_LEFT (type)? BRACKET_RIGHT type BRACE_LEFT value BRACE_RIGHT
    | BRACKET_LEFT (type)? BRACKET_RIGHT type BRACE_LEFT value COLON value (COMMA value COLON value)? BRACE_RIGHT
;

compare:
    EQUAL
    | NOT_EQUAL
    | LESS
    | GREATER 
    | LESS_EQ  
    | GREATER_EQ
;

value_move:
    PLUS PLUS
    | MINUS MINUS
    | PLUS INT_DEC
    | MINUS INT_DEC
;

loop_call:
    FOR var_declaration_composite SEMICOLON ID compare INT_DEC SEMICOLON ID value_move expres
    | var_declaration_composite FOR ID compare INT_DEC expres
    | FOR expres
    | var_declaration_composite FOR ID COMMA ID ASSIGN RANGE ID expres
    | FOR UNDERSCORE COMMA ID ASSIGN RANGE ID expres
;

expres:

;
