package sep.fimball.viewmodel.pinball;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.model.GameElement;
import sep.fimball.model.GameSession;

/**
 * Created by kaira on 06.11.2016.
 */
public class PinballCanvasViewModel
{
    private ListProperty<SpriteSubViewModel> spriteSubViewModels;
    private ListProperty<GameElement> elements;

    public PinballCanvasViewModel()
    {
        spriteSubViewModels = new SimpleListProperty<>();
        elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(spriteSubViewModels, elements, SpriteSubViewModel::new);
        elements.bind(GameSession.getSingletonInstance().getTable().getWorld().getWorldElements());

        // GameSession has been created and filled in the PlayerNameView TODO move this to controller?
        // As the view has finished loading (TODO has it?), we can now start the game
        GameSession.getSingletonInstance().startNewGame();
    }

    public ListProperty<SpriteSubViewModel> spriteSubViewModelsProperty()
    {
        return spriteSubViewModels;
    }
}
