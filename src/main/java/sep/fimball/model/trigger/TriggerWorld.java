package sep.fimball.model.trigger;

import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.game.GameElement;

/**
 * Created by kaira on 22.11.2016.
 */
public interface TriggerWorld
{
    ReadOnlyListProperty<GameElement> gameElementsProperty();
}
