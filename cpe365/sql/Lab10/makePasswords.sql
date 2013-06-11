---------------------------------------------------------------
-- Creates and populate a table for experimenting
-- with SQL Injection
-- M. Liu, Spring 2008
---------------------------------------------------------------
SET AUTOCOMMIT OFF;
DROP TABLE passwords;
CREATE TABLE passwords(
  x_   VARCHAR2(8),
  y_   VARCHAR2(8),
  primary key (x_));

INSERT INTO passwords VALUES ('tom', 'tom1234');
INSERT INTO passwords VALUES ('mary', 'mary2005');
INSERT INTO passwords VALUES ('harry', 'harry999');
----------------------------------------------------------------
COMMIT;
