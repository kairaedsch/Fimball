package sep.fimball.model;

public class CollisionEventArgs
{
    private GameElement otherElement;
    private Collider collider;

    /**
	 *
	 * @param otherElement
	 */
	public CollisionEventArgs(GameElement otherElement, Collider collider)
    {
        this.otherElement = otherElement;
        this.collider = collider;
    }

    public GameElement getOtherElement()
    {
        return otherElement;
    }
}