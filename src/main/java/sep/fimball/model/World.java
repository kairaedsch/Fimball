package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class World
{
    private final double TIMELINE_TICK = 1 / 60;
    private List<GameElement> worldElements;
    private Timeline gameLoop;
    private KeyFrame keyFrame;

    /**
	 *
	 * @param worldElements
	 */
	public World(List<GameElement> worldElements)
    {
        this.worldElements = worldElements;
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

    public List<GameElement> getWorldElements()
    {
        return worldElements; // TODO immutable?
    }

    //Called by a timeline created in this class, update all gameobjects
    //is NOT the physics loop
    private void updateWorld()
    {
        throw new UnsupportedOperationException();
    }
}