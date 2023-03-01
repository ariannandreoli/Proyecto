# Proyecto
Proyecto Tratamiento de la Información en Sistemas Distribuidos

---
## Integrantes 
Arianna Andreoli

Alvaro Lopez 

Francisco Lopez 


---
## Tema
Crearemos una aplicacion que guarde la informacion sobre pokemons, sus nombres, tipos, genero, y habilidades. La aplicacion deberia de permitir que los entrenadores busquen la informacion sobre los pokemons segun lo que les interese y que los tarbajadores de los pokemon center puedan facilitarle esta informacion o incluso ayudar a curarlo si esta herido.  

---
## Diagrama UML

  El diagrama UML consta de dos actores: entrenador pokemon y centro pokemon.

![UML](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/UML.jpg)

  El entrenador puede informarse de los pokemons que quiere capturar a traves del pokedex. 
  El centro pokemon proporciona la informacion de la localizcion de los pokemons y da la posibilidad de intercambiar, curar y evolucionar los pokemons de los entrenadores que asistan al centro. 


---
## Diagrama Entidad Relación

![ER](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/E_R.jpg)

En el diagrama podemos encontrar 5 entidades con sus respectivos atributos y relaciones.
La primera entidad a comentar es "Entrenador" que tiene los atributos "Género" y "Nombre" en donde se no admitiran datos nulos. La segunda entidad es "Pokédex" que tiene los atributos "Id", "Nombre", "Foto" y "Descripción". La tercera entidad es "Pokémon" que tiene los atributos "Id", "Tipo", "Vida", "Habilidad", "Género "y "Ruta", donde se admitiran datos nulos en el sexo, ya que ciertos pokemons no poseen uno. La cuarta entidad es "Centro Pokémon" que tiene los atributos "Ciudad" y "Trabajadores". La quinta entidad es "Gimnasio" que tiene los atributos "Líder", "Medalla" y "Ciudad". 


---
## Tablas de interfaz

Extraemos del diagrama entidad relacion las distintas entidades:





