(* Garrett Milster
   Assignment 3: Recognizer *)

open TextIO;
val keywords = ["else", "false", "function", "if", "new", "print" , "return" , "this" , "true", "typeof" , "undefined" , "var",  "while"]; 
val symbolList = ["[", "]", "{", "}", "(", ")", ".", ",", ":", ";", "?", "+", "-", "*", "/", "%"];
val escapes = ["\"", "\\", "b", "f", "n", "r", "t", "v"];

fun printInts infile s category = (
	input1(infile);
	case lookahead(infile) of
       		SOME c => 
			if (Char.isDigit(c)) then (printInts infile (s ^ Char.toString(c)) category)
			else((print category); (print s); (print "\n"))
)

fun checkKeyword s (x::xs) =
        if null xs then 
	   if x = s then (print("keyword: "); (print s); print("\n")) else (print("identifier: "); (print s); print("\n"))
	else if x = s then (print("keyword: "); (print s); print("\n")) else (checkKeyword s xs)

;

fun printChars infile s = (
	input1(infile);
	case lookahead(infile) of
       		SOME c => 
			if (Char.isAlpha(c)) then (printChars infile (s ^ Char.toString(c)))
			else if (Char.isDigit(c)) then (printInts infile (s ^ Char.toString(c)) ("identifier: "))
			else (checkKeyword s keywords)
)		

fun deleteSpace infile = (
	input1(infile);
	case lookahead(infile) of
	SOME c =>
       		if (Char.isSpace(c)) then (deleteSpace infile) else () 	
	| NONE =>
		print("end-of-file\n")
)

fun checkSymbol s (x::xs) = 
	if null xs then 
		if x = s then (print("symbol: "); (print s); print("\n")) else (print("invalid symbol: '" ^ s ^ "'\n"); OS.Process.exit(OS.Process.failure))
	else if x = s then (print("symbol: "); (print s); print("\n")) else (checkSymbol s xs)
;

fun printSymbols infile s = (
	input1(infile);
	if s = "&" then
	   if lookahead(infile) = (SOME #"&") then (input1(infile);print("symbol: &&\n")) else (print("invalid symbol: '" ^ s ^ "'\n"); OS.Process.exit(OS.Process.failure))
	else if s = "|" then
	   if lookahead(infile) = (SOME #"|") then (input1(infile);print("symbol: ||\n")) else (print("invalid symbol: '" ^ s ^ "'\n");OS.Process.exit(OS.Process.failure))
	else if s = ">" then
	   if lookahead(infile) = (SOME #"=") then (input1(infile);print("symbol: >=\n")) else print("symbol: >\n")
	else if s = "<" then
	   if lookahead(infile) = (SOME #"=") then (input1(infile);print("symbol: <=\n")) else print("symbol: <\n")
	else if s = "=" then
	   if lookahead(infile) = (SOME #"=") then (input1(infile);print("symbol: ==\n")) else print("symbol: =\n")
	else if s = "!" then
	   if lookahead(infile) = (SOME #"=") then (input1(infile);print("symbol: !=\n")) else print("symbol: !\n")
	else
	   (checkSymbol s symbolList)	
)			

fun checkSequence s (x::xs) = 
	if null xs then
           if x = s then true else false
        else if x = s then true else (checkSequence s xs)
;
fun printSequenceError s = 
	print("invalid escape sequence: '" ^ "\\" ^ s ^ "'\n");

fun printString infile s = (
	input1(infile);
	case lookahead(infile) of
	SOME c => 
		if (c = #"\"") then (input1(infile); print("string: "); print(s ^ "\""); print("\n"))
		else if (c = #"\\") then (
			input1(infile);
			if (checkSequence (Char.toString(valOf(lookahead(infile)))) escapes) orelse ("\\" ^ (Char.toString(valOf(lookahead(infile))))) = "\"" then 
			   (printString infile (s ^ "\\" ^ Char.toString(valOf(lookahead(infile))))) 
			else (printSequenceError(Char.toString(valOf(lookahead(infile)))); OS.Process.exit(OS.Process.failure)))
		else (printString infile (s ^ (Char.toString(valOf(lookahead(infile))))))		
	| NONE =>
		(print("string not terminated\n");OS.Process.exit(OS.Process.failure))
)

fun recognizeToken infile =
	case lookahead(infile) of
		SOME c =>
	       		if (Char.isDigit(c)) then (printInts infile (Char.toString(c)) ("number: "))
			else if (Char.isAlpha(c)) then (printChars infile (Char.toString(c)))
			else if (Char.isSpace(c)) then (deleteSpace infile)
			else if (c = #"\"") then (printString infile "\"")
			else (printSymbols infile (Char.toString(c))) 
		|
		NONE =>
			print("end-of-file\n")
;			
