package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.PlungerModify;
import sep.fimball.model.physics.element.PlungerPhysicsElement;
import sep.fimball.model.input.manager.KeyObserverEventArgs.KeyChangedToState;

import java.util.Optional;

/**
 * Created by kaira on 14.12.2016.
 */
public class PlungerGameElement extends GameElement
{
    private long pressStart;
    private KeyChangedToState oldState = KeyChangedToState.UP;

    /**
     * Erstellt ein neues PlungerGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public PlungerGameElement(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }

    public void setPhysicsElement(PhysicsHandler physicsHandler, PlungerPhysicsElement<GameElement> plungerPhysicsElement)
    {
        InputManager.getSingletonInstance().addListener(KeyBinding.PLUNGER, args ->
        {
            //TODO - Hässliche oldState Logik auslagern
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN && oldState == KeyChangedToState.UP)
            {
                oldState = KeyChangedToState.DOWN;
                pressStart = System.currentTimeMillis();
            }
            else if (args.getState() == KeyChangedToState.UP && oldState == KeyChangedToState.DOWN)
            {
                oldState = KeyChangedToState.UP;
                double force = calcForce();
                physicsHandler.addModify(plungerPhysicsElement, new PlungerModify(force));
            }
        });
    }

    private double calcForce()
    {
        double force = 32;
        double secondsPressed = (System.currentTimeMillis() - pressStart) / 1000d;
        return Math.min(force * 3, force * secondsPressed);
    }
}
