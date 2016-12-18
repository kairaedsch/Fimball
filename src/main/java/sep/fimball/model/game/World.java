package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import sep.fimball.model.handler.HandlerWorld;
import sep.fimball.model.physics.game.ElementEventArgs;

import java.util.List;

/**
 * Eine World stellt die Spielwelt eines Automaten dar.
 */
public class World implements HandlerWorld
{
    /**
     * Liste der Elemente in der Spielwelt.
     */
    private ListProperty<GameElement<?>> gameElements;

    /**
     * Die Elemente der Spielwelt sortiert. Sie werden so sortiert dass sie in der korrekten Reihenfolge gezeichnet werden können.
     */
    private ListProperty<GameElement<?>> sortedGameElements;

    /**
     * Erzeugt eine World mit der übergebenen Liste von GameElements.
     *
     * @param elements Liste der Elemente in der Spielwelt.
     */
    public World(ObservableList<GameElement<?>> elements)
    {
        gameElements = new SimpleListProperty<>(elements);
        sortedGameElements = new SimpleListProperty<>(new SortedList<>(gameElements, GameElement::compare));
    }

    /**
     * Fügt das gegebene Element in die Spielwelt ein.
     *
     * @param element Element, welches in die Spielwelt eingefügt wird.
     */
    public void addGameElement(GameElement element)
    {
        gameElements.add(element);
    }

    /**
     * Synchronisiert alle GameElements mit ihren Repräsentationen in der Physik.
     *
     * @param elementEventArgsList Liste der Änderungen für GameElements.
     */
    public void synchronizeWithPhysics(List<ElementEventArgs<GameElement<?>>> elementEventArgsList)
    {
        for (ElementEventArgs<GameElement<?>> elementEventArgs : elementEventArgsList)
        {
            elementEventArgs.getGameElement().synchronizeWithPhysics(elementEventArgs);
        }
    }

    /**
     * Gibt die Liste der GameElements zurück.
     *
     * @return Die Liste der GameElements.
     */
    public ReadOnlyListProperty<GameElement<?>> gameElementsProperty()
    {
        return sortedGameElements;
    }

    /**
     * Gibt die Liste der GameElements zurück.
     *
     * @return Die Liste der GameElements.
     */
    public ListProperty<GameElement<?>> gameElementsOpenProperty()
    {
        return gameElements;
    }
}