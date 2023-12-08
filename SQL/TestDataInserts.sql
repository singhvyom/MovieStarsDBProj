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

INSERT INTO Admin(name, username, passwd, state, phone, email, tax_id) VALUES	('John Admin','admin','secret','CA','(805)6374632','admin@stock.com','000001000');

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

INSERT INTO ActorProfileStock(name, stock_symbol, current_price, closing_price, dob) VALUES	('Kim Basinger','SKB',40.0,40.0,'08-DEC-1958');
INSERT INTO ActorProfileStock(name, stock_symbol, current_price, closing_price, dob) VALUES	('Michael Douglas','SMD',71,71,'25-SEP-1944');
INSERT INTO ActorProfileStock(name, stock_symbol, current_price, closing_price, dob) VALUES	('Tom Cruise','STC',32.5,32.5,'03-JUL-1962');

INSERT INTO Movie(title, year) VALUES ('L.A. Confidential',1997);
INSERT INTO Movie(title, year) VALUES ('A Perfect Murder',1998);
INSERT INTO Movie(title, year) VALUES ('Jerry Maguire',1996);

INSERT INTO Review(title, year, rating, review) VALUES	('L.A. Confidential',1997,10.0,'I loved it - it is almost as good as Chinatown!');
INSERT INTO Review(title, year, rating, review) VALUES	('L.A. Confidential',1997,10.0,'Super clever story with an amazing cast');
INSERT INTO Review(title, year, rating, review) VALUES	('A Perfect Murder',1998,6.1,'Truly one of the movies of all time.');
INSERT INTO Review(title, year, rating, review) VALUES	('Jerry Maguire',1996,8.3,'What an emotional rollercoaster!');

INSERT INTO Contract(contract_id, title, year, stock_symbol, actor_role, total_value)VALUES (1, 'L.A. Confidential', 1997, 'SKB', 'Actor', 5000000);
INSERT INTO Contract(contract_id, title, year, stock_symbol, actor_role, total_value)VALUES (2, 'A Perfect Murder', 1998, 'SMD', 'Actor', 10000000);
INSERT INTO Contract(contract_id, title, year, stock_symbol, actor_role, total_value)VALUES (3, 'Jerry Maguire', 1996, 'STC', 'Actor', 5000000);

Commit;