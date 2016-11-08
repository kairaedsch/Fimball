package sep.fimball.model.input;

/**
 * Die Argumente, die beim Auslösen eines Tastendruck-Events im InputManager übergeben werden.
 */
public class KeyObserverEventArgs
{
    /**
     * Status, zu dem die im InputManager festgelegte Taste gewechselt hat.
     */
    public enum KeyChangedToState
    {
        /**
         * Die Taste wurde gedrückt.
         */
        DOWN,
        /**
         * Die Taste wurde losgelassen.
         */
        UP
    }

    /**
     * Die Funktion, die dieser Taste im InputManager zugeordnet ist.
     */
    private KeyBinding binding;

    /**
     * Status, zu dem die im InputManager festgelegte Taste gewechselt hat.
     */
    private KeyChangedToState state;

    /**
     * Erstellt eine neue Instanz der Klasse.
     * @param binding
     * @param state
     */
    public KeyObserverEventArgs(KeyBinding binding, KeyChangedToState state)
    {
        this.binding = binding;
        this.state = state;
    }

    public KeyBinding getBinding()
    {
        return binding;
    }

    public KeyChangedToState getState()
    {
        return state;
    }
}
