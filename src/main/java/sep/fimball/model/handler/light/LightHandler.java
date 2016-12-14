package sep.fimball.model.handler.light;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.util.Duration;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.GameHandler;
import sep.fimball.model.handler.HandlerGameElement;
import sep.fimball.model.handler.HandlerGameSession;
import sep.fimball.model.media.Animation;

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

    /**
     * Die Schleife, in der die Lichter gewechselt werden.
     */
    private Timeline lightChangeLoop;

    /**
     * Die LightChangers, die benutzt werden, um die Lichter zu wechseln.
     */
    private List<LightChanger> lightChangers;

    /**
     * Der aktuelle LightChanger.
     */
    private LightChanger currentLightChanger;

    /**
     * Die Zeit, zu der der aktuelle LightChanger gestartet wurde.
     */
    private long currentLightChangerStart;

    /**
     * Erstellt einen neuen LightHandler.
     * @param gameSession Die zugehörige GameSession.
     * @param lightChangers Die LightChangers, die entscheiden, ob sich Lichter ändern.
     */
    public LightHandler(HandlerGameSession gameSession, List<LightChanger> lightChangers)
    {
        lights = new FilteredList<>(gameSession.getWorld().gameElementsProperty(), e -> e.getElementType() == BaseElementType.LIGHT);

        lightChangeLoop = new Timeline();
        lightChangeLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(DesignConfig.LIGHT_CHANGE_TICK_RATE), event -> changeLights());
        lightChangeLoop.getKeyFrames().add(keyFrame);

        this.lightChangers = lightChangers;
        currentLightChanger = lightChangers.get(0);
    }

    /**
     * Wechselt die Lichter.
     * TODO bessere Beschreibung
     */
    private void changeLights()
    {
        long now = System.currentTimeMillis();
        long delta = now - currentLightChangerStart;

        if(delta > currentLightChanger.getDuration())
        {
            currentLightChanger = lightChangers.get(new Random().nextInt(lightChangers.size()));
            currentLightChangerStart = now;
            delta = 0;
        }

        for (HandlerGameElement light : lights)
        {
            Optional<Animation> animation = light.getMediaElement().getEventMap().entrySet().iterator().next().getValue().getAnimation();
            if (animation.isPresent() && currentLightChanger.determineLightStatusWithAnimation(light.positionProperty().get(), delta))
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
            lightChangeLoop.stop();
        }
        if(gameEvent == GameEvent.BALL_SPAWNED)
        {
            lightChangeLoop.play();
        }
    }
}
