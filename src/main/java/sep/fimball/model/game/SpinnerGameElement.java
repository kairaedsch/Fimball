package sep.fimball.model.game;

import javafx.animation.AnimationTimer;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.physics.element.SpinnerPhysicsElement;

/**
 * Created by alexcekay on 15.01.17.
 */
public class SpinnerGameElement extends GameElement
{
    private SpinnerPhysicsElement spinnerPhysicsElement;
    private double leftSpins;
    private double currentSpin;
    private int currentFrame = 1;
    private final double spinsPerDirectHit = 80;
    private final int slowDownSpeed = 3;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public SpinnerGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);

        AnimationTimer spinnerUpdate = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //TODO Find better sync with physics
                double spinnerAcceleration = spinnerPhysicsElement.getSpinnerAcceleration();

                if (spinnerAcceleration > 0)
                {
                    leftSpins = spinnerAcceleration * spinsPerDirectHit;
                    spinnerPhysicsElement.setSpinnerAcceleration(0);
                }
                double spinSpeed = Math.max(0.1, leftSpins / spinsPerDirectHit);
                currentSpin += spinSpeed;

                if (currentSpin >= 1.0)
                {
                    if (leftSpins > 0)
                    {
                        currentSpin = 0;
                        leftSpins = leftSpins - slowDownSpeed;
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

    public void setPhysicsElement(SpinnerPhysicsElement physicsElement)
    {
        this.spinnerPhysicsElement = physicsElement;
    }
}
