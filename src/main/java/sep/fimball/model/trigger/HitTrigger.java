package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class HitTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}