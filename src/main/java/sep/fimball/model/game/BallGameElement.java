package sep.fimball.model.game;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerBallGameElement;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.BallResetModifi;
import sep.fimball.model.physics.element.BallTiltModifi;
import sep.fimball.model.physics.element.BallPhysicsElement;

public class BallGameElement extends GameElement implements HandlerBallGameElement
{
    private PhysicsHandler physicsHandler;

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
        physicsHandler.addModifi(ballPhysicsElement, new BallTiltModifi(left));
    }

    public void reset()
    {
        physicsHandler.addModifi(ballPhysicsElement, new BallResetModifi(getPlacedElement().positionProperty().get()));
    }
}
