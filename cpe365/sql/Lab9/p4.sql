-- Lab 9 | Part 4
-- Name: Garrett Milster
-- Partner: Ross McKelvie
CREATE OR REPLACE TRIGGER partsWatcher
BEFORE INSERT OR UPDATE OR DELETE ON parts
BEGIN
   dbms_output.put_line('*****' || to_char(sysdate, 'MON-DD-YY, HH:MI:SS') || ' ' || USER || ' changing the parts table.');
END;
/
show errors;