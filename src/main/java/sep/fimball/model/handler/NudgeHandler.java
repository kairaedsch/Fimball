package sep.fimball.model.handler;

public class NudgeHandler implements UserHandler
{
    private HandlerGameSession handlerGameSession;


    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private int[] tiltCounters;

    /**
     * Gibt an wie oft die "Nudge-Funktion" verwendet werden kann bevor der Tilt einsetzt.
     */
    private static final int MAX_TILT_COUNTER = 5;

    public NudgeHandler(HandlerGameSession handlerGameSession) {
        this.handlerGameSession = handlerGameSession;
        this.tiltCounters = new int[handlerGameSession.getNumberOfPlayers()];
    }

    @Override
    public void activateUserHandler(int playerIndex)
    {
        ++tiltCounters[playerIndex];
        if (tiltCounters[playerIndex] > MAX_TILT_COUNTER) {
            handlerGameSession.stopUserControllingElements();
            tiltCounters[playerIndex] = 0;
        }
    }
}
