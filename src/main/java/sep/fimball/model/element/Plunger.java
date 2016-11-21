package sep.fimball.model.element;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Der Plunger stellt den Plunger des Automaten, der die Kugel anstößt, dar.
 */
public class Plunger extends GameElement
{
    /**
     * Erzeugt eine neue Instanz von Plunger.
     *
     * @param element Die Vorlage, aus der der Plunger erstellt werden soll.
     * @param bind Gibt an, ob sich der Plunger an die Properties des PlacedElement binden soll.
     */
    public Plunger(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }
}
