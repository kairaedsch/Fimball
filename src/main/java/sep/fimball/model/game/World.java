package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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

    private ListProperty<GameElement> sortedGameElements;

    /**
     * Vorlage, aus der bei Bedarf neue Bälle generiert werden können.
     */
    private PlacedElement ballTemplate;

    /**
     * Erzeugt eine World mit der übergebenen Liste von GameElements.
     *
     * @param elements     Liste der Elemente in der Spielwelt.
     */
    public World(ObservableList<GameElement> elements)
    {
        this.ballTemplate = ballTemplate;

        gameElements = new SimpleListProperty<>(elements);
        sortedGameElements =  new SimpleListProperty<>(new SortedList<>(gameElements, GameElement::compare));
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
     * Gibt die Liste der GameElements zurück.
     *
     * @return Die Liste der GameElements.
     */
    public ReadOnlyListProperty<GameElement> gameElementsProperty()
    {
        return sortedGameElements;
    }

    /**
     * Gibt die Liste der GameElements zurück.
     *
     * @return Die Liste der GameElements.
     */
    public ListProperty<GameElement> gameElementsAidsAidsAidsAidsProperty()
    {
        return gameElements;
    }
}