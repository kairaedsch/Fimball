package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Duration;
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
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    private final double TIMELINE_TICK = 1 / 60D;

    /**
     * Liste der Elemente in der Spielwelt.
     */
    private ListProperty<GameElement> gameElements;

    /**
     * Die Kugel welche gesondert gespeichert wird da häufig auf sie zugegriffen wird
     */
    private ObjectProperty<Ball> ballProperty;

    /**
     * Die Schleife, die die Spielwelt aktualisiert.
     */
    private Timeline gameLoop;

    /**
     * Speichert welche Aktion bei jedem Schritt der Schleife ausgeführt wird.
     */
    private KeyFrame keyFrame;

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
     * Startet die Gameloop.
     */
    public void startTimeline()
    {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        keyFrame = new KeyFrame(Duration.seconds(TIMELINE_TICK), (event ->
        {
            // TODO update GameElements
        }));
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopTimeline()
    {
        gameLoop.stop();
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
}