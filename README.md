| master          | develop           |
| :-------------: |:-------------:|
| [![Build Status](https://travis-ci.org/palmithor/springboot-veggies.svg?branch=master)](https://travis-ci.org/palmithor/springboot-veggies)        | [![Build Status](https://travis-ci.org/palmithor/springboot-veggies.svg?branch=develop)](https://travis-ci.org/palmithor/springboot-veggies) |

# springboot-veggies
Small project for testing out Spring Boot JPA layer. It is a webservice which exposes few RESTful resources for getting information inventory status on vegetables.

Its dependencies and build functions are configured using Maven.

## Testing
The tests can be run via maven: 
```bash 
mvn test
```

## Building
Maven package creates an executable jar file
```bash 
mvn package
```

# Running
The service is built with Spring Boot and can be run with the Spring Boot Maven plugin:

```bash 
mvn spring-boot:run 
```

Notice that the database is pre-initialized with three items. Currently only in-memory database is used and no cache has been configured.

## An example requests:
 ```
 GET http://localhost:8080/api/v1/vegetables
 GET http://localhost:8080/api/v1/vegetables/1
 ```

