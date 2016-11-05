package sep.fimball.viewmodel.window.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.model.GameElement;
import sep.fimball.model.GameSession;
import sep.fimball.viewmodel.pinball.SpriteViewModel;

/**
 * Created by TheAsuro on 04.11.2016.
 */
public class GameViewModel
{
    private ListProperty<SpriteViewModel> spriteViewModels;
    private ListProperty<GameElement> elements;

    public GameViewModel()
    {
        spriteViewModels = new SimpleListProperty<>();
        elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(spriteViewModels, elements, SpriteViewModel::new);
        elements.bind(GameSession.getSingletonInstance().getTable().getWorld().getWorldElements());

        // GameSession has been created and filled in the PlayerNameView TODO move this to controller?
        // As the view has finished loading (TODO has it?), we can now start the game
        GameSession.getSingletonInstance().startNewGame();
    }

    public ReadOnlyListProperty<SpriteViewModel> getSpriteViewModels()
    {
        return spriteViewModels;
    }
}
