CREATE TABLE clients (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name TEXT NOT NULL,
	surname TEXT NOT NULL,
	street TEXT,
	postalCode TEXT,
	locality TEXT,
	province TEXT
);

CREATE TABLE emails (
	client INT NOT NULL,
	email TEXT NOT NULL,
	PRIMARY KEY(client, email)
	FOREIGN KEY(client) REFERENCES clients(id)
);

CREATE TABLE phones (
	client INT NOT NULL,
	phone TEXT NOT NULL,
	PRIMARY KEY(client, phone)
	FOREIGN KEY(client) REFERENCES clients(id)
);

CREATE TABLE bills (
	id INT AUTO_INCREMENT PRIMARY KEY,
	client INT NOT NULL,
	vat DOUBLE NOT NULL DEFAULT 0.0
);

CREATE TABLE PAYMENTS (
	id INT AUTO_INCREMENT PRIMARY KEY,
	concept TEXT NOT NULL,
	amount DOUBLE NOT NULL
);


