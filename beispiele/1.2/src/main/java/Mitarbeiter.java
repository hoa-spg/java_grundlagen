/*
  Personalverwaltung – die Klasse Mitarbeiter modelliert eine Person,
  die in einer Firma arbeitet.
  Stand: Lektion 1.2 (Attribute, Get- und Set-Methoden)
*/
public class Mitarbeiter {

    // Objekteigenschaften (Attribute)
    private String name;
    private int geburtsjahr;

    // Get-/Set-Methoden
    public String getName() {
        return name;
    }

    public void setName(String neuerName) {
        name = neuerName;
    }

    public int getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(int neuesGeburtsjahr) {
        geburtsjahr = neuesGeburtsjahr;
    }
}
