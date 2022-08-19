BEGIN TRANSACTION;

DROP TABLE IF EXISTS prize;
DROP TABLE IF EXISTS family_user;
DROP TABLE IF EXISTS family_account;
DROP TABLE IF EXISTS reading_details;
DROP TABLE IF EXISTS book_user;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS public.users;


CREATE TABLE IF NOT EXISTS prize (
	prize_id SERIAL,
	name varchar(500) NOT NULL UNIQUE,
	description varchar(500) NOT NULL,
	milestone int NULL,
	maxprizes int NULL,
	startdate date NULL,
	enddate date NULL,

	CONSTRAINT PK_prizeid PRIMARY KEY (prize_id)
);

CREATE TABLE IF NOT EXISTS book (
	book_id SERIAL,
	book_name varchar(500) NOT NULL,
	isbn varchar(50) NOT NULL,
	description varchar(10000) NULL,
	author varchar(50) NOT NULL,
	numberofpages int DEFAULT 0,
	rating varchar(2) NULL,
	genre varchar(50) NULL,
	format varchar(50) DEFAULT 'PAPER',
	UNIQUE(isbn),

	CONSTRAINT PK_bookid PRIMARY KEY (book_id)
);


CREATE TABLE IF NOT EXISTS public.users
(
    user_id  SERIAL,
    username character varying(50) NOT NULL,
    password_hash character varying(200) NOT NULL,
    role character varying(50),
    numofprizes integer DEFAULT 0,
    minutesread integer DEFAULT 0,
    booksread integer DEFAULT 0,
    status smallint DEFAULT 1,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT users_username_key UNIQUE (username),
	UNIQUE(username)
);



CREATE TABLE IF NOT EXISTS family_account (
	family_id SERIAL,
	family_name varchar(50) NOT NULL,
	CONSTRAINT PK_family PRIMARY KEY (family_id)
);

CREATE TABLE IF NOT EXISTS family_user (
	family_id INT NOT NULL,
	user_id INT NOT NULL,
	CONSTRAINT FK_family_id FOREIGN KEY (family_id) REFERENCES family_account (family_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS book_user (
	book_id INT NOT NULL,
	user_id INT NOT NULL,
	status varchar(15) DEFAULT 'UNREAD',
	CONSTRAINT FK_book_id FOREIGN KEY (book_id) REFERENCES book (book_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS reading_details(
	book_id INT NOT NULL,
	isbn varchar(50) NOT NULL,
	user_id INT NOT NULL, 
	minutes_read INT DEFAULT 0,
	CONSTRAINT FK_book_id FOREIGN KEY (book_id) REFERENCES book (book_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);



COMMIT TRANSACTION;

select user_id from users where username = 'Sebrina'


