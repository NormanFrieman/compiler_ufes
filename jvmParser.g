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
    IMPORT STRING_VALUE # simpleImport
    | IMPORT PAREN_LEFT (STRING_VALUE SEMICOLON?)* PAREN_RIGHT # multiImport
;

init:
    PACKAGE ID*
    imports*
;

stmt_sect:
    function_stmt*
;

scope:
    command*
;

command:
    return_stmt
    | function_call

    | var_assign
    
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
    | BRACKET_LEFT size=value? BRACKET_RIGHT type                  # arrayType
;

// Value
value_increase:
    PLUS PLUS
    | MINUS MINUS
;

value:
    // value primitive
    INT_DEC # valueIntD
    | INT_HEX # valueIntH
    | INT_OCT # valueIntO
    | INT_BIN # valueIntB
    | NEGATIVE_INT_VALUE # valueIntN
    | FLOAT_LITERAL # valueFloat
    | NEGATIVE_FLOAT_LITERAL # valueFloatN
    | STRING_VALUE # valueString
    | CHAR_VALUE # valueChar
    | TRUE # valueTrue
    | FALSE # valueFalse

    // value composite
    /// value array
    | BRACKET_LEFT size=value? BRACKET_RIGHT type BRACE_LEFT (value (COMMA value)*)? BRACE_RIGHT # valueArrayInit
    | ID BRACKET_LEFT (ID | value) BRACKET_RIGHT # valueArrayGet

    /// value conversion
    | type PAREN_LEFT (ID | value | function_call | math_stmt) PAREN_RIGHT # valueConversion

    /// value prop
    | ID (DOT ID)+ # valueProp
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
    (ID | value | function_call) (math_operations (ID | value | function_call))*
;

// Bool expressions
compare:
    EQUAL
    | NOT_EQUAL
    | LESS
    | GREATER 
    | LESS_EQ  
    | GREATER_EQ
    
    | AND
    | OR
;

bool_stmt: // retorna um valor boolean
    (ID | value | function_call | math_stmt | NULL)
    | bool_stmt compare bool_stmt
    | NOT bool_stmt
;

// VARIABLES AND ATTRIBUTES
attr:
    ID type? (COMMA ID type?)*
;

var_assign:
    (CONST | VAR)? ID type? # varWithoutValue
    | (CONST | VAR)? varInit=ID type? math_operations? (ASSIGN_VAR | ASSIGN) (ID | value | math_stmt | bool_stmt | function_call) #varWithValue
    | ID value_increase # varWithIncrease
;

// FUNCTIONS
function_declaration:
    FUNCTION ID PAREN_LEFT attr? PAREN_RIGHT type?
;

function_call:
    parent=ID PAREN_LEFT ((ID | value | math_stmt | function_call) (COMMA (ID | value | math_stmt | function_call))*)? PAREN_RIGHT # functionWithParam
    | parent=ID DOT function_call # functionRecursive
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
    bool_stmt
    | PAREN_LEFT bool_stmt PAREN_RIGHT
    | var_assign SEMICOLON bool_stmt
;

if_stmt:
    IF if_init BRACE_LEFT scope BRACE_RIGHT else_if_stmt* else_stmt?
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