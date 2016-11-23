package sep.fimball.model.handler;

import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyEventType;

/**
 * Handler, der bei User-Aktionen ausgelöst wird.
 */
public interface UserHandler
{
    /**
     * TODO
     * @param keyBinding
     * @param keyEventType
     */
    void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType);
}
