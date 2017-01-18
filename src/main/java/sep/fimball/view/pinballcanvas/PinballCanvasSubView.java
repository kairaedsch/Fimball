package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
     * Das zum PinballCanvasSubView gehörende PinballCanvasViewModel.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Die Hilfsklasse welche die Operationen auf dem GraphicsContext des Canvas ausführt.
     */
    private PinballCanvasDrawer pinballCanvasDrawer;

    /**
     * Die Kamera, welche den Bereich des Spielfelds ausrechnet der angezeigt wird.
     */
    private Camera camera;

    /**
     * Setzt das ViewModel dieses Objekts und bindet die Eigenschaften der View an die entsprechenden Eigenschaften des ViewModels.
     *
     * @param pinballCanvasViewModel Das neue ViewModel.
     */
    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        this.pinballCanvasViewModel = pinballCanvasViewModel;
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), (viewModel) -> new SpriteSubView(viewModel, ImageCache.getInstance()));

        camera = new Camera(pinballCanvasViewModel.getDrawMode(), pinballCanvasViewModel.cameraPositionProperty(), pinballCanvasViewModel.cameraZoomProperty());

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.addRedrawObserver(redrawObserver);

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());

        canvas.cursorProperty().bind(pinballCanvasViewModel.cursorProperty());

        pinballCanvasViewModel.setViewScreenshotCreator(this);

        pinballCanvasDrawer = new PinballCanvasDrawer(canvas, pinballCanvasViewModel.getDrawMode(), sprites, pinballCanvasViewModel.getBoundingBox());
    }

    /**
     * {@inheritDoc}
     */
    public WritableImage getScreenshot()
    {
        double cameraScale = 1.0;
        RectangleDouble rectangleDouble = pinballCanvasViewModel.getBoundingBox();
        ViewportRestrictor viewportRestrictor = new ViewportRestrictor(cameraScale);
        ViewportRestrictor.RestrictedViewport restrictedViewport = viewportRestrictor.restrictRectangle(rectangleDouble);
        return createScreenshot(restrictedViewport.getRestrictedCameraScale(), restrictedViewport.getRestrictedRectangle());
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
        camera.updatePosition(canvas.getWidth(), canvas.getHeight());
        pinballCanvasDrawer.draw(camera.getSoftCameraPosition(), camera.getSoftCameraZoom(), pinballCanvasViewModel.selectingRectangleProperty());
    }

    /**
     * Erstellt ein Bild des aktuellen Automaten und gibt dieses zurück.
     *
     * @param cameraScale     Die für den Screenshot verwendete Skalierung der Kamera.
     * @param rectangleDouble Die für den Screenshot verwendete Größe des Automaten-Ausschnitts.
     * @return Ein Bild des aktuellen Automaten.
     */
    private WritableImage createScreenshot(double cameraScale, RectangleDouble rectangleDouble)
    {
        Canvas screenShotCanvas = new Canvas();
        screenShotCanvas.setHeight(rectangleDouble.getHeight() * DesignConfig.PIXELS_PER_GRID_UNIT);
        screenShotCanvas.setWidth(rectangleDouble.getWidth() * DesignConfig.PIXELS_PER_GRID_UNIT);

        PinballCanvasDrawer screenshotCanvasDrawer = new PinballCanvasDrawer(screenShotCanvas, DrawMode.SCREENSHOT, sprites, pinballCanvasViewModel.getBoundingBox());
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
        return pinballCanvasDrawer.canvasPosToGridPos(camera.getSoftCameraPosition(), camera.getSoftCameraZoom(), mouseEvent.getX(), mouseEvent.getY());
    }
}
