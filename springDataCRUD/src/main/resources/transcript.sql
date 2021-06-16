drop database if exists springdatacrud;
create database springdatacrud;
use  springdatacrud;

create table customer(
   customer_id int AUTO_INCREMENT PRIMARY KEY,
   name varchar(50),
   city varchar(50),
   phone_no varchar(10),
   email_id varchar(50),
   birth_date DATE,
   password varchar(50)
);

create table admin(
  admin_id int AUTO_INCREMENT PRIMARY KEY,
  login_id varchar(50),
  password varchar(50)
);

insert into admin(admin_id,login_id,password) values (1,'admin','admin123');