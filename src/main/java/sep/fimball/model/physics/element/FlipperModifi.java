package sep.fimball.model.physics.element;

public class FlipperModifi extends Modifi
{
    private boolean up;

    public FlipperModifi(boolean up)
    {
        this.up = up;
    }

    public boolean isUp()
    {
        return up;
    }
}
