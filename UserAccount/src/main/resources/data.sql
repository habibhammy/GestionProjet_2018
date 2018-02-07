MERGE INTO users KEY(id, username, passwords, email, firstname, lastname, country) 
VALUES 
     	('100','alice','alice12','alice@exemple.com','alice','alice','FRANCE'),
		('200','bob','bob12','bob@exemple.com','bob','bob','CANADA'),
		('300','charles','charles12','charles@exemple.com','charles','charles','MAROC');