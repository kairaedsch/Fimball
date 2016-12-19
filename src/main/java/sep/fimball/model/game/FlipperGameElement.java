package sep.fimball.model.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

/**
 * Das Spielelement des Flippers.
 */
public class FlipperGameElement extends GameElement
{
    /**
     * Gibt an ob dies ein linker Flipper ist.
     */
    private boolean left;

    /**
     * Gibt an, ob nicht auf User-Input reagiert wird.
     * TODO umbenennen
     */
    private BooleanProperty stopReacting;

    /**
     * Erstellt ein neues FlipperGameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem FlipperGameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     * @param left    Gibt an, ob dies ein linker Flipper ist.
     * @param stopReacting Gibt an, ob nicht auf User-Input reagiert werden soll.
     */
    public FlipperGameElement(PlacedElement element, boolean bind, boolean left, ReadOnlyBooleanProperty stopReacting)
    {
        super(element, bind);
        this.left = left;
        this.stopReacting = new SimpleBooleanProperty();
        this.stopReacting.bind(stopReacting);
    }

    public void setPhysicsElement(FlipperPhysicsElement<GameElement> flipperPhysicsElement)
    {
        InputManager.getSingletonInstance().addListener(left ? KeyBinding.LEFT_FLIPPER : KeyBinding.RIGHT_FLIPPER, args ->
        {
            if(!stopReacting.get())
            {
                flipperPhysicsElement.addModify(() -> args.getState() != KeyEventArgs.KeyChangedToState.DOWN);
            }
            System.out.println("Stop Reacting: " + stopReacting.get());
        });
    }
}
