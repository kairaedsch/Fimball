package sep.fimball.model.physics.element;

public class BallTiltModifi extends Modifi<BallPhysicsElement>
{
    private boolean left;

    public BallTiltModifi(BallPhysicsElement ballPhysicsElement, boolean left)
    {
        super(ballPhysicsElement);
        this.left = left;
    }

    public boolean isLeft()
    {
        return left;
    }
}
