package sep.fimball.model.element;

import sep.fimball.model.GameSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaira on 16.11.2016.
 */
public class TriggerFactory
{
    public static List<Trigger> generateAllTriggers(GameSession gameSession)
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

        return triggers;
    }
}
