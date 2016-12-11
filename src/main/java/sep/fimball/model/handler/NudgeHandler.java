package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;

import java.util.HashMap;
import java.util.Map;

/**
 * Der NudgeHandler reagiert auf das Rütteln am Automaten.
 */
public class NudgeHandler implements UserHandler
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
     * Erzeugt einen neuen NudgeHandler.
     * @param handlerGameSession Die zugehörige HandlerGameSession.
     */
    public NudgeHandler(HandlerGameSession handlerGameSession) {
        this.handlerGameSession = handlerGameSession;
        this.tiltCounters = new HashMap<>();
    }

    @Override
    public void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        HandlerPlayer currentPlayer = handlerGameSession.getCurrentPlayer();
        if (tiltCounters.containsKey(currentPlayer)) {
            tiltCounters.put(currentPlayer, tiltCounters.get(currentPlayer) + 1);
        } else {
            tiltCounters.put(currentPlayer, 1);
        }
        if (tiltCounters.get(currentPlayer) > MAX_TILT_COUNTER) {
            handlerGameSession.stopUserControllingElements();
            tiltCounters.put(currentPlayer, 0);
        }
    }
}
