package sep.fimball.view.window.mainmenu;

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
    public Pane previewImage;
    @FXML
    public Label previewName;

    public void bindToViewModel(TableBlueprintPreview preview)
    {
        previewName.textProperty().bind(preview.getNameProperty());
        String css = "-fx-background-image: url(\"" + preview.getImagePathProperty().get() + "\");";
        previewImage.setStyle(css);
    }
}
