package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Cursor;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.message.busy.BusyMessageViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen Flipper-Automaten zur Verfügung und ermöglicht es,
 * diesen zu bearbeiten.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    /**
     * Der zugehörige PinballMachineEditor.
     */
    private PinballMachineEditor pinballMachineEditor;

    /**
     * Das korrespondierende EditorSessionSubViewModel.
     */
    private EditorSessionSubViewModel editorSessionSubViewModel;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Basis-Elemente.
     */
    private ListProperty<AvailableElementSubViewModel> availableBasicElements;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Hindernisse.
     */
    private ListProperty<AvailableElementSubViewModel> availableObstacleElements;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Rampen.
     */
    private ListProperty<AvailableElementSubViewModel> availableRampElements;

    /**
     * Die zur Platzierung auf dem Spielfeld verfügbaren Spezial-Elemente.
     */
    private ListProperty<AvailableElementSubViewModel> availableAdvancedElements;

    /**
     * Die aktuelle Position der Kamera, die ihren Wert immer an die Kamera-Position im PinballCanvasViewModel sendet.
     * Dies geschieht durch Property-Binding.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Die aktuelle Stärke des Zooms der Kamera, die ihren Wert immer an die Kamera Stärke des Zooms in
     * PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
     */
    private DoubleProperty cameraZoom;

    /**
     * Der Name des editierten Automaten.
     */
    private StringProperty machineName;

    /**
     * Das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld.
     */
    private SelectedElementSubViewModel selectedElementSubViewModel;

    /**
     * Der ausgewählte MouseMode.
     */
    private ObjectProperty<MouseMode> mouseMode;

    /**
     * Das aktuell ausgewählte Element aus der Liste der platzierbaren Elemente.
     */
    private ObjectProperty<Optional<BaseElement>> selectedAvailableElement;

    /**
     * Gibt an, ob aktuell die Umschau-Taste gedrückt wird.
     */
    private boolean moveModifier = false;

    /**
     * Gibt an, ob sich die Maus auf dem Canvas befindet.
     */
    private boolean mouseOnCanvas = false;

    /**
     * Das Auswahl-Rechteck, falls vorhanden.
     */
    private Optional<RectangleDoubleByPoints> selectionRect;

    /**
     * Der obere Bildpfad des aktuell ausgewählten Elements.
     */
    private ObjectProperty<Optional<String>> topBackgroundPath;

    /**
     * Der untere Bildpfad des aktuell ausgewählten Elements.
     */
    private ObjectProperty<Optional<String>> botBackgroundPath;

    /**
     * Gibt an, ob ein zum Platzieren verfügbaren Element ausgewählt ist.
     */
    private BooleanProperty availableElementSelected;

    /**
     * Der aktuell gewünschte Cursor das Canvas.
     */
    private ObjectProperty<Cursor> cursorProperty;

    public static void setAsWindowWithBusyDialog(SceneManagerViewModel sceneManager, PinballMachine pinballMachine)
    {
        sceneManager.pushDialog(new BusyMessageViewModel("machine.loading", () -> {
            sceneManager.setWindow(new PinballMachineEditorViewModel(pinballMachine));
        }));
    }

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.MACHINE_EDITOR);

        pinballMachineEditor = new PinballMachineEditor(pinballMachine);

        selectedAvailableElement = new SimpleObjectProperty<>(Optional.empty());

        mouseMode = new SimpleObjectProperty<>(MouseMode.SELECTING);

        machineName = new SimpleStringProperty();
        machineName.bindBidirectional(pinballMachine.nameProperty());

        cameraPosition = new SimpleObjectProperty<>(new Vector2());
        cameraZoom = new SimpleDoubleProperty(0.75);
        selectedElementSubViewModel = new SelectedElementSubViewModel(pinballMachineEditor);

        selectionRect = Optional.empty();

        ObservableList<AvailableElementSubViewModel> availableElements = FXCollections.observableArrayList();
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));
        SortedList<AvailableElementSubViewModel> availableElementsSorted = new SortedList<>(availableElements, Comparator.comparing(o -> o.nameProperty().get()));

        availableBasicElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.BASIC))));
        availableObstacleElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.OBSTACLE))));
        availableRampElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.RAMP))));
        availableAdvancedElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.ADVANCED))));


        topBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        botBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        availableElementSelected = new SimpleBooleanProperty();
        availableElementSelected.bind(Bindings.isNull(selectedAvailableElement));

        cursorProperty = new SimpleObjectProperty<>();

        editorSessionSubViewModel = new EditorSessionSubViewModel(this, pinballMachine);
    }

    /**
     * Gibt die zur Platzierung auf dem Spielfeld verfügbaren Basis-Elemente zurück.
     *
     * @return Die zur Platzierung auf dem Spielfeld verfügbaren Basis-Elemente.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableBasicElementsProperty()
    {
        return availableBasicElements;
    }

    /**
     * Gibt die zur Platzierung auf dem Spielfeld verfügbaren Hindernisse zurück.
     *
     * @return Die zur Platzierung auf dem Spielfeld verfügbaren Hindernisse.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableObstacleElementsProperty()
    {
        return availableObstacleElements;
    }

    /**
     * Gibt die zur Platzierung auf dem Spielfeld verfügbaren Rampen zurück.
     *
     * @return Die zur Platzierung auf dem Spielfeld verfügbaren Rampen.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableRampElementsProperty()
    {
        return availableRampElements;
    }

    /**
     * Gibt die zur Platzierung auf dem Spielfeld verfügbaren Spezial-Elemente zurück.
     *
     * @return Die zur Platzierung auf dem Spielfeld verfügbaren Basis-Elemente.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableAdvancedElementsProperty()
    {
        return availableAdvancedElements;
    }

    /**
     * Verarbeitet eine Drag-Bewegung.
     *
     * @param startX  Die x-Position, an der die Drag-Bewegung angefangen hat.
     * @param startY  Die y-Position, an der die Drag-Bewegung angefangen hat.
     * @param endX    Die x-Position, an der sich die Drag-Bewegung befindet.
     * @param endY    Die y-Position, an der sich die Drag-Bewegung befindet.
     * @param gridPos Die neue Position auf dem Canvas.
     * @param button  Die gedrückte Maustaste.
     */
    public void dragged(double startX, double startY, double endX, double endY, Vector2 gridPos, MouseButton button)
    {
        double divX = (((endX - startX) / DesignConfig.PIXELS_PER_GRID_UNIT) / cameraZoom.get());
        double divY = (((endY - startY) / DesignConfig.PIXELS_PER_GRID_UNIT) / cameraZoom.get());

        if (button == MouseButton.SECONDARY || button == MouseButton.MIDDLE || moveModifier)
        {
            cameraPosition.set(new Vector2(cameraPosition.get().getX() - divX, cameraPosition.get().getY() - divY));
            cursorProperty.set(Cursor.MOVE);
        }
        else if (button == MouseButton.PRIMARY && mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.moveSelectionBy(new Vector2(divX, divY));
            cursorProperty.set(Cursor.CLOSED_HAND);
        }
        else if (button == MouseButton.PRIMARY && mouseMode.get() == MouseMode.SELECTING && selectionRect.isPresent())
        {
            selectionRect = Optional.of(new RectangleDoubleByPoints(selectionRect.get().getPointA(), gridPos));
        }
    }

    /**
     * Behandelt das Drücken der Maustaste auf dem Canvas.
     *
     * @param mouseEvent Das Event, in dem die Maustaste gedrückt wurde.
     * @param gridPos    Die Position im Grid, an der die Maustaste gedrückt wurde.
     */
    public void mousePressedOnCanvas(MouseEvent mouseEvent, Vector2 gridPos)
    {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;

        List<PlacedElement> elements = pinballMachineEditor.getElementsAt(gridPos);

        Optional<PlacedElement> selectedElement = elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(0));
        pinballMachineEditor.selectElement(selectedElement, mouseEvent.isControlDown());

        if (elements.isEmpty())
        {
            mouseMode.setValue(MouseMode.SELECTING);
            selectionRect = Optional.of(new RectangleDoubleByPoints(gridPos, gridPos));
        }
        else if (!mouseEvent.isControlDown())
        {
            mouseMode.setValue(MouseMode.PLACING);
        }
    }

    /**
     * Behandelt das Loslassen der Maustaste.
     *
     * @param mouseEvent Das Event, in dem die Maustaste losgelassen wurde.
     */
    public void mouseReleased(MouseEvent mouseEvent)
    {
        cursorProperty.set(Cursor.DEFAULT);

        // Nur Linksklick ist interessant
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;

        // Leere auswahl wenn ctrl nicht gedrückt
        if (!mouseEvent.isControlDown() && mouseMode.get() != MouseMode.PLACING)
            pinballMachineEditor.clearSelection();

        // Auswahlrechteck
        if (mouseMode.get() == MouseMode.SELECTING && selectionRect.isPresent())
        {
            RectangleDoubleByPoints rectangle = selectionRect.get();
            if (rectangle.getHeight() > 0 || rectangle.getWidth() > 0)
            {
                pinballMachineEditor.addToSelection(pinballMachineEditor.getElementsAt(rectangle));
                selectionRect = Optional.empty();
            }
        }

        if (mouseMode.get() == MouseMode.PLACING)
        {
            mouseMode.setValue(MouseMode.SELECTING);

            if (mouseOnCanvas)
                pinballMachineEditor.placeSelection();
            else
                setSelectedAvailableElement(null);
        }
    }

    /**
     * Reagiert auf das Fahren der Maus in den Canvas.
     *
     * @param gridPos Die Position, an der die Maus in den Canvas gefahren ist.
     */

    public void mouseEnteredCanvas(Vector2 gridPos)
    {
        mouseOnCanvas = true;
        if (mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.moveSelectionTo(gridPos);
            pinballMachineEditor.placeSelection();
            setSelectedAvailableElement(null);
        }
    }

    /**
     * Reagiert darauf, dass die Maus den Canvas verlassen hat.
     */
    public void mouseExitedCanvas()
    {
        mouseOnCanvas = false;
        if (!pinballMachineEditor.getSelection().isEmpty() && mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.removeSelection();
            PlacedElement aPlacedElement = pinballMachineEditor.getSelection().iterator().next();
            setSelectedAvailableElement(aPlacedElement.getBaseElement());
        }
    }

    /**
     * Wählt eines der verfügbaren Basis-Elemente aus.
     *
     * @param baseElement Das Basis-Element, das ausgewählt werden soll.
     */
    void select(BaseElement baseElement)
    {
        setSelectedAvailableElement(baseElement);
        mouseMode.setValue(MouseMode.PLACING);
        pinballMachineEditor.clearSelection();
        pinballMachineEditor.addToSelection(baseElement);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        if (Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode()).isPresent())
        {
            KeyBinding binding = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode()).get();

            switch (binding)
            {
                case EDITOR_ROTATE:
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                    {
                        pinballMachineEditor.rotateSelection();
                    }
                    break;
                case EDITOR_DELETE:
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                    {
                        pinballMachineEditor.removeSelection();
                        pinballMachineEditor.clearSelection();
                    }
                    break;
                case EDITOR_DUPLICATE:
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                    {
                        pinballMachineEditor.duplicateSelection();
                    }
                    break;
                case EDITOR_MOVE:
                    moveModifier = keyEvent.getEventType() == KeyEvent.KEY_PRESSED;
                    break;
            }

            super.handleKeyEvent(keyEvent);
        }
    }

    /**
     * Stellt der View die Position der Kamera zur Verfügung.
     *
     * @return Die Position der Kamera
     */
    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt der View die Stärke des Zooms der Kamera zur Verfügung.
     *
     * @return Die Stärke des Zooms der Kamera.
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Stellt der View das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld zur
     * Verfügung.
     *
     * @return Das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld
     */
    public SelectedElementSubViewModel getSelectedElementSubViewModel()
    {
        return selectedElementSubViewModel;
    }

    /**
     * Stellt der View den Namen des Automaten zur Verfügung.
     *
     * @return Der Name des Automaten.
     */
    public StringProperty machineNameProperty()
    {
        return machineName;
    }

    /**
     * Stellt der View as aktuell auf dem Spielfeld ausgewählte Element zur Verfügung.
     *
     * @return Das aktuell auf dem Spielfeld ausgewählte Element.
     */
    public Set<PlacedElement> getSelection()
    {
        return pinballMachineEditor.getSelection();
    }

    /**
     * Gibt den oberen Bildpfad des aktuell ausgewählten Elements zurück.
     *
     * @return Der obere Bildpfad des aktuell ausgewählten Elements.
     */
    public ReadOnlyObjectProperty<Optional<String>> getTopBackgroundPath()
    {
        return topBackgroundPath;
    }

    /**
     * Gibt den unteren Bildpfad des aktuell ausgewählten Elements zurück.
     *
     * @return Der untere Bildpfad des aktuell ausgewählten Elements.
     */
    public ReadOnlyObjectProperty<Optional<String>> getBotBackgroundPath()
    {
        return botBackgroundPath;
    }

    /**
     * Gibt zurück, ob ein zur Platzierung verfügbares Element ausgewählt ist.
     *
     * @return {@code true}, falls ein Element ausgewählt ist, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty isAvailableElementSelected()
    {
        return availableElementSelected;
    }

    /**
     * Gibt das Auswahl-Rechteck, falls vorhanden, zurück.
     *
     * @return Das Auswahl-Rechteck, falls vorhanden.
     */
    public Optional<RectangleDoubleByPoints> selectionRectProperty()
    {
        return selectionRect;
    }

    /**
     * Der aktuelle Cursor welcher im Editor Canvas sichtbar ist.
     *
     * @return Der Cursor welcher im Editor Canvas sichtbar ist.
     */
    public ReadOnlyObjectProperty<Cursor> cursorProperty()
    {
        return cursorProperty;
    }

    /**
     * Setzt das aktuell ausgewählte Element aus der Liste der möglichen Elemente auf das gegebene Element.
     *
     * @param selectedAvailableElement Das neue ausgewählte Element.
     */
    private void setSelectedAvailableElement(BaseElement selectedAvailableElement)
    {
        if (selectedAvailableElement != null)
        {
            this.selectedAvailableElement.set(Optional.of(selectedAvailableElement));
            topBackgroundPath.set(Optional.of(selectedAvailableElement.getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, 0, 0)));
            botBackgroundPath.set(Optional.of(selectedAvailableElement.getMedia().elementImageProperty().get().getImagePath(ImageLayer.BOTTOM, 0, 0)));
        }
        else
        {
            this.selectedAvailableElement.set(Optional.empty());
            topBackgroundPath.set(Optional.empty());
            botBackgroundPath.set(Optional.empty());
        }
    }

    /**
     * Gibt das korrespondierende EditorSessionSubViewModel zurück.
     *
     * @return Das korrespondierende EditorSessionSubViewModel.
     */
    public EditorSessionSubViewModel getEditorSessionSubViewModel()
    {
        return editorSessionSubViewModel;
    }

    /**
     * Gibt das SceneManagerViewModel des ViewModels zurück.
     *
     * @return Das SceneManagerViewModel des ViewModels.
     */
    public SceneManagerViewModel getSceneManagerViewModel()
    {
        return sceneManager;
    }

    /**
     * Setzt den Zoom der Kamera auf den gegebenen Wert.
     *
     * @param cameraZoom Der neue Zoom der Kamera.
     */
    public void setCameraZoom(double cameraZoom)
    {
        this.cameraZoom.set(cameraZoom);
    }

    public IntegerProperty selectionSizeProperty()
    {
        return pinballMachineEditor.selectionSizeProperty();
    }
}
