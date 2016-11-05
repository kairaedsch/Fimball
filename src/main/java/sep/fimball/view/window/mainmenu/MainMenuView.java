package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachineSelectorSubViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuView extends WindowView
{
    @FXML
    private VBox machineOverview;
    @FXML
    private Pane buttonPlaySinglePlayer;
    @FXML
    private Pane buttonPlayMultiplayer;
    @FXML
    private Pane buttonEdit;
    @FXML
    private Pane detailedPreviewImage;
    @FXML
    private Label detailedPreviewName;
    @FXML
    private VBox highscores;

    private MainMenuViewModel mainMenuViewModel;

    @FXML
    public void initialize()
    {
        mainMenuViewModel = new MainMenuViewModel();

        detailedPreviewName.textProperty().bind(mainMenuViewModel.getPinballMachineInfoSubViewModel().nameProperty());
        detailedPreviewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", mainMenuViewModel.getPinballMachineInfoSubViewModel().imagePathProperty(), "\");"));
        mainMenuViewModel.pinballMachineSelectorSubViewModelListProperty().addListener(new ListChangeListener<PinballMachineSelectorSubViewModel>()
        {
            @Override
            public void onChanged(Change<? extends PinballMachineSelectorSubViewModel> c)
            {
                previewListChanged();
            }
        });
        previewListChanged();

       // updateHighsores();
    }

    private void updateHighsores()
    {
        highscores.getChildren().clear();
        for (sep.fimball.general.Highscore score : mainMenuViewModel.getPinballMachineInfoSubViewModel().getHighscoreList())
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(WindowType.MAIN_MENU_DETAILD_PREVIEW_HIGHSCORE_ENTRY);
            highscores.getChildren().add(simpleFxmlLoader.getRootNode());
            ((HighscoreSubView) simpleFxmlLoader.getFxController()).bindToViewModel(score);
        }
    }

    private void previewListChanged()
    {
        machineOverview.getChildren().clear();

        for (PinballMachineSelectorSubViewModel preview : mainMenuViewModel.pinballMachineSelectorSubViewModelListProperty())
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(WindowType.MAIN_MENU_PREVIEW);
            machineOverview.getChildren().add(simpleFxmlLoader.getRootNode());
            ((PinballMachineSelectorSubView) simpleFxmlLoader.getFxController()).bindToViewModel(mainMenuViewModel, preview);
        }
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
