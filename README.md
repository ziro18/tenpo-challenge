# Tenpo challenge

Simple overview of use/purpose.

## Description

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

* Describe any prerequisites, libraries, OS version, etc., needed before installing program.
* ex. Windows 10

### Installing

* How/where to download your program
* Any modifications needed to be made to files/folders

### Executing program

* How to run the program
* Step-by-step bullets
```
code blocks for commands
```

## Help

Any advise for common problems or issues.
```
command to run if program contains helper info
```

## Authors

Contributors names and contact info

ex. Dominique Pizzie  
ex. [@DomPizzie](https://twitter.com/dompizzie)

## Version History

* 0.2
    * Various bug fixes and optimizations
    * See [commit change]() or See [release history]()
* 0.1
    * Initial Release

## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details

## Acknowledgments
