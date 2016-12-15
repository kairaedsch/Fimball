package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;

/**
 * Handler, der bei User-Aktionen ausgelöst wird.
 */
public abstract class UserHandler
{
    /** Erstellt einen neuen UserHandler
     * @param keyBindings Die KeyBindings, auf der der UserHandler reagieren soll.
     */
    public UserHandler(KeyBinding... keyBindings)
    {
        for (KeyBinding keyBinding : keyBindings)
        {
            InputManager.getSingletonInstance().addListener(keyBinding, (keyObserver) -> activateUserHandler(keyBinding, keyObserver.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN ? KeyEventType.DOWN : KeyEventType.UP));
        }
    }

    /**
     * Benachrichtigt den Handler über eine Benutzeraktion.
     *
     * @param keyBinding   Die Aktion, die vom Nutzer ausgelöst wurde.
     * @param keyEventType Der Status des Tastendrucks, der die Aktion ausgelöst hat.
     */
    abstract void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType);
}
