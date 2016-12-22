package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

/**
 * LineLightChanger erzeugt einen Lichteffekt welcher aus horizontalen oder vertikalen Linien besteht.
 */
public class LineLightChanger extends LightChanger
{
    /**
     * Gibt an, ob die Linie vertikal oder horizontal verläuft.
     */
    private boolean vertical;

    /**
     * Erstellt einen neuen LineLightChanger.
     *
     * @param revertedAnimation Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
     * @param vertical          Gibt an, ob die Linie vertikal oder horizontal verläuft.
     */
    LineLightChanger(boolean revertedAnimation, boolean vertical)
    {
        super(revertedAnimation);
        this.vertical = vertical;
    }

    @Override
    public boolean determineLightStatus(Vector2 position, long delta)
    {
        // Die Geschwindigkeit des Effekts in Grideinheiten pro Sekunde
        double speed = 30;

        // Die Breite einer Lichter-Linie
        double width = 6;

        // Der Abstand zwischen zwei Lichter-Linien
        double space = 16;

        double line = (delta / 1000.0) * speed;

        // Berechne den Abstand zur line
        double distanceToPoint = Math.abs(line - (vertical ? position.getX() : position.getY()));

        // Rechne mit Modulo, um den Effekt zu duplizieren
        return distanceToPoint % (space + width) <= width;
    }
}
