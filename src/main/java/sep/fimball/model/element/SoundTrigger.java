package sep.fimball.model.element;

import sep.fimball.model.GameSession;
import sep.fimball.model.SoundManager;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class SoundTrigger implements Trigger
{
    private GameSession session;

    public SoundTrigger(GameSession session)
    {
        this.session = session;
    }

    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        // TODO SoundManager.getSingletonInstance().playFxClip(~);
    }
}
