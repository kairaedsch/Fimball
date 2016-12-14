package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;

/**
 * Handler, der bei User-Aktionen ausgelöst wird.
 */
public interface UserHandler
{
    /**
     * Benachrichtigt den Handler über eine Benutzeraktion.
     *
     * @param keyBinding   Die Aktion, die vom Nutzer ausgelöst wurde.
     * @param keyEventType Der Status des Tastendrucks, der die Aktion ausgelöst hat.
     */
    void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType);
}
