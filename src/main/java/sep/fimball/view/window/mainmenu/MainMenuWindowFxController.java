package sep.fimball.view.window.mainmenu;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.FxControllerType;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.window.Window;
import sep.fimball.viewmodel.MainMenuViewModel;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

import java.net.URL;
import java.util.ResourceBundle;

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
        mainMenuViewModel.getTableBlueprintPreviewListProperty().addListener(new ListChangeListener<TableBlueprintPreview>()
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

        for(TableBlueprintPreview preview : mainMenuViewModel.getTableBlueprintPreviewListProperty())
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(FxControllerType.MAIN_MENU_PREVIEW);
            machineOverview.getChildren().add(simpleFxmlLoader.getRootNode());
            ((MainMenuPreviewFxController) simpleFxmlLoader.getFxController()).bindToViewModel(preview);
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
