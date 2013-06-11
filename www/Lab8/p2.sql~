/**
p2.sql in PL/SQL
Garrett Milster
Partner: Ross McKelvie
*/

DECLARE

pno_ parts.pno%TYPE;

CURSOR part_cur IS
   SELECT ono, pno, qty
   FROM odetails
   WHERE pno = pno_
   ORDER BY ono;

part_rec part_cur%ROWTYPE;
order_count INTEGER :=0;

BEGIN
   pno_:=&pno_;
   dbms_output.put_line('***The orders for the part you chose are:');
   FOR part_rec in part_cur LOOP
     EXIT WHEN part_cur%NOTFOUND;
   dbms_output.put_line(
      part_cur%ROWCOUNT || '. ' || part_rec.ono || ', ' || part_rec.pno || ', ' || part_rec.qty);
   END LOOP;
   
EXCEPTION
   WHEN OTHERS THEN
    dbms_output.put_line('Error occurred: ' || ':' || SQLERRM);
END;
/

