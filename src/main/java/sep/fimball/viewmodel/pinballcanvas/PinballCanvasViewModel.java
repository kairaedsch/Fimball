package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.Session;

import java.util.List;
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
    private DrawMode drawMode;

    /**
     * Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
     */
    private Observable redrawObservable;

    /**
     * Element aus der View, welches ein Screenshot des Flipperautomaten erstellen kann.
     */
    private ViewScreenshotCreator viewScreenshotCreator;

    /**
     * Die angezeigte PinballMachine.
     */
    private PinballMachine pinballMachine;

    /**
     * Erstellt ein neues PinballCanvasViewModel.
     *
     * @param session  Die Spielsitzung.
     * @param drawMode Der Zeichenmodus.
     */
    protected PinballCanvasViewModel(Session session, DrawMode drawMode)
    {
        pinballMachine = session.getPinballMachine();
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();
        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());

        redrawObservable = new Observable();

        Observer gameObserver = (o, args) -> redraw();
        session.addGameLoopObserver(gameObserver);

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
    public RectangleDouble getBoundingBox()
    {
        return pinballMachine.getBoundingBox();
    }

    /**
     * Es wurde auf das Canvas vom Nutzer gedrückt.
     *
     * @param vector2    Position der Maus.
     * @param mouseEvent Das Ausgelöste MouseEvent.
     */
    public abstract void mousePressedOnGame(Vector2 vector2, MouseEvent mouseEvent);

    /**
     * Der aktuelle Cursor welcher auf dem Canvas sichtbar ist.
     *
     * @return Der Cursor welcher auf dem Canvas sichtbar ist.
     */
    public abstract ReadOnlyObjectProperty<Cursor> cursorProperty();

    /**
     * Setzt den ViewScreenshotCreator, welcher ein Screenshot des Flipperautomaten erstellen kann.
     *
     * @param viewScreenshotCreator Der ViewScreenshotCreator.
     */
    public void setViewScreenshotCreator(ViewScreenshotCreator viewScreenshotCreator)
    {
        this.viewScreenshotCreator = viewScreenshotCreator;
    }

    /**
     * Beauftragt den ViewScreenshotCreator ein Bild zu machen.
     *
     * @return Das gemachte Bild.
     */
    public WritableImage createScreenshot()
    {
        return viewScreenshotCreator.getScreenshot();
    }

    /**
     * Gibt den Bereich zurück, welchen der Nutzer gerade auswählt.
     *
     * @return Der Bereich, welchen der Nutzer gerade auswählt.
     */
    public abstract Optional<RectangleDoubleByPoints> selectingRectangleProperty();

    /**
     * Benachrichtigt die Observer, dass sich etwas an den zu zeichnenden Objekten verändert hat.
     */
    private void redraw()
    {
        redrawObservable.setChanged();
        redrawObservable.notifyObservers();
    }

    /**
     * Vergrößert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomOut()
    {
        if (cameraZoom.get() >= 1)
        {
            setCameraZoom(Math.max(Config.MIN_ZOOM, cameraZoom.get() - 0.125));
        }
        else
        {
            setCameraZoom(Math.max(Config.MIN_ZOOM, cameraZoom.get() - 0.1));
        }
    }

    /**
     * Verkleinert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomIn()
    {
        if (cameraZoom.get() >= 1)
        {
            setCameraZoom(Math.min(Config.MAX_ZOOM, cameraZoom.get() + 0.125));
        }
        else
        {
            setCameraZoom(Math.min(Config.MAX_ZOOM, cameraZoom.get() + 0.1));
        }
    }

    /**
     * Setzt den Zoom der Kamera auf den gegebenen Wert.
     *
     * @param zoom Der neue Zoom der Kamera.
     */
    protected abstract void setCameraZoom(double zoom);

    /**
     * Gibt die Pfade aller Bilder zurück, die es gibt.
     *
     * @return Die Pfade aller Bilder die es gibt.
     */
    public List<String> getAllImagePaths()
    {
        return BaseElementManager.getInstance().getAllImagePaths();
    }
}
