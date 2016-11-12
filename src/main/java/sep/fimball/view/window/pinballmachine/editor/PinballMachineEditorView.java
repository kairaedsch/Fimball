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
     * @param pinballMachineEditorViewModel Das zu setzende PinballMachineEditorViewModel.
     */
    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den editierten Automaten spielen möchte.
     */
    @FXML
    private void playClicked() {

    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer in die Editoreinstellungen wechsseln möchte.
     */
    @FXML
    private void settingsClicked() {

    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer in den editierten Automaten reinzoomen möchte.
     */
    @FXML
    private void zoomInClicked() {

    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer aus dem editierten Automaten herauszoomen möchte.
     */
    @FXML
    private void zoomOutClicked() {

    }
}
