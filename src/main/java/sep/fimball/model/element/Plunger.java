package sep.fimball.model.element;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Der Plunger stellt den Plunger des Automaten, der die Kugel anstößt, dar.
 */
public class Plunger extends GameElement
{
    /**
     * Erzeugt eine neue Instanz von Plunger.
     * @param element
     */
    public Plunger(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }
}
