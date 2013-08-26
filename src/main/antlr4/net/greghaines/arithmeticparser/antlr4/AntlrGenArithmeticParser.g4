grammar AntlrGenArithmeticParser;

expression : expr;

expr
    : primary
    | expr (MULTIPLICATION | DIVISION) expr
    | expr (ADDITION | SUBTRACTION) expr
    ;
    
primary
    : '(' expr ')'
    | NUMBER
    ;

NUMBER : '-'? ('.' DIGIT+ | DIGIT+ ('.' DIGIT*)? );
fragment
DIGIT : [0-9];

ADDITION : '+';

SUBTRACTION : '-';

MULTIPLICATION : '*';

DIVISION : '/';

WS : [ \t\n\r\f]+ -> skip;
