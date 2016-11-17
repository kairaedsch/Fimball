package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Created by alexcekay on 11/14/16.
 */
public class ElementTriggerEventArgs
{
    private int colliderId;

    private GameElement hitGameElement;

    public ElementTriggerEventArgs(int colliderId, GameElement hitGameElement)
    {
        this.colliderId = colliderId;
        this.hitGameElement = hitGameElement;
    }

    public int getColliderId()
    {
        return colliderId;
    }

    public GameElement getHitGameElement()
    {
        return hitGameElement;
    }
}
