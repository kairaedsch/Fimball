package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyListProperty;

/**
 * Die World aus der Sicht der Handler.
 */
public interface HandlerWorld
{
    /**
     * Gibt die Liste der in der World enthaltenen GameElements zurück.
     *
     * @return Die Liste der in der World enthaltenen GameElements
     */
    ReadOnlyListProperty<HandlerGameElement> gameElementsProperty();
}
