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

* Docker , Maven.

### Executing program

* Primero: ingresar a la carpeta de challenge y levantar el ambiente de postgress con el siguiente comando:
```
docker compose -f postgres.yml up
```
* Segundo: generar los .jar con el comando :
```
cd .. 
mvn -f clean package challenge/
mvn -f clean package calculator/
```
* Tercero: una vez generado los jars ya se puede bajar el container de postgres :
```
docker compose -f postgres.yml down
```
* Cuarto: levantar el contenedor en el directorio root el cual contiene las 2 aplicaciones y la db :
```
docker compose -f docker-compose.yml up --build
```
