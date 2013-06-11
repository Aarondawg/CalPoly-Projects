---------------------------------------------------------------
-- Recipe Database; Create Airline Tables Script
-- Garrett Milster, Winter 2012
---------------------------------------------------------------

drop table flight cascade constraints;
-- If the table recipe does not already exist, the statement
--   above will cause a harmless error message.
create table flight (
  fno          numeric(4) check (fno between 0 AND 9000),  
  origin       char(3) not null,
  destination  char(3) not null,
  distance     numeric(5) check (distance > 0),
  departs      date not null,    
  arrives      date not null,
  fare         numeric(6,2) check (fare > 0),
  primary key (fno)
);


create table aircraft (
  aid     numeric(3) check (aid between 0 and 100),
  aname   varchar2(30) not null,
  range   numeric(6) check (range > 0),
  primary key(aid)
);

create table employee (
  eid      numeric(9) check (eid > 0),
  ename    varchar2(20) not null,
  salary   numeric(10,2) check (salary > 0),
  sid      numeric(9) check (sid > 0),
  foreign key(sid) references employee (eid),
  primary key(eid)    
);

create table certified (
  eid      numeric(9) check (eid > 0),
  aid      numeric(3) check (aid between 0 and 100),
  foreign key(eid) references employee (eid),
  foreign key(aid) references aircraft (aid),
  primary key(eid,aid)
);
