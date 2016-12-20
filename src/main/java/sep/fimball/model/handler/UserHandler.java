package sep.fimball.model.handler;

import sep.fimball.model.input.manager.KeyEventArgs;

/**
 * Handler, der bei User-Aktionen ausgelöst wird.
 */
public interface UserHandler extends SomeHandler
{
    /**
     * Benachrichtigt den Handler über eine Benutzeraktion.
     *
     * @param keyEventArgs Der Status des Tastendrucks, der die Aktion ausgelöst hat.
     */
    void activateUserHandler(KeyEventArgs keyEventArgs);
}
