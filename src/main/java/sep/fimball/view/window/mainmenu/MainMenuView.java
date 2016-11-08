package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuView extends WindowView<MainMenuViewModel>
{
    @FXML
    private VBox machineOverview;
    @FXML
    private Pane detailedPreviewImage;
    @FXML
    private Label detailedPreviewName;
    @FXML
    private VBox highscoreTable;

    private MainMenuViewModel mainMenuViewModel;

    @Override
    public void setViewModel(MainMenuViewModel mainMenuViewModel)
    {
        this.mainMenuViewModel = mainMenuViewModel;

        detailedPreviewName.textProperty().bind(mainMenuViewModel.getPinballMachineInfoSubViewModel().nameProperty());
        detailedPreviewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", mainMenuViewModel.getPinballMachineInfoSubViewModel().imagePathProperty(), "\");"));

        ViewModelListToPaneBinder.bindViewModelsToViews(machineOverview, mainMenuViewModel.pinballMachineSelectorSubViewModelListProperty(), WindowType.MAIN_MENU_PREVIEW,
                (view, pinballMachineSelectorSubViewModel) ->
                {
                    ((PinballMachineSelectorSubView) view).setViewModel(mainMenuViewModel, pinballMachineSelectorSubViewModel);
                });

        ViewModelListToPaneBinder.bindViewModelsToViews(highscoreTable, mainMenuViewModel.getPinballMachineInfoSubViewModel().highscoreListProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
    }

    @FXML
    private void editClicked(MouseEvent mouseEvent)
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().editClicked();
    }

    @FXML
    private void playClicked(MouseEvent mouseEvent)
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().playClicked();
    }
}
