package sep.fimball.model.game;

import javafx.animation.AnimationTimer;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.*;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

/**
 * Das Spielelement des Spinners.
 */
public class SpinnerGameElement extends GameElement implements ElementHandler, GameHandler
{
    /**
     * Die verbleibende Anzahl an Umdrehungen des Spinners.
     */
    private double remainingSpins;

    /**
     * Der Fortschritt der aktuellen Umdrehung.
     */
    private double currentSpinPercentage;

    /**
     * Das aktuelle Bild der Spinner Animation.
     */
    private int currentFrame;

    /**
     * Der Winkel in dem der Spinner vom Ball getroffen wurde.
     */
    private double spinnerHitAngle;

    /**
     * Die Geschwindigkeit des Balls.
     */
    private Vector2 ballSpeedDelta;

    /**
     * Ist wahr sobald der Spinner getroffen wurde. Wird zur Synchronisierung mit dem AnimationTimer genutzt.
     */
    private boolean accelerationUpdated;

    /**
     * Der AnimationTimer welcher sich um die Updates des Spinner kümmert.
     */
    private AnimationTimer spinnerUpdate;

    /**
     * Die GameSession welche genutzt wird um den Spieler Punkte bei jeder Umdrehung des Spinners hinzuzufügen.
     */
    private HandlerGameSession handlerGameSession;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     * @param gameBall Das Spielelement des Balls.
     * @param handlerGameSession Die GameSession aus der Sicht der Handler.
     */
    public SpinnerGameElement(PlacedElement element, boolean bind, BallGameElement gameBall, HandlerGameSession handlerGameSession)
    {
        super(element, bind);
        this.handlerGameSession = handlerGameSession;
        ballSpeedDelta = new Vector2();

        gameBall.positionProperty().addListener(((observable, oldValue, newValue) -> {
            double deltaX = newValue.getX() - oldValue.getX();
            double deltaY = newValue.getY() - oldValue.getY();
            ballSpeedDelta = new Vector2(deltaX, deltaY);
        }));

        spinnerUpdate = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (accelerationUpdated)
                {
                    remainingSpins = Math.abs(spinnerHitAngle) * Config.SPINS_PER_DIRECT_HIT;
                    accelerationUpdated = false;
                }
                double spinSpeed = Math.max(0.1, remainingSpins / Config.SPINS_PER_DIRECT_HIT);
                currentSpinPercentage += spinSpeed;

                if (currentSpinPercentage >= 1.0)
                {
                    if (remainingSpins > 0)
                    {
                        currentSpinPercentage = 0;
                        remainingSpins = remainingSpins - Config.SPINNER_SLOWDOWN_SPEED;
                        SoundManager.getInstance().addSoundToPlay(new Sound("spinner.wav", false));
                        addPointToPlayer();
                        switchToNextFrame();
                    }
                    else
                    {
                        currentSpinPercentage = 0;
                    }
                }
                setCurrentAnimation(getMediaElement().getEventMap().get(-1 * (currentFrame + 1)).getAnimation());
            }
        };
        spinnerUpdate.start();
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs)
    {
        if (element == this)
        {
            Vector2 spinnerDirection = new Vector2(0, -1).rotate(Math.toRadians(rotationProperty().get()));
            spinnerHitAngle = spinnerDirection.normalized().dot(ballSpeedDelta.normalized());
            accelerationUpdated = true;
        }
    }

    /**
     * Wechselt je nach Drehrichtung des Spinners zum korrekten nächsten Bild der Spinneranimation.
     */
    private void switchToNextFrame()
    {
        int spinnerAnimationFrames = 7;
        currentFrame = (currentFrame + (int)Math.signum(spinnerHitAngle)) % spinnerAnimationFrames;

        if (currentFrame < 0)
        {
            currentFrame += spinnerAnimationFrames;
        }
    }

    /**
     * Fügt dem aktuellen Spieler die Anzahl an Punkten welche dieser Spinner gibt hinzu.
     */
    private void addPointToPlayer()
    {
        handlerGameSession.getCurrentPlayer().addPoints(getPointReward());
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        switch (gameEvent)
        {
            case START:
                spinnerUpdate.start();
                break;
            case PAUSE:
                spinnerUpdate.stop();
                break;
        }
    }
}
