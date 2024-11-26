# Unsere API soll einen generischen Zugriff auf alle Objekte haben

* Die Typen `Story`, `Comment` und `Publisher` sollen über eine generisches `node`-Feld am `Query` abgefragt werden können.
* Erweitere das Schema:
  * Füge das Interface `Node` hinzu, das nur aus dem Feld `id` besteht
  * Die o.g. drei Typen müssen dieses Interface implementieren
* Lege eine neue SchemaMapping-Methode an (Klasse: `NodeController`)
  * Diese SchemaMapping-Methode soll für alle `id`-Felder aller bekannten Node-Typen jeweils einen String im Format "Erster_Buchstabe_Typename:Id" zurückliefern. Beispiel:
    * Story: `S:1`
    * Comment: `C:1`
    * Publisher: `P:1`
  * Wenn du jetzt z.B. die Ids aller `stories` abfragst mit allen Kommentaren und den jeweiligen Publishern, müssten die Ids im entsprechenden Format zurückkommen.
* Nun kommt die "Gegenrichtung": wir erhalten eine Id im o.g. Format
  * Erweitere das Schema um das Feld `Query.node`. Dieses Feld soll den Typ `Node` zurückliefern und ein Argument `id: ID!` haben. 
  * Lege die QueryMapping-Funktion für `Query.node` an
  * Darin musst du die übergeben Id parsen (aufteilen in Typ und Id) und dann mittels des `StoryRepository` bzw. des `PublisherServiceClient` das angefragte Objekt lesen
* Der vollständigkeithalber solltest Du auch `Query.story` und `AddCommentInput` anpassen, denn auch dort werden ja (Story-)Ids übergeben  
* Du kannst dir meine `NodeId`-Klasse auch aus GitHub kopieren, wenn du parsen etc. nicht selbst bauen willst
* Denk dann daran, den `NodeIdConverter` in der Klasse `GraphQlConfig` als `@Bean` zu registrieren
* **Optional**
  * Schreibe einen Test, der `Query.node` testet
  * Im Test kannst musst Du den `NodeController` importieren und auch die `GraphqlConfig`-Klasse:
    * `@Import({StoryRepository.class, GraphQlConfig.class})`
    * Den `PublisherServiceClient` kannst du mocken:
      * ```java
          import org.springframework.context.annotation.Import; 
          import org.springframework.test.context.bean.override.mockito.MockitoBean;

          @GraphQlTest(NodeController.class)
          @Import({StoryRepository.class, GraphQlConfig.class})

          class NodeControllerTest {
            @MockitoBean
            PublisherServiceClient publisherServiceClient;

            // ...
         }
        ``` 
    * Damit kannst du im Test dann eine Story oder einen Kommentar per Id abfragen      