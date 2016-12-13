package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerWorld;

/**
 * Eine World stellt die Spielwelt eines Automaten dar.
 */
public class World implements HandlerWorld
{
    /**
     * Liste der Elemente in der Spielwelt.
     */
    private ListProperty<GameElement> gameElements;

    /**
     * Erzeugt eine World mit der übergebenen Liste von GameElements.
     *
     * @param elements     Liste der Elemente in der Spielwelt.
     */
    public World(ObservableList<GameElement> elements)
    {
        this.gameElements = new SimpleListProperty<>(elements);
    }

    /**
     * Gibt die Liste der GameElements zurück.
     *
     * @return Die Liste der GameElements.
     */
    public ListProperty<GameElement> gameElementsProperty()
    {
        return gameElements;
    }
}