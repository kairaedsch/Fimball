package sep.fimball.model.element;

import sep.fimball.model.GameSession;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class ScoreTrigger implements Trigger
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
