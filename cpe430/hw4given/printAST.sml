use "parser.sml";

fun printBOP BOP_PLUS = "+"
	| printBOP BOP_MINUS = " - "
	| printBOP BOP_TIMES = " * "
	| printBOP BOP_DIVIDE = " / "
	| printBOP BOP_MOD = " % "
	| printBOP BOP_EQ = " == "
	| printBOP BOP_NE = " != "
        | printBOP BOP_LT = " < "
        | printBOP BOP_GT = " > "
        | printBOP BOP_LE = " <= "
        | printBOP BOP_GE = " >= "
        | printBOP BOP_AND = " && "
        | printBOP BOP_OR = " || "
        | printBOP BOP_COMMA = " , "
;

fun printUOP  UOP_NOT = "!" | printUOP UOP_TYPEOF = "typeof " | printUOP UOP_MINUS = "-" ;

fun printExpression (EXP_NUM(x)) = print((Int.toString x))
   | printExpression (EXP_STRING(x)) = (print("\"" ^ x ^ "\""))
   | printExpression EXP_TRUE = (print "true")
   | printExpression EXP_FALSE = (print "false")
   | printExpression EXP_UNDEFINED = (print "undefined")
   | printExpression (EXP_BINARY {opr = opr, lft = lft, rht = rht}) =
   	(print("(");(printExpression lft);(print(printBOP opr));(printExpression rht);print(")"))
   | printExpression (EXP_UNARY {opr = opr, opnd = opnd}) =
   	(print("("); (print(printUOP opr));(printExpression opnd);(print(")")))
   | printExpression (EXP_COND {guard = guard, thenExp = thenExp, elseExp = elseExp}) =
   	(print("("); (printExpression guard); print(" ? "); (printExpression thenExp); print(" : "); (printExpression elseExp); print(")"))
;


fun printStatement (ST_EXP {exp = x}) = ((printExpression x);(print(";\n")))

fun printSourceElement (STMT {stmt = x}) = (printStatement x);

fun printAST (PROGRAM {elems = []}) = ()
   | printAST (PROGRAM {elems = (x::xs)}) =
   ((printSourceElement x);(printAST (PROGRAM {elems = xs})))
;
