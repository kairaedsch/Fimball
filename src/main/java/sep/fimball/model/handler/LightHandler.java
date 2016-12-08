package sep.fimball.model.handler;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.util.Duration;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;

import java.util.Optional;

/**
 * Verwaltet die im Spielfeld platzierten Lichter.
 */
public class LightHandler implements GameHandler
{
    /**
     * Die Lichter, die verwaltet werden.
     */
    private FilteredList<? extends HandlerGameElement> lights;

    private Timeline gameLoop;

    /**
     * Erstellt einen neuen LightHandler.
     *
     * @param world Aktuelle World.
     */
    public LightHandler(HandlerWorld world)
    {
        lights = new FilteredList<>(world.gameElementsProperty(), e -> e.getElementType() == BaseElementType.LIGHT);

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> changeLights());
        gameLoop.getKeyFrames().add(keyFrame);
    }

    /**
     * Wechselt die Farbe der verwalteten Lichter zufällig auf andere verfügbaren Farben.
     */
    public void changeLights()
    {
        for (HandlerGameElement light : lights)
        {
            Optional<Animation> animation = light.getMediaElement().getEventMap().entrySet().iterator().next().getValue().getAnimation();
            if (animation.isPresent() && Math.random() > 0.5)
            {
                light.setCurrentAnimation(animation);
            }

        }
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        if(gameEvent == GameEvent.BALL_LOST)
        {
            gameLoop.stop();
        }
        if(gameEvent == GameEvent.BALL_SPAWNED)
        {
            gameLoop.play();
        }
    }
}
