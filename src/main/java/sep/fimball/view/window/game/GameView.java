package sep.fimball.view.window.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameView extends WindowView<GameViewModel>
{
    private GameViewModel gameViewModel;

    @FXML
    private Label playername;

    @FXML
    private Label score;

    @FXML
    private HBox reserveBalls;


    @Override
    public void setViewModel(GameViewModel gameViewModel)
    {
        this.gameViewModel = gameViewModel;
    }
}
