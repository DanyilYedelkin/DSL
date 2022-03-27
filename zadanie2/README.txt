expr      ->    plusminus* EOF 
plusminus ->    multdiv { ( '+' | '-' ) multdiv } 
multdiv   ->    factor  { ( '*' | '/' ) factor } 
factor    ->    [-] factor
factor    ->    NUMBER | '(' expr ')' 
