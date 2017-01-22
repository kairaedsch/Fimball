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
    private double remainingSpins;
    private double currentSpinPercentage;
    private int currentFrame;
    private double spinnerHitAngle;
    private Vector2 ballSpeedDelta;
    private boolean accelerationUpdated;
    private AnimationTimer spinnerUpdate;
    private HandlerGameSession handlerGameSession;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     * @param gameBall Das Spielelement des Balls.
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
    public void activateElementHandler(HandlerGameElement element, int colliderId)
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
