package sep.fimball.model.input.manager;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Kontrolliert die Eingaben, die der Spieler über die Tastatur auslöst.
 */
public class KeyEventConverter
{
    /**
     * Speichert für die Tasten welchen Status (Losgelassen/Gedrückt) sie zuletzt hatten.
     */
    private Map<KeyCode, EventType<KeyEvent>> oldKeyState;

    /**
     * Erzeugt einen neuen InputManager dabei wird der Status aller Tasten auf die Default Value RELEASED gesetzt.
     */
    public KeyEventConverter()
    {
        oldKeyState = new HashMap<>();
        for (KeyCode keyCode : KeyCode.values())
        {
            oldKeyState.put(keyCode, KeyEvent.KEY_RELEASED);
        }
    }

    /**
     * Wandelt ein KeyEvent in ein KeyEventArgs um.
     *
     * @param keyEvent Das KeyEvent, welches umgewandelt wird.
     * @return Das umgewandelte KeyEvent.
     */
    public Optional<KeyEventArgs> triggerKeyEvent(KeyEvent keyEvent)
    {
        if (Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode()).isPresent())
        {
            KeyBinding binding = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode()).get();
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
                new IllegalArgumentException("Invalid keyEvent: " + keyEvent.getEventType()).printStackTrace();
                return Optional.empty();
            }

            boolean stateSwitched = oldKeyState.get(keyEvent.getCode()) != keyEvent.getEventType();

            if (stateSwitched)
            {
                oldKeyState.put(keyEvent.getCode(), keyEvent.getEventType());
            }

            return Optional.of(new KeyEventArgs(binding, state, stateSwitched));
        }
        return Optional.empty();
    }
}