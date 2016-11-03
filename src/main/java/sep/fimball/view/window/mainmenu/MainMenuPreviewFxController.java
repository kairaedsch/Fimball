package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

    public void bindToViewModel(TableBlueprintPreview preview)
    {
        previewName.textProperty().bind(preview.getNameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", preview.getImagePathProperty().get(), "\");"));
    }
}
