package sep.fimball.model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputManager
{
    private List<KeyBinding> receivedKeys = new ArrayList<>();
    private HashMap<KeyBinding, KeyObserver> keyObserversMap = new HashMap<>();

    public void addKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding = Settings.getSingletonInstance().getKeyBindingsMap().get(keyEvent.getCode());

        for (Map.Entry<KeyBinding, KeyObserver> mapEntry : keyObserversMap.entrySet())
        {
            if (mapEntry.getKey().equals(binding))
            {
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                {
                    mapEntry.getValue().keyDown(binding);
                }
                else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                {
                    mapEntry.getValue().keyUp(binding);
                }
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