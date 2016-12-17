package sep.fimball.model.game;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerBallGameElement;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.BallNudgeModify;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BallResetModify;

/**
 * Das Spielelement des Balls.
 */
public class BallGameElement extends GameElement implements HandlerBallGameElement
{
    /**
     * Der PhysicHandler welcher sich um die Simulation der Physik kümmert. Wird benötigt um zu diesem Modifiers hinzuzufügen.
     */
    private PhysicsHandler physicsHandler;

    /**
     * Das physikalische Element des Balls.
     */
    private BallPhysicsElement ballPhysicsElement;

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

    public void setPhysicsElement(PhysicsHandler physicsHandler, BallPhysicsElement ballPhysicsElement)
    {
        this.physicsHandler = physicsHandler;
        this.ballPhysicsElement = ballPhysicsElement;
    }

    public void nudge(boolean left)
    {
        physicsHandler.addModify(ballPhysicsElement, (BallNudgeModify) () -> left);
    }

    public void reset()
    {
        Vector2 newPos = getPlacedElement().positionProperty().get();
        physicsHandler.addModify(ballPhysicsElement, (BallResetModify) () -> newPos);
    }
}
