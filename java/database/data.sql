BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO 
	prize(name, description, milestone, maxprizes, startdate, enddate) 
values
	('star', 'https://clipartix.com/gold-star-clipart-image-46710/', 10,100 ,'08/09/2022', '08/30/2022' ),
	('puppy', 'https://www.pinterest.com/pin/817895982300625447/', 10,100 ,'08/09/2022', '08/30/2022' ),
	('bike', 'https://www.shutterstock.com/search/bicycle', 10,100 ,'08/09/2022', '08/30/2022' ),
	('shirt', 'https://www.rushordertees.com/design-t-shirts/', 10,100 ,'08/09/2022', '08/30/2022' ),
	('kitten', 'https://i.etsystatic.com/33728303/r/il/239341/3579213608/il_794xN.3579213608_re6w.jpg', 10,100 ,'08/09/2022', '08/30/2022' );



COMMIT TRANSACTION;
