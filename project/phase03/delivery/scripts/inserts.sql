INSERT INTO Photographer VALUES('David','Rozas','drozas@gm.com','amateur photographer','www.davidrozas.com');
INSERT INTO Photographer VALUES('Miguel','Bono','mbono@gm.com','prof photographer','www.bono.com');



INSERT INTO Camera VALUES(1,'nikon','ae2',1,'David','Rozas');
INSERT INTO Camera VALUES(2,'nikona','ae3',1,'Miguel','Bono');


INSERT INTO Picture VALUES('cabin01.jpg', 'pictures of my cabin trip', '2000-01-01',1,1,'Digital camera');
INSERT INTO Picture VALUES('cabin02.jpg', 'pictures of my cabin trip2', '2000-02-02',1,2,'Digital camera');

INSERT INTO Version(storingDate,description,idPicture) VALUES ('2000-01-01','version one cabin01.jpg','cabin01.jpg');
INSERT INTO Version(storingDate,description,idPicture) VALUES ('2000-01-02','version two cabin01.jpg','cabin01.jpg');

INSERT INTO Version(storingDate,description,idPicture) VALUES ('2000-01-01','version one cabin02.jpg','cabin02.jpg');
INSERT INTO Version(storingDate,description,idPicture) VALUES ('2000-01-02','version two cabin02.jpg','cabin02.jpg');





