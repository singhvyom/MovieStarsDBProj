-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

DROP TABLE Customer;

CREATE TABLE Customer
(
	name varchar2(20),
	state varchar2(2),
	phone varchar2(15),
	email varchar2(30),
	tax_id varchar2(9),
	username varchar2(20),
	passwd varchar2(20),
	PRIMARY KEY (username),
	--CONSTRAINT c_unique_phone UNIQUE (phone),
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

Commit;

DROP TABLE MarketAccount;

CREATE TABLE MarketAccount
(
    mkta_id int,
    username varchar2(20),
    balance float,
    PRIMARY KEY (mkta_id),
    FOREIGN KEY (username) REFERENCES Customer(username),
    CHECK (balance >= 0)
);

INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('alfred',001,10000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('billy',002,100000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('cindy',003,50000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('david',004,45000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('sailor',005,200000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('brush',006,5000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('ivan',007,2000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('joe',008,10000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('magic',009,130200);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('olive',010,35000);
INSERT INTO MarketAccount(username, mkta_id, balance) VALUES	('frank',011,30500);

Commit;

CREATE TABLE ActorProfileStock
(
    name varchar2(20),
    stock_symbol varchar2(3),
    current_price float,
    closing_price float,
    dob date,
    PRIMARY KEY (stock_symbol),
    CONSTRAINT c_unique_name UNIQUE (name) -- keep this?
);

INSERT INTO ActorProfileStock(name, stock_symbol, current_price, closing_price, dob) VALUES	('Alfred Hitchcock','AFH',100,100,'13-DEC-1899');
INSERT INTO ActorProfileStock(name, stock_symbol, current_price, closing_price, dob) VALUES	('Billy Clinton','BCL',200,200,'14-DEC-1899');

CREATE TABLE Movie
(
    title varchar2(20),
    year int,
    PRIMARY KEY (title, year)
);

INSERT INTO Movie(title, year) VALUES	('Psycho',1960);
INSERT INTO Movie(title, year) VALUES	('The Birds',1963);

--DROP TABLE Contract;
CREATE TABLE Contract
(
    contract_id int,
    title varchar2(20),
    year int,
    stock_symbol varchar2(3),
    actor_role varchar2(10),
    total_value float,
    PRIMARY KEY (contract_id),
    FOREIGN KEY (title, year) REFERENCES Movie(title, year),
    FOREIGN KEY (stock_symbol) REFERENCES ActorProfileStock(stock_symbol)
);

INSERT INTO Contract(contract_id, title, year, stock_symbol, actor_role, total_value)VALUES (001, 'Psycho', 1960, 'AFH', 'Director', 100000);
INSERT INTO Contract(contract_id, title, year, stock_symbol, actor_role, total_value)VALUES (002, 'The Birds', 1963, 'BCL', 'Director', 200000);

--DROP TABLE Review;
CREATE TABLE Review
(
    title varchar2(20),
    year int,
    rating float,
    review varchar2(100),
    FOREIGN KEY (title, year) REFERENCES Movie(title, year),
    CHECK ( rating >= 0 and rating <= 10 )
);

INSERT INTO Review(title, year, rating, review) VALUES	('Psycho',1960,8,'Great movie!');
INSERT INTO Review(title, year, rating, review) VALUES	('The Birds',1963,9,'Greater movie!');

Commit;

--DROP TABLE StockAccount;
CREATE TABLE StockAccount
(
    stock varchar2(3),
    mkta_id int,
    shares_owned float,
    PRIMARY KEY (stock, mkta_id),
    FOREIGN KEY (stock) REFERENCES ActorProfileStock(stock_symbol),
    FOREIGN KEY (mkta_id) REFERENCES MarketAccount(mkta_id)
);

INSERT INTO StockAccount(stock, mkta_id, shares_owned)VALUES ('AFH', 001, 100);

--DROP TABLE MarketAccountTransaction;
CREATE TABLE MarketAccountTransaction
(
    transaction_id int GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL,
    mkta_id int,
    amount float,
    type varchar2(10),
    transaction_date date,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (mkta_id) REFERENCES MarketAccount(mkta_id)
);

INSERT INTO MarketAccountTransaction(mkta_id, amount, type, transaction_date)VALUES (001, 100, 'BUY', '01-JAN-2018');

--DROP TABLE StockAccountTransaction;
CREATE TABLE StockAccountTransaction
(
    transaction_id int GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL,
    stock varchar2(3),
    mkta_id int,
    shares float,
    type varchar2(10),
    transaction_date date,
    profit float,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (stock, mkta_id) REFERENCES StockAccount(stock, mkta_id)
);

INSERT INTO StockAccountTransaction(stock, mkta_id, shares, type, transaction_date, profit)VALUES ('AFH', 001, 100, 'BUY', '01-JAN-2018', 0);

CREATE TABLE SysInfo
(
    market_date date,
    is_open boolean,
    PRIMARY KEY (market_date)
)

Commit;