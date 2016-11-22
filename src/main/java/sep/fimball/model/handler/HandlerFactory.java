package sep.fimball.model.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Generiert eine Liste aller Handler für die gegebene GameSession.
 */
public class HandlerFactory
{
    /**
     * Generiert eine Liste aller Handler für die gegebene GameSession und gibt diese zurück.
     *
     * @param gameSession Die GameSession, für die die Handler generiert werden sollen.
     * @return Eine Liste aller Handler.
     */
    public static List<Handler> generateAllHandlers(HandlerGameSession gameSession)
    {
        List<Handler> triggers = new ArrayList<>();

        Handler hitTrigger = new Handler();
        hitTrigger.setElementHandler(new HitHandler());
        triggers.add(hitTrigger);

        Handler scoreTrigger = new Handler();
        scoreTrigger.setElementHandler(new ScoreHandler(gameSession));
        triggers.add(scoreTrigger);

        Handler soundTrigger = new Handler();
        soundTrigger.setElementHandler(new SoundHandler());
        triggers.add(soundTrigger);

        Handler animationTrigger = new Handler();
        animationTrigger.setElementHandler(new AnimationHandler());
        triggers.add(animationTrigger);

        Handler lightTrigger = new Handler();
        animationTrigger.setGameHandler(new LightHandler(gameSession.getWorld()));
        triggers.add(lightTrigger);

        return triggers;
    }
}
