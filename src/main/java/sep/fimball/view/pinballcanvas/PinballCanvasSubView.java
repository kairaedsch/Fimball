package sep.fimball.view.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.DrawMode;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;

import java.util.Observer;

import static sep.fimball.general.data.Config.pixelsPerGridUnit;

/**
 * Die PinballCanvasSubView ist für das Zeichnen eines Flipperautomaten mit all seinen Elementen zuständig.
 */
public class PinballCanvasSubView implements ViewBoundToViewModel<PinballCanvasViewModel>
{
    /**
     * Das Canvas, in dem die Sprites gezeichnet werden.
     */
    @FXML
    private Canvas canvas;

    /**
     * Alle Sprites der zu zeichnenden Flipperautomaten-Elemente.
     */
    private ListProperty<SpriteSubView> sprites;

    /**
     * Die Position der Kamera, die den Ausschnitt des Flipperautomaten angibt, der gezeichnet werden soll.
     */
    private SimpleObjectProperty<Vector2> cameraPosition;

    private Vector2 softCameraPosition;

    /**
     * Die Stärke des Zooms der Kamera, die die Größe der Flipperautomaten-Elemente bestimmt.
     */
    private SimpleDoubleProperty cameraZoom;

    private double softCameraZoom;

    /**
     * Das zum PinballCanvasSubView gehörende PinballCanvasViewModel.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Die Zeit, zu der sich dieses Objekt zuletzt neu gezeichnet hat.
     */
    private long lastDraw;

    private DrawMode drawMode;

    /**
     * Setzt das ViewModel dieses Objekts und bindet die Eigenschaften der View an die entsprechenden Eigenschaften des ViewModels.
     *
     * @param pinballCanvasViewModel Das neue ViewModel.
     */
    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        this.pinballCanvasViewModel = pinballCanvasViewModel;
        lastDraw = System.currentTimeMillis();
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), (viewModel) -> new SpriteSubView(viewModel, ImageCache.getInstance()));

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(pinballCanvasViewModel.cameraPositionProperty());
        softCameraPosition = cameraPosition.get();

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(pinballCanvasViewModel.cameraZoomProperty());
        softCameraZoom = cameraZoom.get();

        drawMode = pinballCanvasViewModel.getDrawMode();

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.addRedrawObserver(redrawObserver);

        Observer generateImageObserver = (o, arg) -> drawToImage();
        pinballCanvasViewModel.addGenerateImageObserver(generateImageObserver);

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());
    }

    private void redraw()
    {
        double camFollowSpeed = drawMode == DrawMode.GAME ? 500 : 50;
        double cameraZoomSpeed = 50;

        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);

        double camFollowStep = delta / camFollowSpeed;
        camFollowStep = Math.max(Math.min(camFollowStep, 1), 0);

        double camZoomStep = delta / cameraZoomSpeed;
        camZoomStep = Math.max(Math.min(camZoomStep, 1), 0);

        softCameraPosition = softCameraPosition.lerp(cameraPosition.get(), camFollowStep);
        softCameraZoom = softCameraZoom * (1 - camZoomStep) + cameraZoom.get() * camZoomStep;

        PinballCanvasDrawer.draw(canvas, sprites, softCameraPosition, softCameraZoom, drawMode);
    }

    private void drawToImage()
    {
        Canvas screenShotCanvas = new Canvas();
        screenShotCanvas.setHeight(100);
        screenShotCanvas.setWidth(100);
        PinballCanvasDrawer.draw(screenShotCanvas, sprites, cameraPosition.getValue(), cameraZoom.getValue(), DrawMode.SCREENSHOT);
        WritableImage writeableImage = new WritableImage((int) screenShotCanvas.getWidth(), (int) screenShotCanvas.getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        screenShotCanvas.snapshot(snapshotParameters, writeableImage);
        pinballCanvasViewModel.setGeneratedPreviewImage(writeableImage);
    }

    /**
     * Benachrichtigt das {@code pinballCanvasViewModel}, dass der Spieler an einer bestimmten Stelle im Grid geklickt hat.
     *
     * @param mouseEvent Das Mouse-Event, das verarbeitet werden soll.
     */
    public void mouseClicked(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mouseClickedOnGame(mousePosToGridPos(mouseEvent), mouseEvent.getButton());
    }

    /**
     * Benachrichtigt das {@code pinballCanvasViewModel}, dass der Spieler die Maustaste  einer bestimmten Stelle im Grid gedrückt hat.
     *
     * @param mouseEvent Das Mouse-Event, das verarbeitet werden soll.
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mousePressedOnGame(mousePosToGridPos(mouseEvent), mouseEvent.getButton());
    }

    /**
     * Berechnet die Position des MouseEvents auf dem Grid.
     *
     * @param mouseEvent Das MoimageuseEvent, dessen Position berechnet werden soll.
     * @return Die Position des MouseEvents auf dem Grid.
     */
    private Vector2 mousePosToGridPos(MouseEvent mouseEvent)
    {
        return PinballCanvasDrawer.canvasPosToGridPos(canvas, softCameraPosition, softCameraZoom, mouseEvent.getX(), mouseEvent.getY());
    }
}
