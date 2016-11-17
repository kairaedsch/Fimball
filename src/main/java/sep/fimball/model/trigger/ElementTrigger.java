package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Created by alexcekay on 15.11.16.
 */
public interface ElementTrigger
{
    public void activateTrigger(GameElement element, int colliderId);
}
