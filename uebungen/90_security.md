# Übung: Security

## Vorbereitung

* Bitte setze in der `application.properties`-Datei das Property `demo-token-generator.enable` auf `true`
* Wenn du die Anwendung jetzt neu startest, werden dir auf der Konsole zwei Tokens für zwei Benutzer mit den Rollen `ROLE_ADMIN` und `ROLE_USER` ausgegeben
* Beim Absenden von Queries aus GraphiQL kannst du die Token im `Header`-Tab einfügen, um Queries mit dem jeweils einen oder anderen User auszuführen (oder ohne Header für anoynmen Zugriff)

## Schritte

* Die `createComment`-Mutation soll nur für Benutzer mit der Rolle `ROLE_USER` erlaubt sein
* Eine Story soll nur dann zurückgeliefert werden, wenn die Story entweder:
  * _nicht_ den `PublicationState` `draft` hat
  * _oder_ der Benutzer die Rolle `ROLE_ADMIN` hat
  * (Admins dürfen alles sehen, alle anderen nur Stories, die in Review oder Published sind)

## Doku

* Spring Security `Authentication` Objekt: https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-authentication
* PreAuthorize, um bei einem Methoden-Aufruf die korrekten Berechtigungen sicherzustellen: https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-preauthorize
* PostAuthorize, um Rückgabe-Werte von Methoden zu authorisieren: https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-postauthorize
* PostFilter, um zurückgegebene Listen von Methoden zu authorisieren: https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-postfilter

* Hintergrund: "Spring Expression Language (SpEL)" https://docs.spring.io/spring-framework/reference/core/expressions.html