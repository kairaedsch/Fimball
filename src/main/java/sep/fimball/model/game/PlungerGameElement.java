package sep.fimball.model.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.input.manager.KeyEventArgs.KeyChangedToState;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.PlungerModify;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import java.util.Optional;

/**
 * Das Spielelement des Plungers.
 */
public class PlungerGameElement extends GameElement
{
    /**
     * Der Zeitpunkt zu dem das Aufladen des Plungers begonnen wurde.
     */
    private long pressStart;

    /**
     * Gibt an ob der Plunger aktuell aufgeladen wird.
     */
    private boolean plungerPressed;

    /**
     * Erstellt ein neues PlungerGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public PlungerGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);

        Timeline lightChangeLoop = new Timeline();
        lightChangeLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(25), event ->
        {
            if (plungerPressed)
            {
                setCurrentAnimation(getMediaElement().getEventMap().values().iterator().next().getAnimation());
            }
            else
            {
                setCurrentAnimation(Optional.empty());
            }
        });
        lightChangeLoop.getKeyFrames().add(keyFrame);
        lightChangeLoop.play();
    }

    public void setPhysicsElement(PhysicsHandler physicsHandler, PlungerPhysicsElement<GameElement> plungerPhysicsElement)
    {
        InputManager.getSingletonInstance().addListener(KeyBinding.PLUNGER, args ->
        {
            if (args.getState() == KeyEventArgs.KeyChangedToState.DOWN && args.isStateSwitched())
            {
                plungerPressed = true;
                pressStart = System.currentTimeMillis();

            }
            else if (args.getState() == KeyChangedToState.UP && args.isStateSwitched())
            {
                plungerPressed = false;
                double force = calcForce();
                physicsHandler.addModify(plungerPhysicsElement, (PlungerModify) () -> force);
            }
        });
    }

    /**
     * Berechnet die Kraft mit der der Plunger den Ball wegschießen soll abhängig davon wie lange er aufgeladen wurde.
     *
     * @return Die Kraft mit der der Plunger den Ball wegschießen soll.
     */
    private double calcForce()
    {
        double force = 32;
        double secondsPressed = (System.currentTimeMillis() - pressStart) / 1000d;
        return Math.min(force * 3, force * secondsPressed);
    }
}
