package sep.fimball.model.handler.light;

import sep.fimball.model.handler.HandlerGameSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Generiert eine Liste von LightChangern für die gegebene GameSession.
 */
public class LightChangerFactory
{
    /**
     * Generiert eine Liste von LightChangern für die gegebene GameSession und gibt diese zurück.
     *
     * @param gameSession Die zugehörige GameSession.
     * @return Eine Liste von LightChangern.
     */
    public static List<LightChanger> generateLightChangers(HandlerGameSession gameSession)
    {
        List<LightChanger> lightChangers = new ArrayList<>();
        lightChangers.add(new RandomLightChanger());
        lightChangers.add(new FormLightChanger(true, gameSession.gameBallProperty().get().positionProperty(), false));
        lightChangers.add(new FormLightChanger(false, gameSession.gameBallProperty().get().positionProperty(), true));
        lightChangers.add(new LineLightChanger(true, true));
        lightChangers.add(new LineLightChanger(true, false));
        lightChangers.add(new LineLightChanger(false, true));
        lightChangers.add(new LineLightChanger(false, false));
        return lightChangers;
    }
}
