/***************************************************************
   PL/SQL: An anonymous block that illustrates the use of a 
           cursor to SELECT multiple rows.               
  M. Liu, Winter 2012                                             
****************************************************************/
DECLARE
  -- Declare a basic cursor for use with fetching rows 
  -- successively from the customers table.
  CURSOR cust_cur IS
      SELECT *
      FROM  customers
      ORDER BY cno;
  cust_rec   cust_cur%ROWTYPE;
  order_count INTEGER := 0;

BEGIN
  -- List all customers
  DBMS_OUTPUT.PUT_LINE('****The customers are:');
  FOR cust_rec IN cust_cur LOOP
    EXIT WHEN cust_cur%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(
          cust_cur%ROWCOUNT || '. ' || cust_rec.cno || ', ' || cust_rec.cname);
  END LOOP;

END;
/
