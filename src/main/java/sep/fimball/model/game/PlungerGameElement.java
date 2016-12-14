package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Created by kaira on 14.12.2016.
 */
public class PlungerGameElement extends GameElement
{
    /**
     * Erstellt ein neues PlungerGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public PlungerGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }
}
