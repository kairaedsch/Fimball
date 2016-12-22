package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import sep.fimball.general.data.Vector2;

/**
 * ShapeLightChanger erzeugt einen Lichteffekt welcher größer werdenden Kreise oder Quadrate darstellt.
 */
public class ShapeLightChanger extends LightChanger
{
    /**
     * Die Mitte des Kreises, um den die Lichter angeschalten werden.
     */
    private ReadOnlyObjectProperty<Vector2> center;

    /**
     * Gibt an, ob die Form ein Kreis ist oder ein Quadrat.
     */
    private boolean circle;

    /**
     * Erstellt einen neuen ShapeLightChanger.
     *
     * @param revertedAnimation Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
     * @param center            Die Mitte des Kreises, um den die Lichter angeschalten werden.
     * @param circle            Gibt an, ob die Form ein Kreis ist oder ein Quadrat.
     */
    ShapeLightChanger(boolean revertedAnimation, ReadOnlyObjectProperty<Vector2> center, boolean circle)
    {
        super(revertedAnimation);
        this.center = center;
        this.circle = circle;
    }

    @Override
    public boolean determineLightStatus(Vector2 position, long delta)
    {
        // Die Geschwindigkeit des Effekts in Grideinheiten pro Sekunde
        double speed = 30;

        // Der Abstand zwischen zwei Formen
        double space = 16;

        // Die Breite eines Form
        double width;

        double radius = (delta / 1000.0) * speed;
        Vector2 relativePosition = position.minus(center.get());

        double distance;
        if (circle)
        {
            width = 8;

            // Berechne Abstand zum Kreismittelpunkt
            distance = Math.abs(radius - relativePosition.magnitude());
        }
        else
        {
            width = 6;

            // Berechne die Abstände zu den 4 Ecken des Quadrats
            double d1 = Math.abs(radius - relativePosition.getX());
            double d2 = Math.abs(radius - relativePosition.getY());
            double d3 = Math.abs(-radius - relativePosition.getX());
            double d4 = Math.abs(-radius - relativePosition.getY());

            // Nehme den kleinsten Abstand
            distance = Math.min(Math.min(d1, d2), Math.min(d3, d4));
        }

        // Rechne mit Modulo, um den Effekt zu duplizieren
        return distance % (space + width) <= width;
    }
}
