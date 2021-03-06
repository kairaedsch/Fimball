package sep.fimball.model.game;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.UserHandler;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;
import sep.fimball.model.physics.element.AngularDirection;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

/**
 * Das Spielelement des Flippers.
 */
public class FlipperGameElement extends GameElement implements UserHandler
{
    /**
     * Gibt an ob dies ein linker Flipper ist.
     */
    private boolean left;

    /**
     * Das FlipperPhysicsElement des FlipperGameElements.
     */
    private FlipperPhysicsElement<GameElement> flipperPhysicsElement;

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

    /**
     * Setzt das FlipperPhysicsElement dieses FlipperGameElements.
     *
     * @param flipperPhysicsElement Das FlipperPhysicsElement dieses FlipperGameElements.
     */
    public void setPhysicsElement(FlipperPhysicsElement<GameElement> flipperPhysicsElement)
    {
        this.flipperPhysicsElement = flipperPhysicsElement;
    }

    @Override
    public void activateUserHandler(KeyEventArgs keyEventArgs)
    {
        if ((left && keyEventArgs.getBinding() == KeyBinding.LEFT_FLIPPER) || (!left && keyEventArgs.getBinding() == KeyBinding.RIGHT_FLIPPER))
        {
            boolean rotateUp = keyEventArgs.getState() != KeyEventArgs.KeyChangedToState.DOWN;
            flipperPhysicsElement.addModify(() -> rotateUp ? AngularDirection.DOWN : AngularDirection.UP);

            if (keyEventArgs.isStateSwitched())
            {
                if (!rotateUp)
                {
                    playFlipperSound("flipper.wav");
                }
                else
                {
                    playFlipperSound("flipperDown.wav");
                }
            }

        }
    }

    /**
     * Spielt den Flipper Sound ab.
     *
     * @param soundPath Der Pfad des Flipper Sound.
     */
    private void playFlipperSound(String soundPath)
    {
        SoundManager.getInstance().addSoundToPlay(new Sound(soundPath, false));
    }
}
