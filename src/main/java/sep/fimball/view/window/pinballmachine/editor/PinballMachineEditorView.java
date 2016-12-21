package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.view.tools.ViewLoader;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.util.Optional;

import static sep.fimball.general.data.DesignConfig.PIXELS_PER_GRID_UNIT;


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
     * Die zur Platzierung auf dem Spielfeld verfügbaren Rampen.
     */
    @FXML
    private VBox availableElementsRamp;

    /**
     * Die obere Pane der Drag-Vorschau.
     */
    @FXML
    private Pane previewTop;

    /**
     * Die untere Pane der Drag-Vorschau.
     */
    @FXML
    private Pane previewBot;

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

        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsBasic, pinballMachineEditorViewModel.availableBasicElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);
        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsObstacles, pinballMachineEditorViewModel.availableObstacleElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);
        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsRamp, pinballMachineEditorViewModel.availableRampElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);
        ViewModelListToPaneBinder.bindViewModelsToViews(availableElementsAdvanced, pinballMachineEditorViewModel.availableAdvancedElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT);

        ViewLoader<PinballCanvasSubView> viewLoaderCanvas = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoaderCanvas.getRootNode());
        viewLoaderCanvas.getView().setViewModel(pinballMachineEditorViewModel.getPinballCanvasViewModel());

        ViewLoader<SelectedElementSubView> viewLoader = new ViewLoader<>(WindowType.EDITOR_SELECTED_ELEMENT);
        selectedElement.setContent(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(pinballMachineEditorViewModel.getSelectedElementSubViewModel());

        previewTop.disableProperty().bind(pinballMachineEditorViewModel.isAvailableElementSelected());

        pinballMachineEditorViewModel.getTopBackgroundPath().addListener((observable, oldValue, newValue) -> bindPaneToImage(newValue, previewTop));
        pinballMachineEditorViewModel.getBotBackgroundPath().addListener((observable, oldValue, newValue) -> bindPaneToImage(newValue, previewBot));
    }

    /**
     * Bindet die gegebene Pane an das gegeben Image.
     *
     * @param imagePath Der Pfad zu dem Image, an den sich die Pane binden soll.
     * @param pane      Die Pane, die sich an das Image binden soll.
     */
    private void bindPaneToImage(Optional<String> imagePath, Pane pane)
    {
        if (imagePath.isPresent())
        {
            pane.styleProperty().set(DesignConfig.fillBackgroundImageCss(imagePath.get()));

            ImageCache cache = ImageCache.getInstance();
            pane.prefWidthProperty().unbind();
            pane.prefWidthProperty().bind(Bindings.multiply(pinballMachineEditorViewModel.cameraZoomProperty(), cache.getImage(imagePath.get()).widthProperty()));
            pane.prefHeightProperty().unbind();
            pane.prefHeightProperty().bind(Bindings.multiply(pinballMachineEditorViewModel.cameraZoomProperty(), cache.getImage(imagePath.get()).heightProperty()));
        }
        else
        {
            pane.styleProperty().set(DesignConfig.CSS_NO_IMAGE);
        }
    }

    /**
     * Fügt EventFilter zur Erkennung von Maus-Bewegungen zur {@code stage} hinzu.
     *
     * @param stage Die Stage.
     */
    public void addEventFilterToStage(Stage stage)
    {
        stage.addEventFilter(MouseEvent.MOUSE_MOVED, this::updatePreviewPosition);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::updatePreviewPosition);
    }

    /**
     * Aktualisiert die Position der Drag-Vorschau.
     *
     * @param event Das Event, das die Position des Vorschaubildes bestimmt.
     */
    private void updatePreviewPosition(MouseEvent event)
    {
        previewBot.setTranslateX(event.getX());
        previewBot.setTranslateY(event.getY());

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
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer in die Editoreinstellungen wechseln möchte.
     */
    @FXML
    private void abortClicked()
    {
        pinballMachineEditorViewModel.goToMainMenu();
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
    public void dragged(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.dragged(mouseDown.getX(), mouseDown.getY(), mouseEvent.getX(), mouseEvent.getY(), mousePosToCanvasPos(new Vector2(mouseEvent.getX(), mouseEvent.getY())), mouseEvent.getButton());
        mouseDown = mouseEvent;
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer eine Drag-Bewegung mit der Maus beendet hat.
     *
     * @param event Das Event, das die "Drag"-Bewegung beendet hat.
     */
    public void draggedOver(MouseDragEvent event)
    {
        pinballMachineEditorViewModel.dragged(mouseDown.getX(), mouseDown.getY(), event.getX(), event.getY(), mousePosToCanvasPos(new Vector2(event.getX(), event.getY())), event.getButton());
        mouseDown = event;
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
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer mit der Maus in den Pinball-Canvas gefahren ist.
     *
     * @param mouseEvent Das Event, in dem der Nutzer mit der Maus in den Pinball-Canvas gefahren ist.
     */
    public void mouseEnteredCanvas(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.mouseEnteredCanvas(mousePosToCanvasPos(new Vector2(mouseEvent.getX(), mouseEvent.getY())));
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer mit der Maus den Pinball-Canvas verlassen hat.
     */
    public void mouseExitedCanvas()
    {
        pinballMachineEditorViewModel.mouseExitedCanvas();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer mit der Maus in einer Drag-Bewegung in den
     * Pinball-Canvas
     * gefahren ist.
     *
     * @param event Das Event, in dem der Nutzer mit der Maus in einer Drag-Bewegung in den Pinball-Canvas gefahren ist.
     */
    public void mouseDragEnteredCanvas(MouseDragEvent event)
    {
        mouseDown = event;
        pinballMachineEditorViewModel.mouseEnteredCanvas(mousePosToCanvasPos(new Vector2(event.getX(), event.getY())));
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer mit der Maus in einer Drag-Bewegung den
     * Pinball-Canvas verlassen hat.
     */
    public void mouseDragExitedCanvas()
    {
        pinballMachineEditorViewModel.mouseExitedCanvas();
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer die Maustaste losgelassen hat.
     *
     * @param mouseEvent Das Event, in dem der Nutzer die Maustaste losgelassen hat.
     */
    public void mouseReleased(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.mouseReleased(mouseEvent);
    }

    /**
     * Berechnet die Position der Maus auf dem Canvas und gibt diese zurück.
     *
     * @param mousePos Die Position der Maus.
     * @return Die berechnete Position auf dem Canvas.
     */
    private Vector2 mousePosToCanvasPos(Vector2 mousePos)
    {
        Vector2 posToMiddle = new Vector2(mousePos.getX(), mousePos.getY()).minus(new Vector2(pinballCanvasContainer.getWidth(), pinballCanvasContainer.getHeight()).scale(0.5));
        return posToMiddle.scale(1 / (PIXELS_PER_GRID_UNIT * pinballMachineEditorViewModel.cameraZoomProperty().get())).plus(pinballMachineEditorViewModel.cameraPositionProperty().get());

    }
}
