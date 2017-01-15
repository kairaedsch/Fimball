package sep.fimball.model.game;

import javafx.animation.AnimationTimer;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.ElementHandler;
import sep.fimball.model.handler.HandlerGameElement;

/**
 * Created by alexcekay on 15.01.17.
 */
public class SpinnerGameElement extends GameElement implements ElementHandler
{
    private double remainingSpins;
    private double currentSpinPercentage;
    private int currentFrame;
    private double spinnerHitAngle;
    private Vector2 ballSpeedDelta;
    private boolean accelerationUpdated;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public SpinnerGameElement(PlacedElement element, boolean bind, BallGameElement gameBall)
    {
        super(element, bind);
        ballSpeedDelta = new Vector2();

        gameBall.positionProperty().addListener(((observable, oldValue, newValue) -> {
            double deltaX = newValue.getX() - oldValue.getX();
            double deltaY = newValue.getY() - oldValue.getY();
            ballSpeedDelta = new Vector2(deltaX, deltaY);
        }));

        AnimationTimer spinnerUpdate = new AnimationTimer()
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

    private void switchToNextFrame()
    {
        int spinnerAnimationFrames = 7;
        currentFrame = (currentFrame + (int)Math.signum(spinnerHitAngle)) % spinnerAnimationFrames;

        if (currentFrame < 0)
        {
            currentFrame += spinnerAnimationFrames;
        }
    }
}
