package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import java.util.List;
import java.util.Optional;

import static sep.fimball.general.data.Config.pixelsPerGridUnit;

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
     * Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Der ausgewählte MouseMode.
     */
    private ObjectProperty<MouseMode> mouseMode;

    private GameSession gameSession;

    private RectangleDouble selectionRect = new RectangleDouble(new Vector2(0, 0), 0, 0);

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.MACHINE_EDITOR);
        this.pinballMachine = pinballMachine;

        this.pinballMachineEditor = new PinballMachineEditor(pinballMachine);

        mouseMode = new SimpleObjectProperty<>(MouseMode.SELECTING);

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());

        cameraPosition = new SimpleObjectProperty<>(new Vector2(0, 0));
        cameraZoom = new SimpleDoubleProperty(0.75);

        ObservableList<AvailableElementSubViewModel> availableElements = FXCollections.observableArrayList();
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));
        SortedList<AvailableElementSubViewModel> availableElementsSorted = new SortedList<>(availableElements, (o1, o2) -> o1.nameProperty().get().compareTo(o2.nameProperty().get()));

        availableBasicElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.BASIC))));
        availableObstacleElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.OBSTACLE))));
        availableAdvancedElements = new SimpleListProperty<>(new FilteredList<>(availableElementsSorted, (original -> original.getElementCategory().get().equals(BaseElementCategory.ADVANCED))));

        gameSession = GameSession.generateEditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, this);
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
        sceneManager.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, new String[]{""}, true)));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, wo der gerade vom Nutzer bearbeitete Flipperautomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        pinballCanvasViewModel.notifyToGenerateImage();
        pinballMachine.savePreviewImage(pinballCanvasViewModel.getGeneratedPreviewImage());
        gameSession.pauseAll();
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Verarbeitet eine Drag-Bewegung.
     *
     * @param endX Die x-Position, an der sich die Drag-Bewegung befindet.
     * @param endY Die y-Position, an der sich die Drag-Bewegung befindet.
     */
    public void mouseDragged(double startX, double startY, double endX, double endY, MouseButton button)
    {
        double diffX = (((endX - startX) / Config.pixelsPerGridUnit) / cameraZoom.get());
        double diffY = (((endY - startY) / Config.pixelsPerGridUnit) / cameraZoom.get());

        if (button == MouseButton.MIDDLE)
        {
            cameraPosition.set(new Vector2(cameraPosition.get().getX() - diffX, cameraPosition.get().getY() - diffY));
        }
        else if (button == MouseButton.PRIMARY && mouseMode.get() == MouseMode.SELECTING)
        {
            Vector2 origin = selectionRect.getOrigin();
            selectionRect = new RectangleDouble(origin, origin.getX() - endX, origin.getY() - endY);
        }
        else if (button == MouseButton.PRIMARY && mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.moveSelectionTo(new Vector2(endX, endY));
        }
    }

    /**
     * Verarbeitet einen Mausklick auf das Spielfeld.
     *
     * @param gridPosition Die Position im Grid, auf die geklickt wurde.
     * @param onlyPressed  Gibt an, ob die Maustaste nur gedrückt wurde.
     */
    public void mousePressedOnCanvas(Vector2 gridPosition, MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
        {
            ListProperty<PlacedElement> elements = (ListProperty<PlacedElement>) pinballMachineEditor.getElementsAt(gridPosition);
            if (!elements.isEmpty())
            {
                System.out.println("element grabbed, now placing mode");

                PlacedElement element = elements.get(0);
                if (pinballMachineEditor.getSelection().contains(element))
                {
                    if (mouseEvent.isControlDown())
                    {
                        pinballMachineEditor.removeFromSelection(element);
                    }
                    else
                    {
                        mouseMode.setValue(MouseMode.PLACING);
                    }
                }
                else
                {
                    if (mouseEvent.isControlDown())
                    {
                        pinballMachineEditor.addToSelection(element);
                    }
                    else
                    {
                        mouseMode.setValue(MouseMode.PLACING);
                        pinballMachineEditor.clearSelection();
                        pinballMachineEditor.addToSelection(element);
                    }
                }
            }
            else
            {
                System.out.println("no element grabbed, now selecting");
                mouseMode.setValue(MouseMode.SELECTING);
                pinballMachineEditor.clearSelection();
                selectionRect = new RectangleDouble(gridPosition, 0, 0);
            }
        }
    }

    public void mouseExitedCanvas()
    {
        if (mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.removeSelection();
        }
    }

    public void mouseEnteredCanvas(Vector2 gridPos)
    {
        if (mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.moveSelectionTo(gridPos);
            pinballMachineEditor.placeSelection();
        }
    }

    public void mouseReleasedOnCanvas(MouseEvent mouseEvent)
    {
        if (mouseMode.get() == MouseMode.SELECTING)
        {
            pinballMachineEditor.addToSelection((ListProperty<PlacedElement>) pinballMachineEditor.getElementsAt(selectionRect));
        }
        else if (mouseMode.get() == MouseMode.PLACING)
        {
            mouseMode.setValue(MouseMode.SELECTING);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {
        if (mouseMode.get() == MouseMode.PLACING)
        {
            pinballMachineEditor.clearSelection();
        }
    }

    public void selectNewElement(BaseElement baseElement)
    {
        System.out.println("new Element selected");
        mouseMode.setValue(MouseMode.PLACING);
        pinballMachineEditor.clearSelection();
        pinballMachineEditor.addToSelection(baseElement);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        KeyBinding binding = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode());
        if (binding == null)
            return;
        switch (binding)
        {

            case EDITOR_ROTATE:
                pinballMachineEditor.rotateSelection();
                pinballMachineEditor.clearSelection();
                break;

            case EDITOR_DELETE:
                pinballMachineEditor.removeSelection();
                pinballMachineEditor.clearSelection();
                break;

            case EDITOR_MOVE:
                // TODO wat
                break;
        }
        super.handleKeyEvent(keyEvent);
    }

    /**
     * Stellt der View das PinballCanvasViewModel des angezeigten Spielfelds zur Verfügung.
     *
     * @return Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    public PinballCanvasViewModel getPinballCanvasViewModel()
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
     * Stellt der View den Namen des Automaten zur Verfügung.
     *
     * @return Der Name des Automaten.
     */
    public ReadOnlyStringProperty machineNameProperty()
    {
        return machineName;
    }

    public RectangleDouble getSelectionRect()
    {
        return selectionRect;
    }

}
