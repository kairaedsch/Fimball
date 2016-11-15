package sep.fimball.model.element;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class ScoreTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        //session.getCurrentPlayer().pointsProperty().set(session.getCurrentPlayer().getPoints() + element.getPointReward());
    }
}
