lexer grammar jvm;

WS         : [ \t\r\n]+       -> skip ;
COMMENTS   : '//' ~[\r\n]*    -> skip ;

PACKAGE    : 'package' ;
IMPORT     : 'import' ;
FUNCTION   : 'func' ;
VAR        : 'var' ;
INT        : 'int' ;
FLOAT32    : 'float32' ;
FLOAT64    : 'float64' ;
STRING     : 'string' ;
FOR        : 'for' ;
RANGE      : 'range' ;
IF         : 'if' ;
ELSE       : 'else' ;
RETURN     : 'return' ;
TRUE       : 'true' ;
FALSE      : 'false' ;

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

DIGITS         : [0-9]+ ;
ID             : [a-zA-Z_][a-zA-Z0-9_]* ;
STRING_VALUE   : '"' (~["\\] | '\\' .)* '"' ;
CHAR_VALUE     : '\'' (~["\\] | '\\' .)* '\'' ;