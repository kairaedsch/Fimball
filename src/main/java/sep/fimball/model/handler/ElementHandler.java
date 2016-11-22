package sep.fimball.model.handler;

import sep.fimball.model.game.GameElement;

/**
 * Handler, der bei der Kollision von Spielelement und Ball ausgelöst wird.
 */
public interface ElementHandler
{
    /**
     * Ist eine Kollision passiert, wird der Handler aktiviert.
     *
     * @param element    Das Element, das mit dem Ball kollidiert ist.
     * @param colliderId Die ID des Colliders des Elements, das mit dem Ball kollidiert ist.
     */
     void activateElementHandler(GameElement element, int colliderId);
}