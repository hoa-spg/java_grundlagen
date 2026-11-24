# Kursprojekt „Personalverwaltung“ – Stand nach jeder Lektion

Jeder Ordner enthält den **vollständigen, lauffähigen Stand nach der
jeweiligen Lektion** (z. B. `2.5/` = nach Lektion 2.5).

| Stufen | Art des Projekts | Öffnen / Starten |
|--------|------------------|------------------|
| `1.1` – `3.7` | Maven-Projekt `personalverwaltung` (Klassen in `src/main/java`, Tests in `src/test/java`, noch ohne Packages) | IntelliJ: *Open* → `pom.xml` → *Open as Project*. Objekte ausprobieren mit dem Plugin *Object Workbench*. Ab 1.6: `TestMitarbeiter.main` (Kurzform `void main()`, Java 25), ab 1.11: `Programm.main` starten. Tests (ab 1.7): Rechtsklick auf `src/test/java` → *Run All Tests* oder `mvn test` |
| `3.8` | Maven-Projekt `company-model` (Package `at.spengergasse.company.model`) | wie oben, Start: `Main.main` |
| `4.2` – `4.5` | Vaadin-Webanwendung `company-web` (Spring Boot) | IntelliJ: `pom.xml` öffnen, `Application.main` starten → <http://localhost:8080>. Oder `mvn spring-boot:run` |

Hinweise

- JUnit 5 kommt in Lektion 1.7 als Abhängigkeit in die `pom.xml`. Die einfachen
  Testklassen `TestMitarbeiter`/`TestFirma` (1.6–1.11) bestehen nur aus `void main()` und Testmethoden (ohne Klasse, Java 25)
  und werden in 2.1 durch reine JUnit-Tests ersetzt.
- Ab 3.3 liest `Programm` die Datei `firma.csv` aus dem **Projektordner**
  (Arbeitsverzeichnis beim Start).
- Teil 4 benötigt beim ersten Start Internet (Maven und Vaadin laden ihre
  Abhängigkeiten bzw. das Frontend-Tooling herunter).
- Die Ordner werden aus `java/kurs/_build/java-grundlagen/projekt/` erzeugt
  (dort liegen pro Lektion nur die neuen/geänderten Dateien) – Änderungen
  bitte dort vornehmen und neu bauen.
