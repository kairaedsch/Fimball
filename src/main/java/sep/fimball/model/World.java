package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import sep.fimball.model.blueprint.PlacedElement;
import sep.fimball.model.blueprint.PlacedElementList;

public class World
{
    private final double TIMELINE_TICK = 1 / 60D;
    private ListProperty<GameElement> worldElements;
    private Timeline gameLoop;
    private KeyFrame keyFrame;

	public World(PlacedElementList elementList)
    {
        worldElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (PlacedElement pe : elementList.elementsProperty().get().values())
            worldElements.add(new GameElement(pe));
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

    public ReadOnlyListProperty<GameElement> getWorldElements()
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