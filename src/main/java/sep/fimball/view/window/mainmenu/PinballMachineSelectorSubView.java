package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachineSelectorSubViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSelectorSubView
{
    @FXML
    private Pane previewImage;
    @FXML
    private Label previewName;

    private IntegerProperty blueprintElementId;

    private MainMenuViewModel mainMenuViewModel;

    public void setViewModel(MainMenuViewModel mainMenuViewModel, PinballMachineSelectorSubViewModel preview)
    {
        this.mainMenuViewModel = mainMenuViewModel;
        previewName.textProperty().bind(preview.nameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", preview.imagePathProperty().get(), "\");"));
        blueprintElementId = new SimpleIntegerProperty();
        blueprintElementId.bind(preview.blueprintTableIdProperty());
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        mainMenuViewModel.blueprintPreviewClick(blueprintElementId.get());
    }
}
