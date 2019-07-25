Bank Transfer API
===

This project is the result of a test asked by [Revolut](https://www.revolut.com).

The project was completely developed using TDD.

### Tech Stack

- Java 11.0.4
- Gradle 4.10.3
- Hibernate 5 for persistence
- Jetty as servlet container
- H2 for data storage in memory
- RestEasy for rest services
- Guice for dependency injection
- Jackson for serialization/deserialization
- And for test purposes: Junit, Mockito and FixtureFactory

### Running instructions

To run the application, navigate to the project path, and then run the commands bellow.

For unix-based systems:

```
./gradlew clean test shadowJar

java -jar build/libs/bank-transfer-api-1.0-SNAPSHOT-all.jar
```

For windows systems:

```
gradlew.bat clean test shadowJar

java -jar build\libs\bank-transfer-api-1.0-SNAPSHOT-all.jar
```

### Results

The SQL executed commands were intentionally left showing, so the user can see the program's actions being executed.
Also was left some "debug code" printing how much money each account involved in the transfer has after the operation (same purpose as above).
There are unit tests for each business rule.
 
Everything in the project can be verified by it's tests.
Besides that, a postman collection was made available with the main possible requests (failing and succeeding) at the postman folder, on the root the project.

To facilitate testing, it was created a SQL import script with 2 insert commands. This script is run when the application goes up.
It can be changed if the user needs some more accounts or if he wants to change the accounts or values. The current file is as follows:

```
INSERT INTO ACCOUNT VALUES (123, 10000, CURRENT_TIMESTAMP);
INSERT INTO ACCOUNT VALUES (321, 50, CURRENT_TIMESTAMP);
```
### Tests

As stated before, the project was developed following TDD's rules, and because of that, a total of 27 tests were made; each single one born for a specific purpose.

At the end only the config part of the project was not tested. All the rest is 100% unit tested.