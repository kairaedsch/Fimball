package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    /**
     * Das Canvas, in welchen der Flipperautomat gezeichnet wird.
     */
    private Canvas canvas;

    /**
     *
     */
    @FXML
    private Node selectedElement;

    /**
     *
     */
    @FXML
    private VBox AvailableElements;

    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

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
