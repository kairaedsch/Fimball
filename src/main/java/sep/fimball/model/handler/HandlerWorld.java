package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.game.GameElement;

/**
 * Created by kaira on 22.11.2016.
 */
public interface HandlerWorld
{
    ReadOnlyListProperty<GameElement> gameElementsProperty();
}
