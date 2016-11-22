package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.GameSession;
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
     * Die aktuelle Zoomstärke der Kamera, die ihren Wert immer an die Kamera Zoomstärke in PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
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
    private ObjectProperty<MouseMode> mouseModus;

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

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_EDITOR);
        this.pinballMachine = pinballMachine;

        this.selectedAvailableElement = Optional.empty();
        selectedPlacedElement = new SimpleObjectProperty<>(Optional.empty());

        mouseModus = new SimpleObjectProperty<>(MouseMode.SELECTING);

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());

        cameraPosition = new SimpleObjectProperty<>(new Vector2(0, 0));
        cameraZoom = new SimpleDoubleProperty(0.75);
        selectedElementSubViewModel = new SelectedElementSubViewModel(pinballMachine);

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));

        GameSession gameSession = GameSession.generateEditorSession(pinballMachine);
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
            cameraZoom.set(cameraZoom.get() - 0.5);
        }
        else
        {
            cameraZoom.set(cameraZoom.get() / 2);
        }
    }

    /**
     * Verkleinert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomIn()
    {
        if (cameraZoom.get() >= 1)
        {
            cameraZoom.set(cameraZoom.get() + 0.5);
        }
        else
        {
            cameraZoom.set(cameraZoom.get() * 2);
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
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Reagiert auf eine Drag-Bewegung. TODO
     *
     * @param x Die x-Position, an der sich die Drag-Bewegung befindet.
     * @param y Die y-Positon, an der sich die Drag-Bewegung befindet.
     */
    public void dragged(double x, double y)
    {
        double divX = ((x / Config.pixelsPerGridUnit) / cameraZoom.get());
        double divY = ((y / Config.pixelsPerGridUnit) / cameraZoom.get());

        if (mouseModus.get() == MouseMode.DRAGGING)
        {
            cameraPosition.get().setX(cameraPosition.get().getX() - divX);
            cameraPosition.get().setY(cameraPosition.get().getY() - divY);
        }
        else if (mouseModus.get() == MouseMode.SELECTING && selectedPlacedElement.get().isPresent())
        {
            selectedPlacedElementPosition.set(new Vector2(divX, divY).add(selectedPlacedElementPosition.get()));
        }
    }


    public void setMouseModus(MouseMode mouseMode)
    {
        this.mouseModus.set(mouseMode);
    }

    public void setSelectedAvailableElement(BaseElement selectedAvailableElement)
    {
        this.selectedAvailableElement = Optional.of(selectedAvailableElement);
    }

    /**
     * Reagiert auf einen Mausklick auf das Spielfeld. TODO
     *
     * @param gridPosition Die Position im Grid, auf die geklickt wurde.
     * @param onlyPressed Gibt an, ob die Maustaste nur gedrückt wurde.
     */
    public void mouseClickedOnGame(Vector2 gridPosition, boolean onlyPressed)
    {
        if (mouseModus.get() == MouseMode.SELECTING && onlyPressed)
        {
            setSelectedPlacedElement(pinballMachine.getElementAt(gridPosition));
        }
        else if (mouseModus.get() == MouseMode.PLACING && selectedAvailableElement.isPresent() && onlyPressed)
        {
            PlacedElement placedElement = pinballMachine.addElement(selectedAvailableElement.get(), gridPosition.round());
            setMouseModus(MouseMode.SELECTING);
            setSelectedPlacedElement(Optional.of(placedElement));
        }
    }

    private void setSelectedPlacedElement(Optional<PlacedElement> placedElement)
    {
        selectedPlacedElement.set(placedElement);
        if (selectedPlacedElement.get().isPresent())
        {
            selectedPlacedElementPosition = new SimpleObjectProperty<>(new Vector2().add(selectedPlacedElement.get().get().positionProperty().get()));
            selectedPlacedElementPosition.addListener((observable, oldValue, newValue) -> selectedPlacedElement.get().get().setPosition(new Vector2().add(newValue).round()));
        }

        selectedElementSubViewModel.setPlacedElement(selectedPlacedElement.get());
    }

    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    public SelectedElementSubViewModel getSelectedElementSubViewModel()
    {
        return selectedElementSubViewModel;
    }

    public ReadOnlyStringProperty machineNameProperty()
    {
        return machineName;
    }

    public ReadOnlyObjectProperty<MouseMode> mouseModusProperty()
    {
        return mouseModus;
    }

    public ReadOnlyObjectProperty<Optional<PlacedElement>> getSelectedPlacedElement()
    {
        return selectedPlacedElement;
    }
}
