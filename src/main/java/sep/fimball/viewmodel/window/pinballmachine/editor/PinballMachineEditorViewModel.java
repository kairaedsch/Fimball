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

    private SelectedElementSubViewModel selectedElementSubViewModel;

    private PinballCanvasViewModel pinballCanvasViewModel;

    private MouseModus mouseModus;
    private BaseElement selectedAvailableElement;

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_EDITOR);
        this.pinballMachine = pinballMachine;
        mouseModus = MouseModus.SELECTING;

        cameraPosition = new SimpleObjectProperty<>(new Vector2(0, 0));
        cameraZoom = new SimpleDoubleProperty(0.75);
        selectedElementSubViewModel = new SelectedElementSubViewModel(pinballMachine, pinballMachine.getElements().get(0));

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(availableElements, BaseElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(this, element));

        GameSession gameSession = GameSession.generateEditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, this);
    }

    public void mouseEvent(double xPos, double yPos)
    {
        Optional<PlacedElement> selectedElement = pinballMachine.getElementAt(new Vector2(xPos, yPos));
        
        if (selectedElement.isPresent())
        {
            selectedElementSubViewModel.setPlacedElement(selectedElement.get());
        }
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
        if(cameraZoom.get() >= 1)
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
        if(cameraZoom.get() >= 1)
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
        sceneManager.setWindow(new GameViewModel(pinballMachine, new String[]{new String(new byte[]{71, 111, 116, 116, 105, 109, 112, 101, 114, 97, 116, 111, 114, 32, 84, 114, 117, 109, 112})}));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, wo der gerade vom Nutzer bearbeitete Flipperautomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    public ObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    public DoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    public SelectedElementSubViewModel getSelectedElementSubViewModel()
    {
        return selectedElementSubViewModel;
    }

    public void dragged(double x, double y)
    {
        if(mouseModus == MouseModus.DRAGGING)
        {
            cameraPosition.get().setX(cameraPosition.get().getX() + ((x / Config.pixelsPerGridUnit) / cameraZoom.get()));
            cameraPosition.get().setY(cameraPosition.get().getY() + ((y / Config.pixelsPerGridUnit) / cameraZoom.get()));
        }
    }

    public void setMouseModus(MouseModus mouseModus)
    {
        this.mouseModus = mouseModus;
    }

    public void setSelectedAvailableElement(BaseElement selectedAvailableElement)
    {
        this.selectedAvailableElement = selectedAvailableElement;
    }

    public void mouseClickedOnGame(Vector2 gridPosition)
    {
        if(mouseModus == MouseModus.SELECTING)
        {
            PlacedElement placedElement = null;
            selectedElementSubViewModel.setPlacedElement(placedElement);
        }
        else if(mouseModus == MouseModus.PLACING)
        {
            pinballMachine.addElement(selectedAvailableElement, gridPosition);
        }
    }
}
