package sep.fimball.model.game;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerBallGameElement;
import sep.fimball.model.physics.element.BallNudgeModify;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BallResetModify;

/**
 * Das Spielelement des Balls.
 */
public class BallGameElement extends GameElement implements HandlerBallGameElement
{
    /**
     * Das physikalische Element des Balls.
     */
    protected BallPhysicsElement physicsElement;

    /**
     * Erstellt ein neues BallGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem BallGameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public BallGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }

    /**
     * Setzt das zu diesem GameElement gehörige PhysicsElement.
     *
     * @param physicsElement Das zu diesem GameElement gehörige PhysicsElement.
     */
    public void setPhysicsElement(BallPhysicsElement physicsElement)
    {
        this.physicsElement = physicsElement;
    }

    public void nudge(boolean left)
    {
        physicsElement.addModify((BallNudgeModify) () -> left);
    }

    /**
     * Setzt den Ball auf die ursprüngliche Position zurück.
     */
    public void reset()
    {
        Vector2 newPos = getPlacedElement().positionProperty().get();
        physicsElement.addModify((BallResetModify) () -> newPos);
    }
}
