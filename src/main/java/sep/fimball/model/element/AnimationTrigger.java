package sep.fimball.model.element;

import sep.fimball.model.GameSession;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class AnimationTrigger implements Trigger
{
    private GameSession session;

    public AnimationTrigger(GameSession session)
    {
        this.session = session;
    }

    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        // TODO
    }
}
