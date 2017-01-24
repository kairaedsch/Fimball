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
     * @param elementHandlerArgs Die Argumente die bei einer Kollision an den ElementHandler übergeben werden.
     */
    void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs);
}
