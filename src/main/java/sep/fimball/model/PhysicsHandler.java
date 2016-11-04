package sep.fimball.model;

import java.util.Timer;
import java.util.TimerTask;

public class PhysicsHandler
{
    private final int TIMER_DELAY = 0;
    private final int TICK_RATE = 1000 / 60;
    private World world;
    private Timer physicTimer;
    private TimerTask timerTask;

    public PhysicsHandler(World world)
    {
        this.world = world;
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {

            }
        };
    }

    public void startTicking()
    {
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(timerTask, TIMER_DELAY, TICK_RATE);
    }

    public void stopTicking()
    {
        if (physicTimer != null)
        {
            physicTimer.cancel();
            physicTimer.purge();
        }
    }
}