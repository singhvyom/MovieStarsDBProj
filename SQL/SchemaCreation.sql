DROP TABLE Customer;

CREATE TABLE Customer
(
	name char(20),
	state char(2),
	phone char(15),
	email char(30),
	tax_id char(9),
	username char(20),
	passwd char(20),
	PRIMARY KEY (username),
	CONSTRAINT c_unique_phone UNIQUE (phone),
    CONSTRAINT c_unique_email UNIQUE (email)
);

INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Alfred Hitchcock','alfred','hi','CA','(805)2574499','alfred@hotmail.com','000001022');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Billy Clinton','billy','cl','CA','(805)5629999','billy@yahoo.com','000003045');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Cindy Laugher','cindy','la','CA','(805)6930011','cindy@hotmail.com','000002034');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('David Copperfill','david','co','CA','(805)8240011','david@yahoo.com','000004093');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Elizabeth Sailor','sailor','sa','CA','(805)1234567','sailor@hotmail.com','000001234');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('George Brush','brush','br','CA','(805)1357999','george@hotmail.com','000008956');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Ivan Stock','ivan','st','NJ','(805)3223243','ivan@yahoo.com','000002341');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Joe Pepsi','joe','pe','CA','(805)5668123','pepsi@pepsi.com','000000456');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Magic Jordon','magic','jo','NJ','(805)4535539','jordon@jordon.org','000003455');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Olive Stoner','olive','st','CA','(805)2574499','olive@yahoo.com','000001123');
INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES	('Frank Olson','frank','ol','CA','(805)3456789','frank@gmail.com','000003306');