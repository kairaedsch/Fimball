package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
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
import sep.fimball.viewmodel.window.pinballmachine.editor.MouseMode;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer, einen Flipperautomaten zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    /**
     * TODO
     */
    @FXML
    private Button dragButton;

    /**
     * TODO
     */
    @FXML
    private Button selectButton;

    /**
     * TODO
     */
    @FXML
    private Button placeButton;

    /**
     * Zeigt den Namen des editierten Automaten an.
     */
    @FXML
    private Label nameLabel;

    /**
     * Enthält das Pinball-Canvas des Automaten.
     */
    @FXML
    private StackPane pinballCanvasContainer;

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
     * TODO
     */
    private MouseEvent mouseDown;

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

        //TODO make mor beautyful
        dragButton.disabledProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.DRAGGING));
        selectButton.disabledProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.SELECTING));
        placeButton.disabledProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.PLACING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.DRAGGING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.SELECTING));
        pinballMachineEditorViewModel.mouseModusProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModusProperty().get() == MouseMode.PLACING));
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

    /**
     * Benachrichtig das {@code pinballMachineEditorViewModel}, dass der Nutzer abhängig von der Richtung des Scrollens im ScrollEvent rein- oder rauszoomen möchte.
     * @param scrollEvent
     */
    public void zoom(ScrollEvent scrollEvent)
    {
        if(scrollEvent.getDeltaY() < 0) pinballMachineEditorViewModel.zoomOut();
        else if(scrollEvent.getDeltaY() > 0) pinballMachineEditorViewModel.zoomIn();
    }

    /**
     * TODO
     * @param mouseEvent
     */
    public void dragged(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.dragged(mouseEvent.getX() - mouseDown.getX(), mouseEvent.getY() - mouseDown.getY());
        mouseDown = mouseEvent;
    }

    /**
     * TODO
     * @param mouseEvent
     */
    public void down(MouseEvent mouseEvent)
    {
        mouseDown = mouseEvent;
    }

    /**
     * TODO
     * @param mouseEvent
     */
    public void placeClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseMode.PLACING);
    }

    /**
     * TODO
     * @param mouseEvent
     */
    public void selectClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseMode.SELECTING);
    }

    /**
     * TODO
     * @param mouseEvent
     */
    public void dragClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseModus(MouseMode.DRAGGING);
    }
}
