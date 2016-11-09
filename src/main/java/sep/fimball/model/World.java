package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import sep.fimball.model.blueprint.PlacedElement;
import sep.fimball.model.blueprint.PlacedElementList;
import sep.fimball.model.element.Ball;
import sep.fimball.model.element.GameElement;

import java.util.Observable;
import java.util.Observer;

/**
 * Eine World stellt die Spielwelt eines Automaten dar.
 */
public class World
{
    /**
     * Die Wiederholungsrate, mit der sich die Game Loop aktualisiert.
     */
    private final double TIMELINE_TICK = 1 / 60D;

    /**
     * Liste der Elemente in der Spielwelt.
     */
    private ListProperty<GameElement> gameElements;

    /**
     *
     */
    private ObjectProperty<Ball> ballProperty;

    /**
     * Die Schleife, die die Spielwelt aktualisiert.
     */
    private Timeline gameLoop;

    private KeyFrame keyFrame;

    private Observable observable;

    /**
     * Erzeugt eine World mit den übergebenen GameElemente.
     * @param elementList
     */
	public World(PlacedElementList elementList)
    {
        observable = new Observable();
        gameElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ballProperty = new SimpleObjectProperty<>();

        for (PlacedElement pe : elementList.elementsProperty().get().values())
            //TODO check if PlacedElement is Flipper/Plunger or ball and assign
            //TODO if a ball is already set and another one gets added -> unknown behaviour
            addPlacedElement(pe);

        // TODO generate walls around playfield
    }

    public void notifyToRedraw(Observer observer)
    {
        observable.addObserver(observer);
    }

    /**
     * Startet die Gameloop.
     */
    public void startTimeline()
    {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        keyFrame = new KeyFrame(Duration.seconds(TIMELINE_TICK), (event -> {
            observable.hasChanged();
            observable.notifyObservers();
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

    public ReadOnlyListProperty<GameElement> gameElementsProperty()
    {
        return gameElements;
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