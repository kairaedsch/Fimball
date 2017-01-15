package sep.fimball.model.handler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;

import java.util.HashMap;
import java.util.Map;

/**
 * Der TiltHandler reagiert auf das Rütteln am Automaten.
 */
public class TiltHandler implements UserHandler
{
    /**
     * Gibt an wie oft die "Nudge-Funktion" verwendet werden kann bevor der Tilt einsetzt.
     */
    private static final int MAX_TILT_COUNTER = 5;
    /**
     * Die Zeit, nach der der Ball automatisch als verloren gilt, wenn der Tilt aktiviert wurde.
     */
    private static final int TILT_DURATION_BEFORE_BALL_LOSS = 5;
    /**
     * Die zugehörige HandlerGameSession.
     */
    private HandlerGameSession handlerGameSession;
    /**
     * Dient dazu, den UserInput zu aktiviert oder zu deaktiviert.
     */
    private InputModifier inputModifier;
    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private Map<HandlerPlayer, Integer> tiltCounters;

    /**
     * Erzeugt einen neuen TiltHandler.
     *
     * @param handlerGameSession Die zugehörige HandlerGameSession.
     * @param inputModifier      Dient dazu, den UserInput zu aktiviert oder zu deaktiviert.
     */
    TiltHandler(HandlerGameSession handlerGameSession, InputModifier inputModifier)
    {
        super();
        this.handlerGameSession = handlerGameSession;
        this.inputModifier = inputModifier;
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
                activateTilt();
                tiltCounters.put(currentPlayer, 0);
            }

            handlerGameSession.gameBallProperty().get().nudge(keyEventType.getBinding() == KeyBinding.NUDGE_LEFT);
        }
    }

    /**
     * Löst den Tilt aus.
     */
    private void activateTilt()
    {
        inputModifier.setKeyEventsActivated(false);
        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(TILT_DURATION_BEFORE_BALL_LOSS), (event -> handlerGameSession.ballLost()));
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(1);
        timeline.statusProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == Animation.Status.STOPPED)
            {
                inputModifier.setKeyEventsActivated(true);
            }
        });
        handlerGameSession.getCurrentPlayer().ballsProperty().addListener((observable, oldValue, newValue) -> timeline.stop());
        timeline.play();
    }
}
