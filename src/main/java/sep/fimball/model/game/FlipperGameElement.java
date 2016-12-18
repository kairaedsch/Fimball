package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

/**
 * Das Spielelement des Flippers.
 */
public class FlipperGameElement extends GameElement<FlipperPhysicsElement<GameElement<?>>>
{
    /**
     * Gibt an ob dies ein linker Flipper ist.
     */
    private boolean left;

    /**
     * Erstellt ein neues FlipperGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem FlipperGameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     * @param left    Gibt an, ob dies ein linker Flipper ist.
     */
    public FlipperGameElement(PlacedElement element, boolean bind, boolean left)
    {
        super(element, bind);
        this.left = left;
    }

    @Override
    public void setPhysicsElement(FlipperPhysicsElement<GameElement<?>> flipperPhysicsElement)
    {
        InputManager.getSingletonInstance().addListener(left ? KeyBinding.LEFT_FLIPPER : KeyBinding.RIGHT_FLIPPER, args -> flipperPhysicsElement.addModify(() -> args.getState() != KeyEventArgs.KeyChangedToState.DOWN));
    }
}
