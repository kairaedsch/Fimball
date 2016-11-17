package sep.fimball.model.trigger;

import sep.fimball.model.GameSession;
import sep.fimball.model.element.GameElement;

/**
 * TODO
 */
public class ScoreTrigger implements ElementTrigger
{
    /**
     * TODO
     */
    private GameSession session;

    /**
     * TODO
     * @param session
     */
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
