package sep.fimball.view.window.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachinePreviewSubViewModel;

/**
 * Die PinballMachinePreviewSubView ist für die Darstellung der Vorschau eines Flipperautomaten in der Vorschau-Liste zuständig und ermöglicht dem Nutzer, diesen auszuwählen, wodurch eine größere Vorschau im MainMenuView erscheint.
 */
public class PinballMachinePreviewSubView implements ViewBoundToViewModel<PinballMachinePreviewSubViewModel>
{
    @FXML
    private HBox machine;

    /**
     * Zeigt das Vorschau-Bild des Flipperautomaten an.
     */
    @FXML
    private Pane previewImage;

    /**
     * Zeigt den Namen des Flipperautomaten an.
     */
    @FXML
    private Label previewName;

    /**
     * Das zum PinballMachinePreviewSubView gehörende PinballMachinePreviewSubViewModel.
     */
    private PinballMachinePreviewSubViewModel pinballMachinePreviewSubViewModel;

    @Override
    public void setViewModel(PinballMachinePreviewSubViewModel pinballMachinePreviewSubViewModel)
    {
        this.pinballMachinePreviewSubViewModel = pinballMachinePreviewSubViewModel;
        previewName.textProperty().bind(pinballMachinePreviewSubViewModel.nameProperty());
        previewImage.styleProperty().bind(DesignConfig.backgroundImageCss(pinballMachinePreviewSubViewModel.imagePathProperty()));

        pinballMachinePreviewSubViewModel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) machine.getStyleClass().add("selected");
            else machine.getStyleClass().remove("selected");
        });

        if(pinballMachinePreviewSubViewModel.selectedProperty().get()) machine.getStyleClass().add("selected");
        else machine.getStyleClass().remove("selected");
    }

    /**
     * Benachrichtigt das PinballMachinePreviewSubViewModel, dass der Nutzer den Flipperautomaten auswählen will, um ihn im Detail zu sehen, sodass er diesen auch starten oder bearbeiten kann.
     */
    public void mouseClicked()
    {
        pinballMachinePreviewSubViewModel.selectPinballMachine();
    }
}
