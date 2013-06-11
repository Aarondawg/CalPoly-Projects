-- Name: Garrett Milster
-- Partner: Ross McKelvie
-- CSC 365 - Lab 04 - AM Section

-- 15 List how many employees there are in the database
SELECT count(*) NUM_EMPLOYEES
FROM employee;

-- 16 Show the value of the lowest, highest, the average, and the total salary of the employees.
SELECT min(salary) LOWEST, max(salary) MAX, avg(salary) AVERAGE, sum(salary) TOTAL
FROM employee;

-- 17 How many pilots are there? (A pilot is an employee certified to fly at least one aircraft)
SELECT count(distinct eid) NUM_PILOTS
FROM certified;

-- 18 List the FNO of each flight that does not originate from LAX in increasing order of FNO.
SELECT fno FNO_NOT_FROM_LAX
FROM flight
WHERE origin <> 'LAX'
ORDER BY fno ASC;

-- 19 How many flights are there that do not originate from LAX?
SELECT count(*) NUM_FLIGHTS_NOT_FROM_LAX
FROM flight
WHERE origin <> 'LAX';

-- 20 List the AID of each aircraft that can cover the distance of flight number 2. Output in increasing order of AID
SELECT aid AIRCRAFT_CAN_COVER_FNO2
FROM aircraft a, flight f
WHERE f.fno = 2 AND a.range >= f.distance
ORDER BY aid ASC;

-- 21 How many aircrafts can cover the distance of flight number 2?
SELECT count(*) NUM_AIRCRAFT_CAN_COVER_FNO2
FROM aircraft a, flight f
WHERE f.fno = 2 AND a.range >= f.distance;

-- 22 List the EID of each employee directly supervised by an employee whose last name is Nguyen. Output in increasing order of eid
SELECT e.eid EMPLOYEES_SUP_BY_NGUYEN
FROM employee e, employee s
WHERE e.sid = s.eid AND s.ename LIKE '%Nguyen';

-- 23 How many employees are directly supervised by an employee whose last name is Nguyen?
SELECT count(*) NUM_EMPLOYEES_SUP_BY_NGUYEN
FROM employee e, employee s
WHERE e.sid = s.eid AND s.ename LIKE '%Nguyen';

-- 24 How many recipes are in each type? Ouput with column heading TYPE and COUNT, in creasing order of the TYPE name
SELECT type TYPE, count(*) COUNT
FROM recipe
GROUP BY type;

-- 25 What is the largest count (a single value) of recipes in any of the (recipe) type?
--    Output the value with a column heading MAX_RECIPE _COUNT_IN_TYPE
SELECT max(count(*)) MAX_RECIPE_COUNT_IN_TYPE
FROM recipe
GROUP BY type;

-- 26 How many different ingredients are there in each recipe?
--    Output should have a heading of RNAME and INGREDIENT_COUNT in increasing order of RNAME
SELECT rname RNAME, count(*) INGREDIENT_COUNT
FROM contains
GROUP BY rname
ORDER BY rname ASC;

-- 27 What is the largest total count of ingredients in any of the recipes?
--    Output the value with a column heading MAX_INGREDIENT_COUNT.
SELECT max(count(*)) MAX_INGREDIENT_COUNT
FROM contains
GROUP BY rname;

-- 28 For each food group that appears in the INGREDIENT table, output how many recipes contain one or more ingredient in that food group.
--    Do not count the same recipe twice for each food group. The output should be under column headings of FOODGROUP and COUNT, in increasing order of FOODGROUP
SELECT i.foodGroup FOODGROUP, count(c.rname) COUNT
FROM ingredient i, contains c
WHERE c.iname = i.iname
GROUP BY i.foodGroup
ORDER BY i.foodGroup ASC;