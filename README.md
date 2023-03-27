# Proyecto
Proyecto Tratamiento de la Información en Sistemas Distribuidos

---
## Integrantes 
Arianna Andreoli

Alvaro Lopez 

Francisco Lopez 


---
## Tema
Crearemos una aplicacion que guarde la informacion sobre pokemons, sus nombres, tipos, genero, y habilidades. La aplicacion deberia de permitir que los entrenadores busquen la informacion sobre los pokemons segun lo que les interese o encontrar un pokemon y actualizar la información en el pokedex y que los trabajadores de los pokemon center puedan facilitarle esta informacion.  

---
## Diagrama UML

  El diagrama UML consta de dos actores: entrenador pokemon y centro pokemon.

![UML](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/UML.jpg)

  El entrenador puede informarse de los pokemons, de forma que si este consigue uno nuevo puede actualizar el pokedex para agregar la informacion en este o incluso para capturar dicho pokemon. 
  El centro pokemon proporciona la informacion de la localizcion de los pokemons y da la posibilidad de evolucionar los pokemons de los entrenadores que asistan al centro. 


---
## Diagrama Entidad Relación

![ER](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/ER.jpg)

En el diagrama podemos encontrar 5 entidades con sus respectivos atributos y relaciones.
La primera entidad a comentar es "Entrenador" que tiene los atributos "Género" y "Nombre" en donde se no admitiran datos nulos, este entrenador debemos relacionarlo con el pokedex ya que esta va a poder actualizar y editar la informacion de este en caso de conseguir un pokemon y relacionarlo con los pokemon ya que el entrenador puede tener distintos pokemones. 
La segunda entidad es "Pokédex" que tiene los atributos "Id y "Descripcion", debemos relacionarlo con los pokemones ya que en el pokedex queremos cargar informacion sobre estos. 
La tercera entidad es "Pokémon" que tiene los atributos "Id", "Nombre", "Nivel", "Habilidad", "Género "y "RutaP", donde se admitiran datos nulos en el sexo, ya que ciertos pokemons no poseen uno, a traves de los pokemons podremos obtener el tipo de este.
La cuarta entidad es "Centro Pokémon" que tiene los atributos "Ciudad", "Trabajadores" e "Id", estos seran los capaces de conseguir la localizacion del pokemon y la forma de evolucionar el pokemon del entrenador que lo desee. 
La quinta entidad es "Tipo" que tiene los atributos "Id" y "Nombre" donde se podra ver los distintos tipos de pokemon que hay. 
La sexta y ultima entidad es "Ruta" que tiene los atributos "Id" y "Nombre" que son la rutas donde estaran los pokemon y donde los Centros podran localizarlos. 


---
## Tablas de interfaz

Extraemos del diagrama entidad relacion las distintas entidades:

![T_ER](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/TABLAS_ER.jpg)


Podemos encontrar tablas de relacion entre el entranador y los pokemones ya que distintos entrenadores pueden tener distintos pokemones, cada pokemon contiene uno o mas tipos por lo que relacionamos las entidades muchos a muchos. Muchos entrenadores pueden visitar muchos Centros Pokemons por lo que debemos realizar otra tabla de relacion entre ellas. 


---
## Diagrama Interfaz

![DiagramaInterfaz](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/DiagramaInterfaz.jpg)

Utilizamos el modelo de entidad relación y lo pasamos a tablas de forma que cada entidad tiene su tabla propia y cada relación cumple con los requisitos según su tipo:
-Las n a m: Debe de volverlas una tabla separada donde relacionemos ambas entidades, entre ellas estas: “Pokedex_Pokemon” , “Entrenador_Pokemon”, “Pokemon-Tipo” y “Centro_Pokemon”. 
-Las 1 a 1: Debemos de llamar la clave primaria de alguna de las tablas en la otra tabla, esto lo hicimos en la relación entre Entrenador y Pokédex donde en la tabla del atributo Pokédex agregamos el Id del entrenador. 
-Las 1 a n: debemos de agregar la clave primaria como clave ajena en la tabla de muchos, esto lo realizamos en la tabla de Pokemon donde agregamos un atributo llamado “RutaP” que es una clave primaria al atributo Ruta. 




