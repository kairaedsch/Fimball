package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.GameSession;
import sep.fimball.model.World;
import sep.fimball.viewmodel.window.game.GameViewModel;

import java.util.Observable;
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

    /**
     * Erstellt ein neues PinballCanvasViewModel.
     *
     * @param session Die Spielsitzung.
     * @param gameViewModel Das korrespondierende GameViewModel.
     */
    public PinballCanvasViewModel(GameSession session, GameViewModel gameViewModel)
    {
        init(session.getWorld());
        Observer gameObserver = (o, args) -> redraw();
        session.addGameLoopObserver(gameObserver);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());
    }

    /**
     * Initialisiert die Zeichnung des Canvas.
     * @param world Die Spielwelt des anzuzeigenden Flipperautomaten.
     */
    private void init(World world)
    {
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, world.gameElementsProperty(), SpriteSubViewModel::new);

        redrawObservable = new Observable();
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
        redrawObservable.hasChanged();
        redrawObservable.notifyObservers();
    }
}
