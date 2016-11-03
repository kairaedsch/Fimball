package sep.fimball.model;

public class CollisionEventArgs
{
    private GameElement otherElement;

    /**
	 *
	 * @param otherElement
	 */
	public CollisionEventArgs(GameElement otherElement)
    {
        this.otherElement = otherElement;
    }

    public GameElement getOtherElement()
    {
        return otherElement;
    }
}