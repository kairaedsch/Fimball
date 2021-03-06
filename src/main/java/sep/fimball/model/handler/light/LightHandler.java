package sep.fimball.model.handler.light;

import javafx.animation.AnimationTimer;
import javafx.collections.transformation.FilteredList;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.handler.*;
import sep.fimball.model.media.ElementImage;

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
    private GameElementAnimationTimer lightChangeLoop;

    /**
     * Die LightChangers, die benutzt werden, um die Lichter zu wechseln und verschiedene Effekte erzeugen.
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
     *
     * @param gameSession   Die zugehörige GameSession.
     * @param lightChangers Die LightChangers, die entscheiden, wie sich Lichter ändern.
     */
    public LightHandler(HandlerGameSession gameSession, List<LightChanger> lightChangers)
    {
        lights = new FilteredList<>(gameSession.getWorld().gameElementsProperty(), e -> e.getElementType() == BaseElementType.LIGHT);

        lightChangeLoop = new GameElementAnimationTimer(new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                changeLights();
            }
        });

        this.lightChangers = lightChangers;
        currentLightChanger = lightChangers.get(0);
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        lightChangeLoop.activate(gameEvent);
    }

    /**
     * Ruft den aktuell aktiven LightChanger auf solange dieser aktiv ist. Wenn der aktuelle fertig ist wird zufällig ein anderer LightChanger gewählt.
     */
    private void changeLights()
    {
        long now = System.currentTimeMillis();
        long delta = now - currentLightChangerStart;

        if (delta > currentLightChanger.getDuration())
        {
            currentLightChanger = lightChangers.get(new Random().nextInt(lightChangers.size()));
            currentLightChangerStart = now;
            delta = 0;
        }

        for (HandlerGameElement light : lights)
        {
            Optional<ElementImage> animation = light.getMediaElement().getEventMap().entrySet().iterator().next().getValue().getAnimation();
            if (animation.isPresent() && currentLightChanger.determineLightStatusWithAnimation(light.positionProperty().get(), delta))
            {
                light.setCurrentAnimation(animation);
            }
        }
    }
}
