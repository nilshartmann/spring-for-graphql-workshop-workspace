# Schritt 1: Schema erzeugen

* Erzeuge in der Datei `src/main/resources/graphql/schema.graphqls` die Basis für unser Schema:
* Es soll den Typen `Story` geben, der folgende Felder hat (alle nicht nullable):
  * `id`, `title`, `body`, `excerpt`, `state` und `comments`
  * `title`, `body` und `excerpt` enthalten Texte
  * `id` ist eine Id.
  * `state` soll ein enum-Type `PulicationState` mit drei Ausprägungen sein: `draft`, `in_review`, `published`
  * `comments` ist eine Liste von `Comment`-Objekten
* Der `Comment`-Typ hat ein `id`-, ein `text`- und ein `rating`-Feld
  * Rating ist eine Zahl
* Es soll zwei `Query`-Felder geben:
  * `story` mit einem Argument `storyId`. Dieses Feld soll ein `Story`-Objekt zurückliefern
  * Füge etwas Dokumentation zum `story`-Feld hinzu (du kannst dafür Markdown-Syntax verwenden)
  * `stories` liefert eine Liste von `Story` Objekten zurück
* Du kannst die Test-Klasse `nh.springgraphql.graphqlservice.gqljava.SchemaTest` ausführen, um zu überprüfen, ob dein Schema korrekt ist
* Bis auf `Query.stories` sind alle Felder nicht-nullable!
  * Ist das eigentlich eine gute Idee? 
  * Was könnte dagegen sprechen (selbst wenn die Felder alle fachlich *immer* einen Wert haben?)


## Doku

* https://graphql.org/learn/schema/
* https://www.graphql-java.com/documentation/schema#creating-a-schema-using-the-sdl