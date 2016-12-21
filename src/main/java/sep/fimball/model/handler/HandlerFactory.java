package sep.fimball.model.handler;

import sep.fimball.model.handler.light.LightChangerFactory;
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
     * @param gameSession    Die GameSession, für die die Handler generiert werden sollen.
     * @param handlerManager Der HandlerManager der übergebenen GameSession.
     * @return Eine Liste aller Handler.
     */
    public static List<Handler> generateAllHandlers(HandlerGameSession gameSession, HandlerManager handlerManager)
    {
        List<Handler> handlers = new ArrayList<>();
        handlers.add(new Handler(new HitHandler()));
        handlers.add(new Handler(new ScoreHandler(gameSession)));
        handlers.add(new Handler(new SoundHandler(SoundManager.getInstance())));
        handlers.add(new Handler(new AnimationHandler()));
        handlers.add(new Handler(new LightHandler(gameSession, LightChangerFactory.generateLightChangers(gameSession))));
        handlers.add(new Handler(new BallLostHandler(gameSession)));
        handlers.add(new Handler(new TiltHandler(gameSession, handlerManager)));

        return handlers;
    }
}
