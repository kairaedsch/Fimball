package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * TODO
 */
public interface ElementTrigger
{
    /**
     * TODO
     * @param element
     * @param colliderId
     */
    public void activateTrigger(GameElement element, int colliderId);
}
