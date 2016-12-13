package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import sep.fimball.general.data.Vector2;

/**
 * FormLightChanger entscheidet in größer werdenden Kreisen oder Quadraten, ob Lichter angeschalten werden sollen.
 */
public class FormLightChanger extends LightChanger
{
    /**
     * Die Mitte des Kreises, um den die Lichter angeschalten werden.
     */
    private final ReadOnlyObjectProperty<Vector2> center;

    /**
     * Gibt an, ob die Form ein Kreis ist oder ein Quadrat.
     */
    private final boolean circle;

    /**
     * Erstellt einen neuen FormLightChanger.
     *
     * @param revertedAnimation Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
     * @param center Die Mitte des Kreises, um den die Lichter angeschalten werden.
     * @param circle Gibt an, ob die Form ein Kreis ist oder ein Quadrat.
     */
    FormLightChanger(boolean revertedAnimation, ReadOnlyObjectProperty<Vector2> center, boolean circle)
    {
        super(revertedAnimation);
        this.center = center;
        this.circle = circle;
    }

    @Override
    public boolean determineLightStatus(Vector2 position, long delta)
    {
        // units per second
        double speed = 40;
        double space = 16;
        double width;

        double radius = (delta / 1000.0) * speed;
        Vector2 relativePosition = position.minus(center.get());

        double distance;
        if(circle)
        {
            width = 8;
            distance = Math.abs(radius - relativePosition.magnitude());
        }
        else
        {
            width = 6;
            double d1 =  Math.abs(radius - relativePosition.getX());
            double d2 =  Math.abs(radius - relativePosition.getY());
            double d3 =  Math.abs(-radius - relativePosition.getX());
            double d4 =  Math.abs(-radius - relativePosition.getY());

            distance = Math.min(Math.min(d1, d2), Math.min(d3, d4));
        }
        return distance % (space + width) <= width;
    }
}
