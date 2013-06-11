(* Garrett Milster
   Assignment 4 part 1
   Parser *)
use "tokenizer.sml";

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
	

fun parseEq tk fstr = (nextToken fstr);
fun parseRel tk fstr = (nextToken fstr);
fun parseAdd tk fstr = (nextToken fstr);
fun parseMult tk fstr = (nextToken fstr);
fun parseUnary tk fstr = (nextToken fstr);
fun parseAnd tk fstr = (nextToken fstr);
fun parseOr tk fstr = (nextToken fstr);
fun parseAny tk fstr = (nextToken fstr);
fun parseComma tk fstr = (nextToken fstr);

fun tokenToString TK_LBRACE = "{" 
   | tokenToString TK_RBRACE = "}"
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

fun primaryExpression TK_TRUE infile = (nextToken infile)
	| primaryExpression TK_FALSE infile = (nextToken infile)
	| primaryExpression (TK_NUM(x)) infile = (nextToken infile)
	| primaryExpression (TK_STRING(x)) infile = (nextToken infile)
	| primaryExpression TK_UNDEFINED infile = (nextToken infile)
	| primaryExpression TK_LPAREN infile = 
		let
			val tk = (expression (nextToken infile) infile)
		in
			if(tk = TK_RPAREN) then
				(nextToken infile)
			else
				(output (TextIO.stdErr, "expected ')', found '" ^  (tokenToString tk) ^ "'\n");OS.Process.exit(OS.Process.failure))
		end
	| primaryExpression tk infile = (output (TextIO.stdErr, "expected 'value', found '" ^  (tokenToString tk) ^ "'\n");OS.Process.exit(OS.Process.failure))	
and

memberExpression tk fstr = (primaryExpression tk fstr) and 

callExpression tk fstr = (memberExpression tk fstr) and

leftHandSideExpression tk fstr = (callExpression tk fstr) and

unaryExpression tk fstr = 
	if (isUnaryOp tk) then
		let 
			val tk1 = (parseUnary tk fstr)
			val tk2 = (leftHandSideExpression tk1 fstr)
		in
			tk2
		end
	else	
		(leftHandSideExpression tk fstr) 
and

multiplicativeExpression tk fstr =
	let
		val tk1 = (unaryExpression tk fstr)
	in
		if (isMultOp tk1) then 
			let
				val tk2 = (parseMult tk1 fstr)
				val tk3 = (multiplicativeExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end		
and

additiveExpression tk fstr =
	let
		val tk1 = (multiplicativeExpression tk fstr)
	in
		if (isAddOp tk1) then 
			let
				val tk2 = (parseAdd tk1 fstr)
				val tk3 = (additiveExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end		
and


relationalExpression tk fstr =
	let
		val tk1 = (additiveExpression tk fstr)
	in
		if (isRelOp tk1) then 
			let
				val tk2 = (parseRel tk1 fstr)
				val tk3 = (relationalExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end		
and

equalityExpression tk fstr =
	let
		val tk1 = (relationalExpression tk fstr)
	in
		if (isEqOp tk1) then 
			let
				val tk2 = (parseEq tk1 fstr)
				val tk3 = (equalityExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end		
and

logicalANDExpression tk fstr =
	let
		val tk1 = (equalityExpression tk fstr)
	in
		if(tk1 = TK_AND) then
			let 
				val tk2 = (parseAnd tk fstr)
				val tk3 = (logicalANDExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end
and	


logicalORExpression tk fstr =
	let
		val tk1 = (logicalANDExpression tk fstr)
	in
		if(tk1 = TK_OR) then
			let 
				val tk2 = (parseOr tk fstr)
				val tk3 = (logicalORExpression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end
and	

conditionalExpression tk fstr = 
	let
		val tk1 = (logicalORExpression tk fstr)
	in
		if(tk1 = TK_QUESTION) then
			let
				val tk2 = (parseAny tk1 fstr)
				val tk3 = (assignmentExpression tk2 fstr)
			in
				if(tk3 = TK_COLON) then
					let
						val tk4 = (parseAny tk3 fstr)
						val tk5 = (assignmentExpression tk4 fstr)
					in
						tk5
					end
				else (output(TextIO.stdErr,"expected ':', found '" ^ (tokenToString tk3) ^ "'\n") ;OS.Process.exit(OS.Process.failure))	
			end
		else
			tk1
	end		
and

assignmentExpression tk fstr = (conditionalExpression tk fstr) and

expression tk fstr =
	let
		val tk1 = (assignmentExpression tk fstr)
	in
		if(tk1 = TK_COMMA) then
			let
				val tk2 = (parseComma tk1 fstr)
				val tk3 = (expression tk2 fstr)
			in
				tk3
			end
		else
			tk1
	end		
and

expressionStatement tk fstr = 
	let
		val tk1 = (expression tk fstr) 
	in
		if(tk1 = TK_SEMI) then
			(parseAny tk1 fstr)
		else (output (TextIO.stdErr,"expected ';', found '" ^ (tokenToString tk1) ^ "'\n") ;OS.Process.exit(OS.Process.failure))
	end	
and

statement tk fstr = (expressionStatement tk fstr) and

sourceElement tk fstr = (statement tk fstr);

fun program tk fstr =
	if(matchSource tk) then 
		let
			val tk1 = (sourceElement tk fstr)
			val tk2 = (program tk1 fstr)
		in
			tk2 
		end	
	else
		tk
;

fun parse filename =
	let 
		val infile = openIn(filename)
        	val tk = (nextToken infile)
		val tk1 = (program tk infile)
	in 	
		if(tk1 <> TK_EOF) then
			(output (TextIO.stdErr,"expected 'eof', found '" ^ (tokenToString tk1) ^ "'\n") ;OS.Process.exit(OS.Process.failure))
		else
			tk1 
	end
;	
