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
    
    | CONTINUE
    | BREAK ID?
    | goto_init
    | goto_call
;

// VALUES AND TYPES

// Type
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

    // type composite
    | BRACKET_LEFT (value)? BRACKET_RIGHT type
    | PAREN_LEFT (ID | type) (COMMA (ID | TYPE))+ PAREN_RIGHT

    // map
    | MAP BRACKET_LEFT type BRACKET_RIGHT type
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
    | type BRACE_LEFT (value_assign)? BRACE_RIGHT
    | ID BRACKET_LEFT (ID | value) BRACKET_RIGHT

    /// inicialize map
    | MAP BRACKET_LEFT type BRACKET_RIGHT type

    /// value map
    | MAP BRACKET_LEFT type BRACKET_RIGHT type
        BRACE_LEFT
            (STRING_VALUE COLON value COMMA)* STRING_VALUE COLON value (COMMA)?
        BRACE_RIGHT

    /// value struct
    | ID (DOT ID)+
;

value_assign:
    value
    | ID (BRACKET_LEFT (ID | value) BRACKET_RIGHT)?
    | function_call
    | value_assign (COMMA value_assign)+

    // type conversion
    | type PAREN_LEFT value_assign PAREN_RIGHT

    // math operations
    | value_assign math_operations value_assign

    // bool expressions
    | ((value | ID | function_call) | NULL)
    | ((value | ID | function_call) | NULL) compare ((value | ID | function_call) | NULL)
    | ((value | ID | function_call) | NULL) compare PAREN_LEFT (value | ID | function_call) PAREN_RIGHT
    | PAREN_LEFT (value | ID | function_call) PAREN_RIGHT compare ((value | ID | function_call) | NULL)
;

// Math expressions
math_operations:
    PLUS
    | MINUS
    | TIMES
    | DIV
    | MOD
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

bool_stmt:
    value_assign
    | (NOT)? (PAREN_LEFT)? (NOT)? value_assign bool_operations (NOT)? value_assign (PAREN_RIGHT)?
    | bool_stmt bool_operations bool_stmt
;

// VARIABLES AND ATTRIBUTES
attr:
    ID (type)? (COMMA (ID (type)?))*
;

var_init:
    VAR ID (type)? (ASSIGN_VAR value_assign)?
    | CONST ID (ASSIGN_VAR value_assign)?
    | ID ASSIGN value_assign
    | (ID | UNDERSCORE) (COMMA (ID | UNDERSCORE))* (ASSIGN | ASSIGN_VAR) value_assign
;

var_update:
    ID (ASSIGN | ASSIGN_VAR) value_assign
    | ID BRACKET_LEFT (ID | value) BRACKET_RIGHT (ASSIGN | ASSIGN_VAR) value_assign

    // assignment operator
    | ID PLUS ASSIGN_VAR value_assign
    | ID MINUS ASSIGN_VAR value_assign
    | ID TIMES ASSIGN_VAR value_assign

    | ID (BRACKET_LEFT (ID | value) BRACKET_RIGHT)? value_increase
;

// FUNCTIONS
function_declaration:
    FUNCTION ID PAREN_LEFT (attr)? PAREN_RIGHT (type)?
;

function_call:
    ID PAREN_LEFT value_assign? PAREN_RIGHT
    | ID DOT function_call
;

function_stmt:
    function_declaration BRACE_LEFT scope BRACE_RIGHT
;

return_stmt:
    RETURN value_assign?
;

// LOOP
for_init:
    ID ASSIGN value_assign
;

for_cond:
    ID compare (value | ID | function_call)
;

for_end:
    ID value_increase
;

for_range:
    (ID | UNDERSCORE) (COMMA (ID | UNDERSCORE))* ASSIGN RANGE ID
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
    | var_init SEMICOLON bool_stmt
    | PAREN_LEFT bool_stmt PAREN_RIGHT
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