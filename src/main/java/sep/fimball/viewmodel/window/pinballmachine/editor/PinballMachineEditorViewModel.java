package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

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

    /**
     * Eine Liste, die alle Flipperautomat-Elemente enthält, die vom Nutzer platziert werden können.
     */
    private ListProperty<AvailableElementSubViewModel> availableElements;

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
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Der ausgewählte MouseMode.
     */
    private ObjectProperty<MouseMode> mouseMode;

    /**
     * Das aktuell ausgewählte Element aus der Liste der platzierbaren Elemente.
     */
    private Optional<BaseElement> selectedAvailableElement;

    /**
     * Das aktuell auf dem Spielfeld ausgewählte Element.
     */
    private ObjectProperty<Optional<PlacedElement>> selectedPlacedElement;

    /**
     * Die Position des aktuell auf dem Spielfeld ausgewählten Elements.
     */
    private ObjectProperty<Vector2> selectedPlacedElementPosition;

    private GameSession gameSession;

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.MACHINE_EDITOR);
        this.pinballMachine = pinballMachine;

        this.selectedAvailableElement = Optional.empty();
        selectedPlacedElement = new SimpleObjectProperty<>(Optional.empty());

        mouseMode = new SimpleObjectProperty<>(MouseMode.SELECTING);

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());

        cameraPosition = new SimpleObjectProperty<>(new Vector2(0, 0));
        cameraZoom = new SimpleDoubleProperty(0.75);
        selectedElementSubViewModel = new SelectedElementSubViewModel(pinballMachine);

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));

        gameSession = GameSession.generateEditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, this);
    }

    /**
     * Stellt der View die Liste, die alle Flipperautomat-Elemente enthält, die vom Nutzer platziert werden können, zur Verfügung.
     *
     * @return Eine Liste aller platzierbaren Flipperautomat-Elemente.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableElementsProperty()
    {
        return availableElements;
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
        sceneManager.setWindow(new GameViewModel(pinballMachine, new String[]{new String(new byte[]{71, 111, 116, 116, 105, 109, 112, 101, 114, 97, 116, 111, 114, 32, 84, 114, 117, 109, 112})}, true));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, wo der gerade vom Nutzer bearbeitete Flipperautomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        gameSession.pauseAll();
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Verarbeitet eine Drag-Bewegung.
     *
     * @param x Die x-Position, an der sich die Drag-Bewegung befindet.
     * @param y Die y-Position, an der sich die Drag-Bewegung befindet.
     */
    public void dragged(double x, double y, boolean primaryMouse)
    {
        double divX = ((x / Config.pixelsPerGridUnit) / cameraZoom.get());
        double divY = ((y / Config.pixelsPerGridUnit) / cameraZoom.get());

        if (!primaryMouse)
        {
            cameraPosition.set(new Vector2(cameraPosition.get().getX() - divX, cameraPosition.get().getY() - divY));
        }
        else if (mouseMode.get() == MouseMode.SELECTING && selectedPlacedElement.get().isPresent())
        {
            selectedPlacedElementPosition.set(new Vector2(divX, divY).plus(selectedPlacedElementPosition.get()));
        }
    }


    /**
     * Setzt den MouseMode.
     * @param mouseMode Der neue MouseMode.
     */
    public void setMouseMode(MouseMode mouseMode)
    {
        this.mouseMode.set(mouseMode);
    }

    /**
     * Setzt das aktuell ausgewählte Element aus der Liste der möglichen Elemente auf das gegebene Element.
     * @param selectedAvailableElement Das neue ausgewählte Element.
     */
    public void setSelectedAvailableElement(BaseElement selectedAvailableElement)
    {
        this.selectedAvailableElement = Optional.of(selectedAvailableElement);
    }

    /**
     * Verarbeitet einen Mausklick auf das Spielfeld.
     *
     * @param gridPosition Die Position im Grid, auf die geklickt wurde.
     * @param onlyPressed Gibt an, ob die Maustaste nur gedrückt wurde.
     */
    public void mouseClickedOnGame(Vector2 gridPosition, boolean onlyPressed)
    {
        if (mouseMode.get() == MouseMode.SELECTING && onlyPressed)
        {
            setSelectedPlacedElement(pinballMachine.getElementAt(gridPosition));
        }
        else if (mouseMode.get() == MouseMode.PLACING && selectedAvailableElement.isPresent() && onlyPressed)
        {
            PlacedElement placedElement = pinballMachine.addElement(selectedAvailableElement.get(), gridPosition.round());
            setMouseMode(MouseMode.SELECTING);
            setSelectedPlacedElement(Optional.of(placedElement));
        }
    }

    /**
     * Setzt das aktuell ausgewählte Element auf dem Automaten auf das gegebene Element.
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

    /**
     * Stellt der View das PinballCanvasViewModel des angezeigten Spielfelds zur Verfügung.
     * @return Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    /**
     * Stellt der View die Position der Kamera zur Verfügung.
     * @return Die Position der Kamera
     */
    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt der View die Stärke des Zooms der Kamera zur Verfügung.
     * @return Die Stärke des Zooms der Kamera.
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Stellt der View das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld zur Verfügung.
     * @return Das SelectedElementSubViewModel des aktuell ausgewählten Elements auf dem Spielfeld
     */
    public SelectedElementSubViewModel getSelectedElementSubViewModel()
    {
        return selectedElementSubViewModel;
    }

    /**
     * Stellt der View den Namen des Automaten zur Verfügung.
     * @return Der Name des Automaten.
     */
    public ReadOnlyStringProperty machineNameProperty()
    {
        return machineName;
    }

    /**
     * Stellt der View den ausgewählten MouseMode zur Verfügung.
     * @return Der ausgewählte MouseMode.
     */
    public ReadOnlyObjectProperty<MouseMode> mouseModeProperty()
    {
        return mouseMode;
    }

    /**
     * Stellt der View as aktuell auf dem Spielfeld ausgewählte Element zur Verfügung.
     * @return Das aktuell auf dem Spielfeld ausgewählte Element.
     */
    public ReadOnlyObjectProperty<Optional<PlacedElement>> getSelectedPlacedElement()
    {
        return selectedPlacedElement;
    }
}
