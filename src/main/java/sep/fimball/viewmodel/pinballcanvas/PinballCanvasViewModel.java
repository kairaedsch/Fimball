package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;

import java.util.Observer;

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

    protected DrawMode drawMode;

    /**
     * Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
     */
    protected Observable redrawObservable;

    protected SimpleObjectProperty<RectangleDouble> boundingBox;

    protected PinballMachine pinballMachine;

    private ViewScreenshotCreater viewScreenshotCreater;


    /**
     * Erstellt ein neues PinballCanvasViewModel.
     *
     * @param gameSession   Die Spielsitzung.
     */
    protected PinballCanvasViewModel(GameSession gameSession, DrawMode drawMode)
    {
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();
        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());

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

    public ReadOnlyObjectProperty<RectangleDouble> boundingBoxProperty()
    {
        return new SimpleObjectProperty<>(pinballMachine.getBoundingBox());
    }

    public void mouseClickedOnGame(Vector2 vector2, MouseButton button)
    {

    }

    public void mousePressedOnGame(Vector2 vector2, MouseButton button)
    {

    }

    public void setViewScreenshotCreater(ViewScreenshotCreater viewScreenshotCreater)
    {
        this.viewScreenshotCreater = viewScreenshotCreater;
    }

    public WritableImage createScreenshot()
    {
        return viewScreenshotCreater.drawToImage();
    }
}
