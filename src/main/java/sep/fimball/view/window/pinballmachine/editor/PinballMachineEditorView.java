package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.general.ViewUtil;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.tools.ViewLoader;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.EditorPreviewSubViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.EditorSessionSubViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;


/**
 * Die PinballMachineEditorView ist für die Darstellung des Editors zuständig und ermöglicht dem Nutzer, einen Flipperautomaten
 * zu bearbeiten.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{
    /**
     * Beinhaltet alle Elemente (Obere Seite des Bildes), die vom Nutzer aktuell gedragged werden.
     */
    @FXML
    public Pane previewBaseTop;

    /**
     * Beinhaltet alle Elemente (Untere Seite des Bildes), die vom Nutzer aktuell gedragged werden.
     */
    @FXML
    public Pane previewBaseBottom;

    /**
     * Zeigt den Namen des editierten Automaten an. Über dieses Feld kann der Name auch geändert werden.
     */
    @FXML
    private TextField tableName;

    /**
     * Enthält das Pinball-Canvas des Automaten.
     */
    @FXML
    private StackPane pinballCanvasContainer;

    /**
     * Das zur Zeit auf dem Spielfeld vom Nutzer ausgewählte Element.
     */
    @FXML
    private VBox selectedElement;

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
     * Das zur PinballMachineEditorView gehörende PinballMachineEditorViewModel.
     */

    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    /**
     * Das zur PinballMachineEditorView gehörende EditorSessionSubViewModel.
     */
    private EditorSessionSubViewModel editorSessionSubViewModel;

    /**
     * Das letzte MouseEvent, bei dem die Maus auf dem Spielfeld gedrückt worden ist.
     */
    private MouseEvent mouseDown;

    @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
        this.editorSessionSubViewModel = pinballMachineEditorViewModel.getEditorSessionSubViewModel();

        tableName.textProperty().bindBidirectional(pinballMachineEditorViewModel.machineNameProperty());

        ViewModelListToPaneBinder.<AvailableElementSubView, AvailableElementSubViewModel>bindViewModelsToViews(availableElementsBasic, pinballMachineEditorViewModel.availableBasicElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT, (view, viewModel) -> view.init(viewModel, this));
        ViewModelListToPaneBinder.<AvailableElementSubView, AvailableElementSubViewModel>bindViewModelsToViews(availableElementsObstacles, pinballMachineEditorViewModel.availableObstacleElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT, (view, viewModel) -> view.init(viewModel, this));
        ViewModelListToPaneBinder.<AvailableElementSubView, AvailableElementSubViewModel>bindViewModelsToViews(availableElementsRamp, pinballMachineEditorViewModel.availableRampElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT, (view, viewModel) -> view.init(viewModel, this));
        ViewModelListToPaneBinder.<AvailableElementSubView, AvailableElementSubViewModel>bindViewModelsToViews(availableElementsAdvanced, pinballMachineEditorViewModel.availableAdvancedElementsProperty(), WindowType.EDITOR_AVAILABLE_ELEMENT, (view, viewModel) -> view.init(viewModel, this));

        ViewLoader<PinballCanvasSubView> viewLoaderCanvas = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoaderCanvas.getRootNode());
        viewLoaderCanvas.getView().setViewModel(editorSessionSubViewModel.getPinballCanvasViewModel());

        ViewModelListToPaneBinder.<EditorPreviewSubView, EditorPreviewSubViewModel>bindViewModelsToViews(previewBaseTop, pinballMachineEditorViewModel.previewsProperty(), WindowType.EDITOR_PREVIEW, (view, viewModel) -> view.init(viewModel, this, ImageLayer.TOP));
        previewBaseTop.setPickOnBounds(false);
        previewBaseTop.setMaxHeight(0);
        previewBaseTop.setMaxWidth(0);

        ViewModelListToPaneBinder.<EditorPreviewSubView, EditorPreviewSubViewModel>bindViewModelsToViews(previewBaseBottom, pinballMachineEditorViewModel.previewsProperty(), WindowType.EDITOR_PREVIEW, (view, viewModel) -> view.init(viewModel, this, ImageLayer.BOTTOM));
        previewBaseBottom.setPickOnBounds(false);
        previewBaseBottom.setMaxHeight(0);
        previewBaseBottom.setMaxWidth(0);

        ViewLoader<SelectedElementSubView> viewLoader = new ViewLoader<>(WindowType.EDITOR_SELECTED_ELEMENT);
        selectedElement.getChildren().add(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(pinballMachineEditorViewModel.getSelectedElementSubViewModel());

    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer eine Drag-Bewegung mit der Maus ausgeführt hat.
     *
     * @param mouseEvent Das Event, das die "Drag"-Bewegung ausgelöst hat.
     */
    public void dragged(MouseEvent mouseEvent)
    {
        pinballMachineEditorViewModel.dragged(mouseDown.getX(), mouseDown.getY(), mouseEvent.getX(), mouseEvent.getY(), canvasPixelToGridPos(new Vector2(mouseEvent.getX(), mouseEvent.getY())), mouseEvent.getButton());
        mouseDown = mouseEvent;
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer eine Drag-Bewegung mit der Maus beendet hat.
     *
     * @param event Das Event, das die "Drag"-Bewegung beendet hat.
     */
    public void draggedOver(MouseDragEvent event)
    {
        pinballMachineEditorViewModel.dragged(mouseDown.getX(), mouseDown.getY(), event.getX(), event.getY(), canvasPixelToGridPos(new Vector2(event.getX(), event.getY())), event.getButton());
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
        pinballMachineEditorViewModel.mouseEnteredCanvas(canvasPixelToGridPos(new Vector2(mouseEvent.getX(), mouseEvent.getY())));
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
        pinballMachineEditorViewModel.mouseEnteredCanvas(canvasPixelToGridPos(new Vector2(event.getX(), event.getY())));
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
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass der Nutzer den bearbeiteten Automaten spielen möchte.
     */
    @FXML
    private void playClicked()
    {
        editorSessionSubViewModel.startPinballMachine();
    }

    /**
     * Benachrichtigt das {@code editorSessionSubViewModel}, dass der Nutzer in das Hauptmenü wechseln möchte.
     */
    @FXML
    private void menuClicked()
    {
        editorSessionSubViewModel.exitToMainMenu();
    }

    /**
     * Benachrichtigt das {@code editorSessionSubViewModel}, dass der Nutzer speichern möchte.
     */
    @FXML
    private void saveClicked()
    {
        editorSessionSubViewModel.savePinballMachine();
    }

    /**
     * Benachrichtigt das {@code editorSessionSubViewModel}, dass der Nutzer den Automaten löschen möchte.
     */
    @FXML
    private void deleteClicked()
    {
        editorSessionSubViewModel.deletePinballMachine();
    }

    /**
     * Berechnet die Position der Maus auf dem Canvas und gibt diese zurück.
     *
     * @param mousePos Die Position der Maus.
     * @return Die berechnete Position auf dem Canvas.
     */
    private Vector2 canvasPixelToGridPos(Vector2 mousePos)
    {
        return ViewUtil.canvasPixelToGridPos(
                pinballMachineEditorViewModel.cameraPositionProperty().get(),
                pinballMachineEditorViewModel.cameraZoomProperty().get(),
                new Vector2(mousePos.getX(), mousePos.getY()),
                new Vector2(pinballCanvasContainer.getWidth(), pinballCanvasContainer.getHeight())
        );
    }

    /**
     * Nimmt eine Position auf der JavaFX scene und rechnet die entsprechende Position auf dem Grid aus.
     *
     * @param sceneX Die Position in der JavaFX scene.
     * @param sceneY Die Position in der JavaFX scene.
     * @return Die berechnete Position auf dem Grid.
     */
    Vector2 scenePixelToGridPos(double sceneX, double sceneY)
    {
        Point2D canvasPos = pinballCanvasContainer.sceneToLocal(sceneX, sceneY);
        return canvasPixelToGridPos(new Vector2(canvasPos.getX(), canvasPos.getY()));
    }

    /**
     * Nimmt eine Position in Grid-Einheiten und rechnet die entsprechende Position auf dem Canvas in Pixeln aus.
     *
     * @param gridPos Die Position auf dem Grid.
     * @return Die berechnete Position auf dem Canvas.
     */
    Vector2 gridToCanvasPixelPos(Vector2 gridPos)
    {
        Point2D canvasScenePos = pinballCanvasContainer.localToScene(0, 0);
        return ViewUtil.gridToCanvasPixelPos(
                pinballMachineEditorViewModel.cameraPositionProperty().get(),
                pinballMachineEditorViewModel.cameraZoomProperty().get(),
                new Vector2(gridPos.getX(), gridPos.getY()),
                new Vector2(pinballCanvasContainer.getWidth(), pinballCanvasContainer.getHeight()).plus(new Vector2(canvasScenePos.getX(), canvasScenePos.getY()).scale(2))
        );
    }
}
