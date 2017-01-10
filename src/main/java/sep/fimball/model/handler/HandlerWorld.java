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
    ReadOnlyListProperty<? extends HandlerGameElement> gameElementsProperty();

    /**
     * Gibt die unterste Position das Automaten zurück.
     *
     * @return Die unterste Position das Automaten.
     */
    double getMaximumYPosition();
}
