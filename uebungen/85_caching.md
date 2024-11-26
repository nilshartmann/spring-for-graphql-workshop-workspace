# Übung: Caching

* **Füge Caching in die Anwendung ein**

* Aktiviere den Spring Boot Cache-Support
  * Für die Annotation `@EnableCaching` an der Klasse `nh.springgraphql.graphqlservice.GraphqlServiceApplication` hinzu 
* An welchen Stellen hälst du bei uns Caching für sinnvoll?
  * Füge dort Caches ein
* Als "künstliches" Beispiel zum ausprobieren kannst du zum Beispiel das `Story.excerpt`-Feld verwenden
  * Im `StoryRepository` findest Du eine Methode `generateExcerpt`
  * Die Schema-Mapping-Methode für `Story.excerpt` kann diese Methode verwenden, um das Excerpt zu "generieren"
  * In der `generateExcerpt` kannst du die `sleep`-Methode verwenden, um die Methode künstlich langsam zu machen.
* Füge die `Cacheable`-Annotation hinzu
  * Über die Actuator-Endpunkte `http://localhost:8008/actuator/caches` kannst du die Caches ansehen
  * Wenn du einen Cache mit dem Nmen `excerpt-cache` verwendest, kannst du den Endpunkt `http://localhost:8008/actuator/excerpt-cache` verwenden, um auch den Inhalt des Caches zu sehen
  * Tipp: In IntelliJ gibt es die **Actuator**-Ansicht, mit dem du die Endpunkte aufrufen kannst
* Kannst du den Key so setzen, dass er z.B. ein String ist, bestehend aus `Story.id` und `maxLength`?

## Doku

* Cache-Support in Spring: https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html
* Hintergrund: "Spring Expression Language (SpEL)" https://docs.spring.io/spring-framework/reference/core/expressions.html