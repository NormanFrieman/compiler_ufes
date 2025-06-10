lexer grammar jvmLexer;

WS         : [ \t\r\n]+       -> skip ;
COMMENTS   : '//' ~[\r\n]*    -> skip ;

TYPE_UINT     : 'uint' ;
TYPE_INT      : 'int' ;
TYPE_INT8     : 'int8' ;
TYPE_INT16    : 'int16' ;
TYPE_INT32    : 'int32' ;
TYPE_INT64    : 'int64' ;
TYPE_FLOAT32  : 'float32' ;
TYPE_FLOAT64  : 'float64' ;
TYPE_STRING   : 'string' ;
TYPE_BOOL     : 'bool' ;

PACKAGE    : 'package' ;
IMPORT     : 'import' ;
FUNCTION   : 'func' ;
VAR        : 'var' ;
CONST      : 'const' ;
RETURN     : 'return' ;
FOR        : 'for' ;
RANGE      : 'range' ;
IF         : 'if' ;
ELSE       : 'else' ;
TRUE       : 'true' ;
FALSE      : 'false' ;
MAP        : 'map' ;

ASSIGN     : ':=' ;
ASSIGN_VAR : '=' ;
EQUAL      : '==' ;
NOT_EQUAL  : '!=' ;
LESS       : '<' ;
GREATER    : '>' ;
LESS_EQ    : '<=' ;
GREATER_EQ : '>=' ;
PLUS       : '+' ;
MINUS      : '-' ;
TIMES      : '*' ;
DIV        : '/' ;
MOD        : '%' ;
AND        : '&&' ;
OR         : '||' ;
NOT        : '!' ;

DOT              : '.' ;
COMMA            : ',' ;
SEMICOLON        : ';' ;
COLON            : ':' ;

BRACE_LEFT       : '{' ;
BRACE_RIGHT      : '}' ;
BRACKET_LEFT     : '[' ;
BRACKET_RIGHT    : ']' ;
PAREN_LEFT       : '(' ;
PAREN_RIGHT      : ')' ;

UNDERSCORE       : '_' ;

INT_DEC        : [0-9]+ ;
INT_HEX        : '0x' [0-9a-fA-F]+ ;
INT_OCT        : '0o' [0-7]+ ;
INT_BIN        : '0b' [01]+ ;

FLOAT_LITERAL  : [0-9]+ '.' [0-9]* ([eE][+-]?[0-9]+)? |
                 '.' [0-9]+ ([eE][+-]?[0-9]+)?        |
                 [0-9]+ [eE] [+-]?[0-9]+              ;

ID                      : [a-zA-Z_][a-zA-Z0-9_]* |
                          [\p{L}_][\p{L}\p{N}_]* ;
STRING_VALUE            : '"' (~["\\] | '\\' .)* '"' |
                          '"' ( ESC_SEQ | ~["\\\r\n] )* '"' ;

NEGATIVE_FLOAT_LITERAL  : '-' FLOAT_LITERAL ;

CHAR_VALUE              : '\'' (~['\\] | '\\' .)* '\'' ;
NEGATIVE_INT_VALUE      : '-' [0-9]+ ;

fragment ESC_SEQ
    : '\\' [btnr"\\]         // \b \t \n \r \" \\
    | '\\u' HEX HEX HEX HEX  // Unicode escape
    ;

fragment HEX : [0-9a-fA-F] ;

// uint int8 int16 ...
// unicode
// regras de escape
// variante valores numericos
