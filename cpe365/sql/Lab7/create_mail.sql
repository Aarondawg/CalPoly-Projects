---------------------------------------------------------------
-- Mail Order Database; Create Tables Script
-- Chapter 2; Oracle Programming -- A Primer
--            by R. Sunderraman
-- Modified by M. Liu, Fall 2011
---------------------------------------------------------------
drop table zipcodes cascade constraints;
create table zipcodes (
  zip      number(5),
  city     varchar2(30),
  primary key (zip));

drop table employees cascade constraints;
create table employees (
  eno      number(4) not null primary key, 
  ename    varchar2(10),
  zip      references zipcodes,
  hdate    date);

drop table parts cascade constraints;
create table parts(
  pno      number(5) not null primary key,
  pname    varchar2(30),
  qoh      integer check(qoh >= 0),
  price    number(6,2) check(price >= 0.0),
  olevel   integer);

drop table customers cascade constraints;
create table customers (
  cno      number(5) not null primary key,
  cname    varchar2(10),
  street   varchar2(30),
  zip      references zipcodes,
  phone    char(12));
 
drop table orders cascade constraints;
create table orders (
  ono      number(5) not null primary key,
  cno      references customers,
  eno      references employees,
  received date,
  shipped  date);

drop table odetails cascade constraints;
create table odetails (
  ono      not null references orders,
  pno      not null references parts,
  qty      integer check(qty > 0),
  primary key (ono,pno));
