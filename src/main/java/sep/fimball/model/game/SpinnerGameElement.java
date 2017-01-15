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
    private double leftSpins;
    private double currentSpin;
    private int currentFrame;
    private double spinnerAcceleration;
    private Vector2 ballDelta;
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
        ballDelta = new Vector2();

        gameBall.positionProperty().addListener(((observable, oldValue, newValue) -> {
            double deltaX = newValue.getX() - oldValue.getX();
            double deltaY = newValue.getY() - oldValue.getY();
            ballDelta = new Vector2(deltaX, deltaY);
        }));

        AnimationTimer spinnerUpdate = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (accelerationUpdated)
                {
                    leftSpins = Math.abs(spinnerAcceleration) * Config.SPINS_PER_DIRECT_HIT;
                    accelerationUpdated = false;
                }
                double spinSpeed = Math.max(0.1, leftSpins / Config.SPINS_PER_DIRECT_HIT);
                currentSpin += spinSpeed;

                if (currentSpin >= 1.0)
                {
                    if (leftSpins > 0)
                    {
                        currentSpin = 0;
                        leftSpins = leftSpins - Config.SPINNER_SLOWDOWN_SPEED;
                        switchToNextFrame();
                    }
                    else
                    {
                        currentSpin = 0;
                    }
                }
                setCurrentAnimation(getMediaElement().getEventMap().get(-1 * (currentFrame + 1)).getAnimation());
            }
        };
        spinnerUpdate.start();
    }

    private void switchToNextFrame()
    {
        currentFrame = (currentFrame + (int)Math.signum(spinnerAcceleration)) % 7;

        if (currentFrame < 0)
        {
            currentFrame += 7;
        }
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderId)
    {
        if (element == this)
        {
            Vector2 direction = new Vector2(0, -1).rotate(Math.toRadians(rotationProperty().get()));
            spinnerAcceleration = direction.normalized().dot(ballDelta.normalized());
            accelerationUpdated = true;
        }
    }
}
