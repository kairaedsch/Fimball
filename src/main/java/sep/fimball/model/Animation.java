package sep.fimball.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.ElementType;

/**
 * Enthält die Pfade zu den Bild-Dateien einer Animation, und stellt das aktuell angezeigte Bild bereit.
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
     * Der Pfad zum aktuellen Bild der Animation.
     */
    private StringProperty currentFrame;

    /**
     * Erstellt eine neue Animation aus der Vorlage eines Elements.
     * @param blueprintName
     * @param animationBlueprint
     */
    public Animation(String blueprintName, ElementType.Collider.AnimationObject animationBlueprint)
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
     * Startet die Animation und setzt diese auf das erste Bild zurück.
     */
    public void startAnimation()
    {

    }

    public ReadOnlyStringProperty currentFrameProperty()
    {
        return currentFrame;
    }
}
