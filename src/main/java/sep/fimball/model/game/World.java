package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerGameElement;
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
     * Vorlage, aus der bei Bedarf neue Bälle generiert werden können.
     */
    private PlacedElement ballTemplate;

    /**
     * Erzeugt eine World mit der übergebenen Liste von GameElements.
     *
     * @param elements     Liste der Elemente in der Spielwelt.
     * @param ballTemplate Vorlage für den Ball.
     */
    public World(ObservableList<GameElement> elements, PlacedElement ballTemplate)
    {
        this.gameElements = new SimpleListProperty<>(elements);
        this.ballTemplate = ballTemplate;
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
     * Generiert das Rechteck, welches das Spielfeld umschließt und den Spielfeldrand darstellt, und gibt dieses zurück.
     *
     * @return Ein Rechteck, das den Spielfeldrand darstellt.
     */
    private RectangleDouble generateWalls()
    {
        //TODO If GameElements contain the ball this method won't work anymore
        double minX = gameElements.get(0).positionProperty().get().getX();
        double maxX = gameElements.get(0).positionProperty().get().getX();
        double minY = gameElements.get(0).positionProperty().get().getY();
        double maxY = gameElements.get(0).positionProperty().get().getY();

        for (GameElement gameElement : gameElements)
        {
            double currentX = gameElement.positionProperty().get().getX();
            double currentY = gameElement.positionProperty().get().getY();

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

    public ListProperty<GameElement> gameElementsProperty()
    {
        return gameElements;
    }

    /**
     * Gibt die Vorlage, aus der bei Bedarf neue Bälle generiert werden können, zurück.
     * @return Die Vorlage, aus der bei Bedarf neue Bälle generiert werden können.
     */
    public PlacedElement getBallTemplate()
    {
        return ballTemplate;
    }

    /**
     * Setzt die Vorlage, aus der bei Bedarf neue Bälle generiert werden können.
     * @param ballTemplate Die neue Vorlage, aus der bei Bedarf neue Bälle generiert werden können.
     */
    public void setBallTemplate(PlacedElement ballTemplate)
    {
        this.ballTemplate = ballTemplate;
    }
}