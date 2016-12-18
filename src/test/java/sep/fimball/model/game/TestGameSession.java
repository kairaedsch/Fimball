package sep.fimball.model.game;

import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Diese Klasse abstrahiert von View-spezifischen Eigenschaften einer GameSession wie zum Beispiel Animationen, sodass Tests keine JavaFX spezifische Eigenschaften beachten müssen.
 */
public class TestGameSession extends GameSession
{
    /**
     * Der Timer welcher zur Erzeugung der Test Spielschleife genutzt wird.
     */
    private Timer timer;

    /**
     * Erstellt ein neues Spiel sowie die dazugehörige Physik im durch {@code pinballMachine} angegebenen Automaten und mit den durch {@code playerNames} angegebenen Spielern.
     *
     * @param pinballMachine Der Bauplan des Automaten.
     * @param playerNames    Die Namen der Spieler.
     */
    public TestGameSession(PinballMachine pinballMachine, String[] playerNames)
    {
        super(pinballMachine, playerNames, false);
    }

    /**
     * Startet einen {@link Timer}, der regelmäßig die Ergebnisse des Physik-Threads ausliest und sie anwendet.
     */
    @Override
    public void startUpdateLoop()
    {
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                loopUpdate();
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long) (Config.UPDATE_LOOP_TICK * 1000.0));
    }

    /**
     * Stoppt den Timer, der die Ergebnisse der Physik auf die Spielelemente anwendet.
     */
    @Override
    public void stopUpdateLoop()
    {
        timer.cancel();
        timer.purge();
    }
}
