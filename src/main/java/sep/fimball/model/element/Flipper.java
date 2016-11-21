package sep.fimball.model.element;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Repräsentiert einen Flipperarm auf dem Spielfeld eines Flipperautomaten. Dieser kann durch Tastendruck nach oben bewegt werden, und so mögliche Bälle die in der Bahn liegen wegschleudern.
 */
public class Flipper extends GameElement
{
    /**
     * Gibt an, ob der Flipper ein linksseitiger oder rechtsseitiger Flipper ist.
     */
    private boolean isLeft;

    /**
     * Erstellt einen neuen Flipperarm.
     *
     * @param element Die Vorlage, aus der der Flipper erstellt werden soll.
     * @param bind Gibt an, ob sich der Ball an die Properties des PlacedElements binden soll.
     * @param isLeft Gibt an, ob der Flipper ein linksseitiger oder rechtsseitiger Flipper ist.
     */
    public Flipper(PlacedElement element, boolean bind, boolean isLeft)
    {
        super(element, bind);
        this.isLeft = isLeft;
    }

    /**
     * Gibt an, ob der Flipper ein linksseitiger oder rechtsseitiger Flipper ist.
     *
     * @return {@code true}, wenn der Flipper linksseitig ist, {@code false} sonst.
     */
    public boolean isLeft()
    {
        return isLeft;
    }
}
