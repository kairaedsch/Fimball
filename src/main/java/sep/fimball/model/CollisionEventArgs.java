package sep.fimball.model;

public class CollisionEventArgs
{
    private PhysicsObject otherObject;

    public CollisionEventArgs(PhysicsObject otherObject)
    {
        this.otherObject = otherObject;
    }

    public PhysicsObject getOtherObject()
    {
        return otherObject;
    }

}