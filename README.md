[![Build Status](https://travis-ci.com/thealmarques/spring-boot-smartpoint-api.svg?token=7aSoHWUPqHZg4AGkquDJ&branch=master)](https://travis-ci.com/thealmarques/spring-boot-smartpoint-api)
# Smart Entry Point
RESTful API written in spring boot for a specific use case project.

# Use case

The goal is to create an API where businesses can control their employee's timesheet during work (including lunch hours, work time, etc.)

# Details
* Java 8 with Spring Boot 2
* MySql database with Spring JPA
* Flyway migrations
* Spring security with JWT
* Unit tests with JUnit and Mockito
* Caching with Ehcache
* Continuous integration with TravisCI
* Docker compose for local environment testing

# How to run

```sh
git clone https://github.com/thealmarques/spring-boot-smartpoint-api.git
cd spring-boot-smartpoint-api
docker-compose up --build -d
mvn spring-boot:run
Access endpoints through http://localhost:8080
Documentation: http://localhost:8080/swagger-ui.html
```
