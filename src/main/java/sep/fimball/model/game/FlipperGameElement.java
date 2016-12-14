package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.FlipperModifi;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

public class FlipperGameElement extends GameElement
{
    private boolean left;

    /**
     * Erstellt ein neues FlipperGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem FlipperGameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public FlipperGameElement(PlacedElement element, boolean bind, boolean left)
    {
        super(element, bind);
        this.left = left;
    }

    public void setPhysicsElement(PhysicsHandler physicsHandler, FlipperPhysicsElement flipperPhysicsElement)
    {
        InputManager.getSingletonInstance().addListener(left ? KeyBinding.LEFT_FLIPPER : KeyBinding.RIGHT_FLIPPER, args -> {
            physicsHandler.addModifi(flipperPhysicsElement, new FlipperModifi(args.getState() != KeyObserverEventArgs.KeyChangedToState.DOWN));
        });
    }
}
