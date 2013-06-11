datatype binaryOperator =
     BOP_PLUS
   | BOP_MINUS
   | BOP_TIMES
   | BOP_DIVIDE
   | BOP_MOD
   | BOP_EQ
   | BOP_NE
   | BOP_LT
   | BOP_GT
   | BOP_LE
   | BOP_GE
   | BOP_AND
   | BOP_OR
   | BOP_COMMA
;

datatype unaryOperator =
     UOP_NOT
   | UOP_TYPEOF
   | UOP_MINUS
;

datatype expression =
     EXP_NUM of int
   | EXP_STRING of string
   | EXP_ID of string
   | EXP_TRUE
   | EXP_FALSE
   | EXP_UNDEFINED
   | EXP_BINARY of {opr: binaryOperator, lft: expression, rht: expression}
   | EXP_UNARY of {opr: unaryOperator, opnd: expression}
   | EXP_COND of {guard: expression, thenExp: expression, elseExp: expression}
   | EXP_ASGN of {cond: expression, assignment: expression}
   | EXP_CALL of {member: expression, args: argument list}
   | EXP_FUNC of {id: expression list, params: expression list, elems: sourceElement list}

and

argumentList = 
   ARGS_LIST of {args: expression list} 

and

argument = 
   ARGS of {args: argumentList}
and

variable = 
     VAR_SINGLE of {id: expression}
   | VAR_ASGN of {id: expression, assignment: expression}


and statement =
   ST_EXP of {exp: expression}
   | ST_BLOCK of {block: statement list}
   | ST_IF of {guard: expression, thenStatement: statement, elseStatement: statement}
   | ST_PRINT of {exp:expression}
   | ST_WHILE of {guard: expression, thenStatement: statement}
   | ST_VAR of {declarations: variable list}
   | ST_RETURN of {exp: expression list}
and


function = 
   FUNC_DEF of {id: expression, params: expression list, elems: sourceElement list}

and

sourceElement =
   STMT of {stmt: statement}
   | FUNC of {func: function}
;

datatype program =
   PROGRAM of {elems: sourceElement list}
;
