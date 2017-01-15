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
    private int currentFrame = 1;
    private double spinnerAcceleration;
    private BallGameElement ballGameElement;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public SpinnerGameElement(PlacedElement element, boolean bind, BallGameElement gameBall)
    {
        super(element, bind);
        this.ballGameElement = gameBall;

        AnimationTimer spinnerUpdate = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (spinnerAcceleration > 0)
                {
                    leftSpins = spinnerAcceleration * Config.SPINS_PER_DIRECT_HIT;
                    spinnerAcceleration = 0;
                }
                double spinSpeed = Math.max(0.1, leftSpins / Config.SPINS_PER_DIRECT_HIT);
                currentSpin += spinSpeed;

                if (currentSpin >= 1.0)
                {
                    if (leftSpins > 0)
                    {
                        currentSpin = 0;
                        leftSpins = leftSpins - Config.SPINNER_SLOWDOWN_SPEED;
                        currentFrame = ((currentFrame + 1) % 7) + 1;
                    }
                    else
                    {
                        currentSpin = 0;
                    }
                }
                setCurrentAnimation(getMediaElement().getEventMap().get(-1 * currentFrame).getAnimation());

            }
        };
        spinnerUpdate.start();
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderId)
    {
        if (element == this)
        {
            Vector2 direction = new Vector2(0, -1).rotate(Math.toRadians(rotationProperty().get()));
            spinnerAcceleration = 1;
        }
    }
}
