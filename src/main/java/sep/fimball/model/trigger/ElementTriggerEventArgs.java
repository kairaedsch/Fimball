package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * TODO
 */
public class ElementTriggerEventArgs
{
    /**
     * TODO
     */
    private int colliderId;

    /**
     * TODO
     */
    private GameElement hitGameElement;

    /**
     * TODO
     * @param colliderId
     * @param hitGameElement
     */
    public ElementTriggerEventArgs(int colliderId, GameElement hitGameElement)
    {
        this.colliderId = colliderId;
        this.hitGameElement = hitGameElement;
    }

    /**
     * TODO
     * @return
     */
    public int getColliderId()
    {
        return colliderId;
    }

    /**
     * TODO
     * @return
     */
    public GameElement getHitGameElement()
    {
        return hitGameElement;
    }
}
