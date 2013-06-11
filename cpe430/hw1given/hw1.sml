(* Garrett Milster -- Assignment 1 *)

(* Part 1 *)
fun intToString x =
	if x >= 0 then
		Int.toString x
	else
		"-" ^ Int.toString(~1 * x)
;

(* Part 2 *)
fun listLength x =
	if x = nil then
		0		
	else
		1 + listLength(tl(x))
;

(* Part 3 *)
fun numberOf v L = 
	if L = nil then
		0
	else
		if hd(L) = v then
			1 + numberOf v (tl(L))
		else
			0 + numberOf v (tl(L))
;


(* Part 4 *)
fun swap (x, y) =
	(y,x)
;

fun pairSwap L = 
	if null L then  
		nil
	else	
		swap(hd(L)) :: pairSwap(tl(L))
;	


(* Part 5 *)
fun getFirst (x,y) =
	x	
;

fun getSecond (x,y) =
	y
;

fun findPair v L =
	if null L then
		NONE
	else if v = (getFirst(hd(L))) then
		SOME (hd L)
	else
	        findPair v (tl(L))
;

(* Part 6 *)
fun unzipFirst L =
	if null L then
		nil
	else
		(getFirst(hd(L))) :: unzipFirst (tl(L))
;

fun unzipSecond L =
	if null L then
		nil			
	else
		(getSecond(hd(L))) :: unzipSecond (tl(L))
;

fun unzip [] =([], []) | unzip L = 
	((unzipFirst L),(unzipSecond L)) 
;


(* Part 7 *)
exception UnbalancedZip 

fun zip L K =
	if (null L) andalso (null K) then
		nil	
	else if (null L) orelse (null K) then
		raise UnbalancedZip
	else
		(hd(L),hd(K)) :: zip (tl(L)) (tl(K))	
;

(* Part 8 *)
fun splitFilter f [] = ([], [])
| splitFilter f (x::xs) =
	let
		val result = splitFilter f xs;
     	in
        	if f x then
	        	((x :: (#1 result)), (#2 result))
   		else
        		((#1 result), (x :: (#2 result)))
 	end;
;

(* Part 9 *)
fun readList(infile) = 
	if TextIO.endOfStream(infile) then 
		nil
	else 
		TextIO.inputN(infile,1) ::readList(infile)
;
		
fun checkSub x L = 
	if null L then x
	else if (getFirst(hd(L))) = x then (getSecond(hd(L)))
	else checkSub x (tl(L))		
;

fun traverse (x::xs) L = 
	if xs = nil then
		(TextIO.print (checkSub x L))		
	else 
		((TextIO.print (checkSub x L));(traverse xs L))
;

fun fileSubst (filename:string) L =
	let
		val file = readList(TextIO.openIn(filename))	
	in
		(traverse file L)
	end;	
;
