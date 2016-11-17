package sep.fimball.model.trigger;

import sep.fimball.model.GameSession;
import sep.fimball.model.element.GameElement;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class ScoreTrigger implements ElementTrigger
{
    private GameSession session;

    public ScoreTrigger(GameSession session)
    {
        this.session = session;
    }

    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        session.getCurrentPlayer().pointsProperty().set(session.getCurrentPlayer().getPoints() + element.getPointReward());
    }
}
