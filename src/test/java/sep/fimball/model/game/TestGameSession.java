package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Diese Klasse abstrahiert von viewspezifischen Eigenschaften einer GameSession wie zum Beispiel Animationen, sodass .
 */
public class TestGameSession extends GameSession
{
    private Timer timer;

    /**
     * Erstellt ein neues Spiel sowie die dazugehörige Physik im durch {@code machineBlueprint} angegebenen Automaten und mit den durch {@code playerNames} angegebenen Spielern.
     *
     * @param machineBlueprint Der Bauplan des Automaten.
     * @param playerNames Die Namen der Spieler.
     */
    public TestGameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        super(machineBlueprint, playerNames);
    }

    /**
     * Startet einen {@link Timer}, der regelmäßig die Ergebnisse des Physik-Threads ausliest und sie anwendet.
     */
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

    /**
     * Stoppt den Timer, der die Ergebnisse der Physik auf die Spielelemente anwendet.
     */
    @Override
    public void stopGameLoop()
    {
        timer.cancel();
        timer.purge();
    }
}
