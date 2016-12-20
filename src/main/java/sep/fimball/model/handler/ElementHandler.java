package sep.fimball.model.handler;

/**
 * Handler, der bei der Kollision von Spielelement und Ball ausgelöst wird.
 */
public interface ElementHandler extends SomeHandler
{
    /**
     * Ist eine Kollision passiert, wird der Handler aktiviert.
     *
     * @param element    Das Element, das mit dem Ball kollidiert ist.
     * @param colliderId Die ID des Colliders des Elements, das mit dem Ball kollidiert ist.
     */
    void activateElementHandler(HandlerGameElement element, int colliderId);
}
