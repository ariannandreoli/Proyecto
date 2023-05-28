CREATE TABLE IF NOT EXISTS Pokemon  (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT, 
Nivel INTEGER  NOT  NULL, 
Habilidad TEXT , 
Genero TEXT, 
RutaP INTEGER,
FOREIGN KEY (RutaP) REFERENCES Ruta);

CREATE TABLE IF NOT EXISTS Tipo ( 
Id INTEGER UNIQUE PRIMARY KEY,
Nombre TEXT);

CREATE TABLE IF NOT EXISTS PokemonTipo (
IdPokemon INTEGER,
IdTipo INTEGER,
FOREIGN KEY (IdTipo) REFERENCES Tipo,
FOREIGN KEY (IdPokemon) REFERENCES Pokemon,
PRIMARY KEY (IdPokemon, IdTipo));

CREATE TABLE IF NOT EXISTS Entrenador (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY ,
Genero TEXT,
Nombre TEXT
);

CREATE TABLE IF NOT EXISTS Centro (
Id INTEGER UNIQUE NOT NULL PRIMARY KEY ,
Trabajadores TEXT,
Ciudad TEXT
);

CREATE TABLE IF NOT EXISTS "Entrenador-Centro" (
IdEntrenador INTEGER,
IdCentro INTEGER,
FOREIGN KEY (IdEntrenador) REFERENCES Entrenador,
FOREIGN KEY (IdCentro) REFERENCES Centro,
PRIMARY KEY (IdEntrenador, IdCentro));

CREATE TABLE IF NOT EXISTS "Entrenador-Pokemon" (
IdEntrenador INTEGER,
IdPokemon INTEGER,
Cantidad INTEGER,
FOREIGN KEY (IdEntrenador) REFERENCES Entrenador,
FOREIGN KEY (IdPokemon) REFERENCES Pokemon,
PRIMARY KEY (IdEntrenador, IdPokemon)
);

CREATE TABLE IF NOT EXISTS Ruta (
Id INTEGER NOT NULL PRIMARY KEY,
Nombre TEXT
);
