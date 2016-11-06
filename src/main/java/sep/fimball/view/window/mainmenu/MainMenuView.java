package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ParentNodeBinder;
import sep.fimball.view.BoundToViewModel;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuView extends WindowView implements BoundToViewModel<MainMenuViewModel>
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
    public void bindToViewModel(MainMenuViewModel mainMenuViewModel)
    {
        this.mainMenuViewModel = mainMenuViewModel;

        detailedPreviewName.textProperty().bind(mainMenuViewModel.getPinballMachineInfoSubViewModel().nameProperty());
        detailedPreviewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", mainMenuViewModel.getPinballMachineInfoSubViewModel().imagePathProperty(), "\");"));

        ParentNodeBinder.bindList(machineOverview, mainMenuViewModel.pinballMachineSelectorSubViewModelListProperty(), WindowType.MAIN_MENU_PREVIEW,
                (view, pinballMachineSelectorSubViewModel) ->
                {
                    ((PinballMachineSelectorSubView) view).bindToViewModel(mainMenuViewModel, pinballMachineSelectorSubViewModel);
                });

        ParentNodeBinder.bindListToSimpleBoundParent(highscoreTable, mainMenuViewModel.getPinballMachineInfoSubViewModel().highscoreListProperty(), WindowType.MAIN_MENU_DETAILD_PREVIEW_HIGHSCORE_ENTRY);
    }

    @FXML
    public void edit(MouseEvent mouseEvent)
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().editClicked();
    }

    @FXML
    public void play(MouseEvent mouseEvent)
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().playClicked();
    }
}