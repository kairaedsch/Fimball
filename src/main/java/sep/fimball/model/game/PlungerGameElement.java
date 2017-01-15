package sep.fimball.model.game;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.UserHandler;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.input.manager.KeyEventArgs.KeyChangedToState;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import static sep.fimball.general.data.PhysicsConfig.DEFAULT_PLUNGER_FORCE;
import static sep.fimball.general.data.PhysicsConfig.MAX_PLUNGER_FORCE_MULTIPLY;

/**
 * Das Spielelement des Plungers.
 */
public class PlungerGameElement extends GameElement implements UserHandler
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
     * Das PlungerPhysicsElement des PlungerGameElement.
     */
    private PlungerPhysicsElement<GameElement> plungerPhysicsElement;

    /**
     * Zum Zurücksetzten der Plungerstärke vom PlungerPhysicsElement.
     */
    private PauseTransition resetTransition;

    /**
     * Erstellt ein neues PlungerGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public PlungerGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);

        AnimationTimer plungerUpdate = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (plungerPressed)
                {
                    int power = (int) Math.ceil(Math.max(1, Math.min(1, getSecondsPressed() / MAX_PLUNGER_FORCE_MULTIPLY) * 4));
                    setCurrentAnimation(getMediaElement().getEventMap().get(-power).getAnimation());
                }
            }
        };
        plungerUpdate.start();

        resetTransition = new PauseTransition(Duration.seconds(PhysicsConfig.PLUNGER_FORCE_DURATION));
        resetTransition.setOnFinished(e -> plungerPhysicsElement.addModify(() -> 0));
    }

    /**
     * Wird benutzt um dem GameElement mitzuteilen welches das zugehörige physikalische Element ist.
     *
     * @param plungerPhysicsElement Das zum Plunger gehörende physikalische Element.
     */
    public void setPhysicsElement(PlungerPhysicsElement<GameElement> plungerPhysicsElement)
    {
        this.plungerPhysicsElement = plungerPhysicsElement;
    }

    @Override
    public void activateUserHandler(KeyEventArgs keyEventArgs)
    {
        if (keyEventArgs.getBinding() == KeyBinding.PLUNGER)
        {
            if (keyEventArgs.getState() == KeyEventArgs.KeyChangedToState.DOWN && keyEventArgs.isStateSwitched())
            {
                plungerPressed = true;
                pressStart = System.currentTimeMillis();

            }
            else if (keyEventArgs.getState() == KeyChangedToState.UP && keyEventArgs.isStateSwitched())
            {
                plungerPressed = false;
                double force = calcForce();
                plungerPhysicsElement.addModify(() -> force);
                setCurrentAnimation(getMediaElement().getEventMap().get(-5).getAnimation());

                resetTransition.stop();
                resetTransition.play();
            }
        }
    }

    /**
     * Berechnet die Kraft mit der der Plunger den Ball wegschießen soll abhängig davon wie lange er aufgeladen wurde.
     *
     * @return Die Kraft mit der der Plunger den Ball wegschießen soll.
     */
    private double calcForce()
    {
        return Math.min(DEFAULT_PLUNGER_FORCE * MAX_PLUNGER_FORCE_MULTIPLY, DEFAULT_PLUNGER_FORCE * getSecondsPressed());
    }

    /**
     * Gibt zurück wie lange der Plunger bereits geladen wurde.
     *
     * @return Wie lange der Plunger bereits geladen wurde.
     */
    private double getSecondsPressed()
    {
        return (System.currentTimeMillis() - pressStart) / 1000d;
    }
}
