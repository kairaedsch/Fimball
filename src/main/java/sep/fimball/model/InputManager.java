package sep.fimball.model;

import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class InputManager
{
    private static InputManager singletonInstance;

    public static InputManager getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new InputManager();

        return singletonInstance;
    }

    private HashMap<KeyBinding, KeyObserver> keyObserversMap = new HashMap<>();

    private InputManager()
    {

    }

    public void addKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding = Settings.getSingletonInstance().getKeyBindingsMap().get(keyEvent.getCode());

        for (Map.Entry<KeyBinding, KeyObserver> mapEntry : keyObserversMap.entrySet())
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

                mapEntry.getValue().keyEvent(new KeyObserverEventArgs(binding, state));
            }
        }
    }

    public void addListener(KeyBinding binding, KeyObserver observer)
    {
        if (observer == null)
        {
            throw new IllegalArgumentException("KeyObserver can't be null");
        }
        keyObserversMap.put(binding, observer);
    }
}