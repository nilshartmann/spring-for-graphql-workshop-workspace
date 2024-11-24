# Übung: Einen Query ausführen
* **Mach' dich mit der GraphQL-Abfragesprache vertraut**

* Öffne GraphiQL auf meinem Computer (URL gebe ich euch)
* Versuche einen Query auszuführen, der die ersten zehn Stories zurückliefert und folgende Felder abfragt:
  * Id, Titel, Excerpt, Veröffentlichungsdatum, Wer hat die Story geschrieben und die jeweils ersten zehn Kommentare
  * Kannst Du den Query so erweitern, dass er die ersten zehn neusten Stories zurückliefert?
  * Bau eine Fragment (Author), das aus dem Member dessen Id, sowie name und id des Users enthält
  * Verwende das Fragment, um in den Stories und den Kommentaren jeweils die Member-Informationen abzufragen
  * In den Stories zusätzlich noch skills des Members abfragen

* Du kannst Code-Completion und den Docs-Explorer von GraphiQL verwenden, um die API zu untersuchen
  * Mögliche Lösung: https://graphql.schule/queries
