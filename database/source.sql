CREATE TABLE clients (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	surname TEXT NOT NULL,
	street TEXT,
	postalCode TEXT,
	locality TEXT,
	province TEXT
);

CREATE TABLE emails (
	client INTEGER NOT NULL,
	email TEXT NOT NULL,
	PRIMARY KEY(client, email)
	FOREIGN KEY(client) REFERENCES clients(id)
);

CREATE TABLE phones (
	client INTEGER NOT NULL,
	prefix TEXT NOT NULL,
	number TEXT NOT NULL,
	PRIMARY KEY(client, prefix, number)
	FOREIGN KEY(client) REFERENCES clients(id)
);

CREATE TABLE bills (
	id TEXT PRIMARY KEY,
	client INTEGER NOT NULL,
	vat DOUBLE NOT NULL,
	date TEXT NOT NULL,
	FOREIGN KEY(client) REFERENCES clients(id)
);

CREATE TABLE payments (
	id INTEGER PRIMARY KEY,
	bill INTEGER NOT NULL,
	concept TEXT NOT NULL,
	amount DOUBLE NOT NULL,
	FOREIGN KEY(bill) REFERENCES bills(id)
);


