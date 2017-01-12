package sep.fimball.model.game;

import javafx.animation.AnimationTimer;
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
    private AnimationTimer animationTimer;

    /**
     * Das Observable, welches genutzt wird um Observer darüber zu benachrichtigen, dass der nächste Tick der Spielschleife ausgeführt wurde.
     */
    private sep.fimball.general.util.Observable updateLoopObservable;

    /**
     * Erstellt eine neue Session mit einer PinballMachine.
     *
     * @param pinballMachine Die PinballMachine der Session.
     */
    protected Session(PinballMachine pinballMachine)
    {
        updateLoopObservable = new sep.fimball.general.util.Observable();
        this.pinballMachine = pinballMachine;

        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                loopUpdate();
            }
        };
        startUpdateLoop();
    }

    /**
     * Startet die Gameloop.
     */
    protected void startUpdateLoop()
    {
        animationTimer.start();
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopUpdateLoop()
    {
        animationTimer.stop();
    }

    /**
     * Der Updateloop der Session. Benachrichtigt am Ende den updateLoopObservable.
     */
    protected void loopUpdate()
    {
        updateLoopObservable.setChanged();
        updateLoopObservable.notifyObservers();
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
