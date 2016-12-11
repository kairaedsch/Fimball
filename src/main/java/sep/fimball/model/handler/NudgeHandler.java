package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;

public class NudgeHandler implements UserHandler
{
    private HandlerGameSession handlerGameSession;


    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private int tiltCounter;

    /**
     * Gibt an wie oft die "Nudge-Funktion" verwendet werden kann bevor der Tilt einsetzt.
     */
    private static final int MAX_TILT_COUNTER = 5;

    public NudgeHandler(HandlerGameSession handlerGameSession) {
        this.handlerGameSession = handlerGameSession;
    }

    @Override
    public void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        ++tiltCounter;
        if (tiltCounter > MAX_TILT_COUNTER) {
            handlerGameSession.stopUserControllingElements();
        }
    }
}
