-- Garrett Milster --
-- Partner: Ross McKelvie --
-- Lab 5 part 1 AM --

-- 1 How many different ingredients are there in each recipe? List only those that have more than 6 ingredients
SELECT rname RNAME, i_count INGREDIENT_COUNT
FROM (
   SELECT rname rname, count(*) i_count
   FROM contains
   GROUP BY rname
)
WHERE i_count > 6
ORDER BY INGREDIENT_COUNT ASC, RNAME ASC;

-- 2 List each recipe with profit-per-serving exceeding $2. Output with column heading RNAME and CALORIE_PER_SERVING in cincreasing order of cps, then rname.
-- Do not put the division operator on a line by itself.
SELECT rname RNAME,
   ( SELECT sum(calorie * amount) CALS
      FROM contains c
      INNER JOIN ingredient
      USING (iname)
      WHERE d.rname = c.rname
      GROUP BY rname ) / 
   ( SELECT servings
      FROM recipe
      WHERE rname = d.rname ) AS CALORIE_PER_SERVING
FROM contains d
GROUP BY rname
ORDER BY CALORIE_PER_SERVING, rname;

-- 3 How many recipes contain both salt and pepper
SELECT count(*) COUNT
FROM
(
   SELECT c.rname
   FROM contains c
   INNER JOIN contains d
   ON c.rname = d.rname
   WHERE c.iname = 'salt' AND d.iname = 'pepper'
   GROUP BY c.rname
);

-- 4 What is the ratio of recipes that contain an ingredient in the 'dairy' foodgroup?
-- Use a sub-select for the set of such recipes, count how many, and divide over the count of total recipes.
-- Output with a column heading RATIO above a number.
SELECT
(
   SELECT count(*)
   FROM contains c
   INNER JOIN ingredient i
   ON c.iname = i.iname
   WHERE i.foodGroup = 'dairy'
) /
(
   SELECT count(rname)
   FROM recipe
) AS RATIO
FROM dual;


-- 5 How many distinct combinations of appetizer-entree-dessert are there?
-- An examble of one such combination is Hummus-Meatloaf-Apple treat.
-- Output with the column heading HOW_MANY above a number
SELECT count(*) HOW_MANY
FROM recipe r1, recipe r2, recipe r3
WHERE r1.type = 'appetizer' AND r2.type = 'entree' AND r3.type = 'dessert';

-- 6 List the rname, type, and price of each recipe that contains the ingredient "egg"
-- output in increasing order of rname

-- 6a
SELECT r.rname, r.type, r.price
FROM contains c
INNER JOIN recipe r
ON r.rname = c.rname
WHERE c.iname = 'egg'
ORDER BY r.rname;

-- 6b
SELECT rname, type, price
FROM recipe
WHERE rname IN (
   SELECT rname
   FROM contains
   WHERE iname = 'egg'
)
ORDER BY rname;

-- 6c
SELECT rname, type, price
FROM recipe r
WHERE 'egg' IN (
   SELECT iname
   FROM contains c
   WHERE r.rname = c.rname
)
ORDER BY rname;

-- 6d
SELECT rname, type, price
FROM recipe r
WHERE 'egg' = ANY (
   SELECT iname
   FROM contains c
   WHERE c.rname = r.rname
)
ORDER BY rname;

-- 6e
SELECT rname, type, price
FROM recipe r
WHERE EXISTS (
   SELECT *
   FROM contains c
   WHERE c.rname = r.rname AND c.iname = 'egg'
)
ORDER BY rname;
-- 7. List the rname type and price of each recipe that does not contain the ingredient "egg"; ouput in increasing order of rname.

-- 7a
SELECT rname, type, price
FROM recipe 
MINUS
SELECT rname, type, price
FROM contains INNER JOIN recipe USING (rname)
WHERE iname = 'egg'
ORDER BY rname ASC;

-- 7b
SELECT r.rname, type, price
FROM recipe r
WHERE rname NOT IN (
    SELECT c.rname
    FROM contains c
    WHERE iname = 'egg')
ORDER BY r.rname;

-- 7c
SELECT r.rname, type, price
FROM recipe r
WHERE 'egg' NOT IN (
    SELECT iname
    FROM contains c
    WHERE c.rname = r.rname)
ORDER BY r.rname;

-- 7d
SELECT r.rname, type, price
FROM recipe r
WHERE 'egg' <> ALL (
    SELECT iname
    FROM contains c
    where c.rname = r.rname)
ORDER BY rname;

-- 7e
SELECT r.rname, type, price
FROM recipe r
WHERE NOT EXISTS (
    SELECT *
    FROM contains c
    where c.rname = r.rname and iname = 'egg')
ORDER BY rname;

-- 8. List the rname and price of the recipes with the highest price by using a sub-select in the WHERE clause that generates the highest price of all recipes. Output in increasing order or rname
SELECT x.rname, x.price
FROM recipe x
WHERE x.price = (
    SELECT max(r.price)
    FROM recipe r
    )
ORDER BY x.rname;

-- 9. SAME AS 8 BUT WITH ANY/ALL
SELECT r.rname, r.price
FROM recipe r
WHERE r.price >= ALL (
    SELECT x.price
    FROM recipe x)
ORDER BY r.rname;

-- 10. List the rname of each recipe that has the highest ingredient cost
SELECT rname
FROM contains INNER JOIN ingredient USING (iname)
GROUP BY rname
HAVING sum(cost*amount) =
   (SELECT MAX(sum(cost*amount))
    FROM contains INNER JOIN ingredient USING (iname)
    GROUP BY rname
   );

-- 11. List the rname of each recipe that has the highest ingredient cost using ANY/ALL
SELECT rname
FROM contains INNER JOIN ingredient USING (iname)
GROUP BY rname
HAVING sum(cost*amount) >= ALL
   (SELECT sum(cost*amount)
    FROM contains INNER JOIN ingredient USING (iname)
    GROUP BY rname
   );
