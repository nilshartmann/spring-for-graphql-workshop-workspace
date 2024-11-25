# Übung: DataFetcher mit graphql-java

* Implementiere alle notwendigen DataFetcher für unser Schema
* Als Ausgangsbasis findest du im Package `nh.springgraphql.graphqlservice.gqljava` dafür vorbereitete Klassen
* In der Regel brauchst Du in deiner Anwendung mindestens die DataFetcher für die Felder an den Root-Typen `Query`, `Mutation`, `Subscription`
* So auch hier: wir brauchen zwei DataFetcher für `Query.stories` und `Query.story`
  * Im DataFetcher für `Query.story` musst du über das `DataFetchingEnvironment` das Argument `storyId` ermitteln
* Du kannst in den Data-Fetchern das `StoryRepository` instantiieren und verwenden, um Stories zu bekommen
* Außerdem brauchen wir einen DataFetcher für `Story.excerpt`. Warum eigentlich?
  * Im DataFetcher musst du dafür das `Source`-Objekt ermitteln, das eine `Story`-Instanz sein muss
* Wenn du die drei DataFetcher implementiert hast, musst du sie in der Klasse `nh.springgraphql.graphqlservice.gqljava.GraphQLProvider` noch im `RuntimeWiring` bekannt machen.
  * In der Klasse findest du weitere Informationen
* Wenn du die DataFetcher korrekt implementiert hast, sollte der Test `nh.springgraphql.graphqlservice.gqljava.QueryTest` fehlerfrei durchlaufen


## Doku

* https://www.graphql-java.com/documentation/data-fetching
* https://www.graphql-java.com/documentation/schema#creating-a-schema-using-the-sdl
* https://javadoc.io/doc/com.graphql-java/graphql-java/22.0/graphql/schema/DataFetchingEnvironment.html