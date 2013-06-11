
 (* Garrett Milster 
   Assignment 4 part 1 
   Parser *) 
use "tokenizer.sml"; 
use "ast.sml";

fun isUnaryOp tk =  
	if tk = TK_MINUS then true 
	else if tk = TK_TYPEOF then true 
	else if tk = TK_NOT then true 
	else false 
 
 
fun isMultOp tk = 
	if tk = TK_TIMES then true 
	else if tk = TK_DIVIDE then true 
	else if tk = TK_MOD then true 
	else false 
; 
 
fun isAddOp tk =  
	if tk = TK_PLUS then true 
	else if tk = TK_MINUS then true 
	else false 
; 
 
fun isRelOp tk =  
	if tk = TK_LT then true 
	else if tk = TK_LE then true 
	else if tk = TK_GT then true 
	else if tk = TK_GE then true 
	else false 
; 
 
fun isEqOp tk = 
	if tk = TK_EQ then true 
	else if tk = TK_NE then true 
	else false 
;	 
 
fun matchSource (TK_NUM(x)) = true  
	| matchSource TK_TRUE = true 
	| matchSource TK_FALSE = true 
	| matchSource (TK_STRING(x)) = true 
	| matchSource TK_UNDEFINED = true 
	| matchSource TK_LPAREN = true 
	| matchSource TK_NOT = true 
	| matchSource TK_TYPEOF	 = true 
	| matchSource TK_MINUS = true 
	| matchSource tk = false 
;	 
	 
 
fun parseEq TK_EQ fstr = ((nextToken fstr),BOP_EQ) 
	| parseEq TK_NE fstr = ((nextToken fstr),BOP_NE) 
	| parseEq tk fstr = OS.Process.exit(OS.Process.failure)
; 
 
fun parseRel TK_GT fstr = ((nextToken fstr), BOP_GT) 
	| parseRel TK_LT fstr = ((nextToken fstr), BOP_LT) 
	| parseRel TK_GE fstr = ((nextToken fstr), BOP_GE) 
	| parseRel TK_LE fstr = ((nextToken fstr), BOP_LE) 
	| parseRel tk fstr = OS.Process.exit(OS.Process.failure) 
; 
 
fun parseAdd TK_PLUS fstr = ((nextToken fstr),BOP_PLUS) 
	| parseAdd TK_MINUS fstr = ((nextToken fstr), BOP_MINUS)  
	| parseAdd tk fstr = OS.Process.exit(OS.Process.failure) 
; 
 
fun parseMult TK_TIMES fstr = ((nextToken fstr), BOP_TIMES) 
	| parseMult TK_DIVIDE fstr = ((nextToken fstr), BOP_DIVIDE) 
	| parseMult TK_MOD fstr = ((nextToken fstr), BOP_MOD) 
	| parseMult tk fstr = OS.Process.exit(OS.Process.failure) 
; 
 
fun parseUnary TK_NOT fstr = ((nextToken fstr), UOP_NOT) 
	| parseUnary TK_TYPEOF fstr = ((nextToken fstr), UOP_TYPEOF) 
	| parseUnary TK_MINUS fstr = ((nextToken fstr), UOP_MINUS) 
	| parseUnary tk fstr = OS.Process.exit(OS.Process.failure) 
; 
 
fun parseAnd tk fstr = ((nextToken fstr), BOP_AND); 
 
fun parseOr tk fstr = ((nextToken fstr), BOP_OR); 
 
fun parseAny tk fstr = (nextToken fstr); 
 
fun parseComma tk fstr = ((nextToken fstr),BOP_COMMA); 
 
fun tokenToString TK_LBRACE = " {"  
   | tokenToString TK_RBRACE = " }" 
   | tokenToString TK_LPAREN = "(" 
   | tokenToString TK_RPAREN = ")" 
   | tokenToString TK_LBRACKET = "[" 
   | tokenToString TK_RBRACKET = "]" 
   | tokenToString TK_COMMA = "," 
   | tokenToString TK_SEMI = ";" 
   | tokenToString TK_QUESTION = "?" 
   | tokenToString TK_COLON = ":" 
   | tokenToString TK_DOT = "." 
   | tokenToString TK_PLUS = "+" 
   | tokenToString TK_MINUS = "-" 
   | tokenToString TK_TIMES = "*" 
   | tokenToString TK_DIVIDE = "/" 
   | tokenToString TK_MOD = "%" 
   | tokenToString TK_AND = "&&" 
   | tokenToString TK_OR = "||" 
   | tokenToString TK_ASSIGN = "=" 
   | tokenToString TK_EQ = "==" 
   | tokenToString TK_LT = "<" 
   | tokenToString TK_LE = "<=" 
   | tokenToString TK_GT = ">" 
   | tokenToString TK_GE = ">=" 
   | tokenToString TK_NOT = "!" 
   | tokenToString TK_NE = "!=" 
   | tokenToString TK_ELSE = "else" 
   | tokenToString TK_FALSE = "false" 
   | tokenToString TK_FUNCTION = "function" 
   | tokenToString TK_IF = "if" 
   | tokenToString TK_NEW = "new" 
   | tokenToString TK_PRINT = "print" 
   | tokenToString TK_RETURN = "return" 
   | tokenToString TK_THIS = "this" 
   | tokenToString TK_TRUE = "true" 
   | tokenToString TK_TYPEOF = "typeof" 
   | tokenToString TK_UNDEFINED = "undefined" 
   | tokenToString TK_VAR = "var" 
   | tokenToString TK_WHILE = "while" 
   | tokenToString (TK_NUM(x)) = Int.toString(x) 
   | tokenToString (TK_ID(x)) = x 
   | tokenToString (TK_STRING(x)) = x 
   | tokenToString TK_EOF = "eof" 
; 
 
fun primaryExpression TK_TRUE infile = ((nextToken infile), EXP_TRUE) 
	| primaryExpression TK_FALSE infile = ((nextToken infile), EXP_FALSE) 
	| primaryExpression (TK_NUM(x)) infile = ((nextToken infile), (EXP_NUM x)) 
	| primaryExpression (TK_STRING(x)) infile = ((nextToken infile), (EXP_STRING x)) 
	| primaryExpression TK_UNDEFINED infile = ((nextToken infile),EXP_UNDEFINED) 
	| primaryExpression TK_LPAREN infile =  
		let 
			val (tk,expr) = (expression (nextToken infile) infile) 
		in 
			if(tk = TK_RPAREN) then 
				((nextToken infile), expr) 
			else 
				(output (TextIO.stdErr, "expected ')', found '" ^ (tokenToString tk) ^ "'\n");OS.Process.exit(OS.Process.failure)) 
		end 
	| primaryExpression tk infile = (output (TextIO.stdErr, "expected 'value', found '" ^  (tokenToString tk) ^ "'\n");OS.Process.exit(OS.Process.failure)) 
and 
 
memberExpression tk fstr = (primaryExpression tk fstr) and  
 
callExpression tk fstr = (memberExpression tk fstr) and 
 
leftHandSideExpression tk fstr = (callExpression tk fstr) and 
 
unaryExpression tk fstr =  
	if (isUnaryOp tk) then 
		let  
			val (tk1,opr) = (parseUnary tk fstr) 
			val (tk2, opnd) = (leftHandSideExpression tk1 fstr) 
		in 
			(tk2, (EXP_UNARY {opr = opr, opnd = opnd }))		 
		end 
	else	 
		(leftHandSideExpression tk fstr)  
and

repeatMultOp tk fstr lft = 
	if (isMultOp tk) then 
		let
			val (tk1,opr) = (parseMult tk fstr)
			val (tk2,rht) = (unaryExpression tk1 fstr)
		in
			(repeatMultOp tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

multiplicativeExpression tk fstr =
	let
		val (tk1,opnd) = (unaryExpression tk fstr)
	in
		(repeatMultOp tk1 fstr opnd)
	end		
and

repeatAddOp tk fstr lft = 
	if (isAddOp tk) then 
		let
			val (tk1,opr) = (parseAdd tk fstr)
			val (tk2,rht) = (multiplicativeExpression tk1 fstr)
		in
			(repeatAddOp tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

additiveExpression tk fstr =
	let
		val (tk1,opnd) = (multiplicativeExpression tk fstr)
	in
		(repeatAddOp tk1 fstr opnd)
	end		
and

repeatRelOp tk fstr lft = 
	if (isRelOp tk) then 
		let
			val (tk1,opr) = (parseRel tk fstr)
			val (tk2,rht) = (additiveExpression tk1 fstr)
		in
			(repeatRelOp tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

relationalExpression tk fstr =
	let
		val (tk1,opnd) = (additiveExpression tk fstr)
	in
		(repeatRelOp tk1 fstr opnd)
	end		
and

repeatEqOp tk fstr lft = 
	if (isEqOp tk) then 
		let
			val (tk1,opr) = (parseEq tk fstr)
			val (tk2,rht) = (relationalExpression tk1 fstr)
		in
			(repeatEqOp tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

equalityExpression tk fstr =
	let
		val (tk1,opnd) = (relationalExpression tk fstr)
	in
		(repeatEqOp tk1 fstr opnd)
	end		
and

repeatAND tk fstr lft = 
	if(tk = TK_AND) then
		let 
			val (tk1,opr) = (parseAnd tk fstr)
			val (tk2,rht) = (equalityExpression tk1 fstr)
		in
			(repeatAND tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

logicalANDExpression tk fstr =
	let
		val (tk1,opnd) = (equalityExpression tk fstr)
	in
      		(repeatAND tk1 fstr opnd)
	end
and	

repeatOR tk fstr lft = 
	if(tk = TK_OR) then
		let 
			val (tk1,opr) = (parseOr tk fstr)
			val (tk2,rht) = (logicalANDExpression tk1 fstr)
		in
			(repeatOR tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
      (tk,lft)
and

logicalORExpression tk fstr =
	let
		val (tk1,opnd) = (logicalANDExpression tk fstr)
	in
		(repeatOR tk1 fstr opnd)
	end
and	

conditionalExpression tk fstr = 
	let
		val (tk1,guard) = (logicalORExpression tk fstr)
	in
		if(tk1 = TK_QUESTION) then
			let
				val tk2 = (parseAny tk1 fstr)
				val (tk3,thenExp) = (assignmentExpression tk2 fstr)
			in
				if(tk3 = TK_COLON) then
					let
						val tk4 = (parseAny tk3 fstr)
						val (tk5,elseExp) = (assignmentExpression tk4 fstr)
					in
						(tk5,(EXP_COND {guard = guard, thenExp = thenExp, elseExp = elseExp}))
					end
				else (output(TextIO.stdErr,"expected ':', found '" ^ (tokenToString tk3) ^ "'\n") ;OS.Process.exit(OS.Process.failure))	
			end
		else
			(tk1,guard)
	end		
and

assignmentExpression tk fstr = (conditionalExpression tk fstr) and

repeatExpression tk fstr lft =
	if(tk = TK_COMMA) then
		let
			val (tk1,opr) = (parseComma tk fstr)
			val (tk2,rht) = (assignmentExpression tk1 fstr)
		in
			(repeatExpression tk2 fstr (EXP_BINARY {opr = opr, lft = lft, rht = rht}))
		end
	else
		(tk,lft)
and

expression tk fstr =
	let
		val (tk1,lft) = (assignmentExpression tk fstr)
	in
		(repeatExpression tk1 fstr lft)
	end		
;

fun expressionStatement tk fstr = 
	let
		val (tk1,expr) = (expression tk fstr) 
	in
		if(tk1 = TK_SEMI) then
			((parseAny tk1 fstr),expr)
		else (output (TextIO.stdErr,"expected ';', found '" ^ (tokenToString tk1) ^ "'\n") ;OS.Process.exit(OS.Process.failure))
	end	
;

fun statement tk fstr = 
     let
        val (tk1,exp) = (expressionStatement tk fstr);
     in
         (tk1,(ST_EXP {exp = exp}))
     end
;

fun sourceElement tk fstr = 
     let
        val (tk1,stmt) = (statement tk fstr);
     in
         (tk1,(STMT {stmt = stmt}))
     end
;

fun repeatProgram tk fstr =
	if(matchSource tk) then 
	   let
		   val (tk1,source) = (sourceElement tk fstr)
		   val (result) = (repeatProgram tk1 fstr)
	   in
	   	source::result
	   end	
   else
	   if(tk <> TK_EOF) then
			(output (TextIO.stdErr,"expected 'eof', found '" ^ (tokenToString tk) ^ "'\n") ;OS.Process.exit(OS.Process.failure))
		else
			[] 
;

fun program tk fstr =
   PROGRAM {elems = (repeatProgram tk fstr)}
;

fun parse filename =
	let 
		val infile = openIn(filename)
      val tk = (nextToken infile)
	in 	
		(program tk infile)
	end
;	

