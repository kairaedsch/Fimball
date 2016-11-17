package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewLoader;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer, einen Flipperautomaten zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    @FXML
    private StackPane pinballCanvasContainer;

    /**
     * Das Canvas, in dem der Flipperautomat gezeichnet wird.
     */
    private Canvas canvas;

    /**
     * Das zur Zeit auf dem Spielfeld vom Nutzer ausgewählte Element.
     */
    @FXML
    private TitledPane selectedElement;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Elemente.
     */
    @FXML
    private VBox availableElements;

    /**
     * Das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    /**
     * Setzt das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     *
     * @param pinballMachineEditorViewModel Das zu setzende PinballMachineEditorViewModel.
     */
    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(availableElements, pinballMachineEditorViewModel.availableElementsProperty(), WindowType.TABLE_EDITOR_AVAILABLE_ELEMENT);

        ViewLoader<PinballCanvasSubView> viewLoaderCanvas = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoaderCanvas.getRootNode());
        viewLoaderCanvas.getView().setViewModel(pinballMachineEditorViewModel.getPinballCanvasViewModel());

        ViewLoader<SelectedElementSubView> viewLoader = new ViewLoader<>(WindowType.TABLE_EDITOR_SELECTED_ELEMENT);
        selectedElement.setContent(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(pinballMachineEditorViewModel.getSelectedElementSubViewModel());
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den bearbeiteten Automaten spielen möchte.
     */
    @FXML
    private void playClicked() {
        pinballMachineEditorViewModel.startPinballMachine();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer in die Editoreinstellungen wechseln möchte.
     */
    @FXML
    private void settingsClicked() {
        pinballMachineEditorViewModel.showSettingsDialog();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den geöffneten Automaten heranzoomen möchte.
     */
    @FXML
    private void zoomInClicked() {
        pinballMachineEditorViewModel.zoomIn();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer aus dem geöffneten Automaten herauszoomen möchte.
     */
    @FXML
    private void zoomOutClicked() {
        pinballMachineEditorViewModel.zoomOut();
    }
}
