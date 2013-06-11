-- Lab 9 | Part 3
-- Name: Garrett Milster
-- Partner: Ross McKelvie
CREATE OR REPLACE
FUNCTION orderValue(ono_ odetails.ono%type)
	RETUMBER;
AS
   ret odetails.qty%TYPE;

BEGIN
   SELECT SUM (p.price * d.qty) into ret
   FROM parts p inner join odetails d ON p.pno = d.pno
   WHERE p.pno in (
         SELECT pno
         FROM odetails
         WHERE ono = ono_
   )
   GROUP BY d.ono;
   RETURN (ret);

EXCEPTION
   WHEN OTHERS THEN
      ret := NULL;
      RETURN ret;
END;
/
show errors;
