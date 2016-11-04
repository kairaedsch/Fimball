package sep.fimball.view.window.game;

import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.view.Sprite;
import sep.fimball.view.window.Window;
import sep.fimball.viewmodel.GameViewModel;
import sep.fimball.viewmodel.SpriteViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameWindowFxController extends Window
{
    private GameViewModel viewModel;
    private SimpleListProperty<Sprite> sprites;
    private SimpleListProperty<SpriteViewModel> spriteViewModels;

    @FXML
    protected void initialize()
    {
        viewModel = new GameViewModel();

        sprites = new SimpleListProperty<>();
        spriteViewModels = new SimpleListProperty<>();
        ListPropertyBinder.bindList(sprites, spriteViewModels, Sprite::new);
        spriteViewModels.bind(viewModel.getSpriteViewModels());
    }
}
