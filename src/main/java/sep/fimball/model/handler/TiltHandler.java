package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;

import java.util.HashMap;
import java.util.Map;

/**
 * Der TiltHandler reagiert auf das Rütteln am Automaten.
 */
public class TiltHandler extends UserHandler
{
    /**
     * Die zugehörige HandlerGameSession.
     */
    private HandlerGameSession handlerGameSession;

    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private Map<HandlerPlayer, Integer> tiltCounters;

    /**
     * Gibt an wie oft die "Nudge-Funktion" verwendet werden kann bevor der Tilt einsetzt.
     */
    private static final int MAX_TILT_COUNTER = 5;

    /**
     * Erzeugt einen neuen TiltHandler.
     *
     * @param handlerGameSession Die zugehörige HandlerGameSession.
     */
    TiltHandler(HandlerGameSession handlerGameSession)
    {
        super(KeyBinding.NUDGE_RIGHT, KeyBinding.NUDGE_LEFT);
        this.handlerGameSession = handlerGameSession;
        this.tiltCounters = new HashMap<>();
    }

    @Override
    public void activateUserHandler(KeyEventArgs keyEventType)
    {
        if (keyEventType.getState() == KeyEventArgs.KeyChangedToState.DOWN && (keyEventType.getBinding() == KeyBinding.NUDGE_RIGHT || keyEventType.getBinding() == KeyBinding.NUDGE_LEFT) && keyEventType.isStateSwitched())
        {
            HandlerPlayer currentPlayer = handlerGameSession.getCurrentPlayer();
            if (!tiltCounters.containsKey(currentPlayer))
                tiltCounters.put(currentPlayer, 0);
            tiltCounters.put(currentPlayer, tiltCounters.get(currentPlayer) + 1);

            if (tiltCounters.get(currentPlayer) > MAX_TILT_COUNTER)
            {
                handlerGameSession.activateTilt();
                tiltCounters.put(currentPlayer, 0);
            }

            handlerGameSession.gameBallProperty().get().nudge(keyEventType.getBinding() == KeyBinding.NUDGE_LEFT);
        }
    }
}
