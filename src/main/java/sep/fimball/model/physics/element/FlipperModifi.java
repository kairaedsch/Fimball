package sep.fimball.model.physics.element;

public class FlipperModifi extends Modifi<FlipperPhysicsElement>
{
    private boolean up;

    public FlipperModifi(FlipperPhysicsElement flipperPhysicsElement, boolean up)
    {
        super(flipperPhysicsElement);
        this.up = up;
    }

    public boolean isUp()
    {
        return up;
    }
}
