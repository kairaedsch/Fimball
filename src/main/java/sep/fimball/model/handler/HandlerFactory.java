package sep.fimball.model.handler;

import sep.fimball.model.handler.light.LightHandler;
import sep.fimball.model.media.SoundManager;

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
        List<Handler> handlers = new ArrayList<>();

        Handler hitHandler = new Handler();
        hitHandler.setElementHandler(new HitHandler());
        handlers.add(hitHandler);

        Handler scoreHandler = new Handler();
        scoreHandler.setElementHandler(new ScoreHandler(gameSession));
        handlers.add(scoreHandler);

        Handler soundHandler = new Handler();
        soundHandler.setElementHandler(new SoundHandler(SoundManager.getInstance()));
        handlers.add(soundHandler);

        Handler animationHandler = new Handler();
        animationHandler.setElementHandler(new AnimationHandler());
        handlers.add(animationHandler);

        Handler lightHandler = new Handler();
        animationHandler.setGameHandler(new LightHandler(gameSession));
        handlers.add(lightHandler);

        Handler ballLostHandler = new Handler();
        ballLostHandler.setGameHandler(new BallLostHandler(gameSession));
        handlers.add(ballLostHandler);

        Handler nudgeHandler = new Handler();
        nudgeHandler.setGameHandler(new TiltHandler(gameSession));
        handlers.add(nudgeHandler);

        return handlers;
    }
}
