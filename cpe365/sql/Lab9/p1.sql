-- Lab 9 | Part 1
-- Name: Garrett Milster
-- Partner: Ross McKelvie
CREATE OR REPLACE
PROCEDURE listOnePart(pno_ parts.pno%type) AS
	-- DELCARE
	price_ parts.price%TYPE;
	QOH_ parts.qoh%TYPE;
	pname_ parts.pname%TYPE;

BEGIN
   SELECT p.pname, p.price, p.qoh INTO pname_, price_, qoh_
   FROM parts p
   WHERE pno = pno_;
   dbms_output.put_line(pname_ || ',' || price_ || ',' || qoh_);
EXCEPTION
   WHEN OTHERS THEN
    dbms_output.put_line('Error occurred: ' || ':' || SQLERRM);

END;
/
show errors