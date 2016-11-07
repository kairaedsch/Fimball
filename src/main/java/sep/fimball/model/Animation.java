package sep.fimball.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Enthält die Pfade zu den Bild-Dateien einer Animation, und stellt das aktuell angezeigte Bild der Animation bereit.
 */
public class Animation
{
    /**
     * Array mit den Pfaden der einzelnen Bilder der Animation.
     */
    private String[] framePaths;

    /**
     * Wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     */
    private double duration;

    /**
     * Property, an die vom SpriteViewModel gebunden wird. Diese enthält den Pfad zum aktuellen Bild.
     */
    private StringProperty currentFrame;

    /**
     * Erstellt eine neue Animation.
     *
     * @param directoryPath
     */
    public Animation(String directoryPath)
    {
        // load frames and stuff
    }

    /**
     * Wird kurz vor dem Zeichen des Automaten im Canvas aufgerufen, um den Pfad zum aktuell ausgewählte Bild zu aktualisieren.
     */
    public void update()
    {
        // move to next frame logic etc.
    }

    /**
     * Setzt die Animation auf das erste Bild zurück.
     */
    public void startAnimation()
    {

    }

    public ReadOnlyStringProperty currentFrameProperty()
    {
        return currentFrame;
    }
}
