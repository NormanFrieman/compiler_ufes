parser grammar jvmParser;

options {
    tokenVocab = jvmLexer;
}

// STATEMENTS
program:
    init
    stmt_sect
;

imports:
    IMPORT STRING_VALUE
;

init:
    (PACKAGE ID)*
    (imports)*
;

stmt_sect:
    (function_stmt)*
;

scope:
    commands*
;

commands:
    RETURN ID
    | function_call
    | var_init
    | var_update
;

// VALUES AND TYPES
type_primitive:
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

// Type
type_array:
    BRACKET_LEFT value_int BRACKET_RIGHT type
;

type_composite:
    type_array
;

type:
    type_primitive
    | type_composite
;

// Value
value_int:
    INT_DEC
    | INT_HEX
    | INT_OCT
    | INT_BIN
;

value_array:
    type_array BRACE_LEFT value_assign_multiple BRACE_RIGHT
    | ID BRACKET_LEFT (ID | value_int) BRACKET_RIGHT
;

value_primitive:
    value_int
    | NEGATIVE_INT_VALUE
    | FLOAT_LITERAL
    | NEGATIVE_FLOAT_LITERAL
    | STRING_VALUE
;

value_composite:
    value_array
;

value:
    value_primitive
    | value_composite
;

value_assign:
    value
    | ID
;

value_assign_multiple:
    (value_assign (COMMA value_assign)*)*
;

// VARIABLES AND ATTRIBUTES
attr:
    ID (type)?
;

attr_multiple:
    (attr (COMMA attr)*)*
;

var_init:
    VAR ID type
    | ID ASSIGN value_assign
    | (CONST | VAR) ID (type_array)? ASSIGN_VAR value_array
;

var_update:
    ID ASSIGN_VAR value
;

// FUNCTIONS
function_declaration:
    FUNCTION ID PAREN_LEFT (attr_multiple)? PAREN_RIGHT (type)?
;

function_call:
    ID PAREN_LEFT (value_assign_multiple)? PAREN_RIGHT
    | ID DOT function_call
;

function_stmt:
    function_declaration BRACE_LEFT (scope)? BRACE_RIGHT
;
