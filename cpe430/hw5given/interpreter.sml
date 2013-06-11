(* Garrett Milster
	Assignment 4/5
	Original Code taken from Assignment 4 Solution *)

use "printAST.sml";
open HashTable;
exception notFound;

datatype value =
     Num_Value of int
   | String_Value of string
   | Bool_Value of bool
   | Undefined_Value
;

fun valueToString (Num_Value n) = 
   (if n < 0 then "-" ^ (Int.toString (~n)) else Int.toString n)
  | valueToString (String_Value s) =
   s
  | valueToString (Bool_Value b) =
   Bool.toString b
  | valueToString Undefined_Value =
   "undefined"
;

fun typeString (Num_Value _) = "number"
  | typeString (Bool_Value _) = "boolean"
  | typeString (String_Value _) = "string"
  | typeString (Undefined_Value) = "undefined"
;
fun notFoundError s =
   error ("variable '" ^ s ^ "' not found\n")
;
fun condTypeError found =
   error ("boolean guard required for 'cond' expression, found " ^
      (typeString found) ^ "\n")
;
fun ifTypeError found =
   error ("boolean guard required for 'if' statement, found " ^
      (typeString found) ^ "\n")
;
fun whileTypeError found =
   error ("boolean guard required for 'while' statement, found " ^
      (typeString found) ^ "\n")
;
fun unaryTypeError expected found oper =
   error ("unary operator '" ^
      (unaryOperatorString oper) ^ "' requires " ^
      (typeString expected) ^ ", found " ^ (typeString found) ^ "\n")
;

fun boolTypeError found oper =
   error ("operator '" ^ (binaryOperatorString oper) ^
      "' requires " ^ (typeString (Bool_Value true)) ^
      ", found " ^ (typeString found) ^ "\n")
;

fun binaryTypeError elft erht flft frht oper =
   error ("operator '" ^ (binaryOperatorString oper) ^ "' requires " ^
      (typeString elft) ^ " * " ^ (typeString erht) ^ ", found " ^
      (typeString flft) ^ " * " ^ (typeString frht) ^ "\n")
;

fun addTypeError flft frht oper =
   error ("operator '" ^ (binaryOperatorString oper) ^ "' requires " ^
      (typeString (Num_Value 0)) ^ " * " ^
      (typeString (Num_Value 0)) ^ " or " ^
      (typeString (String_Value "")) ^ " * " ^
      (typeString (String_Value "")) ^
      ", found " ^ (typeString flft) ^ " * " ^ (typeString frht) ^ "\n")
;

fun operatorFunc comp funcs oper =
   List.find (fn (opr, _) => comp (opr, oper)) funcs
;

fun applyArithOp _ fnc (Num_Value lft) (Num_Value rht) =
   Num_Value (fnc (lft, rht))
  | applyArithOp oper _ lft rht =
   binaryTypeError (Num_Value 0) (Num_Value 0) lft rht oper
;

fun applyDivOp _ fnc (Num_Value lft) (Num_Value rht) =
   if rht = 0
   then (error "divide by zero\n"; Undefined_Value)
   else Num_Value (fnc (lft, rht))
  | applyDivOp oper _ lft rht =
   binaryTypeError (Num_Value 0) (Num_Value 0) lft rht oper
;

fun applyRelOp _ fnc (Num_Value lft) (Num_Value rht) =
   Bool_Value (fnc (lft, rht))
  | applyRelOp oper _ lft rht =
   binaryTypeError (Num_Value 0) (Num_Value 0) lft rht oper
;

fun applyAddOp oper (Num_Value lft) (Num_Value rht) =
   Num_Value (lft + rht)
  | applyAddOp oper (String_Value lft) (String_Value rht) =
   String_Value (lft ^ rht)
  | applyAddOp oper lft rht =
   addTypeError lft rht oper
;

fun applyEqualityOp (Num_Value lft) (Num_Value rht) =
   Bool_Value (lft = rht)
  | applyEqualityOp (String_Value lft) (String_Value rht) =
   Bool_Value (lft = rht)
  | applyEqualityOp (Bool_Value lft) (Bool_Value rht) =
   Bool_Value (lft = rht)
  | applyEqualityOp Undefined_Value Undefined_Value =
   Bool_Value true
  | applyEqualityOp _ _ =
   Bool_Value false
;

fun applyInequalityOp x y =
   let
      val Bool_Value b = applyEqualityOp x y;
   in
      Bool_Value (not b)
   end
;

fun applyCommaOp _ rht = rht;

fun applyEagerBoolOp _ fnc (Bool_Value lft) (Bool_Value rht) =
   Bool_Value (fnc (lft, rht))
  | applyEagerBoolOp oper _ lft rht =
   binaryTypeError (Bool_Value true) (Bool_Value true) lft rht oper
;

fun applyEagerAndOp oper lft rht =
   applyEagerBoolOp oper (fn (a, b) => a andalso b) lft rht
;

fun applyEagerOrOp oper lft rht =
   applyEagerBoolOp oper (fn (a, b) => a orelse b) lft rht
;

val binaryFuncs = [
   (BOP_PLUS, applyAddOp BOP_PLUS),
   (BOP_MINUS, applyArithOp BOP_MINUS (op -)),
   (BOP_TIMES, applyArithOp BOP_TIMES (op * )),
   (BOP_DIVIDE, applyDivOp BOP_DIVIDE (op div)),
   (BOP_MOD, applyDivOp BOP_MOD (op mod)),
   (BOP_EQ, applyEqualityOp),
   (BOP_NE, applyInequalityOp),
   (BOP_LT, applyRelOp BOP_LT (op <)),
   (BOP_GT, applyRelOp BOP_GT (op >)),
   (BOP_LE, applyRelOp BOP_LE (op <=)),
   (BOP_GE, applyRelOp BOP_GE (op >=)),
   (BOP_AND, applyEagerAndOp BOP_AND),
   (BOP_OR, applyEagerOrOp BOP_OR),
   (BOP_COMMA, applyCommaOp)
];

val binaryOperatorFunc =
   operatorFunc ((op =) : binaryOperator * binaryOperator -> bool) binaryFuncs
;

fun applyNotOp _ (Bool_Value b) =
   Bool_Value (not b)
  | applyNotOp oper opnd =
   unaryTypeError (Bool_Value true) opnd oper
;

fun applyMinusOp _ (Num_Value n) =
   Num_Value (~n)
  | applyMinusOp oper opnd =
   unaryTypeError (Num_Value 0) opnd oper
;

fun applyTypeofOp v = String_Value (typeString v);

val unaryFuncs = [
   (UOP_NOT, applyNotOp UOP_NOT),
   (UOP_TYPEOF, applyTypeofOp),
   (UOP_MINUS, applyMinusOp UOP_MINUS)
];

val unaryOperatorFunc =
   operatorFunc ((op =) : unaryOperator * unaryOperator -> bool) unaryFuncs
;

fun verifyBoolValue (v as Bool_Value b) oper =
   v
  | verifyBoolValue v oper =
   binaryTypeError (Bool_Value true) (Bool_Value true)
      (Bool_Value true) v oper
;

fun evalBinary tbl BOP_AND lft rht =
   (case evalExpression tbl lft of
       Bool_Value true => verifyBoolValue (evalExpression tbl rht) BOP_AND
    |  Bool_Value false => Bool_Value false
    |  v => boolTypeError v BOP_AND
   )
  | evalBinary tbl BOP_OR lft rht =
   (case evalExpression tbl lft of
       Bool_Value true => Bool_Value true
    |  Bool_Value false => verifyBoolValue (evalExpression tbl rht) BOP_OR
    |  v => boolTypeError v BOP_OR
   )
  | evalBinary tbl oper lft rht =
   case (binaryOperatorFunc oper) of
      SOME (_, func) =>
         func (evalExpression tbl lft) (evalExpression tbl rht)
   |  NONE =>
         error ("operator '" ^ (binaryOperatorString oper) ^ "' not found\n")
and evalUnary tbl oper opnd =
   case (unaryOperatorFunc oper) of
      SOME (_, func) => func (evalExpression tbl opnd)
   |  NONE =>
         error ("operator '" ^ (unaryOperatorString oper) ^ "' not found\n")

and evalExpression tbl (EXP_NUM n) = Num_Value n
  | evalExpression tbl (EXP_STRING s) = String_Value s
  | evalExpression tbl EXP_TRUE = Bool_Value true
  | evalExpression tbl EXP_FALSE = Bool_Value false
  | evalExpression tbl EXP_UNDEFINED = Undefined_Value
  | evalExpression tbl (EXP_ID s) = (lookup tbl s handle notFound => (notFoundError s))
  | evalExpression tbl (EXP_BINARY {opr, lft, rht}) = evalBinary tbl opr lft rht
  | evalExpression tbl (EXP_UNARY {opr, opnd}) = evalUnary tbl opr opnd
  | evalExpression tbl (EXP_COND {guard, thenExp, elseExp}) =
   (case evalExpression tbl guard of
       Bool_Value true => evalExpression tbl thenExp
    |  Bool_Value false => evalExpression tbl elseExp 
    |  v => condTypeError v
   )
  | evalExpression tbl (EXP_ASGN {cond,assignment}) =
    (case cond of
       (EXP_ID x) => 
          let
              val result = (evalExpression tbl assignment)
          in
              (insert tbl(x, result); result)

          end

       | _ => OS.Process.exit(OS.Process.failure)

    )

;

fun evalStatement (ST_EXP {exp}) tbl =
    evalExpression tbl exp 
  | evalStatement (ST_PRINT {exp}) tbl = 
    let
        val result = (evalStatement (ST_EXP {exp = exp}) tbl) 
    in
        (out (valueToString result);result)
    end  
  | evalStatement (ST_BLOCK {block}) tbl = 
    if(block = []) then Bool_Value false
    else 
      let
          val head = (hd block)
          val tail = (tl block)
      in
          (evalStatement head tbl ; evalStatement (ST_BLOCK {block = tail}) tbl)

      end
  | evalStatement (ST_IF {guard,thenStatement, elseStatement}) tbl =
     (case evalStatement (ST_EXP {exp = guard}) tbl of
       Bool_Value true => evalStatement thenStatement tbl
    |  Bool_Value false => evalStatement elseStatement tbl
    |  v => ifTypeError v
   )

  | evalStatement (ST_WHILE {guard,thenStatement}) tbl = 
    (case evalStatement (ST_EXP {exp = guard}) tbl of
       Bool_Value true => 
       (evalStatement thenStatement tbl; evalStatement (ST_WHILE {guard = guard, thenStatement = thenStatement}) tbl)
    |  Bool_Value false => Bool_Value false
    |  v => whileTypeError v
   )

;

fun evalSourceElement (STMT {stmt}) table =
   evalStatement stmt table 
;

fun evalSourceElements [] table = ()
  | evalSourceElements (el::els) table =
   (evalSourceElement el table; evalSourceElements els table)
;

val cmp_fn : string*string->bool = (op =);

fun evalProgram (PROGRAM {elems}) =
   evalSourceElements elems (mkTable (HashString.hashString, cmp_fn) (100, notFound));

;

fun interpret file =
   (evalProgram (parse file); ())
;
