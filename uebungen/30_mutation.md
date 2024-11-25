# Baue eine Mutation zum Anlegen von Kommentaren
* Ergänze das Schema:
  * Wir brauchen einen `CreateCommentInput`-Input-Typen, der die benötigten Informationen enthält, mit denen ein Kommentar angelegt werden kann:
    * `storyId`, `text`, `rating`
  * Wir brauchen eine `createComment`-Mutation, die den Input-Typen verwendet und den neuen Kommentar zurückliefert.
* Schreibe eine Mutation-Handler-Methode dafür
  * **Lege dafür eine neue Controller-Klasse `MutationController` an**
  * Auch hier lässt du dir per Dependency Injection das `StoryRepository` von Spring übergeben
  * Du kannst am `StoryRepository` die Methode `createComment` verwenden, um den Kommentar in unserer "Datenbank" hinzuzufügen
* Du kannst deine Mutation testen:
  * Ausführen der Test-Klasse `nh.springgraphql.graphqlservice.graphql.MutationControllerTest`
  * Ausführen der Mutation in GraphiQL
    * Kannst du die Mutation in GraphiQL ausführen und das Input-Objekt als **Variable** übergeben?

## Doku
* https://graphql.org/learn/queries/#mutations
* https://graphql.org/learn/schema/#input-object-types
* https://graphql.org/learn/queries/#variables


