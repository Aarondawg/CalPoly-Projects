/***
 Winter 2012 CPE365
 lab 7 part 1 
 Name: Garrett Milster
 Partner: Ross McKelvie
***/

SET AUTOCOMMIT OFF;
SET FEEDBACK OFF
SET ECHO OFF
@clear_mail
@insert_mail
SET FEEDBACK ON
SET ECHO ON

-- 1. Add a customer to the database with these values: CNO=4444, CNAME = "Smith" , STREET = "666 Easy St". 
--    The zipcode is that of the city "Columbia"; phone number is not known.  Hint: Use a sub-select to retrieve
--    the value of the zipcode.
INSERT INTO customers (cno, cname, street, zip) VALUES (4444, 'Smith', '666 Easy St', (
   SELECT zip FROM zipcodes WHERE city = 'Columbia'
));

-- List the table Customers to show the effect of the changes made.
SELECT * FROM customers ORDER BY cno;

-- 2. Customer "Barbara" in zip 60606 has gotten married.  Add her husband, named "John",
--  as a  customer.  His CNO is 1 greater than that of Barbara and he has the same
-- street, zip, and phone as "Barbara".
INSERT into customers (
   SELECT (cno + 1), 'John', street, zip, phone
   FROM customers
   WHERE cno = (
      SELECT cno
      FROM customers
      WHERE zip = '60606' AND cname = 'Barbara'
   )
);

-- List the table Customers to show the effect of the changes made.
SELECT * FROM customers ORDER BY cno;

-- 3. Write a single DELETE statement to remove the customers whose CNOs are greater than
-- that of of the customer named Barbara in zip 60606.  You may assume that these customers
-- have not made any orders.
DELETE FROM customers
WHERE cno > (
   SELECT cno
   FROM customers
   WHERE zip = '60606' AND cname = 'Barbara'
);

-- List the table Customers to show the changes made.
SELECT * FROM customers ORDER BY cno;
-- The database should now be back to its original state.

-- 4. Write a single update statement to increase BY 10% the price of each part priced below 20 dollars.
UPDATE parts
SET price = (price + price * 0.1)
WHERE price < 20;

-- cancel the changes made to restore the database to its original state
ROLLBACK;

-- 5. Remove from the database each employee who has not made any order.
DELETE FROM employees
WHERE eno NOT IN (
   SELECT eno
   FROM orders
);

-- Show the outcome of the change.
SELECT * FROM employees ORDER BY eno;

ROLLBACK;

-- 6a) Preliminary: Write a statement to show the count of how many orders in
--     which each PNO has been ordered.  Output in increasing order of the count.
SELECT pno PART_NO, count(*) NUM_TIMES_ORDERED
FROM odetails
GROUP BY pno
ORDER BY NUM_TIMES_ORDERED;

-- 6b) For each part that has been ordered in less than two orders, reduce the price by 10%.
UPDATE parts SET price = (price - price * 0.1)
WHERE pno IN (
   SELECT pno
   FROM odetails
   GROUP BY pno
   HAVING count(*) < 2
);

-- List the parts table to show that the changes have taken effect.
SELECT * FROM parts ORDER BY pno;

ROLLBACK;

-- 7. Write as few statements as you can to change the pno of the part named "Sleeping Beauty" to 12345.
--    You should not assume to know what the current pno of this part is.
INSERT INTO parts (
   SELECT 12345, 'Sleeping Beauty', qoh, price, olevel
   FROM parts
   WHERE pname = 'Sleeping Beauty'
);

UPDATE odetails
SET pno = 12345
WHERE pno = (
   SELECT pno
   FROM parts
   WHERE pname = 'Sleeping Beauty' AND pno <> 12345
);

DELETE FROM parts
WHERE pname = 'Sleeping Beauty' AND pno <> 12345;


-- Show what's in the parts and odetails table afterward.
SELECT * FROM parts ORDER BY pno;
SELECT * FROM odetails ORDER BY ono, pno;

ROLLBACK;

-- 8.	The company is giving up on the city with zipcode 67226.  Write as few statements as possible to remove 
--      that city from the zipcodes table and all tuples depended, directly or indirectly, on that zipcode.
DELETE FROM odetails
WHERE ono IN (
   SELECT ono
   FROM orders
   WHERE cno IN (
      SELECT cno
      FROM customers
      WHERE zip = 67226
   ) OR eno IN (
      SELECT eno
      FROM employees
      WHERE zip = 67226
   )
);

DELETE FROM orders
WHERE cno IN (
   SELECT cno
   FROM customers
   WHERE zip = 67226
) OR eno IN (
   SELECT eno
   FROM employees
   WHERE zip = 67226
);

DELETE FROM employees
WHERE zip = 67226;

DELETE FROM customers
WHERE zip = 67226;

DELETE FROM zipcodes
WHERE zip = 67226;

-- Show what's in the database afterward.
SELECT * FROM zipcodes ORDER BY zip;

SELECT * FROM  customers ORDER BY cno;

SELECT * FROM  employees ORDER BY eno;

SELECT * FROM  orders ORDER BY ono;

SELECT * FROM  odetails ORDER BY ono, pno;

ROLLBACK;

-- THE END --
