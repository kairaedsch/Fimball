package sep.fimball.model.handler;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und den HitCount eines Elements erhöht.
 */
public class HitHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
