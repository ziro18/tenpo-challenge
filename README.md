# Tenpo challenge

Desafio Tenpo.cl

## Descripcion

Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:

1.Realizar :
  - a. Sign up usuarios. 
  - b. Login usuarios.
  - c. Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe
  ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10,
  entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:
    - El servicio externo puede ser un mock, tiene que devolver el % sumado.
    - Dado que ese % varía poco, debe ser consumido cada media hora.
    - Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.
    - Si el servicio externo falla, se puede reintentar hasta 3 veces.
  - d. Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado.
  - e. El historial y la información de los usuarios se debe almacenar en una database PostgreSQL.
  - f. Incluir errores http. Mensajes y descripciones para la serie 4XX.
2. Se deben incluir tests unitarios.
3. Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose
4. Debes agregar un Postman Collection o Swagger para que probemos tu API
5. Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.

## Getting Started

### Dependencies

* Docker , Maven, Java 11.

### Executing program

* Para ejecutar el programa use el siguiente comando:

```
docker-compose -f docker-compose.yml up
```
### Building program (Optional)
### En caso de querer construir las imagenes de docker

* Primero: levantar la db y crear los jars:
```
docker-compose -f utils/postgres.yml up
cd .. 
mvn -f clean package challenge/
mvn -f clean package calculator/
```
* Segundo: una vez generado los jars ya se puede bajar el container de postgres :
```
docker compose -f postgres.yml down
```
* Tercero: levantar el contenedor en el directorio root el cual contiene las 2 aplicaciones y la db:
```
docker compose -f docker-compose-nuild.yml up --build
```

## PostMan

* En la carpeta utils se encontrara el archivo "Tenpo.postman_collection.json" el cual contiene :
  1- Auth:
    - SignUp : Registro de usuarios al sistema.
    - Login : Login por medio de username y password el cual de ser correctos devolvera un token.
  2- Calculator:
    - Calculator: La api solicitada que suma 2 numeros y le aplica un porcentaje que consume de la api "calculator".
  3- Request History:
    - Request histories: Historial de llamadas a los endpoint en formato de pagina.
    - 
 * Aclaracion en el los puntos 2 y 3 se debe autenticar con el bearer token que devuelve el login, en la seccion de test de post esta automatizado el guardado 
 * del token en una variable de en ambiente llamada "token".

## links de interes

# Docker hub

* Challenge App
```
https://hub.docker.com/repository/docker/ziro18/challenge
```

* Calculator App
```
https://hub.docker.com/repository/docker/ziro18/calculator
```
