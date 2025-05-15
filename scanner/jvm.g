lexer grammar jvm;

WS : [ \t\n]+ -> skip ;
COMMENTS : '//' ~[\n]* -> skip;

DIGITS : [0-9]+ ;

PACKAGE : 'package' ;
IMPORT : 'import' ;

BRACE_LEFT : '{' ;
BRACE_RIGHT : '}' ;

BRACKET_LEFT : '[' ;
BRACKET_RIGHT : ']' ;

PARENTHESES_LEFT : '(' ;
PARENTHESES_RIGHT : ')' ;

FUNCTION : 'func' ;

ID : [a-zA-Z]+ ;
DOT : '.' ;
STRING_VALUE : ('"' ~["]* '"') ;

INT : 'int' ;
STRING : 'string' ;

VAR : 'var' ;
FOR : 'for' ;
RANGE : 'range' ;
IF : 'if' ;
ELSE : 'else' ;

EQUAL : '==' ;
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
OVER : '/' ;

ASSING : ':=' ;
COMMA : ',' ;
BIGGER : '>' ;

UNDERSCORE : '_' ;
