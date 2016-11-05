package sep.fimball.view.window.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.view.pinball.SpriteSubView;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.pinball.SpriteViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameView extends WindowView
{
    private GameViewModel viewModel;
    private ListProperty<SpriteSubView> spriteSubViews;
    private ListProperty<SpriteViewModel> spriteViewModels;

    @FXML
    protected void initialize()
    {
        viewModel = new GameViewModel();

        spriteSubViews = new SimpleListProperty<>();
        spriteViewModels = new SimpleListProperty<>();
        ListPropertyBinder.bindList(spriteSubViews, spriteViewModels, SpriteSubView::new);
        spriteViewModels.bind(viewModel.getSpriteViewModels());
    }
}
