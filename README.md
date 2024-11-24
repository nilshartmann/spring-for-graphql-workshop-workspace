# GraphQl Workshop Workspace

## Voraussetzungen

* Du brauchst **Java 21**

## Der Workspace

- Das Repository ein Multi-Module Gradle Projekt
- Du kannst das ganze Repository in deiner IDE öffnen
  - IntelliJ sollte die Module korrekt erkenne.
- Es gibt drei Gradle-Module:
  - `root` (leer)
  - `graphql-service`
  - `remote-service`
  - Wir arbeiten nur im **graphql-service**

## Starten der Anwendung

- Wir werden die Spring Boot-Anwendung `graphql-service` Schritt-für-Schritt entwickeln
- In dem Modul befinden sich ein paar Klassen, insb. ist der "Domain-Layer" fertig. Außerdem gibt es ein paar Konfigurationsdateien und ein paar Klassen als Ausgangsbasis für unseren eigenen Code.
- Du kannst **die Anwendung starten**, in dem du die Klasse `nh.springgraphql.graphqlservice.GraphqlServiceApplication` in deiner IDE startest (diese enthält eine `main`-Methode)
- Der Spring Boot Server mit der GraphQl API lauscht dann auf Port `8080`
  - Du kannst den Port in der `application.properties`-Datei bei Bedarf anpassen (Property `server.port`)


