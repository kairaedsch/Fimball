package sep.fimball.model.input.manager;


import sep.fimball.model.input.data.KeyBinding;

/**
 * Die Argumente, die beim Auslösen eines Tastendruck-Events im InputManager übergeben werden.
 */
public class KeyEventArgs
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

    private boolean stateSwitched;

    /**
     * Erstellt eine neue Instanz der Klasse.
     *
     * @param binding Die Funktion, die ausgeführt werden soll.
     * @param state   Status, zu dem die im InputManager festgelegte Taste gewechselt hat.
     */
    public KeyEventArgs(KeyBinding binding, KeyChangedToState state, boolean stateSwitched)
    {
        this.binding = binding;
        this.state = state;
        this.stateSwitched = stateSwitched;
    }

    /**
     * Gibt die Funktion, die ausgeführt werden soll, zurück.
     *
     * @return Die Funktion, die ausgeführt werden soll.
     */
    public KeyBinding getBinding()
    {
        return binding;
    }

    /**
     * Gibt den Status, zu dem die im InputManager festgelegte Taste gewechselt hat, zurück.
     *
     * @return Der Status, zu dem die im InputManager festgelegte Taste gewechselt hat.
     */
    public KeyChangedToState getState()
    {
        return state;
    }

    public boolean isStateSwitched()
    {
        return stateSwitched;
    }
}
