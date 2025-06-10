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
    expression
;

expression:
    BRACE_LEFT
    (function_call | var_declaration | var_assign | loop_call)*
    BRACE_RIGHT
;

// GENERAL
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
    | type_array
    | type_map
;

type_array:
    BRACKET_LEFT (ints)? BRACKET_RIGHT type
    | BRACKET_LEFT DOT DOT DOT BRACKET_RIGHT type
;

type_map:
    MAP BRACKET_LEFT type BRACKET_RIGHT type
;

ints:
    INT_DEC
    | INT_HEX
    | INT_OCT
    | INT_BIN
;

value:
    STRING_VALUE
    | ints
    | FLOAT_LITERAL
    | ID
    | value_array
    | value_map
    // | value (COMMA value)?
;

value_array:
    type_array BRACE_LEFT ( ints (COMMA ints)* )? BRACE_RIGHT
    | ID BRACKET_LEFT ints BRACKET_RIGHT
    | ID BRACKET_LEFT ID BRACKET_RIGHT
    | ID BRACKET_LEFT value BRACKET_RIGHT
;

value_map:
    type_map BRACE_LEFT ( value COLON value (COMMA value COLON value)* (COMMA)? )? BRACE_RIGHT
;

value_move:
    PLUS PLUS
    | MINUS MINUS
    | PLUS ints
    | MINUS ints
;

compare:
    EQUAL
    | NOT_EQUAL
    | LESS
    | GREATER 
    | LESS_EQ  
    | GREATER_EQ
;

// FUNCTIONS
param_value:
    value
    | function_call
;

param_declaration:
    ID type
;

function_declaration:
    FUNCTION ID PAREN_LEFT (param_declaration (COMMA param_declaration)*)? PAREN_RIGHT type?
;

function_call:
    ID PAREN_LEFT (param_value (COMMA param_value)*)* PAREN_RIGHT
    | ID (DOT function_call)+
;

// VARIABLES
var_declaration:
    VAR ID type
    | var_declaration ASSIGN_VAR value
;

var_assign:
    ID ASSIGN value
    | ID ASSIGN_VAR function_call
    | ID BRACKET_LEFT value BRACKET_RIGHT ASSIGN_VAR value
;

// LOOP
loop_call:
    FOR var_assign SEMICOLON ID compare ints SEMICOLON ID value_move expression
    | FOR var_assign SEMICOLON ID compare function_call SEMICOLON ID value_move expression
    | FOR ID (COMMA ID)* ASSIGN RANGE ID expression
    | FOR ID compare ints expression
    | FOR expression
    | FOR UNDERSCORE COMMA ID ASSIGN RANGE ID expression
;













// var_declaration_composite:
//     ID ASSIGN composite
// ;

// composite:
//     INT_DEC
//     | STRING_VALUE
//     | BRACKET_LEFT (type)? BRACKET_RIGHT type BRACE_LEFT value BRACE_RIGHT
//     | BRACKET_LEFT (type)? BRACKET_RIGHT type BRACE_LEFT value COLON value (COMMA value COLON value)? BRACE_RIGHT
// ;

// expres:

// ;
