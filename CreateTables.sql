DROP TABLE IF EXISTS Items;

CREATE TABLE Items (
    ID int UNIQUE,
    Type varchar(255) NOT NULL,
    Nom  varchar(255) NOT NULL UNIQUE,
    Descriptif varchar(8000),
    Niveau int,
    Nombre varchar(255) DEFAULT 'Seul',
    Dispositif varchar(255) DEFAULT 'Rien',
    Pratiquants varchar(255) DEFAULT 'Non-spécifique',
    Filename varchar(255) NOT NULL
);

DROP TABLE IF EXISTS Tags;

CREATE TABLE Tags (
    ID int IDENTITY(1,1) PRIMARY KEY,
    Nom varchar(255) NOT NULL UNIQUE
);


DROP TABLE IF EXISTS ItemsTags;

CREATE TABLE ItemsTags (
    IDItems int NOT NULL, 
    IDTags int NOT NULL
);

DROP TABLE IF EXISTS ItemsItems;

CREATE TABLE ItemsItems (
    IDItems1 int NOT NULL, 
    IDItems2 int NOT NULL
);


DROP TABLE IF EXISTS ItemsID;

CREATE TABLE ItemsID (
    ID int IDENTITY(1,1) PRIMARY KEY NOT NULL, 
    Nom varchar(255) NOT NULL UNIQUE,
    Type varchar(255) NOT NULL
);


DROP TABLE IF EXISTS Enchainements;

CREATE TABLE Enchainements (
    ID int PRIMARY KEY NOT NULL, 
    Etapes varchar(8000) NOT NULL,
    Details varchar(8000)
);

DROP TABLE IF EXISTS ItemsTagsList;

CREATE TABLE ItemsTagsList(
    ID int NOT NULL, 
   Nom varchar(255) NOT NULL,
   Type  varchar(255) NOT NULL
);
