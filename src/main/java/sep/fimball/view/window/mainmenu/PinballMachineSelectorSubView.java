package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachineSelectorSubViewModel;

/**
 * Die PinballMachineSelectorSubView ist für die Darstellung der Vorschau eines Flipperautomaten in der Vorschauliste zuständig und ermöglicht dem Nutzer, diesen auszuwählen, wodurch eine größere Vorschau im MainMenuView erscheint.
 */
public class PinballMachineSelectorSubView implements ViewBoundToViewModel<PinballMachineSelectorSubViewModel>
{
    /**
     * Zeigt das Vorschaubild des Flipperautomaten an.
     */
    @FXML
    private Pane previewImage;

    /**
     * Zeigt den Namen des Flipperautomaten an.
     */
    @FXML
    private Label previewName;

    /**
     * Das zum PinballMachineSelectorSubView gehörende PinballMachineSelectorSubViewModel.
     */
    private PinballMachineSelectorSubViewModel pinballMachineSelectorSubViewModel;

    /**
     * Setzt das zur PinballMachineSelectorSubView gehörende PinballMachineSelectorSubViewModel.
     * @param pinballMachineSelectorSubViewModel
     */
    public void setViewModel(PinballMachineSelectorSubViewModel pinballMachineSelectorSubViewModel)
    {
        this.pinballMachineSelectorSubViewModel = pinballMachineSelectorSubViewModel;
        previewName.textProperty().bind(pinballMachineSelectorSubViewModel.nameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"", pinballMachineSelectorSubViewModel.imagePathProperty().get(), "\");"));
    }

    /**
     * Benachrichtigt das PinballMachineSelectorSubViewModel, dass der Nutzer den Flipperautomaten auswählen will, da er ihn im Detail sehen will, sodass er diesen auch starten oder editieren kann.
     */
    public void mouseClicked()
    {
        pinballMachineSelectorSubViewModel.selectPinballMachine();
    }
}
