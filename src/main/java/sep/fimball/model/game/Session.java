package sep.fimball.model.game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import java.util.Observer;

/**
 * Eine Session für einen Flipperautomaten. Wird von der GameSession und EditorSession erweitert.
 */
public abstract class Session
{
    /**
     * Die Spielwelt des Flipperautomaten auf dem in der aktuellen Spielpartie gespielt wird.
     */
    protected World world;

    /**
     * Der aktuelle Flipperautomat.
     */
    protected PinballMachine pinballMachine;

    /**
     * Die Schleife, die die Spielwelt aktualisiert.
     */
    PauseTransition resetTransition;

    boolean looping;

    /**
     * Das Observable, welches genutzt wird um Observer darüber zu benachrichtigen, dass der nächste Tick der Spielschleife ausgeführt wurde.
     */
    private sep.fimball.general.util.Observable updateLoopObservable;

    /**
     * Erstellt eine neue Session mit einer PinballMachine.
     *
     * @param pinballMachine Die PinballMachine der Session.
     */
    public Session(PinballMachine pinballMachine)
    {
        updateLoopObservable = new sep.fimball.general.util.Observable();
        this.pinballMachine = pinballMachine;

        resetTransition = new PauseTransition(Duration.seconds(Config.UPDATE_LOOP_TICKRATE));
        resetTransition.setOnFinished(e -> beforeloopUpdate());

        looping = false;
        startUpdateLoop();
    }

    /**
     * Startet die Gameloop.
     */
    public void startUpdateLoop()
    {
        if (!looping)
        {
            looping = true;
            resetTransition.play();
        }
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopUpdateLoop()
    {
        looping = false;
        resetTransition.stop();
    }

    /**
     * Wird immer vor dem Updateloop der Session aufgerufen.
     */
    private void beforeloopUpdate()
    {
        long start = System.currentTimeMillis();

        loopUpdate();

        updateLoopObservable.setChanged();
        updateLoopObservable.notifyObservers();

        if (looping)
        {
            long dif = System.currentTimeMillis() - start;
            double delay = Math.max(1, Config.UPDATE_LOOP_TICKRATE * 1000 - dif);
            resetTransition.setDelay(Duration.millis(delay));
            resetTransition.play();
        }
    }

    /**
     * Der Updateloop der Session. Benachrichtigt am Ende den updateLoopObservable.
     */
    protected void loopUpdate()
    {

    }

    /**
     * Gibt die zu dieser GameSession gehörende World zurück.
     *
     * @return Die zu dieser GameSession gehörende World.
     */
    public World getWorld()
    {
        return world;
    }

    /**
     * Gibt den zur GameSession gehörenden Flipperautomaten zurück.
     *
     * @return Der zur GameSession gehörende Flipperautomat.
     */
    public PinballMachine getPinballMachine()
    {
        return pinballMachine;
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code updateLoopObservable} hinzu, der benachrichtigt wird, wenn das Update der GameLoop fertig ist.
     *
     * @param gameLoopObserver Der Observer, der hinzugefügt werden soll.
     */
    public void addGameLoopObserver(Observer gameLoopObserver)
    {
        updateLoopObservable.addObserver(gameLoopObserver);
    }
}
