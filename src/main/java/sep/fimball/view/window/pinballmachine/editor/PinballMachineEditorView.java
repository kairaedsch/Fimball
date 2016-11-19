package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewLoader;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.MouseModus;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer, einen Flipperautomaten zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    @FXML
    private Button dragButton;

    @FXML
    private Button selectButton;

    @FXML
    private Button placeButton;

    @FXML
    private Label nameLabel;

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

    private MouseEvent mouseDown;

    /**
     * Setzt das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     *
     * @param pinballMachineEditorViewModel Das zu setzende PinballMachineEditorViewModel.
     */
    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;

        nameLabel.textProperty().bind(pinballMachineEditorViewModel.machineNameProperty());

        ViewModelListToPaneBinder.bindViewModelsToViews(availableElements, pinballMachineEditorViewModel.availableElementsProperty(), WindowType.TABLE_EDITOR_AVAILABLE_ELEMENT);

        ViewLoader<PinballCanvasSubView> viewLoaderCanvas = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoaderCanvas.getRootNode());
        viewLoaderCanvas.getView().setViewModel(pinballMachineEditorViewModel.getPinballCanvasViewModel());

        ViewLoader<SelectedElementSubView> viewLoader = new ViewLoader<>(WindowType.TABLE_EDITOR_SELECTED_ELEMENT);
        selectedElement.setContent(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(pinballMachineEditorViewModel.getSelectedElementSubViewModel());

        dragButton.disabledProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.DRAGGING));
        selectButton.disabledProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.SELECTING));
        placeButton.disabledProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.PLACING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.DRAGGING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.SELECTING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseModus.PLACING));
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

    public void zoom(ScrollEvent scrollEvent)
    {
        if(scrollEvent.getDeltaY() < 0) pinballMachineEditorViewModel.zoomOut();
        else if(scrollEvent.getDeltaY() > 0) pinballMachineEditorViewModel.zoomIn();
    }

    public void dragged(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.dragged(mouseEvent.getX() - mouseDown.getX(), mouseEvent.getY() - mouseDown.getY());
        mouseDown = mouseEvent;
    }

    public void down(MouseEvent mouseEvent)
    {
        mouseDown = mouseEvent;
    }

    public void placeClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseModus.PLACING);
    }

    public void selectClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseModus.SELECTING);
    }

    public void dragClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseModus.DRAGGING);
    }
}
