package sep.fimball.view.window.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.window.Window;
import sep.fimball.viewmodel.MainMenuViewModel;
import sep.fimball.viewmodel.ViewModelSceneManager;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuWindowFxController extends Window
{
    @FXML
    private VBox machineOverview;
    @FXML
    private Pane buttonPlaySinglePlayer;
    @FXML
    private Pane buttonPlayMultiplayer;
    @FXML
    private Pane buttonEdit;

    private MainMenuViewModel mainMenuViewModel;

    @FXML
    public void initialize() {
        mainMenuViewModel = new MainMenuViewModel();
    }

    @FXML
    public void edit(MouseEvent mouseEvent)
    {
        mainMenuViewModel.editClicked();
    }

    @FXML
    public void play(MouseEvent mouseEvent)
    {
        mainMenuViewModel.playClicked();
    }
}
