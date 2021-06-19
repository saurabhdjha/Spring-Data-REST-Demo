drop database if exists springdatacrud;
create database springdatacrud;
use  springdatacrud;

create table address(
	address_id int AUTO_INCREMENT PRIMARY KEY,
	city varchar(50),
	state varchar(50),
	pincode int(6)
	
);

create table customer(
   customer_id int AUTO_INCREMENT PRIMARY KEY,
   name varchar(50),
   address_id int unique,
   phone_no varchar(10),
   email_id varchar(50),
   birth_date DATE,
   password varchar(50),
   constraint ps_customer_address_fk foreign key(address_id) references address(address_id)
);

create table admin(
  admin_id int AUTO_INCREMENT PRIMARY KEY,
  login_id varchar(50),
  password varchar(50)
);

create table loan(
	loan_id int AUTO_INCREMENT PRIMARY KEY,
	amount double precision,
	issue_date DATE,
	loan_type varchar(30),
	status varchar(20),
	customer_id int,
	constraint fk_cust_loan foreign key(customer_id) references customer(customer_id)
);

create table card(
	card_id int AUTO_INCREMENT PRIMARY KEY,
	card_no varchar(16),
	exp_date DATE,
	customer_id int,
	constraint fk_card_cust foreign key(customer_id) references customer(customer_id)
);

insert into admin(admin_id,login_id,password) values (1,'admin','admin123');