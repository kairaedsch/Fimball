package sep.fimball.model.trigger;

import java.util.ArrayList;
import java.util.List;

/**
 * Generiert eine Liste aller Trigger für die gegebene GameSession.
 */
public class TriggerFactory
{
    /**
     * Generiert eine Liste aller Trigger für die gegebene GameSession und gibt diese zurück.
     *
     * @param gameSession Die GameSession, für die die Trigger generiert werden sollen.
     * @return Eine Liste aller Trigger.
     */
    public static List<Trigger> generateAllTriggers(TriggerGameSession gameSession)
    {
        List<Trigger> triggers = new ArrayList<>();

        Trigger hitTrigger = new Trigger();
        hitTrigger.setElementTrigger(new HitTrigger());
        triggers.add(hitTrigger);

        Trigger scoreTrigger = new Trigger();
        scoreTrigger.setElementTrigger(new ScoreTrigger(gameSession));
        triggers.add(scoreTrigger);

        Trigger soundTrigger = new Trigger();
        soundTrigger.setElementTrigger(new SoundTrigger());
        triggers.add(soundTrigger);

        Trigger animationTrigger = new Trigger();
        animationTrigger.setElementTrigger(new AnimationTrigger());
        triggers.add(animationTrigger);

        Trigger lightTrigger = new Trigger();
        animationTrigger.setGameTrigger(new LightTrigger(gameSession.getWorld()));
        triggers.add(lightTrigger);

        return triggers;
    }
}
