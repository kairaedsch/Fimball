package sep.fimball.model.trigger;

import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyEventType;

/**
 * Trigger, der bei User-Aktionen ausgelöst wird.
 */
public interface UserTrigger
{
    /**
     * TODO
     */
    void activateUserTrigger(KeyBinding keyBinding, KeyEventType keyEventType);
}
