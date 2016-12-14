package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasEditorViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import java.util.List;
import java.util.Optional;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen Flipper-Automaten zur Verfügung und ermöglicht es, diesen zu bearbeiten.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    /**
     * Der Flipperautomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;

    private PinballMachineEditor pinballMachineEditor;

    private ListProperty<AvailableElementSubViewModel> availableBasicElements;

    private ListProperty<AvailableElementSubViewModel> availableObstacleElements;

    private ListProperty<AvailableElementSubViewModel> availableAdvancedElements;

    /**
     * Die aktuelle Position der Kamera, die ihren Wert immer an die Kamera-Position im PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Die aktuelle Stärke des Zooms der Kamera, die ihren Wert immer an die Kamera Stärke des Zooms in PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
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
     * Das aktuell auf dem Spielfeld ausgewählte Element.
     */
    private ObjectProperty<Optional<PlacedElement>> selectedPlacedElement;

    /**
     * Die Position des aktuell auf dem Spielfeld ausgewählten Elements.
     */
    private ObjectProperty<Vector2> selectedPlacedElementPosition;

    private GameSession gameSession;

    private boolean moveModifier = false;

    private boolean mouseOnCanvas = false;

    private ObjectProperty<Optional<String>> topBackgroundPath;

    private ObjectProperty<Optional<String>> botBackgroundPath;

    private BooleanProperty availableElementSelected;

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

        pinballMachineEditor = new PinballMachineEditor(pinballMachine);

        selectedAvailableElement = new SimpleObjectProperty<>(Optional.empty());
        selectedPlacedElement = new SimpleObjectProperty<>(Optional.empty());

        mouseMode = new SimpleObjectProperty<>(MouseMode.SELECTING);

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());

        cameraPosition = new SimpleObjectProperty<>(new Vector2());
        cameraZoom = new SimpleDoubleProperty(0.75);
        selectedElementSubViewModel = new SelectedElementSubViewModel(pinballMachineEditor.getSelection());

        ObservableList<AvailableElementSubViewModel> availableElements = FXCollections.observableArrayList();
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));
        SortedList<AvailableElementSubViewModel> availableElementsSorted = new SortedList<>(availableElements, (o1, o2) -> o1.nameProperty().get().compareTo(o2.nameProperty().get()));

        availableBasicElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.BASIC))));
        availableObstacleElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.OBSTACLE))));
        availableAdvancedElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.ADVANCED))));

        gameSession = GameSession.generateEditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasEditorViewModel(gameSession, this);

        topBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        botBackgroundPath = new SimpleObjectProperty<>(Optional.empty());
        availableElementSelected = new SimpleBooleanProperty();
        availableElementSelected.bind(Bindings.isNull(selectedAvailableElement));
    }

    public ReadOnlyListProperty<AvailableElementSubViewModel> availableBasicElementsProperty()
    {
        return availableBasicElements;
    }

    public ReadOnlyListProperty<AvailableElementSubViewModel> availableObstacleElementsProperty()
    {
        return availableObstacleElements;
    }

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
            cameraZoom.set(Math.max(Config.minZoom, cameraZoom.get() - 0.125));
        }
        else
        {
            cameraZoom.set(Math.max(Config.minZoom, cameraZoom.get() - 0.1));
        }
    }

    /**
     * Verkleinert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomIn()
    {
        if (cameraZoom.get() >= 1)
        {
            cameraZoom.set(Math.min(Config.maxZoom, cameraZoom.get() + 0.125));
        }
        else
        {
            cameraZoom.set(Math.min(Config.maxZoom, cameraZoom.get() + 0.1));
        }
    }

    /**
     * Führt den Benutzer zu dem Spielfenster, wo der gerade vom Nutzer bearbeitete Flipper-Automat getestet werden kann.
     */
    public void startPinballMachine()
    {
        gameSession.pauseAll();
        sceneManager.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, new String[]{""}, true)));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, wo der gerade vom Nutzer bearbeitete Flipperautomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        pinballMachine.savePreviewImage(pinballCanvasViewModel.createScreenshot());
        gameSession.pauseAll();
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Verarbeitet eine Drag-Bewegung.
     *
     * @param endX Die x-Position, an der sich die Drag-Bewegung befindet.
     * @param endY Die y-Position, an der sich die Drag-Bewegung befindet.
     */
    public void dragged(double startX, double startY, double endX, double endY, Vector2 gridPos, MouseButton button)
    {
        double divX = (((endX - startX) / Config.pixelsPerGridUnit) / cameraZoom.get());
        double divY = (((endY - startY) / Config.pixelsPerGridUnit) / cameraZoom.get());

        if (button == MouseButton.SECONDARY || button == MouseButton.MIDDLE || moveModifier)
        {
            cameraPosition.set(new Vector2(cameraPosition.get().getX() - divX, cameraPosition.get().getY() - divY));
        }
        else if (button == MouseButton.PRIMARY && mouseMode.get() == MouseMode.PLACING)
        {
            System.out.println(gridPos.getX() + " " + gridPos.getY());
            pinballMachineEditor.moveSelectionTo(new Vector2(Math.round(gridPos.getX()), Math.round(gridPos.getY())));
        }
        //TODO implement multi select
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
     * Verarbeitet einen Mausklick auf das Spielfeld.
     *
     * @param gridPosition Die Position im Grid, auf die geklickt wurde.
     * @param onlyPressed  Gibt an, ob die Maustaste nur gedrückt wurde.
     */
    public void mouseClickedOnGame(Vector2 gridPosition, MouseButton button, boolean onlyPressed)
    {
        /*
        if (!moveModifier && onlyPressed)
        {
            if (button == MouseButton.SECONDARY && mouseMode.get() == MouseMode.PLACING)
                setMouseMode(MouseMode.SELECTING);

            if (mouseMode.get() == MouseMode.SELECTING && button == MouseButton.PRIMARY)
            {
                setSelectedPlacedElement(pinballMachine.getElementAt(gridPosition));
            }
            else if (mouseMode.get() == MouseMode.PLACING && selectedAvailableElement.get().isPresent() && button == MouseButton.PRIMARY)
            {
                if (selectedAvailableElement.get().get().getType() == BaseElementType.BALL)
                {
                    for (PlacedElement placedElement : pinballMachine.elementsProperty())
                    {
                        if (placedElement.getBaseElement().getType() == BaseElementType.BALL)
                        {
                            pinballMachine.removeElement(placedElement);
                            break;
                        }
                    }
                }
                PlacedElement placedElement = pinballMachine.addElement(selectedAvailableElement.get().get(), gridPosition.round());
                setSelectedPlacedElement(Optional.of(placedElement));
            }
        }*/


    }

    public void mousePressedOnCanvas(MouseEvent mouseEvent, Vector2 gridPos)
    {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
        {
            return;
        }
        System.out.println("mouse pressed on canvas");


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
                    pinballMachineEditor.clearSelection();
                    pinballMachineEditor.addToSelection(elements.get(0));
                    selectedElementSubViewModel.setPlacedElement(Optional.of(elements.get(0)));
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
                    selectedElementSubViewModel.setPlacedElement(Optional.of(elements.get(0)));
                }
            }
        }
        else
        {
            pinballMachineEditor.clearSelection();
            mouseMode.setValue(MouseMode.SELECTING);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {
        System.out.println("mouse released on canvas");
        if (mouseOnCanvas)
        {
            if (mouseMode.get() == MouseMode.PLACING)
            {
                pinballMachineEditor.placeSelection();
                mouseMode.setValue(MouseMode.SELECTING);
            }
            else if (mouseMode.get() == MouseMode.SELECTING)
            {
                // TODO add selectionrect content to selection
            }
        }
        else
        {
            pinballMachineEditor.clearSelection();
            mouseMode.setValue(MouseMode.SELECTING);
            setSelectedAvailableElement(null);
        }
    }

    public void mouseEnteredCanvas(Vector2 gridPos)
    {
        System.out.println("mouse entered Canvas");
        mouseOnCanvas = true;
        if (mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.moveSelectionTo(gridPos);
            pinballMachineEditor.placeSelection();
            setSelectedAvailableElement(null);
        }
    }

    public void mouseExitedCanvas(Vector2 gridPos)
    {
        System.out.println("mouse exited Canvas");
        mouseOnCanvas = false;
        if (!pinballMachineEditor.getSelection().isEmpty() && mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.removeSelection();
            setSelectedAvailableElement(pinballMachineEditor.getSelection().get(0).getBaseElement());
        }
    }

    public void select(BaseElement baseElement)
    {
        setSelectedAvailableElement(baseElement);
        mouseMode.setValue(MouseMode.PLACING);
        pinballMachineEditor.clearSelection();
        pinballMachineEditor.addToSelection(baseElement);
    }

    /**
     * Setzt das aktuell ausgewählte Element auf dem Automaten auf das gegebene Element.
     *
     * @param placedElement Das neue ausgewählte Element.
     */
    private void setSelectedPlacedElement(Optional<PlacedElement> placedElement)
    {
        selectedPlacedElement.set(placedElement);
        if (selectedPlacedElement.get().isPresent())
        {
            selectedPlacedElementPosition = new SimpleObjectProperty<>(new Vector2().plus(selectedPlacedElement.get().get().positionProperty().get()));
            selectedPlacedElementPosition.addListener((observable, oldValue, newValue) -> selectedPlacedElement.get().get().setPosition(new Vector2().plus(newValue).round()));
        }

        selectedElementSubViewModel.setPlacedElement(selectedPlacedElement.get());
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode());
        if (binding == null)
        {
            return;
        }
        switch (binding)
        {
            case EDITOR_ROTATE:
                pinballMachineEditor.rotateSelection();
                break;
            case EDITOR_DELETE:
                pinballMachineEditor.removeSelection();
                pinballMachineEditor.clearSelection();
                break;
            case EDITOR_MOVE:
                moveModifier = keyEvent.getEventType() == KeyEvent.KEY_PRESSED;
                break;
        }

        super.handleKeyEvent(keyEvent);
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
     * Stellt der View das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld zur Verfügung.
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
    public ReadOnlyStringProperty machineNameProperty()
    {
        return machineName;
    }

    /**
     * Stellt der View as aktuell auf dem Spielfeld ausgewählte Element zur Verfügung.
     *
     * @return Das aktuell auf dem Spielfeld ausgewählte Element.
     */
    public ReadOnlyObjectProperty<Optional<PlacedElement>> getSelectedPlacedElement()
    {
        return selectedPlacedElement;
    }

    public ReadOnlyObjectProperty<Optional<String>> getTopBackgroundPath()
    {
        return topBackgroundPath;
    }

    public ReadOnlyObjectProperty<Optional<String>> getBotBackgroundPath()
    {
        return botBackgroundPath;
    }

    public ReadOnlyBooleanProperty isAvailableElementSelected()
    {
        return availableElementSelected;
    }
}
