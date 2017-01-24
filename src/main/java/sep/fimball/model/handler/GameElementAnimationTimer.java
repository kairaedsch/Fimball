package sep.fimball.model.handler;

import javafx.animation.AnimationTimer;

/**
 * AnimationTimer, der über GameEvents aktiviert und deaktiviert werden kann.
 */
public class GameElementAnimationTimer
{
    /**
     * Der AnimationTimer zum Abspielen der Animation.
     */
    private AnimationTimer timer;

    /**
     * Erstellt eine neue Instanz, die einen AnimationTimer überwacht.
     * @param timer Der überwachte AnimationTimer.
     */
    public GameElementAnimationTimer(AnimationTimer timer)
    {
        this.timer = timer;
    }

    /**
     * Startet/Stoppt den AnimationTimer, falls gameEvent START/STOP ist.
     * @param gameEvent Das Event zum auslösen des AnimationTimers.
     */
    public void activate(GameEvent gameEvent)
    {
        switch (gameEvent)
        {
            case START:
                timer.start();
                break;
            case PAUSE:
                timer.stop();
                break;
        }
    }
}
