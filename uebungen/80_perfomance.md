# Übung: Performance-Tuning

* **Verbessere die Performance beim Zugriff auf den "Publisher Remote Service"**

* Schalte die `SimplifiedTracingInstrumentation` ein
  * Dazu bitte in `` die folgende Bean-Definition ergänzen:
  * ```java
    @Bean
    SimplifiedTracingInstrumentation tracingInstrumentation() {
        return new SimplifiedTracingInstrumentation();
    }
    ```
  * Hinweis: Es gibt eine fertige `TracingInstrumentation` in graphql-java, die du genauso verwenden kannst
    * "Meine" `SimplifiedTracingInstrumentation` gibt etwas weniger Informationen aus und gibt die Zeitangaben in Millisekunden (statt Nanosekunden) aus.
    * Ich finde das zum Verständnis und zum Vorführen einfacher, aber du kannst natürlich (insb. in einer "echten" Anwendung) die `TracingInstrumentation` von graphql-java verwenden
* Verlangsame die Remote-Requests
  * Dazu kannst du die Konstante `slowdownRequest` in `PublisherServiceClient` auf einen (langen) Wert in Millisekunden setzen (z.B. 500), um den jeder Request dann künstlich vom Remote Service verzögert wird
* Probiere verschiedene Strategien zur Verbesserung der Performance:
  * Verwende ein `Callable` als Rückgabe-Type
  * Schalte Virtual Threads ein
  * Überarbeite die Schema-Mapping-Methode für `Story.publisher` mit einem `BatchLoader`


## Doku
* TracingInstrumentation: https://www.graphql-java.com/documentation/instrumentation/#apollo-tracing-instrumentation
* Spring GraphQL sammelt automatisch alle `Instrumentation` Beans ein uns fügt sie dem RuntimeWiring hinzu: https://docs.spring.io/spring-graphql/reference/request-execution.html#execution.graphqlsource
* Batch Loading in Spring GraphQL: https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.batch-mapping
* DataLoader in graphql-java: https://www.graphql-java.com/documentation/batching/
* Konfiguration der TaskExecutor in Spring Boot: https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html#features.task-execution-and-scheduling
* Async-Methoden in Spring: https://spring.io/guides/gs/async-method
