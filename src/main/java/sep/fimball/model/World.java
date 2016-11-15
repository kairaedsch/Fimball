package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.PlacedElement;
import sep.fimball.model.blueprint.PlacedElementList;
import sep.fimball.model.element.Ball;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.element.GameElementList;

import java.util.Observable;
import java.util.Observer;

/**
 * Eine World stellt die Spielwelt eines Automaten dar.
 */
public class World
{
    /**
     * Liste der Elemente in der Spielwelt.
     */
    private ListProperty<GameElement> gameElements;

    /**
     * Die Kugel welche gesondert gespeichert wird da häufig auf sie zugegriffen wird
     */
    private ObjectProperty<Ball> ballProperty;
    
    /**
     * Erzeugt eine World mit der übergebenen Liste von PlacedElements.
     * @param elementList
     */
	public World(PlacedElementList elementList)
    {
        gameElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ballProperty = new SimpleObjectProperty<>();

        for (PlacedElement pe : elementList.elementsProperty().get().values())
            //TODO check if PlacedElement is Flipper/Plunger or ball and assign
            //TODO if a ball is already set and another one gets added -> unknown behaviour
            addPlacedElement(pe);

        // TODO generate walls around playfield
    }

    /**
     * Fügt das gegebene Element in die Spielwelt ein.
     * @param element
     */
    public void addPlacedElement(PlacedElement element)
    {
        gameElements.add(new GameElement(element));
        //TODO check if PlacedElement is Flipper/Plunger or ball and assign
        //TODO if a ball is already set and another one gets added -> unknown behaviour
    }

    public void bindWorldToPlacedElementList(PlacedElementList placedElementList)
    {
        ListPropertyConverter.bindAndConvertMap(gameElements, placedElementList.elementsProperty(), ((placedElementKey, placedElementValue) ->
                new GameElement(placedElementValue)));
    }

    public ReadOnlyListProperty<GameElement> gameElementsProperty()
    {
        return gameElements;
    }

    public GameElementList getGameElements()
    {
        return new GameElementList(gameElements.get().filtered((elem) -> elem != ballProperty.get()), ballProperty.get());
    }

    public ReadOnlyObjectProperty<Ball> ballProperty()
    {
        return ballProperty;
    }

    //Called by a timeline created in this class, update all gameobjects
    //is NOT the physics loop
    private void updateWorld()
    {
        throw new UnsupportedOperationException();
    }

    private RectangleDouble generateWalls()
    {
        //TODO If GameElements contain the ball this method won't work anymore
        double minX = gameElements.get(0).getPosition().getX();
        double maxX = gameElements.get(0).getPosition().getX();
        double minY = gameElements.get(0).getPosition().getY();
        double maxY = gameElements.get(0).getPosition().getY();

        for (GameElement gameElement : gameElements)
        {
            double currentX = gameElement.getPosition().getX();
            double currentY = gameElement.getPosition().getY();

            if (currentX < minX)
            {
                minX = currentX;
            }
            if (currentX > maxX)
            {
                maxX = currentX;
            }
            if (currentY < minY)
            {
                minY = currentY;
            }
            if (currentY > maxY)
            {
                maxY = currentY;
            }
        }
        Vector2 rectOrigin = new Vector2(minX, minY);
        double width = Math.abs(maxX - minX);
        double height = Math.abs(maxY - minY);
        return new RectangleDouble(rectOrigin, width, height);
    }
}