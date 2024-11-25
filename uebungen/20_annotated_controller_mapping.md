# Übung: QueryMapping-Funktionen mit Spring for GraphQL

* **Migriere unsere DataFetcher nach Spring...**
  * ...genauer gesagt: deren Logik
  * In Spring verwende bitte einen Annotated Controller mit Handler-Methoden.
* **Lege eine `@Controller`-Klasse `QueryController` an**
  * Dafür neues Package verwenden: `nh.springgraphql.graphqlservice.graphql`
  * Wir arbeiten ab sofort immer im Package `.graphql`
* Du kannst dir das `StoryRepository` dort mittels Spring Dependency Injection geben lassen
* Darin brauchst du dann zwei `QueryMapping`-Methoden und eine `SchemaMapping`-Methode
* Neue Anforderung:
  * Erweitere das Schema, so dass das `Story.excerpt`-Feld ein Int-Argument `maxLength` bekommt.
  * Dieses Argument ist optional, soll einen Default-Wert von `20` haben, wenn nicht gesetzt
  * Verwende die `maxLength`-Angabe aus einem Query, um nur die ersten `maxLength`-Zeichen des `body` zurückzugeben
    * (anstatt der bisher hardcodierten 3 Zeichen)
* Du kannst außerdem unserem `graphql-service` starten und in GraphiQL Queries ausführen:
  * Starten der Klasse `nh.springgraphql.graphqlservice.GraphqlServiceApplication`
  * GraphiQL läuft auf `http://localhost:8080/`
    * Ggf. den Port in der `application.properties`-Datei anpassen bzw. nachgucken (Property `server.port`)


## Doku

* https://docs.spring.io/spring-graphql/reference/controllers.html
* https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.schema-mapping.argument
* https://docs.spring.io/spring-graphql/reference/graphiql.html