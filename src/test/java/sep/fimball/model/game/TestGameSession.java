package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by theasuro on 23.11.16.
 */
public class TestGameSession extends GameSession
{
    private Timer timer;

    public TestGameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        super(machineBlueprint, playerNames);
    }

    @Override
    public void startGameLoop()
    {
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                gameLoopUpdate();
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long)(GAMELOOP_TICK * 1000.0));
    }

    @Override
    public void stopGameLoop()
    {
        timer.cancel();
        timer.purge();
    }
}
