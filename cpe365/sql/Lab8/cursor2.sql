/***************************************************************
   PL/SQL: An anonymous block that uses SELECT INTO
           and multiple cursors.                
  M. Liu,  Winter 2011                                             
****************************************************************/
DECLARE
  pno_     parts.pno%TYPE;
  pname_   parts.pname%TYPE;

  CURSOR cust_cur IS
      SELECT cno, cname
      FROM   customers
      WHERE  cno IN (SELECT cno FROM orders WHERE (ono, pno_) 
                     IN (SELECT ono, pno FROM odetails));
  cust_rec   cust_cur%ROWTYPE;

  CURSOR emp_cur IS
      SELECT eno, ename
      FROM   employees
      WHERE  eno IN (SELECT eno FROM orders WHERE (ono, pno_) 
                     IN (SELECT ono, pno FROM odetails));
  emp_rec    emp_cur%ROWTYPE;
  count_ INTEGER:= 0;

BEGIN
  pno_ := &pno;
  -- Fetch the part's name
  SELECT pname INTO pname_
  FROM   parts
  where  pno = pno_;
  
  DBMS_OUTPUT.PUT_LINE('****The part ' || pno_ || ', ' || pname_ ||
                       ', has been ordered by these customers:');
  FOR cust_rec in cust_cur LOOP
    EXIT WHEN cust_cur%NOTFOUND;
      count_ := count_ + 1;
      DBMS_OUTPUT.PUT_LINE(
          count_ || '. ' || cust_rec.cno || ', ' || cust_rec.cname);   
  END LOOP;
  IF (count_ = 0) THEN
    DBMS_OUTPUT.PUT_LINE('There is no such customer');
  END IF; 

  DBMS_OUTPUT.PUT_LINE('****The part has been ordered by these employees:');
  count_ := 0;
  FOR emp_rec in emp_cur LOOP
    EXIT WHEN emp_cur%NOTFOUND;
      count_ := count_ + 1;
      DBMS_OUTPUT.PUT_LINE(
          count_ || '. ' || emp_rec.eno || ', ' || emp_rec.ename);   
  END LOOP;
  IF (count_ = 0) THEN
    DBMS_OUTPUT.PUT_LINE('There is no such employee');
  END IF; 

EXCEPTION
 WHEN OTHERS THEN
      dbms_output.put_line('Error occurred: '|| 
                           ': ' || SQLERRM );
END;
/
