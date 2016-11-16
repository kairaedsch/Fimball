package sep.fimball.model.element;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class AnimationTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderId)
    {
        element.setCurrentAnimation(element.getPlacedElement().getBaseElement().getMedia().getEventMap().get(colliderId).getAnimation());
    }
}
