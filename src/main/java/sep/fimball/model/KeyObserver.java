package sep.fimball.model;

/**
 * Created by alexcekay on 03.11.16.
 */
public interface KeyObserver
{
    void keyDown(KeyBinding binding);

    void keyUp(KeyBinding binding);
}
