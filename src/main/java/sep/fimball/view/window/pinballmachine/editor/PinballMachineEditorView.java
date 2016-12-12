package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.mockito.junit.VerificationCollector;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.tools.ViewLoader;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import static sep.fimball.general.data.Config.pixelsPerGridUnit;


/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer, einen Flipperautomaten
 * zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
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
     * Die zur Platzierung auf dem Spielfeld verfügbaren Basis-Elemente.
     */
    @FXML
    private VBox availableElementsBasic;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Spezial-Elemente.
     */
    @FXML
    private VBox availableElementsAdvanced;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Hindernisse.
     */
    @FXML
    private VBox availableElementsObstacles;
    /**
     * Das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    /**
     * Das letzte MouseEvent, bei dem die Maus auf dem Spielfeld gedrückt worden ist.
     */
    private MouseEvent mouseDown;

    private boolean onCanvas = false;

    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;

        nameLabel.textProperty().bind(pinballMachineEditorViewModel.machineNameProperty());

        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsBasic, pinballMachineEditorViewModel
                .availableBasicElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);
        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsObstacles, pinballMachineEditorViewModel
                .availableObstacleElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);
        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsAdvanced, pinballMachineEditorViewModel
                .availableAdvancedElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);

        ViewLoader<PinballCanvasSubView> viewLoaderCanvas = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoaderCanvas.getRootNode());
        viewLoaderCanvas.getView().setViewModel(pinballMachineEditorViewModel.getPinballCanvasViewModel());

        ViewLoader<SelectedElementSubView> viewLoader = new ViewLoader<>(WindowType.EDITOR_SELECTED_ELEMENT);
        selectedElement.setContent(viewLoader.getRootNode());

        // TODO visualize selected elements with pinballMachineEditorViewModel.getSelectionRect()
        //viewLoader.getView().setViewModel(pinballMachineEditorViewModel.getSelectedElementSubViewModel());
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
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer abhängig von der Richtung des Scrollens im
     * ScrollEvent hinein- oder herauszoomen möchte.
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
    public void mouseDragged(MouseEvent mouseEvent)
    {
        Vector2 start = screenPosToCanvasPos(new Vector2(mouseDown.getX(), mouseDown.getY()));
        Vector2 end = screenPosToCanvasPos(new Vector2(mouseEvent.getX(), mouseEvent.getY()));
        pinballMachineEditorViewModel.mouseDragged(start.getX(), start.getY(), end.getX(), end.getY(), mouseEvent.getButton());
        mouseDown = mouseEvent;
    }

    public void mouseEntered(MouseEvent mouseEvent)
    {
        System.out.println("mouse entered");
        onCanvas = true;
        pinballMachineEditorViewModel.mouseEnteredCanvas(new Vector2(0, 0));
    }

    public void mouseExited(MouseEvent mouseEvent)
    {
        System.out.println("mouse exited");
        onCanvas = false;
        pinballMachineEditorViewModel.mouseExitedCanvas();
    }

    /**
     * Setzt das gegebene MouseEvent als das Event, bei dem als letztes auf dem Spielfeld die Maustaste gedrückt wurde.
     *
     * @param mouseEvent Das MouseEvent, bei dem die Maustaste auf dem Spielfeld gedrückt wurde.
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        mouseDown = mouseEvent;
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {

        if (onCanvas)
        {
            System.out.println("mouse released on Canvas");
            pinballMachineEditorViewModel.mouseReleasedOnCanvas(mouseEvent);
        }
        else
        {
            System.out.println("mouse released");
            pinballMachineEditorViewModel.mouseReleased(mouseEvent);
        }
    }

    private Vector2 screenPosToCanvasPos(Vector2 screenPos)
    {
        Vector2 posToMiddle = new Vector2(screenPos.getX() - pinballCanvasContainer.getWidth() / 2.0, screenPos.getY() - pinballCanvasContainer.getHeight() / 2.0);
        double vx = posToMiddle.getX() / (pixelsPerGridUnit * pinballMachineEditorViewModel.cameraZoomProperty().get()) + pinballMachineEditorViewModel.cameraPositionProperty().get().getX();
        double vy = posToMiddle.getY() / (pixelsPerGridUnit * pinballMachineEditorViewModel.cameraZoomProperty().get()) + pinballMachineEditorViewModel.cameraPositionProperty().get().getY();
        return new Vector2(vx, vy);
    }
}
