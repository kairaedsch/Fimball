package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.List;

public class World
{
    private final double TIMELINE_TICK = 1 / 60;
    private SimpleListProperty<GameElement> worldElements;
    private Timeline gameLoop;
    private KeyFrame keyFrame;

    /**
	 *
	 * @param worldElements
	 */
	public World(ObservableList<GameElement> worldElements)
    {
        this.worldElements = new SimpleListProperty<>(worldElements);
    }

    public void startTimeline()
    {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        keyFrame = new KeyFrame(Duration.seconds(TIMELINE_TICK), (event -> {

        }));
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }


    public void stopTimeline()
    {
        gameLoop.stop();
    }

    /**
	 *
	 * @param obj
	 */
	public void addWorldElement(GameElement obj)
    {
        if (worldElements.contains(obj))
            throw new IllegalArgumentException("Added same object twice!");

        worldElements.add(obj);
    }

    public SimpleListProperty<GameElement> getWorldElements()
    {
        return worldElements;
    }

    //Called by a timeline created in this class, update all gameobjects
    //is NOT the physics loop
    private void updateWorld()
    {
        throw new UnsupportedOperationException();
    }
}