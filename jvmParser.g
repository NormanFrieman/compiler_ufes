parser grammar jvmParser;

options {
    tokenVocab = jvmLexer;
}

program:
    init
    stmt_sect
;

imports:
    IMPORT STRING_VALUE
    | IMPORT PAREN_LEFT (STRING_VALUE)+ PAREN_RIGHT
;

init:
    (PACKAGE ID)*
    (imports)*
;

stmt_sect:
    (declaration_struct)*
    (function_declaration expression)+
;

expression:
    BRACE_LEFT
    (function_call
    | var_declaration
    | var_assign
    | loop_call
    | instance_struct
    | ID value_move
    | if_stmt
    | BREAK
    | CONTINUE)*
    (ret)?
    BRACE_RIGHT
;

// GENERAL
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

type:
    type_primitive
    | type_array
    | type_map
;

type_array:
    BRACKET_LEFT (ints)? BRACKET_RIGHT (type | ID)
    | BRACKET_LEFT DOT DOT DOT BRACKET_RIGHT type
;

type_map:
    MAP BRACKET_LEFT type BRACKET_RIGHT type
;

declaration_struct:
    TYPE ID STRUCT BRACE_LEFT
    (ID type)*
    BRACE_RIGHT
;

instance_struct:
    ID ASSIGN ID BRACE_LEFT (attr_struct (COMMA attr_struct)* (COMMA)? )? BRACE_RIGHT
;

attr_struct:
    ID COLON value
;

ints:
    INT_DEC
    | INT_HEX
    | INT_OCT
    | INT_BIN
;

value:
    STRING_VALUE
    | CHAR_VALUE
    | ints
    | FLOAT_LITERAL
    | ID
    | (declaration_array | value_array)
    | value_map
    | (TRUE | FALSE)
;

operations:
    PLUS | MINUS | TIMES | DIV
;

expr_math:
    (ID | ints | FLOAT_LITERAL | conversion) (operations expr_math)*
;

expr_bool:
    (ID | value | function_call) (compare (expr_bool | expr_math))*
;

declaration_array:
    type_array BRACE_LEFT ( value (COMMA value)* )? BRACE_RIGHT
;

value_array:
    ID BRACKET_LEFT ints BRACKET_RIGHT
    | ID BRACKET_LEFT ID BRACKET_RIGHT
    | ID BRACKET_LEFT value BRACKET_RIGHT
;

value_map:
    type_map BRACE_LEFT ( attr_map (COMMA attr_map)* (COMMA)? )? BRACE_RIGHT
;

attr_map:
    value COLON (value | BRACE_LEFT (attr_json COMMA)* BRACE_RIGHT)
;

attr_json:
    BRACE_LEFT ( attr_struct (COMMA attr_struct)* (COMMA)? )* BRACE_RIGHT
;

value_move:
    PLUS PLUS
    | MINUS MINUS
    | PLUS ints
    | MINUS ints
    | PLUS ASSIGN_VAR
;

compare:
    EQUAL
    | NOT_EQUAL
    | LESS
    | GREATER 
    | LESS_EQ  
    | GREATER_EQ
;

logical:
    AND
    | OR
    | NOT
    | MOD
;

conversion:
    type_primitive PAREN_LEFT (value | ID | function_call) PAREN_RIGHT
;

// FUNCTIONS
param_value:
    value
    | function_call
    | expr_math
    | expr_bool
    | value_move
    | type_map
;

param_declaration:
    ID type
;

function_declaration:
    FUNCTION ID PAREN_LEFT (param_declaration (COMMA param_declaration)*)? PAREN_RIGHT type?
;

function_call:
    ID PAREN_LEFT (param_value (COMMA param_value)*)* PAREN_RIGHT
    | ID (DOT function_call)*
;

ret:
    RETURN 
    | RETURN (expr_math | expr_bool)
;

// VARIABLES
var_declaration:
    VAR ID type
    | var_declaration ASSIGN_VAR value
;

var_assign:
    (ID | UNDERSCORE) (COMMA (ID | UNDERSCORE))* (ASSIGN | ASSIGN_VAR) (value | function_call | expr_math | expr_bool)
    | ID BRACKET_LEFT value BRACKET_RIGHT ASSIGN_VAR (value | function_call | expr_math | expr_bool)
    | ID BRACKET_LEFT value BRACKET_RIGHT value_move
;

// LOOP
loop_call:
    FOR var_assign SEMICOLON ID compare ints SEMICOLON ID value_move expression
    | FOR var_assign SEMICOLON ID compare function_call SEMICOLON ID value_move expression
    | FOR ID (COMMA ID)* ASSIGN RANGE ID expression
    | FOR ID compare ints expression
    | FOR expression
    | FOR UNDERSCORE COMMA ID ASSIGN RANGE (ID | function_call | value_array) expression
;

// CONDICIONAL
if_condicional:
    (ID | value | function_call | NULL) (compare (ID | value | function_call | NULL))?
;

if_stmt:
    IF if_condicional (logical if_condicional)* expression
    | if_stmt ELSE (if_stmt | expression)
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
