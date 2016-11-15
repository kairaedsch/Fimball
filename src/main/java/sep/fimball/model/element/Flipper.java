package sep.fimball.model.element;

import sep.fimball.model.blueprint.pinnballmachine.PlacedElement;

/**
 * Repräsentiert einen Flipperarm auf dem Spielfeld eines Flipperautomaten. Dieser kann durch Tastendruck nach oben bewegt werden, und so mögliche Bälle die in der Bahn liegen wegschleudern.
 */
public class Flipper extends GameElement
{
    private boolean isLeft;
    /**
     * Erstellt einen neuen Flipperarm.
     * @param element
     */
    public Flipper(PlacedElement element, boolean isLeft)
    {
        super(element);
        this.isLeft = isLeft;
    }

    public boolean isLeft()
    {
        return isLeft;
    }
}
