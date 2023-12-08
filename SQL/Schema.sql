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

CREATE TABLE Admin
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

CREATE TABLE MarketAccount
(
    mkta_id int,
    username varchar2(20),
    balance float,
    PRIMARY KEY (mkta_id),
    FOREIGN KEY (username) REFERENCES Customer(username),
    CHECK (balance >= 0)
);

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

CREATE TABLE Movie
(
    title varchar2(20),
    year int,
    PRIMARY KEY (title, year)
);

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

CREATE TABLE Review
(
    title varchar2(20),
    year int,
    rating float,
    review varchar2(100),
    FOREIGN KEY (title, year) REFERENCES Movie(title, year),
    CHECK ( rating >= 0 and rating <= 10 )
);

CREATE TABLE StockAccount
(
    stock varchar2(3),
    mkta_id int,
    shares_owned float,
    PRIMARY KEY (stock, mkta_id),
    FOREIGN KEY (stock) REFERENCES ActorProfileStock(stock_symbol),
    FOREIGN KEY (mkta_id) REFERENCES MarketAccount(mkta_id)
);

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

CREATE TABLE SysInfo
(
    market_date date,
    is_open number(1),
    PRIMARY KEY (market_date)
);

CREATE TABLE DailyStockPrice
(
    stock varchar2(3),
    day date,
    closing_price float,
    PRIMARY KEY (stock, day),
    FOREIGN KEY (stock) REFERENCES ActorProfileStock(stock_symbol)
);