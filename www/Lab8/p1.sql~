/**
p1.sql in PL/SQL
Garrett Milster
Partner: Ross McKelvie
*/

DEClARE
   pno_ parts.pno%TYPE;
   price_ parts.price%TYPE;
   QOH_ parts.qoh%TYPE;
   pname_ parts.pname%TYPE;

BEGIN

   pno_:=&pno_;
   SELECT p.pname, p.price, p.qoh INTO pname_, price_, qoh_
   FROM parts p
   WHERE  pno = pno_;
   dbms_output.put_line(pname_ || ',' || price_ || ',' || qoh_);
EXCEPTION
   WHEN OTHERS THEN
    dbms_output.put_line('Error occurred: ' || ':' || SQLERRM);
END;
/
