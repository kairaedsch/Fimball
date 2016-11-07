package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import sep.fimball.model.blueprint.PlacedElement;
import sep.fimball.model.blueprint.PlacedElementList;

import java.util.Observable;
import java.util.Observer;

public class World
{
    private final double TIMELINE_TICK = 1 / 60D;
    private ListProperty<GameElement> gameElements;
    private ListProperty<Flipper> flipperListProperty;
    private ListProperty<Plunger> plungerListProperty;
    private ObjectProperty<Ball> ballProperty;
    private Timeline gameLoop;
    private KeyFrame keyFrame;
    private Observable observable;

	public World(PlacedElementList elementList)
    {
        observable = new Observable();
        gameElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        flipperListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        plungerListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        ballProperty = new SimpleObjectProperty<>();

        for (PlacedElement pe : elementList.elementsProperty().get().values())
            //TODO check if PlacedElement is Flipper/Plunger or ball and assign
            //TODO if a ball is already set and another one gets added -> unknown behaviour
            addPlacedElement(pe);
    }

    public void notifyToRedraw(Observer observer)
    {
        observable.addObserver(observer);
    }

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

    public void stopTimeline()
    {
        gameLoop.stop();
    }

    public void addPlacedElement(PlacedElement element)
    {
        gameElements.add(new GameElement(element));
        //TODO check if PlacedElement is Flipper/Plunger or ball and assign
        //TODO if a ball is already set and another one gets added -> unknown behaviour
    }

    public ReadOnlyListProperty<GameElement> getGameElements()
    {
        return gameElements;
    }

    public ReadOnlyListProperty<Flipper> getFlipperListProperty()
    {
        return flipperListProperty;
    }

    public ReadOnlyListProperty<Plunger> getPlungerListProperty()
    {
        return plungerListProperty;
    }

    public ReadOnlyObjectProperty<Ball> getBallProperty()
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