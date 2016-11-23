package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.game.GameElement;

/**
 * TODO
 */
public interface HandlerWorld
{
    /**
     * TODO
     * @return
     */
    ReadOnlyListProperty<GameElement> gameElementsProperty();
}
