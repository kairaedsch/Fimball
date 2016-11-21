package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Trigger, der bei der Kollision von Spielelement und Ball ausgelöst wird.
 */
public interface ElementTrigger
{
    /**
     * Ist eine Kollision passiert, wird der Trigger aktiviert.
     *
     * @param element    Das Element, das mit dem Ball kollidiert ist.
     * @param colliderId Die ID des Colliders des Elements, das mit dem Ball kollidiert ist.
     */
    public void activateElementTrigger(GameElement element, int colliderId);
}
