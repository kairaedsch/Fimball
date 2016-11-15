package sep.fimball.model.element;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

/**
 * Created by alexcekay on 11/14/16.
 */
public class TriggerEventArgs {

    private int colliderId;

    private GameElement hitGameElement;

    public TriggerEventArgs(int colliderId, GameElement hitGameElement)
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
