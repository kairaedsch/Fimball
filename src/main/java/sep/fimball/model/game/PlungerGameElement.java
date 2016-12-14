package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.PlungerModifi;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import java.util.Optional;

/**
 * Created by kaira on 14.12.2016.
 */
public class PlungerGameElement extends GameElement
{
    private Optional<Long> downSince;

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
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
            {
                pushDown();
            }
            else
            {
                physicsHandler.addModifi(plungerPhysicsElement, new PlungerModifi(calcForce()));
            }
        });
    }

    private void pushDown()
    {
        downSince = Optional.of(System.nanoTime());
    }

    private double calcForce()
    {
        double force = 5;
        // TODO do math
        return force;
    }
}
