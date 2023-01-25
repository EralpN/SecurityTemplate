# SecurityTemplate

## SecurityTemplate is a Spring Boot project that can be used as a template for both monolithic applications and authenticating microservices.

### It is built on Spring version 3.0.2 and Java 17, and utilizes [Spring Security](https://github.com/spring-projects/spring-security/) to provide secure authentication and authorization features.

* This template includes generic base entities and generic base repositories, making it easy to add additional entities to the project.
* It includes a global exception handler that catches and logs exceptions that the program might run into.
* Requests and responses are received and sent in DTO's (data transfer object) entities. As it should be.
* It utilizes REST API.
* It utilizes JWT token as a Bearer token to authorize incoming requests.

### Dependencies
This project uses the following dependencies:

* [Spring Boot Starter Data JPA](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-data-jpa/build.gradle)
* [Spring Boot Starter Security](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-security/build.gradle)
* [Spring Boot Starter Web](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-web/build.gradle)
* [Spring Boot Starter Validation](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-validation/build.gradle)
* [Lombok](https://projectlombok.org/)
* [JJWT (Java JWT: JSON Web Token for Java and Android)](https://github.com/jwtk/jjwt)
* [PostgresSQL](https://www.postgresql.org/) (I have used postgresql but you can use any database you want.)

### How to use
To use this template, simply clone the repository and customize it to fit your needs.
Be sure to update the application properties with your own database configurations and settings.

### Additional Notes
I will try keeping this project up to date with incoming updates. I will also be adding a "forgot password" implementation using email services.

### Additional Resources
You may find the following resources helpful:

* [Spring Boot Docs](https://spring.io/projects/spring-boot)
* [Spring Security Docs](https://spring.io/projects/spring-security)
