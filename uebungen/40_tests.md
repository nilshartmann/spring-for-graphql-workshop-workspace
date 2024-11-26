# Unsere Anwendung muss getestet werden 🫣

* **Schreibe Tests, um die Funktionalität der API sicherzustellen**

* Mache dich mit den Test-Möglichkeiten von Spring GraphQl vertraut
* Du kannst zum Beispiel testen:
  * lesen einer einzelnen Story
  * lesen einer Story mit der Id (was passiert, wenn die abgefragte Id nicht in der DB vorhanden ist?)
  * Anlegen eines Kommentars
    * Kannst du den `AddCommentInput` als Variable im Test verwenden?

## Arten von Tests

* Du kannst entweder einen "Slice" testen oder deine ganze Anwendung
* Bei einem **Slice-Test** startet der Testrunner nur ausgewählte Teile deiner Anwendung
  * Welche das sind, hängt von der jeweiligen Annotation ab, z.B. alle Repositories oder alle Controller
  * Wenn es Beans gibt, die dabei nicht mit gestartet werden, musst du diese Mocken oder explizit auflisten, dass sie doch mitgestartet werden
  * Um einen "Graphql Slice"-Test zu schreiben, musst du deine Testklasse mit `@GraphQltTest` annotieren. Dann werden alle Graphql-relevanten "Infrastruktur"-Beans gestartet und alle `@Controller`-Beans
    * Die Auswahl der `@Controller`-Beans kannst du einschränken, in dem du die Namen der Klassen an `@GraphqlTest` übergibst
    * Das kann z.B. sinnvoll sein, damit du nicht zu viele Beans (die von deinen Controllen gebraucht werden) mocken oder hinzufügen musst. Außerdem geht das starten dann schneller.
    * ```java
      // Nur den QueryController + alle Spring-GraphQL-Beans starten
      @GraphQlTest(controllers = QueryController.class)
      // Zusätzlich das StoryRepository starten
      @Import(StoryRepository.class)
      class QueryControllerTest {
        // ...
      }      
      ```
* Wenn du die Anwendung komplett starten willst, kannst du die Annotation `@SpringBootTest` verwenden
  * Hier wird der Application Context komplett erzeugt und gestartet
  * Allerdings wird per Default kein Webserver gestartet und die Kommunikation zwischen Test und Anwendung erfolgt über eine von Spring gemockte Schicht. Wenn du die Anwendung vollständig auch mit Webserver starten willst, musst du `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` verwenden
  * Um den `GraphqlTester` in deinem Test zu erhalten, musst du in jedem Fall deinen Testklasse auch mit `@AutoConfigureGraphQlTester` annotieren

## Der GraphqlTester

* Die Klasse `GraphQlTester` ermöglicht es dir, im Test GraphQL Queries gegen deine API auszuführen und das Ergebnis zu verifizieren.
  * Es gibt unterschiedliche Implementierungen dieses Interfaces. 
  * **Unabhängig von der gewählten Test-Art funktioniert das Testen mit dem `GraphQlTester` immer identisch**
  * Du kannst einen funktionierenden Test von dir ja mal auf unterschiedliche Arten ausführen lassen (als Slice und als SpringBootTest)
* Mit dem `GraphqlTester` konfigurierst du deinen Request, in dem du das Dokument mit dem Query angibst und evtl. weitere Parameter setzt, z.B. Variablen.
* Dann führst du den Request mit `execute()` aus. 
  * Wenn der Query erfolgreich ist, liefert dir die Methode ein `Response`-Objekt zurück, das du zum überprüfen des Ergebnisses verwenden kannst.
  * In erster Linie verwendet man `path(String)`, um aus dem Ergebnis mit einen JSON-Path-Ausdruck auszuwählen, welche Felder man überprüfen möchte. Die Pfade beginnen mit dem `data`-Knoten der Antwort (exklusiv).
  * Beispiel-Antwort:
  * ```json
    { "data": { "story": { "id": "1" } } }
    ```
  * Mit `path("story.id")` kannst du die Id der Story auswählen und überprüfen
  * Um einen Wert zu überprüfen, musst du ihn mit `entity` in einen Java-Typ umwandeln und dann vergleichen, z.B.:
    * `path("story.id").entity(String.class).isEqualTo("1");`
  * Wenn du erwartest, dass der Query Fehler hat (`errors`-Knoten), dann kannst du diese mit `errors` überprüfen

## GraphQL-Queries im Test
* Du kannst die Tests an den `GraphqlTester` entweder per `document` oder `documentName` übergeben
  * Bei `document` schreibst du direkt den Query als String rein
  * Mit `documentName` gibst du den Namen eines "Dokuments" an, den der GraphQlTester dann im Filesystem sucht.
    * Per Default muss die zugehörige Datei im Verzeichnis `src/test/resources/graphql-test/` liegen und die Endung `.graphql` oder `.gql` haben. Also z.B. für den Dateinamen `get-story.graphql` gibst du als `documentName` "get-story" an.

## Doku

* JsonPath: https://github.com/json-path/JsonPath?tab=readme-ov-file

* `SpringBootTest`: https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.with-running-server
* https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.with-mock-environment
* https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.spring-graphql-tests
* Testen von GraphQL Request mit `GraphqlTester`: https://docs.spring.io/spring-graphql/reference/testing.html
    * insbesondere: https://docs.spring.io/spring-graphql/reference/testing.html#testing.requests
* `@GraphQlTest`: https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.spring-graphql-tests
    * Darin findest du auch eine Aufzählung, welche Beans von `@GraphQlTest` in den Application Context aufgenommen werden

## Tipp: GraphQl-Plugin von IntelliJ verwenden
  * Installieren: https://plugins.jetbrains.com/plugin/8097-graphql
  * Damit hast Du Code Completion und Syntax Highlighting für Queries, die du im Editor schreibst
  * Wenn Du Queries direkt in deinem (Java-)Code schreibst, kannst du Intellij mit einem Kommentar `language=GraphQL` als Sprache "Graphql" setzen. Dann hast du auch dort "inline" Code Completion etc. Dazu am besten einen Java Text Block verwenden:
    * ```java
      class MyTest {
      
        
        // language=GraphQL
        String myQuery = """
           { stories { id } }
        """;
      
      }
      ```
    * Wenn du deine Queries in Dateien im (Test-)Verzeichnis anlegen willst, sollten diese mit `.graphql` enden. IntelliJ behandelt sie dann automatisch als GraphQl-Dateien