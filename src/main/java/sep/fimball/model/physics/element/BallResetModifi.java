package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

public class BallResetModifi extends Modifi<BallPhysicsElement>
{
    private Vector2 newPosition;

    public BallResetModifi(BallPhysicsElement ballPhysicsElement, Vector2 newPosition)
    {
        super(ballPhysicsElement);
        this.newPosition = newPosition;
    }

    public Vector2 getNewPosition()
    {
        return newPosition;
    }
}
