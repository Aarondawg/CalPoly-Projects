(* Garrett Milster *)
(* Assignment 2 *)

(* Part 1 *)
datatype 'a List =
     ListNode of ('a * 'a List)
        | EmptyList
;

fun consList (x,y) =
	ListNode(x,y)
;

fun headList (ListNode (x,y)) = 
	x
;

fun tailList (ListNode (x,y)) =
	y
;


(* Part 2 *)

fun lengthList EmptyList = 0 | lengthList (ListNode(x,y)) =
	1 + (lengthList (y))       	
;

(* Part 3 *)
fun mapList f EmptyList = EmptyList | mapList f (ListNode(x,y)) =
	ListNode((f x), (mapList f y)) 
;

datatype 'a BinTree =
	BinTreeNode of {value: 'a, lft: 'a BinTree, rht: 'a BinTree}
	| EmptyBinTree
;

(* Part 4 *)
fun mapBinTree f EmptyBinTree = EmptyBinTree
| mapBinTree f (BinTreeNode{value = v, lft = l, rht = r}) =
	BinTreeNode{value = (f v), lft = (mapBinTree f l), rht = (mapBinTree f r)}	
;

(* Part 5 *)
datatype 'a ThingCollection =
     OneThing of ('a * 'a ThingCollection)
        | TwoThings of ('a * 'a * 'a ThingCollection)
	   | ManyThings of ('a list * 'a ThingCollection)
	      | Nothing
;

fun listLength [] = 0 | listLength (x::xs) =
	if null xs then 1
	else 1 + (listLength xs)
;

fun countThingsInCollection Nothing = 0 
	| countThingsInCollection (OneThing(x,y)) = 1 + (countThingsInCollection y)
		| countThingsInCollection (TwoThings(x,y,z)) = 2 + (countThingsInCollection z) 
			| countThingsInCollection (ManyThings(x,y)) = (listLength x) + (countThingsInCollection y)
		 
;

(* Part 6 *)
fun countOneThingNodes Nothing = 0 
        | countOneThingNodes (OneThing(x,y)) = 1 + (countOneThingNodes y)
                | countOneThingNodes (TwoThings(x,y,z)) = 0 + (countOneThingNodes z)
                        | countOneThingNodes (ManyThings(x,y)) = 0 + (countOneThingNodes y)

;

(* Part 7 *)
fun countNodesByPredicate f Nothing = if (f Nothing) then 1 else 0
	| countNodesByPredicate f (OneThing(x,y)) = if (f (OneThing(x,y))) then 1 + (countNodesByPredicate f y) else (countNodesByPredicate f y)
	| countNodesByPredicate f (TwoThings(x,y,z)) = if (f (TwoThings(x,y,z))) then 1 + (countNodesByPredicate f z) else (countNodesByPredicate f z)
	| countNodesByPredicate f (ManyThings(x,y)) = if (f (ManyThings(x,y))) then 1 + (countNodesByPredicate f y) else (countNodesByPredicate f y)
;

(* Part 8 *)
fun countTwoThingsNodes things = countNodesByPredicate (fn (TwoThings _) => true | _ => false) things;

(* Part 9 *)
fun reduceThingCollection f b Nothing = b 
	| reduceThingCollection f b (OneThing(x,y)) = f(x,(reduceThingCollection f b y))
	| reduceThingCollection f b (TwoThings(x,y,z)) = f(x, f(y,(reduceThingCollection f b z)))
	| reduceThingCollection f b (ManyThings(x,y)) = foldr f (reduceThingCollection f b y) x
;	

(* Part 10 *)
fun flatten Nothing = [] |
	flatten (OneThing(x,y)) = x::(flatten y) |
	flatten (TwoThings(x,y,z)) = x::y::(flatten z) |
	flatten (ManyThings(x,y)) = x @ (flatten y)
;	

fun flattenThingCollection thing =
	let val x = (flatten thing)
	in ManyThings(x,Nothing)
	end;
;	
