# Proyecto
Proyecto Tratamiento de la Información en Sistemas Distribuidos

---
## Integrantes 
Arianna Andreoli

Alvaro Lopez 

Francisco Lopez 


---
## Tema
Crearemos una aplicacion que guarde la informacion sobre pokemons, sus nombres, tipos, genero, y habilidades. La aplicacion permitirá que los entrenadores consulten la informacion de algún pokemon segun lo que les interese, ademas de atrapar a pokemones y actulizar su cantidad. Los trabajadores de los pokemon center puedan facilitarle al entrenador realizar tareas que sin su ayuda no pueden realizar y les indica donde puedes capturar a un pokemon.  

---
## Diagrama UML 

  El diagrama UML consta de dos actores: entrenador pokemon y centro pokemon.

![UML](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/UML.jpg)

Estos actores desempeñan diferentes papeles:
El entrenador: al tratarse del mundo de los pokemones necesitamos un entrenador capaz de poder capturarlos y actualizar la información de ellos para futuros entrenadores. 
El centro Pokemon: necesitamos a alguien capaz de ayudar al entrenador a realizar tareas y localizar los pokemones.
A continuación, notaremos los casos de uso de nuestro sistema, estos son las acciones que realizan nuestros actores y vienen representados como óvalos:
El entrenador puede consultar información sobre un pokemon cualquiera, ya sea de él o no, si ya consigue alguno nuevo puede capturarlo e incluso agregar esta información a las características del pokemon. 
El centro Pokemon sera capaz de ayudar al entrenador a localizar los pokemons y ayudar al entrenador a realizar acciones como aumentar el nivel de un pokemon. 
Por último, nuestro sistema Mundo Pokemon que lo utilizamos para definir el alcance de los casos de uso y aparece representado como un rectángulo.

![UML](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/DiagraClase.jpg)


---
## Diagrama Entidad Relación

![ER](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/ER.jpg)

En el diagrama podemos encontrar 5 entidades con sus respectivos atributos y relaciones.
La primera entidad a comentar es "Entrenador" que tiene los atributos "Género" y "Nombre" en donde se no admitiran datos nulos, este entrenador debemos relacionarlo con los "Pokémon" ya que el este puede tener distintos pokemones, además debemos de relacionarlo con "Centro Pokemon" de forma que varios entrenadores puedan ir a distintos centros. 
La segunda entidad es "Pokémon" que tiene los atributos "Id", "Nombre", "Nivel", "Habilidad", "Género "y "RutaP", donde se admitiran datos nulos en el sexo, ya que ciertos pokemones no poseen uno, a traves de los pokemons podremos obtener el tipo de este por ello lo relacionamos con la tabla "Tipo".
La tercera entidad es "Centro Pokémon" que tiene los atributos "Ciudad", "Trabajadores" e "Id", estos seran los capaces de conseguir la localizacion del pokemon y ayudar al entrenador a aumentar de nivel alguno de los pokemones que ya posee. 
La cuarta entidad es "Tipo" que tiene los atributos "Id" y "Nombre" donde se podra ver los distintos tipos de pokemon que hay. 
La quinta y ultima entidad es "Ruta" que tiene los atributos "Id" y "Nombre" que son la rutas donde estaran los pokemon y donde los Centros podran localizarlos. 


---
## Tablas de interfaz

Extraemos del diagrama entidad relacion las distintas entidades:

![T_ER](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/T_ER.jpg)


Utilizamos el modelo de entidad relación y lo pasamos a tablas de forma que cada entidad tendra su tabla propia y cada relación cumple con los requisitos según su tipo:
-Las n a m: Debe de volverlas una tabla separada donde relacionemos ambas entidades, entre ellas estas: “Entrenador_Pokemon”, “PokemonTipo” y “Entrenador-Centro”. 
-Las 1 a n: debemos de agregar la clave primaria como clave ajena en la tabla de muchos, esto lo realizamos en la tabla de Pokemon donde agregamos un atributo llamado “RutaP” que es una clave primaria al atributo Ruta. 

---
## Diagrama Interfaz

Aquí podemos encontrar los menus que mostrará nuestra aplicación: 

![DiagramaInterfaz](https://github.com/ariannandreoli/Proyecto/blob/main/fotos/DiagramaInterfaz.jpg)






