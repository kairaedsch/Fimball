package sep.fimball.model.handler;

import javafx.animation.AnimationTimer;

/**
 * Created by TheAsuro on 24.01.2017.
 */
public class GameElementAnimation
{
    private AnimationTimer timer;

    public GameElementAnimation(AnimationTimer timer)
    {
        this.timer = timer;
    }

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
