/*
  Personalverwaltung – eigene (checked) Exception für alle Fehler
  in den Klassen der Personalverwaltung.
  Stand: Lektion 3.1 (Exceptions)
*/
public class FirmaException extends Exception {

    public FirmaException(String message) {
        super(message);
    }

    public FirmaException(String message, Exception e) {
        super(message, e);
    }
}
