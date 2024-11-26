# Baue eine (verbesserte) Mutation zum Hinzufügen von Kommentaren
* Ergänze das Schema:
  * Die `createComment` Mutation soll jetzt `Deprecated` werden
  * Erstellt stattdessen eine neue `addComment` Mutation
  * Diese hat den gleichen Input-Typen wie `createComment`. Du kannst den Typen einfach kopieren und die Kopie `AddCommentInput` nennen.
  * Im Gegensatz zu `createComment` soll die neue `addComment`-Mutation ein verbessertes Fehlerhandling haben:
    * Der Rückgabe-Typ `AddCommentPayload` soll ein Union-Type sein, der entweder `AddCommentSuccess` oder `AddCommentError` ist
    * `AddCommentSuccess` soll das `newComment`-Feld haben mit dem neu angelegten Kommentar
    * `AddCommentFailed` soll ein Feld für eine Fehlermeldung (`msg`) haben
* Implementiere die Mutation-Handler-Methode dafür in der Klasse `MutationController`
  * Wenn beim Aufruf von `StoryRepository.createComment` eine Exception fliegt, liefer den Fehlertyp zurück
  * Du kannst den Fehlerfall testen, in dem Du beim Anlegen einer Story eine nicht vorhandene Story-Id übergibst (z.B. `1000`)
* Führe in GraphiQL die Mutation aus, und frage jeweils die Rückgabe-Werte ab
* **Optional**:
  1. Schreibe Testfälle für die Mutation (du kannst die Testfälle für `createComment` kopieren und anpassen) 
  2. Wenn bei der "alten" Mutation `createComment` ein Fehler auftritt, z.B. ungültige Story-Id, wird das `errors`-Feld in der Antwort des Queries gesetzt
    * Kannst du einen `GraphqlExceptionHandler` schreiben, der ein `GraphlError`-Objekt mit einer (einigermaßen) sprechenden Fehlermeldung zurückliefert?
      * Dein `GraphqlExceptionHandler` sollte im `MutationController` implementiert werden und `ResourceNotFoundException` verarbeiten
      * Bonus-Punkte wenn auch das `location`-Feld korrekt gesetzt wird :-)

## Doku
* https://graphql.org/learn/schema/#union-types
* https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.exception-handler
* `GraphQLError`: https://javadoc.io/static/com.graphql-java/graphql-java/22.0/graphql/GraphQLError.html
* `@GraphQlExceptionHandler`: https://docs.spring.io/spring-graphql/docs/current/api/org/springframework/graphql/data/method/annotation/GraphQlExceptionHandler.html
* `@ControllerAdivce`: https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-advice.html
* 



