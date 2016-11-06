package sep.fimball.view.window.game;

import javafx.fxml.FXML;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameView extends WindowView
{
    private GameViewModel viewModel;

    @FXML
    protected void initialize()
    {
        viewModel = new GameViewModel();
    }
}
