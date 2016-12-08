package sep.fimball.model.handler.light;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.util.Duration;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.GameHandler;
import sep.fimball.model.handler.HandlerGameElement;
import sep.fimball.model.handler.HandlerWorld;
import sep.fimball.model.media.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    private List<LightChanger> lightChangers;

    private LightChanger currentLightChanger;
    private long currentLightChangerStart;

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
        KeyFrame keyFrame = new KeyFrame(Duration.millis(25), event -> changeLights());
        gameLoop.getKeyFrames().add(keyFrame);

        lightChangers = new ArrayList<>();
        lightChangers.add(new RandomLightChanger());
        lightChangers.add(new CircleLightChanger());
        lightChangers.add(new LineLightChanger(true, true));
        lightChangers.add(new LineLightChanger(true, false));
        lightChangers.add(new LineLightChanger(false, true));
        lightChangers.add(new LineLightChanger(false, false));

        currentLightChanger = lightChangers.get(0);
    }

    /**
     * Wechselt die Farbe der verwalteten Lichter zufällig auf andere verfügbaren Farben.
     */
    public void changeLights()
    {
        long now = System.currentTimeMillis();
        long delta = now - currentLightChangerStart;

        if(delta > currentLightChanger.getDuration())
        {
            currentLightChanger = lightChangers.get(new Random().nextInt(lightChangers.size()));
            currentLightChangerStart = now;
        }

        for (HandlerGameElement light : lights)
        {
            Optional<Animation> animation = light.getMediaElement().getEventMap().entrySet().iterator().next().getValue().getAnimation();
            if (animation.isPresent() && currentLightChanger.determineStatus(light.positionProperty().get(), delta))
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
