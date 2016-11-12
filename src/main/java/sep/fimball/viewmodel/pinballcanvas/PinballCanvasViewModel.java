package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.World;
import sep.fimball.viewmodel.window.game.GameViewModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Das PinballCanvasViewModel stellt der View Daten für die Anzeige des Flipperautomaten mit all seinen Elementen zu Verfügung und dient als Observable, um die View bei Änderungen zum eneuten zeichnen auffordern zu können.
 */
public class PinballCanvasViewModel
{
    /**
     * Liste aller spriteSubViewModels die Informationen zum Zeichnen enthalten.
     */
    private ListProperty<SpriteSubViewModel> spriteSubViewModels;

    /**
     * Position der Kamera, welche festlegt von welchen Standpunkt aus die ElementType gezeichnet werden sollen.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Legt fest, wie groß Elemente zu zeichnen sind und legt somit auch fest, wie viele ElementType der Nutzer sehen kann.
     */
    private DoubleProperty cameraZoom;

    /**
     * Observable, um die View bei Änderungen zum erneuten zeichnen auffordern zu können.
     */
    private Observable redrawObservable;

    /**
     * Erstellt ein neues PinballCanvasViewModel.
     * @param world Die Spielwelt des anzuzeigenden Flipperautomatens.
     * @param gameViewModel Das korrespondierende GameViewModel.
     */
    public PinballCanvasViewModel(World world, GameViewModel gameViewModel)
    {
        init(world);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());
    }

    /**
     * Initzialisiert die Zeichnung des Canvas.
     * @param world Die Spielwelt des anzuzeigenden Flipperautomatens.
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
     * Stellt die Liste aller spriteSubViewModels für die View zu Verfügung.
     * @return Eine Liste aller spriteSubViewModels.
     */
    public ReadOnlyListProperty<SpriteSubViewModel> spriteSubViewModelsProperty()
    {
        return spriteSubViewModels;
    }

    /**
     * Stellt die Position der Kamera für die View zu Verfügung.
     * @return Die Position der Kamera.
     */
    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt die Stärke des Kamera-Zooms für die View zu Verfügung.
     * @return Die Stärke des Kamera-Zooms.
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Ermöglicht es der View, sich als Observer zu registrieren, um dann bei Änderungen zum neu zeichnen benachrichtigt zu werden.
     */
    public void notifyToRedraw(Observer observer)
    {
        redrawObservable.addObserver(observer);
    }

    /**
     * TODO
     */
    private void redraw()
    {
        redrawObservable.hasChanged();
        redrawObservable.notifyObservers();
    }
}
