package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachineSelectorSubViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSelectorSubView implements ViewBoundToViewModel<PinballMachineSelectorSubViewModel>
{
    @FXML
    private Pane previewImage;
    @FXML
    private Label previewName;

    private PinballMachineSelectorSubViewModel pinballMachineSelectorSubViewModel;

    public void setViewModel(PinballMachineSelectorSubViewModel pinballMachineSelectorSubViewModel)
    {
        this.pinballMachineSelectorSubViewModel = pinballMachineSelectorSubViewModel;
        previewName.textProperty().bind(pinballMachineSelectorSubViewModel.nameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", pinballMachineSelectorSubViewModel.imagePathProperty().get(), "\");"));
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        pinballMachineSelectorSubViewModel.clicked();
    }
}
