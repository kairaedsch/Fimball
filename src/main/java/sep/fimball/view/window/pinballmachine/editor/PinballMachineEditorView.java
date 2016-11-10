package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer einen Flipperautomaten zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    /**
     * Das Canvas, in welchen der Flipperautomat gezeichnet wird.
     */
    private Canvas canvas;

    /**
     * Das zurzeit auf dem Spielfeld vom Nutzer ausgewählte Element.
     */
    @FXML
    private Node selectedElement;

    /**
     * Die zum Platzieren auf dem Spielfeld verfügbaren Elemente.
     */
    @FXML
    private VBox availableElements;

    /**
     * Das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    /**
     * Setzt das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     * @param pinballMachineEditorViewModel
     */
    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
    }

    @FXML
    private void playClicked() {

    }

    @FXML
    private void settingsClicked() {

    }

    @FXML
    private void zoomInClicked() {

    }

    @FXML
    private void zoomOutClicked() {

    }
}
