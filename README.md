## Tabla de contenido:
1. [Información del proyecto](#informacion-del-proyecto)
2. [Acuerdos](#acuerdos)
3. [Parametrización](#parametrizacion)
4. [Tecnologías](#tecnologias)
5. [Cómo ejecutar](#como-ejecutar)
6. [Pruebas automáticas](#pruebas-automaticas)
### Información del proyecto
***
Las especificaciones y requerimientos iniciales del proyecto se encuentra en el PDF (Examen_Mercadolibre_Mutantes.pdf) publicado en la carpeta dist/ 

## Acuerdos
***
Según lo conversado por correo electrónico, se llegaron los siguientes acuerdos:
* Si en el vector con las secuencias, una cadena posee una letra que no es la (A,T,C,G) **se debe retornar FALSE**, por lo tanto, como se debe retornar **FALSE** el sistema lo va a asumir como un **humano**.
* Para que una letra pueda servir para 2 o más combinaciones se sugirió que **el sistema permita parametrizar si una letra puede estar en 2 o más combinaciones** 

## Parametrización
***
La parametrización consiste en agregar una nueva etiqueta en el JSON que se envía al API para validar si la secuencia es humano o un mutante.
la etiqueta debe estar con el nombre de ```sameWord``` y puede permitir los valores de **Y**, **N** o **nulo** donde:
* Y ó Nulo: Indica que el sistema puede utilizar la letra para 2 o más combinaciones.
* N: Indica que el sistema no debe permitir que una letra no esté en 2 combinaciones.

Ejemplo del JSON con el parámetro:

```
{
"dna":[ "ATTATG","CCGTGC","ATAGCG","AGGTGC","TAGTAT","CCCCGT"],
"sameWord":"N"
}
```

Ejemplo del JSON sin el parámetro:

```
{
"dna":[ "ATTATG","CCGTGC","ATAGCG","AGGTGC","TAGTAT","CCCCGT"]
}
```

Ejemplo de una letra esté en 2 o más combinaciones:
					
![image](https://user-images.githubusercontent.com/106041476/169731634-0a754159-68bc-4999-8edd-41d492a4f226.png)

En el caso de que el parámetro esté en N esas combinaciones solo contará como 1 combinación válida para tener en cuenta si la secuencia es humano o mutante.

**Consideración:**
Las letras que se invalidan para una próxima validación de combinaciones es las que cumplieron con la primera validación, es decir, si primero se validó que cumple las 4 letras horizontalmente, las letras que quedarán invalidadas para una próxima validación es las que se validaron horizontalmente

Ejemplo:

![image](https://user-images.githubusercontent.com/106041476/169738025-a5a6423d-fc32-424d-b299-ec59c671b309.png)

En este caso, en la primera iteración se validó horizontalmente, por lo tanto se invalidan las letras "A" que se encuentran marcadas con rojo mientras las otras "A" que están verticalmente y diagonalmente podrían aplicar para una próxima validación.

En este ejemplo el sistema retornaría TRUE puesto que en la siguiente iteración puede aplicar las combinaciones de las letras "A" faltantes verticalmente o las "A" restantes de forma diagonal. 

## Tecnologías
***
Una lista de tecnologías utilizadas en este proyecto:
* [Java](https://www.java.com/es/): Version 16 
* [Spring Boot](https://spring.io/projects/spring-boot): Version 2.7.0
* [Maven](https://maven.apache.org/download.cgi): Version 3.8.5
* [Junit](https://mvnrepository.com/artifact/junit/junit/4.11): Version 4.11
* [Jacoco](https://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin/0.8.5): Version 0.8.5
* [MockMvc](https://blog.marcnuri.com/mockmvc-introduccion-a-spring-mvc-testing)
* [Postgresql](https://www.postgresql.org/download/): Version 14

## Cómo ejecutar
***
### Modo local
***
Para poder ejecutar el proyecto en local se debe tener en cuenta lo siguiente:
* Crear una instancia de la base de datos Postgresql
* Crear una base de datos que se llame **mutant**
* Modificar las credenciales de ingreso a la instancia/base de datos en el archivo **Aplication.properties** del proyecto ubicado en la ruta: *Magneto\src\main\resources*
* Tener libre el puerto 9092 en el sistema
* Levantar o iniciar los procesos del proyecto para que pueda funcionar como servidor local
* Tener instalado o ejecutar desde el navegador el programa Postman

Dentro de Postman se puede ejecutar los siguientes servicios según su necesidad

### Servicio POST:

http://localhost:9092/magneto/mutant/

Ejemplo ADN mutante:
```
{"dna":["ATGCGA", "CAGGGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]}
```
Returns:
```
Status: 200
```

Ejemplo ADN humano
```
{"dna":["AATACT", "CCCAGA", "GGGATT", "AATTCC", "GGATCG", "TCACTG"]}
```
Returns
```
Status: 403 Forbidden
```
### Servicio GET

http://localhost:9092/magneto/stats

En este caso solo basta con ejecutar el link en Postman y tendrán la siguiente respuesta según la información almacenada en base de datos:
```
{
    "count_mutant_dna": 2,
    "count_human_dna": 1,
    "ratio": 0.6666667
}
```
### Modo API
***
Para poder ejecutar el servicio POST que es el encargado de validar y almacenar la secuencia, es necesario tener instalado o ejecutar desde el navegador el programa [Postman](https://www.postman.com/downloads/) o uno que cumpla la misma función.


Luego puede ejecutar los siguientes links en postman según la necesidad de la prueba:

### Servicio POST:

http://ec2-3-141-41-203.us-east-2.compute.amazonaws.com/magneto/mutant/

Ejemplo ADN mutante:
```
{"dna":["ATGCGA", "CAGGGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]}
```
Returns:
```
Status: 200
```

Ejemplo ADN humano
```
{"dna":["AATACT", "CCCAGA", "GGGATT", "AATTCC", "GGATCG", "TCACTG"]}
```
Returns
```
Status: 403 Forbidden
```

### Servicio GET

http://ec2-3-141-41-203.us-east-2.compute.amazonaws.com/magneto/stats

En este caso solo basta con ejecutar el link en Postman y tendrán la siguiente respuesta según la información almacenada en base de datos:
```
{
    "count_mutant_dna": 2,
    "count_human_dna": 1,
    "ratio": 0.6666667
}
```

## Pruebas automáticas
***
### Pruebas unitarias

Se utilizó la tecnología de JUnit y MockMvc para realizar las pruebas.

### Cobertura
Ejecutando los testers utilizando la tecnología de Jacoco el resultado fue una cobertura de 85%

![image](https://user-images.githubusercontent.com/106041476/169736460-0dd7467f-f413-4670-8030-e7658673818a.png)

