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
import sep.fimball.general.data.*;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.EditorSession;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasEditorViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen Flipper-Automaten zur Verfügung und ermöglicht es,
 * diesen zu bearbeiten.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    /**
     * Der Flipperautomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;

    /**
     * Der zugehörige PinballMachineEditor.
     */
    private PinballMachineEditor pinballMachineEditor;

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
     * Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    private PinballCanvasEditorViewModel pinballCanvasViewModel;

    /**
     * Der ausgewählte MouseMode.
     */
    private ObjectProperty<MouseMode> mouseMode;

    /**
     * Das aktuell ausgewählte Element aus der Liste der platzierbaren Elemente.
     */
    private ObjectProperty<Optional<BaseElement>> selectedAvailableElement;

    /**
     * Die zugehörige GameSession.
     */
    private EditorSession editorSession;

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

    private ObjectProperty<Cursor> cursorProperty;

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.MACHINE_EDITOR);
        this.pinballMachine = pinballMachine;

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

        editorSession = new EditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasEditorViewModel(editorSession, this);

        topBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        botBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        availableElementSelected = new SimpleBooleanProperty();
        availableElementSelected.bind(Bindings.isNull(selectedAvailableElement));

        cursorProperty = new SimpleObjectProperty<>();
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
     * Vergrößert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomOut()
    {
        if (cameraZoom.get() >= 1)
        {
            cameraZoom.set(Math.max(Config.MIN_ZOOM, cameraZoom.get() - 0.125));
        }
        else
        {
            cameraZoom.set(Math.max(Config.MIN_ZOOM, cameraZoom.get() - 0.1));
        }
    }

    /**
     * Verkleinert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomIn()
    {
        if (cameraZoom.get() >= 1)
        {
            cameraZoom.set(Math.min(Config.MAX_ZOOM, cameraZoom.get() + 0.125));
        }
        else
        {
            cameraZoom.set(Math.min(Config.MAX_ZOOM, cameraZoom.get() + 0.1));
        }
    }

    /**
     * Führt den Benutzer zu dem Spielfenster, wo der gerade vom Nutzer bearbeitete Flipper-Automat getestet werden
     * kann.
     */
    public void startPinballMachine()
    {
        editorSession.stopUpdateLoop();
        sceneManager.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, new String[]{"Editor Player"}, true)));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster und speichert den Automaten.
     */
    public void saveAndShowSettingsDialog()
    {
        editorSession.stopUpdateLoop();
        editorSession.stopAutoSaveLoop();
        pinballMachine.savePreviewImage(pinballCanvasViewModel.createScreenshot());
        boolean success = pinballMachine.saveToDisk();
        sceneManager.pushDialog(new MessageViewModel("editor.settings.saveMessage." + (success ? "success" : "fail")));
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, falls er das QuestionViewModel annimmt.
     */
    public void showSettingsDialog()
    {
        sceneManager.pushDialog(new QuestionViewModel("editor.editor.discardQuestion", () ->
        {
            editorSession.stopUpdateLoop();
            editorSession.stopAutoSaveLoop();
            pinballMachine.unloadElements();
            sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
        }));
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
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
        {
            List<PlacedElement> elements = pinballMachineEditor.getElementsAt(gridPos);
            if (!elements.isEmpty())
            {
                if (mouseEvent.isControlDown())
                {
                    if (pinballMachineEditor.getSelection().contains(elements.get(0)))
                    {
                        pinballMachineEditor.removeFromSelection(elements.get(0));
                    }
                    else
                    {
                        pinballMachineEditor.addToSelection(elements.get(0));
                    }
                }
                else
                {
                    if (pinballMachineEditor.getSelection().contains(elements.get(0)))
                    {
                        mouseMode.setValue(MouseMode.PLACING);
                    }
                    else
                    {
                        mouseMode.setValue(MouseMode.PLACING);
                        pinballMachineEditor.clearSelection();
                        pinballMachineEditor.addToSelection(elements.get(0));
                    }
                }
            }
            else
            {
                if (!mouseEvent.isControlDown())
                {
                    pinballMachineEditor.clearSelection();
                }
                mouseMode.setValue(MouseMode.SELECTING);
                selectionRect = Optional.of(new RectangleDoubleByPoints(gridPos, gridPos));
            }
        }
    }

    /**
     * Behandelt das Loslassend der Maustaste.
     *
     * @param mouseEvent Das Event, in dem die Maustaste losgelassen wurde.
     */
    public void mouseReleased(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
        {
            if (mouseOnCanvas)
            {
                if (mouseMode.get() == MouseMode.PLACING)
                {
                    pinballMachineEditor.placeSelection();
                    mouseMode.setValue(MouseMode.SELECTING);
                }
                else if (mouseMode.get() == MouseMode.SELECTING)
                {
                    if (!mouseEvent.isControlDown())
                    {
                        pinballMachineEditor.clearSelection();
                    }

                    if (selectionRect.isPresent())
                    {
                        RectangleDoubleByPoints rectangle = selectionRect.get();
                        if (rectangle.getHeight() > 0 || rectangle.getWidth() > 0)
                        {
                            pinballMachineEditor.addToSelection((ListProperty<PlacedElement>) pinballMachineEditor.getElementsAt(rectangle));
                            selectionRect = Optional.empty();
                        }
                    }
                }
                else
                {
                    pinballMachineEditor.clearSelection();
                    mouseMode.setValue(MouseMode.SELECTING);
                    setSelectedAvailableElement(null);
                }
            }
            else
            {
                if (mouseMode.get() == MouseMode.PLACING)
                {
                    pinballMachineEditor.clearSelection();
                    mouseMode.setValue(MouseMode.SELECTING);
                    setSelectedAvailableElement(null);
                }
                if (mouseMode.get() == MouseMode.SELECTING && selectionRect.isPresent())
                {
                    RectangleDoubleByPoints rectangle = selectionRect.get();
                    if (rectangle.getHeight() > 0 || rectangle.getWidth() > 0)
                    {
                        pinballMachineEditor.addToSelection((ListProperty<PlacedElement>) pinballMachineEditor.getElementsAt(rectangle));
                        selectionRect = Optional.empty();
                    }
                }
            }
        }
        cursorProperty.set(Cursor.DEFAULT);
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
            setSelectedAvailableElement(pinballMachineEditor.getSelection().get(0).getBaseElement());
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
                case EDITOR_MOVE:
                    moveModifier = keyEvent.getEventType() == KeyEvent.KEY_PRESSED;
                    break;
            }

            super.handleKeyEvent(keyEvent);
        }
    }

    /**
     * Stellt der View das PinballCanvasViewModel des angezeigten Spielfelds zur Verfügung.
     *
     * @return Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    public PinballCanvasEditorViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
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
    public ReadOnlyListProperty<PlacedElement> getSelection()
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
}
