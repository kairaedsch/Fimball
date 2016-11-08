package sep.fimball.model;

import sep.fimball.model.blueprint.PlacedElement;

/**
 * Repräsentiert einen Flipperarm auf dem Spielfeld eines Flipperautomaten. Dieser kann durch Tastendruck nach oben bewegt werden, und so mögliche Bälle die in der Bahn liegen wegschleudern.
 */
public class Flipper extends GameElement
{
    /**
     * Erstellt einen neuen Flipperarm.
     * @param element
     */
    public Flipper(PlacedElement element)
    {
        super(element);
    }
}
