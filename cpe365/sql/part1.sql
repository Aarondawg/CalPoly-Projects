-- Name: Garrett Milster
-- Partner: Ross McKelvie
-- CSC 365 - Lab 04 - AM Section

-- 1 List the rname of each recipe that contains the ingredient named 'salt'
SELECT rname
FROM contains
WHERE iname = 'salt'
ORDER BY rname ASC;

-- 2 List the rname of each recipe that does not contain the ingredient 'salt'
SELECT rname
FROM contains
MINUS
SELECT rname
FROM contains
WHERE iname = 'salt'
ORDER BY rname ASC;

-- 3 List the rname of each recipe that contains either the ingredient 'salt' or the ingredient named 'pepper' or both
SELECT rname
FROM contains
WHERE iname = 'salt'
UNION
SELECT rname
FROM contains
WHERE iname = 'pepper'
ORDER BY rname ASC;

-- 4 List the rname of each recipe that contains the ingredient 'salt' and the ingredient 'pepper'
SELECT rname
FROM contains
WHERE iname = 'salt'
INTERSECT
SELECT rname
FROM contains
WHERE iname = 'pepper'
ORDER BY rname ASC;

-- 5 List the rname of each recipe that contains the ingredient 'salt' but not 'pepper'
SELECT rname
FROM contains
WHERE iname = 'salt'
MINUS
SELECT rname FROM contains
WHERE iname = 'pepper'
ORDER BY rname ASC;

-- 6 List the rname of each recipe that contains neither 'salt' nor 'pepper'
SELECT rname FROM contains
MINUS
SELECT rname FROM contains
WHERE iname = 'salt'
MINUS
SELECT rname FROM contains
WHERE iname = 'pepper'
ORDER BY rname ASC;

-- 7 List the rname, iname, amount, and foodGroup of each ingredient in each recipe, order by rname then iname
SELECT rname, c.iname, amount, foodGroup
FROM contains c, ingredient i
WHERE c.iname = i.iname
ORDER BY rname, iname ASC;

-- 8 See #7, use NATURAL JOIN
SELECT rname, iname, amount, foodGroup
FROM contains NATURAL JOIN ingredient
ORDER BY rname, iname ASC;

-- 9 See #7, use INNER JOIN
SELECT rname, iname, amount, foodGroup
FROM contains INNER JOIN ingredient USING (iname)
ORDER BY rname, iname ASC;

-- 10 List the rname and type of each recipe that contains the ingredient 'salt'
SELECT DISTINCT rname, type
FROM contains INNER JOIN recipe USING (rname)
WHERE iname = 'salt'
ORDER BY rname ASC;

-- 11 List rname of each recipe that contains at least one ingredient in the food group 'dairy'
SELECT DISTINCT rname
FROM contains INNER JOIN ingredient USING (iname)
WHERE foodGroup = 'dairy'
ORDER BY rname ASC;

-- 12 List the rname of each recipe that contains no ingredient in the food group "dairy"
SELECT DISTINCT rname FROM contains
MINUS
SELECT DISTINCT rname
FROM contains INNER JOIN ingredient USING (iname)
WHERE foodGroup = 'dairy'
ORDER BY rname ASC;

-- 13 List the rname and price of each recipe that contains one or more ingredient in the food group diary
SELECT DISTINCT r.rname, r.price
FROM contains c, recipe r, ingredient i
WHERE r.rname = c.rname AND i.iname = c.iname AND i.foodGroup = 'dairy'
ORDER BY r.rname ASC;

-- 14 List the names of the least costly ingredients.
SELECT iname
FROM ingredient
MINUS
SELECT i.iname
FROM ingredient i, ingredient x
WHERE i.cost > x.cost
ORDER BY iname ASC;