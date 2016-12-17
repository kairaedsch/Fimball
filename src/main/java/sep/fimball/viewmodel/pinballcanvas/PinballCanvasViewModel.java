package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.RectangleDoubleOfPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;

import java.util.Observer;
import java.util.Optional;

/**
 * Das PinballCanvasViewModel stellt der View Daten für die Anzeige des Flipperautomaten mit all seinen Elementen zur Verfügung und dient als Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
 */
public abstract class PinballCanvasViewModel
{
    /**
     * Liste aller SpriteSubViewModels, die Informationen zum Zeichnen enthalten.
     */
    protected ListProperty<SpriteSubViewModel> spriteSubViewModels;

    /**
     * Position der Kamera, die festlegt, von welchem Standpunkt aus die Automatenelemente gezeichnet werden sollen.
     */
    protected ObjectProperty<Vector2> cameraPosition;

    /**
     * Legt fest, wie groß Elemente zu zeichnen sind und legt somit auch fest, wie viele BaseElement der Nutzer sehen kann.
     */
    protected DoubleProperty cameraZoom;

    /**
     * Der Zeichenmodus.
     */
    protected DrawMode drawMode;

    /**
     * Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
     */
    protected Observable redrawObservable;

    /**
     * Der Bereich des Automaten, in welchen sich Spielelemente befinden.
     */
    protected SimpleObjectProperty<RectangleDouble> boundingBox;

    /**
     * Der zu zeichnende Flipperautomat.
     */
    protected PinballMachine pinballMachine;

    /**
     * Element aus der View, welches ein Screenshot des Flipperautomaten erstellen kann.
     */
    private ViewScreenshotCreater viewScreenshotCreater;

    /**
     * Erstellt ein neues PinballCanvasViewModel.
     *
     * @param gameSession Die Spielsitzung.
     * @param drawMode    Der Zeichenmodus.
     */
    protected PinballCanvasViewModel(GameSession gameSession, DrawMode drawMode)
    {
        pinballMachine = gameSession.getPinballMachine();
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();
        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        boundingBox = new SimpleObjectProperty<>(pinballMachine.getBoundingBox());

        redrawObservable = new Observable();

        Observer gameObserver = (o, args) -> redraw();
        gameSession.addGameLoopObserver(gameObserver);

        this.drawMode = drawMode;
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
     *
     * @param observer Der Observer, der registriert werden soll.
     */
    public void addRedrawObserver(Observer observer)
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

    /**
     * Stellt der View die Information, ob der Flipperautomat im Editor benutzt wird, zur Verfügung.
     *
     * @return Ob das Pinball-Canvas im Editor benutzt wird.
     */
    public DrawMode getDrawMode()
    {
        return drawMode;
    }

    /**
     * Gibt den Bereich des Automaten zurück, in welchen sich Spielelemente befinden.
     *
     * @return Der Bereich des Automaten, in welchen sich Spielelemente befinden.
     */
    public ReadOnlyObjectProperty<RectangleDouble> boundingBoxProperty()
    {
        return boundingBox;
    }

    /**
     * Es wurde auf das Canvas vom Nutzer gedrückt.
     *
     * @param vector2    Position der Maus.
     * @param mouseEvent Das Ausgelöste MouseEvent.
     */
    public void mousePressedOnGame(Vector2 vector2, MouseEvent mouseEvent)
    {

    }

    /**
     * Setzt den ViewScreenshotCreater, welcher ein Screenshot des Flipperautomaten erstellen kann.
     *
     * @param viewScreenshotCreater Der ViewScreenshotCreater.
     */
    public void setViewScreenshotCreater(ViewScreenshotCreater viewScreenshotCreater)
    {
        this.viewScreenshotCreater = viewScreenshotCreater;
    }

    /**
     * Beauftragt den ViewScreenshotCreater ein Bild zu machen.
     *
     * @return Das gemachte Bild.
     */
    public WritableImage createScreenshot()
    {
        return viewScreenshotCreater.drawToImage();
    }

    public Optional<RectangleDoubleOfPoints> selectingRectangleProperty()
    {
        return Optional.empty();
    }
}
