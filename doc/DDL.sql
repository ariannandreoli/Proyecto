CREATE TABLE Pokemon  (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT, 
Nivel INTEGER  NOT  NULL, 
Habilidad TEXT , 
Genero TEXT, 
RutaP INTEGER);

CREATE TABLE Tipo ( 
Id INTEGER UNIQUE PRIMARY KEY,
Nombre TEXT);

CREATE TABLE "Pokemon _Tipo" (
IdPokemon INTEGER,
IdTipo INTEGER,
FOREIGN KEY (IdTipo) REFERENCES Tipo,
FOREIGN KEY (IdPokemon) REFERENCES Pokemon,
PRIMARY KEY (IdPokemon, IdTipo));

CREATE TABLE Entrenador (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY ,
Genero TEXT,
Nombre TEXT
);

CREATE TABLE Centro (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY ,
Trabajadores TEXT,
Ciudad TEXT
);

CREATE TABLE "Entrenador_Centro" (
IdEntrenador INTEGER,
IdCentro INTEGER,
FOREIGN KEY (IdEntrenador) REFERENCES Entrenador,
FOREIGN KEY (IdCentro) REFERENCES Centro,
PRIMARY KEY (IdEntrenador, IdCentro));

CREATE TABLE Pokedex (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY, 
IdEntrenador INTEGER REFERENCES Entrenador,
Descripcion TEXT
);

CREATE TABLE "Pokedex_Pokemon" (
IdPokemon INTEGER,
IdPokedex INTEGER,
FOREIGN KEY (IdPokemon) REFERENCES Pokemon,
FOREIGN KEY (IdPokedex) REFERENCES Pokedex,
PRIMARY KEY (IdPokemon, IdPokedex)
);

CREATE TABLE Ruta (
Id INTEGER NOT NULL PRIMARY KEY,
Nombre TEXT
);