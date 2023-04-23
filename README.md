## Summary

Since I don't have much time to carry out the practical case, and given my PC configuration, the Java version is 11 and the configuration is done through Maven.

To shorten delivery time, some classes do not have unit test.

Also return by state use cases are also yet to be implemented. If necessary, I would fill them in without problem, but I summarize the idea of how they would be:

Being REST I would like that endpoints to be Restful /transactions/channel/{channel} style. In this way they could separate endpoints and use cases, which would facilitate security and reinforce single responsibility (SRP), as each could change due to different actors.
Because of this, even if the single endpoint approach was followed as stated in the requirement, it would separate the use cases even if some parts were reused through strategies, transformers, etc.

Project uses a REST service API First approach and is structured following DDD with the following modules:
* Domain: contains the entities, value objects and interfaces that make up the project.
* Application: in which the use cases reside.
* Repository: where is the persistence implementation. For simplicity, H2 in memory.
* ws-api: used to generate the sources defined in Openapi.
* Api: where the controllers that act as REST adapters reside.
* account-client: used as a client for the "fake account's microservice". This service, for convenience, uses the
  local database, but this implementation that could use any data source, SOAP, REST, etc.
* boot: Application entry point reserved for configurations.

I have structured it as DDD because I think it works very well in microservices and helps to respect SOLID principles.

## Assumptions

Given the requirements, this microservice is separated from the micro of accounts in the subdomain. The root `Transaction` aggregate entity contains its invariants and interacts with `Account` to meet the system requirements. This microservice is only responsible for the persistence of `Transactions` and communicates with the internal or external account service to collect data and report changes. For the simplicity of the exercise, this communication is carried out with the client of the account-client module that saves it in the database itself, but this implementation is in that separate module to avoid coupling and it can be of any type.

## Links of interest

* Swagger: http://localhost:8080/swagger-ui/index.html#/
* H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:example, Username: sa, Password empty)

## Commands

Run test and install:
`mvn clean install`

Launch application:
`mvn spring-boot:run`

## Testing data

File `src/main/resources/data.sql` contains the mock data of the accounts `tenIban` with an initial balance of 10 and `zeroIban` with 0 balance for testing purposes.