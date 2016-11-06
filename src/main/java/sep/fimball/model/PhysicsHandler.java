package sep.fimball.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PhysicsHandler
{
    private final int TIMER_DELAY = 0;
    private final int TICK_RATE = 1000 / 60;
    private World world;
    private Timer physicTimer;
    private TimerTask timerTask;
    private List<KeyObserverEventArgs> bufferedKeyEvents;

    public PhysicsHandler(World world)
    {
        this.world = world;
        bufferedKeyEvents = new ArrayList<>();

        InputManager inputManager = InputManager.getSingletonInstance();
        inputManager.addListener(KeyBinding.LEFT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.RIGHT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_LEFT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_RIGHT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.PAUSE, args -> bufferedKeyEvents.add(args));

        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                // TODO check bufferedKeyEvents
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