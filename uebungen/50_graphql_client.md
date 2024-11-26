# Lese Daten aus einer GraphQL API

## Vorbereitung

- Bitte stelle sicher, dass der "Remote Service" läuft (s. `README_remote_service.md` im Root-Verzeichnis)

## Aufgabe

- Die `Media`-Daten zu einer `Story` sind in einem externen GraphQL Dienst abgelegt ("Media GraphQL Service")
  - Der soll exemplarisch für einen externen Dienst stehen, den wir ansprechen, aber nicht selbst implementieren
  - Bei uns im Workspace läuft der "Media GraphQL Service" läuft als Teil der "remote-services"-Applikation, d.h.:
    - API: http://localhost:8090/graphql
    - GraphiQL: http://localhost:8090/graphiql
  - Der "Media GraphQL Service" kann Media-Daten zu einer Story-Id zurückliefern.

## Schritte:
- Erweitere unser Schema:
  - Im **Remote Service** gibt es bereits ein Schema (`media.graphqls`).
  - Darin ist bereits ein `Media`, ein `MediaMetadata` und ein `MediaType` beschrieben. 
    - Diese Daten wollen wir in der gleichen Form an unserer `Story` anbieten
    - Der einfachheithalber kannst du die drei Typen aus dem `remote-service`-Projekt (`src/resources/schema/media.graphqls`) direkt in dein eigenes Schema kopieren
    - Füge an deinem `Story`-Typ ein Feld `media: [Media!]` hinzu
- Baue die Schema-Mapping-Methode für das `Story.media`-Feld
  - Lege dafür eine eigene `@Controller`-Klasse an: `MediaController`
  - Diese Klasse muss im Konstruktor die URL zum Remote-Service aus den `application.properties` erhalten (Property-Name: `publisher.service.base-url`). Dazu kannst du die `@Value`-Annotation von Spring mit der Spring Expression Language verwenden:
    - ```java
      @Controller
      class MediaController {
        MediaController(@Value("${publisher.service.base-url}") String baseUrl) {
          var graphQlApiBaseUrl = baseUrl + "graphql";
         }
      }      
      ```
  - Für den GraphQlClient musst du zunächst einen Spring `RestClient` erzeugen, der für die Ausführung des HTTP-Requests verantwortlich ist. Dazu kannst du die statische `create`-Factory-Methode verwenden und als `baseUrl` die Remote-Service-Url mit dem Pfad `/graphql` am Ende angeben (`graphQlApiBaseUrl` aus dem Beispiel oben).
  - Mit dem `RestClient` kannst du nun den `HttpSyncGraphqlClient` erzeugen (statische `create`-Methode verwenden). 
  - Wenn du auch den DGS GraphQl Client ausprobieren willst, kannst du den `GraphQlClient` aus dem vorherigen Schritt an `DgsGraphQlClient.create` übergeben.
    - Wichtig: dafür muss der DGS Code Generator bei dir funktionieren (das entsprechende Gradle Plug-in ist im Workspace konfiguriert, sollte also funktionieren)
      - Der Code Generator hat funktioniert wenn in `graphql-service/build/generated/sources/dgs-codegen/nh/graphqlexample/graphqlservice/clients/media` (generierte) Source-Dateien vorhanden sind  
  - In der `SchemaMapping`-Methode verwendest du einen der beiden Clients (`GraphQlClient` oder `DgsGraphQlClient`) um die Media-Daten für die `Story` zu lesen
    - Frage der einfachheithalber einfach _alle_ Felder des `Media`-Typen ab.
    - Die SchemaMapping-Methode muss das `media`-Feld aus der Antwort zurückliefern
      - Du kannst dir dafür die Antwort als Liste von Maps geben lassen oder du schreibst dir ein DTO-Objekt
- **Optional:**
  - Mapping-Methoden können als Parameter ein `DataFetchingFieldSelectionSet` von graphql-java erhalten
    - Damit hast du Zugriff auf die Felder, die im aktuellen Query abgefragt werden
    - Kannst du den GraphQl Query zum "Media Service" so optimieren, dass auch dort nur die Felder abgefragt werden, die für den aktuellen Request benötigt werden?
      - Wenn also im aktuellen Request nur `Media.type` und `Media.metadata.dimensions` abgefragt werden, soll auch der GraphQl Request zum "Media Service" nur diese beiden Felder abfragen
      - Macht diese Optimierung Sinn? In welchen Fällen?

## Doku

* Spring RestClient (für uns ist nur die Erzeugung des Clients relevant, die Ausführung übernimmt dann der GraphQl Client): https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-restclient
* Value Annotation von Spring: https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/value-annotations.html

* GraphQL Client von Spring GraphQL: https://docs.spring.io/spring-graphql/reference/client.html#
  * Wir verwenden die `HttpSyncGraphQlClient` Implementierung: https://docs.spring.io/spring-graphql/reference/client.html#client.httpsyncgraphqlclient
  * Request ausführen: https://docs.spring.io/spring-graphql/reference/client.html#client.requests

* **DgsGraphQlClient** für Spring: https://docs.spring.io/spring-graphql/reference/client.html#client.dgsgraphqlclient
* **DGS Codegenerator** https://netflix.github.io/dgs/generating-code-from-schema/

* Erlaubte Methoden-Parameter von Mapping-Methoden: https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.schema-mapping.signature
* DataFetchingFieldSelectionSet: https://www.graphql-java.com/documentation/field-selection/