parser grammar jvmParser;

@header {
    package generated;
}

options {
    tokenVocab = jvmLexer;
}

// STATEMENTS
program:
    init
    stmt_sect
;

imports:
    IMPORT STRING_VALUE                                 # simpleImport
    | IMPORT PAREN_LEFT (STRING_VALUE)+ PAREN_RIGHT     # multiImport
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
    
    | CONTINUE
    | BREAK ID?
    | goto_init
    | goto_call
;

// VALUES AND TYPES

// Type
type:
    // type primitive
    TYPE_UINT       # uintType
    | TYPE_INT      # intType
    | TYPE_INT8     # int8Type
    | TYPE_INT16    # int16Type
    | TYPE_INT32    # int32Type
    | TYPE_INT64    # int64Type
    | TYPE_FLOAT32  # float32Type
    | TYPE_FLOAT64  # float64Type
    | TYPE_STRING   # stringType
    | TYPE_BOOL     # boolType

    // type composite
    | BRACKET_LEFT (value)? BRACKET_RIGHT type                  # arrayType
;

// Value
value_increase:
    PLUS PLUS
    | MINUS MINUS
;

value:
    // value primitive
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

    // value composite
    /// value array
    | BRACKET_LEFT value? BRACKET_RIGHT type BRACE_LEFT (value COMMA?)* BRACE_RIGHT
    | ID BRACKET_LEFT (ID | value) BRACKET_RIGHT

    /// value conversion
    | type PAREN_LEFT (ID | value | function_call) PAREN_RIGHT
;

// Math expressions
math_operations:
    PLUS
    | MINUS
    | TIMES
    | DIV
    | MOD
;

math_stmt: // retorna um valor numerico
    (ID | value | function_call) math_operations?
    | math_stmt math_stmt+
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

bool_stmt: // retorna um valor boolean
    (ID | value | function_call | math_stmt | NULL)
    | bool_stmt compare bool_stmt
    | NOT bool_stmt
;

// VARIABLES AND ATTRIBUTES
attr:
    ID (type)? (COMMA (ID (type)?))*
;

var_init:
    VAR ID (type)? (ASSIGN_VAR ((ID | value | function_call) COMMA?)+)?   # varInit
    | CONST ID (ASSIGN_VAR (ID | value | function_call))?       # constInit
    | ID ASSIGN (ID | value | function_call)                    # withoutVarInit
    | (ID | UNDERSCORE) (COMMA (ID | UNDERSCORE))* (ASSIGN | ASSIGN_VAR) (ID | value | function_call) # multipleVarsInit
;

var_update:
    ID (ASSIGN | ASSIGN_VAR) (ID | value | math_stmt | bool_stmt)
    | ID BRACKET_LEFT (ID | value | math_stmt | bool_stmt) BRACKET_RIGHT (ASSIGN | ASSIGN_VAR) (ID | value | math_stmt | bool_stmt)

    // assignment operator
    | ID PLUS ASSIGN_VAR (ID | value | math_stmt | bool_stmt)
    | ID MINUS ASSIGN_VAR (ID | value | math_stmt | bool_stmt)
    | ID TIMES ASSIGN_VAR (ID | value | math_stmt | bool_stmt)

    | ID (BRACKET_LEFT (ID | value | math_stmt | bool_stmt) BRACKET_RIGHT)? value_increase
;

// FUNCTIONS
function_declaration:
    FUNCTION ID PAREN_LEFT (attr)? PAREN_RIGHT (type)?
;

function_call:
    ID PAREN_LEFT ((ID | value | math_stmt | function_call) (COMMA)?)* PAREN_RIGHT
    | ID DOT function_call
;

function_stmt:
    function_declaration BRACE_LEFT scope BRACE_RIGHT
;

return_stmt:
    RETURN (ID | value | function_call | math_stmt | bool_stmt)?
;

// LOOP
for_init:
    ID ASSIGN value
;

for_cond:
    ID compare (value | ID | function_call)
;

for_end:
    ID value_increase
;

for_range:
    (ID | UNDERSCORE) (COMMA (ID | UNDERSCORE))* ASSIGN RANGE ID (DOT ID)?
;

for_declaration:
    FOR for_cond
    | FOR for_init SEMICOLON for_cond SEMICOLON for_end
    | FOR for_range
;

for_stmt:
    (FOR | for_declaration) BRACE_LEFT scope BRACE_RIGHT
;

// CONDICIONAL
if_init:
    (bool_stmt bool_operations?)+
    | PAREN_LEFT bool_stmt PAREN_RIGHT
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

// GOTO
goto_init:
    ID COLON scope
;

goto_call:
    GOTO ID
;