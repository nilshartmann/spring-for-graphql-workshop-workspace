# Lade den Publisher einer Story aus dem remote Service

## Vorbereitung: Starten der RemoteServiceApplication

- Bitte stelle sicher, dass der "Remote Service" läuft (s. `README_remote_service.md` im Root-Verzeichnis)

## Schritte

* Ergänze das Schema: unsere Story bekommt jetzt einen Publisher
  * Ein `Publisher` besteht aus `id`, `name` und `contact`
  * Der `Contact` hat zwei Felder: `type` und `value`
    * `type` ist ein enum mit zwei Ausprägungen `email` und `phone`
* Baue eine Handler-Methode für `Story.publisher`
  * **Lege dafür eine neue Controller-Klasse `PublisherController`** an
  * Lass dir den `PublisherRemoteServiceClient` per Constructor-Injection übergeben
  * Das Story-Objekt aus der "Datenbank" hat ein `publisherId`-Property
  * Mit der `publisherId` kannst du über den `PublisherRemoteServiceClient` den Publisher laden
  * Leider passt die zurückgelieferte Map nicht so recht zu unserem Schema 😿
    * Du kannst dir das Ergebnis des REST-Calls im Browser ansehen, z.B. http://localhost:8090/api/publishers/1
  * Wie kannst du das Problem lösen? Es gibt mehrere Optionen...
    * DTOs
    * Neue Maps
    * Resolver-Funktion
    * oder eine Mischung aus alldem
    * Für was entscheidest du dich? Und warum?
* **Optional**:
  * Baue einen Test für das `Story.publisher`-Feld
  * Wie gehts du mit dem Remote-Service um?
    * Du kannst den Remote-Service starten und den "echten" Service verwenden
    * Dann musst du die Bean `PublisherServiceClient` importieren (`@Import(PublisherServiceClient.class` an deiner Testklasse)
    * Alternativ kannst du die Bean auch Mocken (`@MockitoBean`) und eine Mock-Implementierung für `fetchPublisher` bereitstellen
      * ```java
        import static org.mockito.BDDMockito.given;
        
        @Test
        void fetching_publisher_works() {
            given(publisherServiceClient.fetchPublisher("1"))
                .willReturn( /* ... */);
        
          // ...
        }
        ```
## Doku
* Spring RestClient (wird im PublisherServiceClient für den HTTP-Zugriff verwendet): https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-restclient
* MockitoBean: https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-mockitobean.html
* Mockito `given`: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/BDDMockito.html
* 

