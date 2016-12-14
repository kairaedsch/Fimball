package sep.fimball.model.physics.element;

public class BallTiltModifi extends Modifi
{
    private boolean left;

    public BallTiltModifi(boolean left)
    {
        this.left = left;
    }

    public boolean isLeft()
    {
        return left;
    }
}
