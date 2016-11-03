package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.viewmodel.MainMenuViewModel;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuPreviewFxController
{
    @FXML
    private Pane previewImage;
    @FXML
    private Label previewName;

    private SimpleIntegerProperty blueprintElementId;

    private MainMenuViewModel mainMenuViewModel;

    public void bindToViewModel(MainMenuViewModel mainMenuViewModel, TableBlueprintPreview preview)
    {
        this.mainMenuViewModel = mainMenuViewModel;
        previewName.textProperty().bind(preview.getNameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", preview.getImagePathProperty().get(), "\");"));
        blueprintElementId = new SimpleIntegerProperty();
        blueprintElementId.bind(preview.blueprintTableIdProperty());
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        mainMenuViewModel.blueprintPreviewClick(blueprintElementId.get());
    }
}
