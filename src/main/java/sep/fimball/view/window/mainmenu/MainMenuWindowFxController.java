package sep.fimball.view.window.mainmenu;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.FxmlLoader;
import sep.fimball.view.window.Window;
import sep.fimball.viewmodel.MainMenuViewModel;
import sep.fimball.viewmodel.ViewModelSceneManager;
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
            FxmlLoader fxmlLoader = new FxmlLoader("mainmenu/mainMenuPreview.fxml");
            machineOverview.getChildren().add(fxmlLoader.getRootNode());
            ((MainMenuPreviewFxController) fxmlLoader.getFxController()).bindToViewModel(preview);
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
