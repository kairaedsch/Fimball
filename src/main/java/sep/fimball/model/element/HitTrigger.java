package sep.fimball.model.element;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class HitTrigger implements Trigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
