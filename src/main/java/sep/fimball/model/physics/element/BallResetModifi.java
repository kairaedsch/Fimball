package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

public class BallResetModifi extends Modifi
{
    private Vector2 newPosition;

    public BallResetModifi(Vector2 newPosition)
    {
        this.newPosition = newPosition;
    }

    public Vector2 getNewPosition()
    {
        return newPosition;
    }
}
