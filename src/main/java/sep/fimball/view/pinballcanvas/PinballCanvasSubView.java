package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import sep.fimball.general.data.Config;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.pinballcanvas.ViewScreenshotCreater;

import java.util.Observer;

/**
 * Die PinballCanvasSubView ist für das Zeichnen eines Flipperautomaten mit all seinen Elementen zuständig.
 */
public class PinballCanvasSubView implements ViewBoundToViewModel<PinballCanvasViewModel>, ViewScreenshotCreater
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

    private PinballCanvasDrawer pinballCanvasDrawer;

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

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());

        pinballCanvasViewModel.setViewScreenshotCreater(this);

        pinballCanvasDrawer = new PinballCanvasDrawer(canvas, drawMode, sprites);
    }

    private void redraw()
    {
        double camFollowSpeed = drawMode == DrawMode.GAME ? 500 : 50;
        double cameraZoomSpeed = 50;

        camFollowSpeed = 500;

        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);

        double camFollowStep = delta / camFollowSpeed;
        camFollowStep = Math.max(Math.min(camFollowStep, 1), 0);

        double camZoomStep = delta / cameraZoomSpeed;
        camZoomStep = Math.max(Math.min(camZoomStep, 1), 0);

        Vector2 oldP = softCameraPosition;

        softCameraPosition = softCameraPosition.lerp(cameraPosition.get(), camFollowStep);
        softCameraZoom = softCameraZoom * (1 - camZoomStep) + cameraZoom.get() * camZoomStep;

        Vector2 newP = softCameraPosition;

        softCameraPosition = oldP.plus(newP.minus(oldP).clamp(0.25));

        pinballCanvasDrawer.draw(softCameraPosition, softCameraZoom);

        lastDraw = currentDraw;
    }

    public WritableImage drawToImage()
    {
        RectangleDouble rectangleDouble = pinballCanvasViewModel.boundingBoxProperty().get();
        double minWidth = (1280 / Config.pixelsPerGridUnit);
        double minHeight = (720 / Config.pixelsPerGridUnit);
        if (rectangleDouble.getWidth() < minWidth && rectangleDouble.getHeight() < minHeight)
        {
            double scale = (minHeight / rectangleDouble.getWidth());
            double newHeight = rectangleDouble.getHeight() * scale;
            double newOriginY = rectangleDouble.getOrigin().getY() - (newHeight - rectangleDouble.getHeight()) / 2.0;
            double newOriginX = rectangleDouble.getOrigin().getX() - (minHeight - rectangleDouble.getWidth()) / 2.0;
            rectangleDouble = new RectangleDouble(new Vector2(newOriginX, newOriginY), minHeight, newHeight);
        }

        Canvas screenShotCanvas = new Canvas();
        screenShotCanvas.setHeight(rectangleDouble.getHeight() * Config.pixelsPerGridUnit);
        screenShotCanvas.setWidth(rectangleDouble.getWidth() * Config.pixelsPerGridUnit);
        PinballCanvasDrawer screenshotCanvasDrawer = new PinballCanvasDrawer(screenShotCanvas, DrawMode.SCREENSHOT, sprites);
        screenshotCanvasDrawer.draw(rectangleDouble.getMiddle(), 1.0);
        WritableImage writeableImage = new WritableImage((int) screenShotCanvas.getWidth(), (int) screenShotCanvas.getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        screenShotCanvas.snapshot(snapshotParameters, writeableImage);
        return writeableImage;
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
        pinballCanvasViewModel.mousePressedOnGame(mousePosToGridPos(mouseEvent), mouseEvent);
    }

    /**
     * Berechnet die Position des MouseEvents auf dem Grid.
     *
     * @param mouseEvent Das MouseEvent, dessen Position berechnet werden soll.
     * @return Die Position des MouseEvents auf dem Grid.
     */
    private Vector2 mousePosToGridPos(MouseEvent mouseEvent)
    {
        return pinballCanvasDrawer.canvasPosToGridPos(softCameraPosition, softCameraZoom, mouseEvent.getX(), mouseEvent.getY());
    }
}
