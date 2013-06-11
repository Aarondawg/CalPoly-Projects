use "parser.sml";

fun out s = TextIO.output (TextIO.stdOut, s);

fun binaryOperatorString BOP_PLUS = "+"
  | binaryOperatorString BOP_MINUS = "-"
  | binaryOperatorString BOP_TIMES = "*"
  | binaryOperatorString BOP_DIVIDE = "/"
  | binaryOperatorString BOP_MOD = "%"
  | binaryOperatorString BOP_EQ = "=="
  | binaryOperatorString BOP_NE = "!="
  | binaryOperatorString BOP_LT = "<"
  | binaryOperatorString BOP_GT = ">"
  | binaryOperatorString BOP_LE = "<="
  | binaryOperatorString BOP_GE = ">="
  | binaryOperatorString BOP_AND = "&&"
  | binaryOperatorString BOP_OR = "||"
  | binaryOperatorString BOP_COMMA = ","
;

fun unaryOperatorString UOP_NOT = "!"
  | unaryOperatorString UOP_TYPEOF = "typeof "
  | unaryOperatorString UOP_MINUS = "-"
;

fun argsString v =
  if(null v) then ""
  else if(null (tl v)) then (expressionString (hd v))
  else ((expressionString (hd v)) ^ ", " ^ (argsString (tl v)))
and

argumentListString (ARGS_LIST {args})  = 
    (argsString args)
and

argumentsString  (ARGS {args}) = 
  "(" ^ (argumentListString args) ^ ")"
and

expressionString (EXP_NUM n) =
   if n < 0 then "-" ^ (Int.toString (~n)) else Int.toString n
  | expressionString (EXP_STRING s) = "\"" ^ (String.toString s) ^ "\""
  | expressionString (EXP_ID x) = (String.toString x)
  | expressionString EXP_TRUE = "true"
  | expressionString EXP_FALSE = "false"
  | expressionString EXP_UNDEFINED = "undefined"
  | expressionString (EXP_BINARY {opr, lft, rht}) =
   "(" ^
       (expressionString lft) ^
       " " ^ (binaryOperatorString opr) ^ " " ^
       (expressionString rht) ^
   ")"
  | expressionString (EXP_UNARY {opr, opnd}) =
   "(" ^
       (unaryOperatorString opr) ^
       (expressionString opnd) ^
   ")"
  | expressionString (EXP_COND {guard, thenExp, elseExp}) =
   "(" ^
       (expressionString guard) ^
       " ? " ^
       (expressionString thenExp) ^
       " : " ^
       (expressionString elseExp) ^
   ")"
  | expressionString (EXP_ASGN {cond,assignment}) =
    "(" ^
        (expressionString cond) ^
        " = " ^
        (expressionString assignment) ^
    ")"
  | expressionString (EXP_CALL {member,args}) = 
    (expressionString member) ^ (String.concat (map argumentsString args))
  | expressionString (EXP_FUNC {id,params,elems}) = 
      "(function " ^ (String.concat (map expressionString id)) ^ " (" ^ (paramsString params) ^ ")\n{\n" ^ (sourceElementsString elems) ^ "}\n)"
and

varString (VAR_SINGLE {id}) = (expressionString id)
   | varString (VAR_ASGN {id, assignment}) = (expressionString id) ^ " = " ^ (expressionString assignment)
and

variableString v =
  if(null v) then ""
  else if(null (tl v)) then (varString (hd v))
  else ((varString (hd v)) ^ ", " ^ (variableString (tl v)))

and

statementString (ST_EXP {exp}) = expressionString exp ^ ";\n"
  | statementString (ST_BLOCK {block}) = "{\n" ^ (String.concat (map statementString block)) ^ "}\n"
  | statementString (ST_PRINT {exp}) = "print " ^ (expressionString exp) ^ ";\n"
  | statementString (ST_IF {guard,thenStatement,elseStatement}) = 
      "if (" ^ (expressionString guard) ^ ")\n" ^ (statementString thenStatement) ^
      "else\n" ^ (statementString elseStatement)
  | statementString (ST_WHILE {guard,thenStatement}) = 
      "while (" ^ (expressionString guard) ^ ")\n" ^ (statementString thenStatement) 
  | statementString (ST_VAR {declarations}) = 
      "var " ^ (variableString declarations) ^ ";\n"
  | statementString (ST_RETURN {exp}) =
      "return " ^ (String.concat (map expressionString exp)) ^ ";\n" 

and

paramsString params = 
  if(null params) then ""
  else if(null (tl params)) then (expressionString (hd params))
  else ((expressionString (hd params)) ^ ", " ^ (paramsString (tl params)))

and

functionString (FUNC_DEF {id,params,elems}) = 
    "function " ^ (expressionString id) ^ " (" ^ (paramsString params) ^ ")\n{\n" ^ (sourceElementsString elems) ^ "}\n"

and

sourceElementString (STMT {stmt}) =
      statementString stmt
   | sourceElementString (FUNC {func}) = 
      functionString func 

and 

sourceElementsString els =
   String.concat (map sourceElementString els)
;

fun programString (PROGRAM {elems}) =
   sourceElementsString elems

fun printAST prog =
   out (programString prog)
;
