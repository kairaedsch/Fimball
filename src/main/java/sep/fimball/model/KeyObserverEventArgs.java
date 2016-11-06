package sep.fimball.model;

/**
 * Created by theasuro on 06.11.16.
 */
public class KeyObserverEventArgs
{
    public enum KeyChangedToState
    {
        DOWN,
        UP
    }

    private KeyBinding binding;
    private KeyChangedToState state;

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
