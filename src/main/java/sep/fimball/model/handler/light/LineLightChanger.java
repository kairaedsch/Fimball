package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

/**
 * LineLightChanger entscheidet anhand einer Richtung, wann die Lichter angeschalten werden.
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
        // units per second
        double speed = 40;
        double width = 6;
        double space = 16;

        double distance = (delta / 1000.0) * speed;

        return Math.abs(distance - (vertical ? position.getX() : position.getY())) % (space + width) <= width;
    }
}
