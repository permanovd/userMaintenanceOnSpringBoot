# User maintainance application

### Start instructions

1) Configuration

Navigate to project root directory. Copy or rename `/src/main/resources/application-example.properties` to 
`/src/main/resources/application-prod.properties`
(`cp /src/main/resources/application-example.properties /src/main/resources/application-prod.properties`)
Open `/src/main/resources/application-prod.properties` file and enter your database configuration.

```
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/users_maintainer
spring.datasource.username=postgres
spring.datasource.password=example
```

2) Build

Navigate to project root directory and run:

Windows: `mvnw.cmd clean install -DskipTests`
Unix: `./mvnw clean install -DskipTests`

3) Run

Navigate to project root directory and run:

`java -jar ./target/tests-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`

**Application will be started on http://localhost:8081**