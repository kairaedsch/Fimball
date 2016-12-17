package sep.fimball.model.input.manager;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;

import java.util.ArrayList;
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
     * Gibt den bereits existierenden InputManager oder einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return Eine Instanz von InputManager.
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
    private Map<KeyCode, EventType<KeyEvent>> oldKeyState = new HashMap<>();

    /**
     * Erzeugt einen neuen InputManager.
     */
    private InputManager()
    {
        for (KeyCode keyCode : KeyCode.values())
        {
            oldKeyState.put(keyCode, KeyEvent.KEY_RELEASED);
        }
    }

    /**
     * Wenn in einer View ein Tastendruck ausgelöst wurde, wird dieser hier weitergeleitet, um mögliche Observer zu benachrichtigen.
     *
     * @param keyEvent Das KeyEvent, welches von der View registriert wurde.
     */
    public void addKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode());
        for (Map.Entry<KeyBinding, List<KeyObserver>> mapEntry : keyObserversMap.entrySet())
        {
            if (mapEntry.getKey().equals(binding))
            {
                KeyEventArgs.KeyChangedToState state;

                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                {
                    state = KeyEventArgs.KeyChangedToState.DOWN;
                }
                else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                {
                    state = KeyEventArgs.KeyChangedToState.UP;
                }
                else
                {
                    throw new IllegalArgumentException("Invalid keyEvent!");
                }
                boolean stateSwitched = oldKeyState.get(keyEvent.getCode()) != keyEvent.getEventType();

                if (stateSwitched)
                    oldKeyState.put(keyEvent.getCode(), keyEvent.getEventType());

                mapEntry.getValue().forEach((observer) -> observer.keyEvent(new KeyEventArgs(binding, state, stateSwitched)));
            }
        }
    }

    /**
     * Registriert einen neuen Observer, der bei einem bestimmten Tastendruck benachrichtigt wird.
     *
     * @param binding  Das Binding über welches der Observer benachrichtigt werden soll
     * @param observer Der Observer, der über den Tastendruck der zu {@code} binding gehörende Taste informiert wird.
     */
    public void addListener(KeyBinding binding, KeyObserver observer)
    {
        if (observer == null)
        {
            throw new IllegalArgumentException("KeyObserver can't be null");
        }

        if (!keyObserversMap.containsKey(binding))
        {
            keyObserversMap.put(binding, new ArrayList<>());
        }
        keyObserversMap.get(binding).add(observer);
    }
}