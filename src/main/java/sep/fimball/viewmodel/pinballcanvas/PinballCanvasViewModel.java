package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.general.util.Observable;
import sep.fimball.model.GameSession;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.util.Observer;

/**
 * Das PinballCanvasViewModel stellt der View Daten für die Anzeige des Flipperautomaten mit all seinen Elementen zur Verfügung und dient als Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
 */
public class PinballCanvasViewModel
{
    /**
     * Liste aller SpriteSubViewModels, die Informationen zum Zeichnen enthalten.
     */
    private ListProperty<SpriteSubViewModel> spriteSubViewModels;

    /**
     * Position der Kamera, die festlegt, von welchem Standpunkt aus die Automatenelemente gezeichnet werden sollen.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Legt fest, wie groß Elemente zu zeichnen sind und legt somit auch fest, wie viele BaseElement der Nutzer sehen kann.
     */
    private DoubleProperty cameraZoom;

    /**
     * Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
     */
    private Observable redrawObservable;

    private boolean editorMode;

    private PinballMachineEditorViewModel editorViewModel;

    /**
     * Erstellt ein neues PinballCanvasViewModel.
     *
     * @param gameSession Die Spielsitzung.
     * @param gameViewModel Das korrespondierende GameViewModel.
     */
    public PinballCanvasViewModel(GameSession gameSession, GameViewModel gameViewModel)
    {
        init(gameSession);
        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, gameSession.getWorld().gameElementsProperty(), SpriteSubViewModel::new);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());
        editorMode = false;
    }

    public PinballCanvasViewModel(GameSession gameSession, PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        init(gameSession);

        this.editorViewModel = pinballMachineEditorViewModel;
        cameraPosition.bind(pinballMachineEditorViewModel.cameraPositionProperty());
        cameraZoom.bind(pinballMachineEditorViewModel.cameraZoomProperty());
        editorMode = true;

        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, gameSession.getWorld().gameElementsProperty(), (gameElement) -> new SpriteSubViewModel(gameElement, pinballMachineEditorViewModel.getSelectedPlacedElement()));
    }

    /**
     * Initialisiert die Zeichnung des Canvas.
     */
    private void init(GameSession gameSession)
    {
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());

        redrawObservable = new Observable();

        Observer gameObserver = (o, args) -> redraw();
        gameSession.addGameLoopObserver(gameObserver);
    }

    public void mouseClickedOnGame(Vector2 gridPos)
    {
        if (editorMode)
        {
            editorViewModel.mouseClickedOnGame(gridPos, false);
        }
    }

    public void mousePressedOnGame(Vector2 gridPos)
    {
        if (editorMode)
        {
            editorViewModel.mouseClickedOnGame(gridPos, true);
        }
    }

    /**
     * Stellt der View die Liste aller SpriteSubViewModels zur Verfügung.
     *
     * @return Eine Liste aller SpriteSubViewModels.
     */
    public ReadOnlyListProperty<SpriteSubViewModel> spriteSubViewModelsProperty()
    {
        return spriteSubViewModels;
    }

    /**
     * Stellt der View die Position der Kamera zur Verfügung.
     *
     * @return Die Position der Kamera.
     */
    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt der View die Stärke des Kamera-Zooms zur Verfügung.
     *
     * @return Die Stärke des Kamera-Zooms.
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Ermöglicht es der View, sich als Observer zu registrieren, um bei Änderungen benachrichtigt zu werden.
     */
    // TODO Umbenennen
    public void notifyToRedraw(Observer observer)
    {
        redrawObservable.addObserver(observer);
    }

    /**
     * Benachrichtigt die Observer, dass sich etwas an den zu zeichnenden Objekten verändert hat.
     */
    private void redraw()
    {
        redrawObservable.setChanged();
        redrawObservable.notifyObservers();
    }

    public boolean isEditorMode()
    {
        return editorMode;
    }
}
