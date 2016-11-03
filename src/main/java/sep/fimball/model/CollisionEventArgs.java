package sep.fimball.model;

public class CollisionEventArgs
{
    private GameElement otherElement;
    private Class otherElementType;

    /**
	 *
	 * @param otherElement
	 */
	public CollisionEventArgs(GameElement otherElement, Class otherElementType)
    {
        this.otherElement = otherElement;
        this.otherElementType = otherElementType;
    }

    public GameElement getOtherElement()
    {
        return otherElement;
    }

    public Class getOtherElementType()
    {
        return otherElementType;
    }
}