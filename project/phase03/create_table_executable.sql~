--Entity photographer
CREATE TABLE Photographer ( 
firstName VARCHAR(20),
lastName VARCHAR(20),
mail VARCHAR(20),
description VARCHAR(200),
homepage VARCHAR(30),
PRIMARY KEY (firstName,lastName));

--Entity Camera
--digital was a bool, changed to int 
--reference to photographer changed to restricted
--reference to photographer changed to restricted, if I want to delete a photographer, i have to delete all its camera before
CREATE TABLE Camera ( 
id INTEGER PRIMARY KEY,
brand VARCHAR(20),
model VARCHAR(20),
digital INTEGER,
firstName VARCHAR(20),
lastName VARCHAR(20),
FOREIGN KEY (firstName,lastName) REFERENCES Photographer);
--	ON DELETE SET NULL
--	ON UPDATE CASCADE);
--If a photographer is deleted, the camera will take a null (photographer unknown)


--Entity Picture, including constraint for the source
--reference to camera changed to restricted, if I want to delete a camera, i have to delete all its pictures before
--description was text
--is_public was bool
CREATE TABLE Picture( 
title VARCHAR(20) PRIMARY KEY,
description VARCHAR(200),
pdate DATE NOT NULL,
is_public INTEGER,
camera INTEGER,
source VARCHAR(20),
FOREIGN KEY (camera) REFERENCES Camera,
--	ON DELETE SET NULL
--	ON UPDATE CASCADE,
CHECK (source IN ('Digital camera', 'Film scanner', 'Flatbed scanner', 'Drum scanner')));
--If a camera is deleted, the pictures will take a null (camera unknown)

--Version (weak entity)

--reference to picture changed to restricted, if I want to delete a picture, i have to delete all its versions before
--description was text
CREATE TABLE Version (
storingDate DATE,
description VARCHAR(200),
idPicture VARCHAR(20),
data BLOB,
FOREIGN KEY (idPicture) REFERENCES Picture,
--	ON DELETE CASCADE
--	ON UPDATE CASCADE,
PRIMARY KEY (idPicture, storingDate));



