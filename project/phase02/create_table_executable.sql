--Entity photographer
CREATE TABLE Photographer ( 
firstName CHAR(20),
lastName CHAR(20),
mail CHAR(20),
description TEXT,
homepage CHAR(30),
PRIMARY KEY (firstName,lastName));

--Entity Camera
CREATE TABLE Camera ( 
id INTEGER PRIMARY KEY,
brand CHAR(20),
model CHAR(20),
digital BOOL,
firstName CHAR(20),
lastName CHAR(20),
FOREIGN KEY (firstName,lastName) REFERENCES Photographer
	ON DELETE SET NULL
	ON UPDATE CASCADE);
--If a photographer is deleted, the camera will take a null (photographer unknown)

--Entity film
CREATE TABLE Film ( 
id INTEGER PRIMARY KEY,
producer CHAR(20),
type CHAR(20),
sensitivity CHAR(20));

--Entity serie
CREATE TABLE Serie ( 
name CHAR(20) PRIMARY KEY,
description TEXT);


--Entity Picture, including constraint for the source
CREATE TABLE Picture( 
title CHAR(20) PRIMARY KEY,
description TEXT,
pdate DATE NOT NULL,
is_public BOOL,
camera INTEGER,
source CHAR(20),
FOREIGN KEY (camera) REFERENCES Camera
	ON DELETE SET NULL
	ON UPDATE CASCADE,
CHECK (source IN ('Digital camera', 'Film scanner', 'Flatbed scanner', 'Drum scanner')));
--If a camera is deleted, the pictures will take a null (camera unknown)

--Version (weak entity)
CREATE TABLE Version (
storingDate DATE,
description TEXT,
idPicture CHAR(20),
FOREIGN KEY (idPicture) REFERENCES Picture
	ON DELETE CASCADE
	ON UPDATE CASCADE,
PRIMARY KEY (idPicture, storingDate));

--Relationship between film and picture
CREATE TABLE PictureInFilm (
idPicture CHAR(20) PRIMARY KEY,
idFilm INTEGER,
pos INTEGER,
FOREIGN KEY (idPicture) REFERENCES Picture
	ON DELETE CASCADE
	ON UPDATE CASCADE,
FOREIGN KEY (idFilm) REFERENCES Film
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Relationship between serie and picture
CREATE TABLE PictureInSerie (
idSerie CHAR(20),
idPicture CHAR(20),
pos INTEGER,
FOREIGN KEY (idPicture) REFERENCES Picture
	ON DELETE CASCADE
	ON UPDATE CASCADE,
FOREIGN KEY (idSerie) REFERENCES Serie
	ON DELETE CASCADE
	ON UPDATE CASCADE,
PRIMARY KEY (idPicture, idSerie));


--Entity categories
CREATE TABLE Category ( 
title CHAR(20) PRIMARY KEY,
description TEXT NOT NULL,
is_child_of CHAR (20),
FOREIGN KEY (is_child_of) REFERENCES Category);

--Motive
CREATE TABLE Motive ( 
id INTEGER PRIMARY KEY,
description TEXT NOT NULL);

--Person
CREATE TABLE Person ( 
firstName CHAR(20),
lastName CHAR(20),
id_motive INTEGER PRIMARY KEY,
FOREIGN KEY (id_motive) REFERENCES Motive
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Happening
CREATE TABLE Happening ( 
name CHAR(20),
id_motive INTEGER PRIMARY KEY,
FOREIGN KEY (id_motive) REFERENCES Motive
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Object
CREATE TABLE Object ( 
name CHAR(20),
id_motive INTEGER PRIMARY KEY,
FOREIGN KEY (id_motive) REFERENCES Motive
	ON DELETE CASCADE
	ON UPDATE CASCADE);


--Place
CREATE TABLE Place ( 
name CHAR(20),
id_motive INTEGER PRIMARY KEY,
area CHAR(20),
country CHAR(20),
FOREIGN KEY (id_motive) REFERENCES Motive
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Norwegian place
CREATE TABLE Norway ( 
id_motive INTEGER PRIMARY KEY,
FOREIGN KEY (id_motive) REFERENCES Place
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Non Norwegian place
CREATE TABLE ForeignCountry ( 
id_motive INTEGER PRIMARY KEY,
FOREIGN KEY (id_motive) REFERENCES Place
	ON DELETE CASCADE
	ON UPDATE CASCADE);

--Province
CREATE TABLE Province ( 
name CHAR(20) PRIMARY KEY);

--Municipi
CREATE TABLE Municipi ( 
name CHAR(20) PRIMARY KEY,
id_motive INTEGER,
province CHAR(20),
FOREIGN KEY (id_motive) REFERENCES Norway
	ON DELETE SET NULL
	ON UPDATE CASCADE,
FOREIGN KEY (province) REFERENCES Province
	ON DELETE SET NULL
	ON UPDATE CASCADE);
--On delete null on Norway, because if we delete a motive, we do not have to delete the municipi
--On delete null on Province, taking into account that null will be "unknown"



