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
     * Der Button, der den "Drag"-Modus der Bedienung stellt.
     */
    @FXML
    private Button dragButton;

    /**
     * Der Button, der den "Auswählen"-Modus der Bedienung stellt.
     */
    @FXML
    private Button selectButton;

    /**
     * Der Button, der den "Platzieren"-Modus der Bedienung stellt.
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
     * Das letzte MouseEvent, bei dem die Maus auf dem Spielfeld gedrückt worden ist.
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
        dragButton.disabledProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.DRAGGING));
        selectButton.disabledProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.SELECTING));
        placeButton.disabledProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.PLACING));
        pinballMachineEditorViewModel.mouseModeProperty().addListener((observable, oldValue, newValue) -> dragButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.DRAGGING));
        pinballMachineEditorViewModel.mouseModeProperty().addListener((observable, oldValue, newValue) -> selectButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.SELECTING));
        pinballMachineEditorViewModel.mouseModeProperty().addListener((observable, oldValue, newValue) -> placeButton.setDisable(pinballMachineEditorViewModel.mouseModeProperty().get() == MouseMode.PLACING));
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den bearbeiteten Automaten spielen möchte.
     */
    @FXML
    private void playClicked()
    {
        pinballMachineEditorViewModel.startPinballMachine();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer in die Editoreinstellungen wechseln möchte.
     */
    @FXML
    private void settingsClicked()
    {
        pinballMachineEditorViewModel.showSettingsDialog();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den geöffneten Automaten hineinzoomen möchte.
     */
    @FXML
    private void zoomInClicked()
    {
        pinballMachineEditorViewModel.zoomIn();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer aus dem geöffneten Automaten herauszoomen möchte.
     */
    @FXML
    private void zoomOutClicked()
    {
        pinballMachineEditorViewModel.zoomOut();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer abhängig von der Richtung des Scrollens im ScrollEvent hinein- oder herauszoomen möchte.
     *
     * @param scrollEvent Das ScrollEvent, das verarbeitet werden soll.
     */
    public void zoom(ScrollEvent scrollEvent)
    {
        if (scrollEvent.getDeltaY() < 0)
            pinballMachineEditorViewModel.zoomOut();
        else if (scrollEvent.getDeltaY() > 0)
            pinballMachineEditorViewModel.zoomIn();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer eine Drag-Bewegung mit der Maus ausgeführt hat.
     *
     * @param mouseEvent Das Event, das die "Drag"-Bewegung ausgelöst hat.
     */
    public void dragged(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.dragged(mouseEvent.getX() - mouseDown.getX(), mouseEvent.getY() - mouseDown.getY());
        mouseDown = mouseEvent;
    }

    /**
     * Setzt das gegebene MouseEvent als das Event, bei dem als letztes auf dem Spielfeld die Maustaste gedrückt wurde.
     *
     * @param mouseEvent Das MouseEvent, bei dem die Maustaste auf dem Spielfeld gedrückt wurde.
     */
    public void down(MouseEvent mouseEvent)
    {
        mouseDown = mouseEvent;
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Benutzer in den "Platzieren"-Modus wechseln möchte.
     *
     * @param mouseEvent Das MouseEvent, das den Wechsel auslöst.
     */
    public void placeClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseMode(MouseMode.PLACING);
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Benutzer in den "Auswählen"-Modus wechseln möchte.
     *
     * @param mouseEvent Das MouseEvent, das den Wechsel auslöst.
     */
    public void selectClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseMode(MouseMode.SELECTING);
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Benutzer in den "Drag"-Modus wechseln möchte.
     *
     * @param mouseEvent Das MouseEvent, das den Wechsel auslöst.
     */
    public void dragClicked(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.setMouseMode(MouseMode.DRAGGING);
    }
}
