package sep.fimball.model.input;

import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kontrolliert die Eingaben, die der Spieler über die Tastatur auslöst.
 */
public class InputManager
{
    /**
     * Referenz auf die einzige Instanz des Managers. Teil des Singleton-Patterns. Das Pattern wird benötigt, da es keinen Sinn macht mehr als einen InputManager zu haben, und dieser von vielen Klassen des Models zugänglich sein muss.
     */
    private static InputManager singletonInstance;

    /**
     * Gibt den bereits existierenden Inputmanager oder einen neu angelegten zurück, falls noch keiner existiert
     * @return Eine Instanz von Inputmanager.
     */
    public static InputManager getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new InputManager();

        return singletonInstance;
    }

    /**
     * Weist jedem KeyBinding, welches bei einem in den Settings ausgewählten Tastendruck ausgelöst wird, eine Liste von Observern zu, welche bei diesem Tastendruck benachrichtigt werden.
     */
    private Map<KeyBinding, List<KeyObserver>> keyObserversMap = new HashMap<>();

    /**
     * Erzeugt einen neuen InputManager.
     */
    private InputManager()
    {

    }

    /**
     * Wenn in einer View ein Tastendruck ausgelöst wurde, wird dieser hier weitergeleitet, um mögliche Observer zu benachrichtigen.
     * @param keyEvent Das KeyEvent, das beobachet werden soll.
     */
    public void addKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding =  null;//Settings.getSingletonInstance().getKeyBindingsMap().get(keyEvent.getCode());

        for (Map.Entry<KeyBinding, List<KeyObserver>> mapEntry : keyObserversMap.entrySet())
        {
            if (mapEntry.getKey().equals(binding))
            {
                KeyObserverEventArgs.KeyChangedToState state;

                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                    state = KeyObserverEventArgs.KeyChangedToState.DOWN;
                else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                    state = KeyObserverEventArgs.KeyChangedToState.UP;
                else
                    throw new IllegalArgumentException("Invalid keyEvent!");

                mapEntry.getValue().forEach((observer) -> observer.keyEvent(new KeyObserverEventArgs(binding, state)));
            }
        }
    }

    /**
     * Registriert einen neuen Observer, der bei einem bestimmten Tastendruck benachrichtigt wird.
     * @param binding Die Funktion, die beobachtet wird.
     * @param observer Der Observer, der über den Tastendruck der zu {@code} binding gehörende Taste informiert wird.
     */
    public void addListener(KeyBinding binding, KeyObserver observer)
    {
        if (observer == null)
        {
            throw new IllegalArgumentException("KeyObserver can't be null");
        }
        // TODO actually add to that ugly thing
    }
}