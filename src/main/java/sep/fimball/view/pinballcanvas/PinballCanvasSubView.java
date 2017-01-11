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
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.pinballcanvas.ViewScreenshotCreator;

import java.util.Observer;
import java.util.Optional;

/**
 * Die PinballCanvasSubView ist für das Zeichnen eines Flipperautomaten mit all seinen Elementen zuständig.
 */
public class PinballCanvasSubView implements ViewBoundToViewModel<PinballCanvasViewModel>, ViewScreenshotCreator
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

    /**
     * Die Position der Kamera welche der eigentlichen Kamera etwas langsamer folgt um ein angenehmeres Spielgefühl zu erzeugen.
     */
    private Vector2 softCameraPosition;

    /**
     * Die Stärke des Zooms der Kamera, die die Größe der Flipperautomaten-Elemente bestimmt.
     */
    private SimpleDoubleProperty cameraZoom;

    /**
     * Der Zoom der Kamera welcher den eigentlichen Zoom etwas langsamer folgt.
     */
    private double softCameraZoom;

    /**
     * Das zum PinballCanvasSubView gehörende PinballCanvasViewModel.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Die Zeit, zu der sich dieses Objekt zuletzt neu gezeichnet hat.
     */
    private long lastDraw;

    /**
     * Der Modus in dem gezeichnet werden soll.
     */
    private DrawMode drawMode;

    /**
     * Die Hilfsklasse welche die Operationen auf dem GraphicsContext des Canvas ausführt.
     */
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

        pinballCanvasViewModel.setViewScreenshotCreator(this);

        pinballCanvasDrawer = new PinballCanvasDrawer(canvas, drawMode, sprites, pinballCanvasViewModel.boundingBoxProperty());
    }

    /**
     * {@inheritDoc}
     */
    public WritableImage getScreenshot()
    {
        double minWidth = (1280 / DesignConfig.PIXELS_PER_GRID_UNIT);
        double minHeight = (720 / DesignConfig.PIXELS_PER_GRID_UNIT);
        double maxWidth = (3840 / DesignConfig.PIXELS_PER_GRID_UNIT);
        double maxHeight = (2160 / DesignConfig.PIXELS_PER_GRID_UNIT);
        double cameraScale = 1.0;

        RectangleDouble rectangleDouble = pinballCanvasViewModel.boundingBoxProperty().get();
        if (rectangleDouble.getWidth() < minWidth || rectangleDouble.getHeight() < minHeight)
        {
            double scale = (minWidth / rectangleDouble.getWidth());
            double newHeight = rectangleDouble.getHeight() * scale;
            double newOriginY = rectangleDouble.getOrigin().getY() - (newHeight - rectangleDouble.getHeight()) / 2.0;
            double newOriginX = rectangleDouble.getOrigin().getX() - (minHeight - rectangleDouble.getWidth()) / 2.0;
            rectangleDouble = new RectangleDouble(new Vector2(newOriginX, newOriginY), minWidth, newHeight);
        }
        if (rectangleDouble.getWidth() > maxWidth || rectangleDouble.getHeight() > maxHeight)
        {
            double scaleX = (maxWidth / rectangleDouble.getWidth());
            double scaleY = (maxHeight / rectangleDouble.getHeight());
            cameraScale = Math.min(scaleX, scaleY);
            double newBorder = Math.max(rectangleDouble.getWidth() * cameraScale, rectangleDouble.getHeight() * cameraScale);
            double newOriginX = rectangleDouble.getOrigin().getX() + (rectangleDouble.getWidth() - newBorder) / 2.0;
            double newOriginY = rectangleDouble.getOrigin().getY() + (rectangleDouble.getHeight() - newBorder) / 2.0;
            rectangleDouble = new RectangleDouble(new Vector2(newOriginX, newOriginY), newBorder, newBorder);
        }

        return createScreenshot(cameraScale, rectangleDouble);
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
     * Aktualisiert das Canvas durch erneutes Zeichnen.
     */
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

        pinballCanvasDrawer.draw(softCameraPosition, softCameraZoom, pinballCanvasViewModel.selectingRectangleProperty());

        lastDraw = currentDraw;
    }

    /**
     * Erstellt ein Bild des aktuellen Automaten und gibt dieses zurück.
     *
     * @param cameraScale Die für den Screenshot verwendete Skalierung der Kamera.
     * @param rectangleDouble Die für den Screenshot verwendete Größe des Automatenausschnitts.
     * @return Ein Bild des aktuellen Automaten.
     */
    private WritableImage createScreenshot(double cameraScale, RectangleDouble rectangleDouble)
    {
        Canvas screenShotCanvas = new Canvas();
        screenShotCanvas.setHeight(rectangleDouble.getHeight() * DesignConfig.PIXELS_PER_GRID_UNIT);
        screenShotCanvas.setWidth(rectangleDouble.getWidth() * DesignConfig.PIXELS_PER_GRID_UNIT);

        PinballCanvasDrawer screenshotCanvasDrawer = new PinballCanvasDrawer(screenShotCanvas, DrawMode.SCREENSHOT, sprites, pinballCanvasViewModel.boundingBoxProperty());
        screenshotCanvasDrawer.draw(rectangleDouble.getMiddle(), cameraScale, Optional.empty());
        WritableImage writableImage = new WritableImage((int) screenShotCanvas.getWidth(), (int) screenShotCanvas.getHeight());

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        screenShotCanvas.snapshot(snapshotParameters, writableImage);
        return writableImage;
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
