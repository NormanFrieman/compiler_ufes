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
    | IMPORT PAREN_LEFT (STRING_VALUE)+ PAREN_RIGHT
;

init:
    (PACKAGE ID)*
    (imports)*
;

stmt_sect:
    (function_stmt)*
;

scope:
    command*
;

command:
    return_stmt
    | function_call
    | var_init
    | var_update
    | for_stmt
    | if_stmt
;

// VALUES AND TYPES

// Type
type_composite:
    BRACKET_LEFT (value_primitive)? BRACKET_RIGHT type
    | PAREN_LEFT (ID | type) (COMMA (ID | TYPE))+ PAREN_RIGHT
;

type:
    // type primitive
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

    | type_composite
;

// Value
value_increase:
    PLUS PLUS
    | MINUS MINUS
;

value_primitive:
    INT_DEC
    | INT_HEX
    | INT_OCT
    | INT_BIN
    | NEGATIVE_INT_VALUE
    | FLOAT_LITERAL
    | NEGATIVE_FLOAT_LITERAL
    | STRING_VALUE
    | CHAR_VALUE
    | TRUE
    | FALSE
;

value_composite:
    // value array
    type_composite BRACE_LEFT (value_assign | value_assign_multiple) BRACE_RIGHT
    | ID BRACKET_LEFT (ID | value_primitive) BRACKET_RIGHT

    // value struct
    | ID (DOT ID)+
;

value:
    value_primitive
    | value_composite
;

value_assign:
    value
    | ID
    | function_call
;

value_assign_multiple:
    value_assign (COMMA value_assign)+
;

// Math expressions
math_operations:
    PLUS
    | MINUS
    | TIMES
    | DIV
    | MOD
;

math_expr:
    (ID | value_primitive | function_call) math_operations (ID | value_primitive | function_call)
    | (ID | value_primitive | function_call) math_operations math_expr
;

// Bool expressions
bool_operations:
    AND
    | OR
;

compare:
    EQUAL
    | NOT_EQUAL
    | LESS
    | GREATER 
    | LESS_EQ  
    | GREATER_EQ
;

bool_inputs:
    ID
    | value
    | function_call
    | math_expr
    | NULL
;

bool_expr:
    bool_inputs
    | bool_inputs compare bool_inputs
    | bool_inputs compare PAREN_LEFT bool_expr PAREN_RIGHT
    | PAREN_LEFT bool_expr PAREN_RIGHT compare bool_inputs
;

bool_stmt:
    bool_expr
    | (NOT)? (PAREN_LEFT)? (NOT)? bool_expr bool_operations (NOT)? bool_expr (PAREN_RIGHT)?
    | bool_stmt bool_operations bool_stmt
;

// VARIABLES AND ATTRIBUTES
attr:
    ID (type)? (COMMA (ID (type)?))*
;

var_or_under:
    ID | UNDERSCORE
;

var_without_var:
    (var_or_under) (COMMA var_or_under)* ASSIGN value_assign
;

var_init:
    VAR ID type #InitWithVar
    | (var_or_under) (COMMA var_or_under)* ASSIGN value_assign #InitiWithoutVar
    | (CONST | VAR) ID (type_composite)? ASSIGN_VAR value_composite #InitArray
;

var_update:
    ID ASSIGN_VAR value_assign
;

// FUNCTIONS
function_declaration:
    FUNCTION ID PAREN_LEFT (attr)? PAREN_RIGHT (type)?
;

function_call:
    ID PAREN_LEFT (value_assign | value_assign_multiple) PAREN_RIGHT
    | ID DOT function_call
;

function_stmt:
    function_declaration BRACE_LEFT scope BRACE_RIGHT
;

return_stmt:
    RETURN (ID | function_call)
;

// LOOP
id_multiple:
    ID (COMMA ID)*
;

for_init:
    ID ASSIGN value_assign
;

for_cond:
    ID compare function_call
;

for_end:
    ID value_increase
;

for_range:
    id_multiple ASSIGN RANGE ID
;

for_declaration:
    FOR for_init SEMICOLON for_cond SEMICOLON for_end
    | FOR for_range
;

for_stmt:
    for_declaration BRACE_LEFT scope BRACE_RIGHT
;

// CONDICIONAL
if_init:
    bool_stmt
    | var_init SEMICOLON bool_stmt
;

if_stmt:
    IF if_init BRACE_LEFT scope BRACE_RIGHT (else_if_stmt)* (else_stmt)?
;

else_if_stmt:
    ELSE IF if_init BRACE_LEFT scope BRACE_RIGHT
;

else_stmt:
    ELSE BRACE_LEFT scope BRACE_RIGHT
;