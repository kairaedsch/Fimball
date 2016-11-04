package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.general.Highscore;
import sep.fimball.view.FxControllerType;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.window.Window;
import sep.fimball.viewmodel.MainMenuViewModel;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

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
    @FXML
    private Pane detailedPreviewImage;
    @FXML
    private Label detailedPreviewName;
    @FXML
    private TableView<Highscore> highscores;

    private MainMenuViewModel mainMenuViewModel;

    @FXML
    public void initialize() {
        mainMenuViewModel = new MainMenuViewModel();

        highscores.setItems(mainMenuViewModel.getTableBlueprintDetailedPreview().getHighscoreList());
        detailedPreviewName.textProperty().bind(mainMenuViewModel.getTableBlueprintDetailedPreview().nameProperty());
        detailedPreviewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", mainMenuViewModel.getTableBlueprintDetailedPreview().imagePathProperty(), "\");"));
        mainMenuViewModel.tableBlueprintPreviewListProperty().addListener(new ListChangeListener<TableBlueprintPreview>()
        {
            @Override
            public void onChanged(Change<? extends TableBlueprintPreview> c)
            {
                previewListChanged();
            }
        });
        previewListChanged();
    }

    private void previewListChanged()
    {
        machineOverview.getChildren().clear();

        for(TableBlueprintPreview preview : mainMenuViewModel.tableBlueprintPreviewListProperty())
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(FxControllerType.MAIN_MENU_PREVIEW);
            machineOverview.getChildren().add(simpleFxmlLoader.getRootNode());
            ((MainMenuPreviewFxController) simpleFxmlLoader.getFxController()).bindToViewModel(mainMenuViewModel, preview);
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
