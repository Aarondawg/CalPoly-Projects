(* Garrett Milster 
	Assignment 4 *)

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

fun expressionString (EXP_NUM n) =
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
;


fun  
  statementString (ST_EXP {exp}) = expressionString exp ^ ";\n"
  | statementString (ST_BLOCK {block}) = "{\n" ^ (String.concat (map statementString block)) ^ "}\n"
  | statementString (ST_PRINT {exp}) = "print " ^ (expressionString exp) ^ ";\n"
  | statementString (ST_IF {guard,thenStatement,elseStatement}) = 
      "if (" ^ (expressionString guard) ^ ")\n" ^ (statementString thenStatement) ^
      "else\n" ^ (statementString elseStatement)
  | statementString (ST_WHILE {guard,thenStatement}) = 
      "while (" ^ (expressionString guard) ^ ")\n" ^ (statementString thenStatement) 
;

fun sourceElementString (STMT {stmt}) =
   statementString stmt
;

fun sourceElementsString els =
   String.concat (map sourceElementString els)
;

fun programString (PROGRAM {elems}) =
   sourceElementsString elems

fun printAST prog =
   out (programString prog)
;
