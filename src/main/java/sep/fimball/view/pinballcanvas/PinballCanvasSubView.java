package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
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

    private long lastDraw;

    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        this.pinballCanvasViewModel = pinballCanvasViewModel;
        lastDraw = System.currentTimeMillis();
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), SpriteSubView::new);

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(pinballCanvasViewModel.cameraPositionProperty());
        softCameraPosition = cameraPosition.get();

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(pinballCanvasViewModel.cameraZoomProperty());
        softCameraZoom = cameraZoom.get();

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.addRedrawObserver(redrawObserver);

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     */
    private void redraw()
    {
        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);

        double camFollowSpeed = 200.0;
        double camFollowStep = delta / camFollowSpeed;

        double cameraZoomSpeed = 50.0;
        double camZoomStep = delta / cameraZoomSpeed;

        softCameraPosition = softCameraPosition.lerp(cameraPosition.get(), camFollowStep);
        softCameraZoom = softCameraZoom * (1 - camZoomStep) + cameraZoom.get() * camZoomStep;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Config.primaryColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        graphicsContext.translate(canvas.getWidth() / 2.0 - softCameraPosition.getX() * pixelsPerGridUnit * softCameraZoom, canvas.getHeight() / 2.0 - softCameraPosition.getY() * pixelsPerGridUnit * softCameraZoom);

        graphicsContext.scale(softCameraZoom, softCameraZoom);

        if (pinballCanvasViewModel.isEditorMode())
        {
            graphicsContext.save();
            Vector2 gridStart = canvasPosToGridPos(0, 0).scale(pixelsPerGridUnit);
            Vector2 gridEnd = canvasPosToGridPos(canvas.getWidth(), canvas.getHeight()).scale(pixelsPerGridUnit);
            for (int gridX = (int) gridStart.getX() - (int) gridStart.getX() % pixelsPerGridUnit; gridX <= gridEnd.getX(); gridX += pixelsPerGridUnit)
            {
                Color lineColor;
                int lineWidth;

                // Make every second line bigger
                if (Math.abs(gridX) % (pixelsPerGridUnit * 2) == 0)
                {
                    lineColor = Config.primaryColorLightLight;
                    lineWidth = 2;
                }
                else
                {
                    lineColor = Config.primaryColorLight;
                    lineWidth = 1;
                }

                graphicsContext.setStroke(lineColor);
                graphicsContext.setLineWidth(lineWidth);
                graphicsContext.strokeLine(gridX, gridStart.getY(), gridX, gridEnd.getY());
            }
            for (int gridY = (int) gridStart.getY() - (int) gridStart.getY() % pixelsPerGridUnit; gridY <= gridEnd.getY(); gridY += pixelsPerGridUnit)
            {
                Color lineColor;
                int lineWidth;

                // Make every second line bigger
                if (Math.abs(gridY) % (pixelsPerGridUnit * 2) == pixelsPerGridUnit)
                {
                    lineColor = Config.primaryColorLightLight;
                    lineWidth = 2;
                }
                else
                {
                    lineColor = Config.primaryColorLight;
                    lineWidth = 1;
                }

                graphicsContext.setStroke(lineColor);
                graphicsContext.setLineWidth(lineWidth);
                graphicsContext.strokeLine(gridStart.getX(), gridY, gridEnd.getX(), gridY);
            }
            graphicsContext.restore();
        }

        for (SpriteSubView spriteTop : sprites)
        {
            spriteTop.draw(canvas.getGraphicsContext2D(), ImageLayer.BOTTOM);
        }
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(canvas.getGraphicsContext2D(), ImageLayer.TOP);
        }

        Debug.draw(graphicsContext);

        graphicsContext.restore();

        lastDraw = currentDraw;
    }

    /**
     * Benachrichtigt das {@code pinballCanvasViewModel}, dass der Spieler an einer bestimmten Stelle im Grid geklickt hat.
     *
     * @param mouseEvent Das Mouse-Event, das verarbeitet werden soll.
     */
    public void mouseClicked(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mouseClickedOnGame(mousePosToGridPos(mouseEvent));
    }

    /**
     * Benachrichtigt das {@code pinballCanvasViewModel}, dass der Spieler die Maustaste  einer bestimmten Stelle im Grid gedrückt hat.
     *
     * @param mouseEvent Das Mouse-Event, das verarbeitet werden soll.
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mousePressedOnGame(mousePosToGridPos(mouseEvent));
    }

    /**
     * Berechnet die Position des MouseEvents auf dem Grid.
     *
     * @param mouseEvent Das MouseEvent, dessen Position berechnet werden soll.
     * @return Die Position des MouseEvents auf dem Grid.
     */
    private Vector2 mousePosToGridPos(MouseEvent mouseEvent)
    {
        return canvasPosToGridPos(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * Rechnet die durch die {@code x} und {@code y} gegebene Position auf dem Canvas auf die zugehörige Grid-Position um.
     *
     * @param x Der x-Wert der Position auf dem Canvas.
     * @param y Der y-Wert der Position auf dem Canvas.
     * @return Die Position auf dem Grid.
     */
    private Vector2 canvasPosToGridPos(double x, double y)
    {
        Vector2 posToMiddle = new Vector2(x - canvas.getWidth() / 2.0, y - canvas.getHeight() / 2.0);

        double vx = posToMiddle.getX() / (pixelsPerGridUnit * softCameraZoom) + softCameraPosition.getX();
        double vy = posToMiddle.getY() / (pixelsPerGridUnit * softCameraZoom) + softCameraPosition.getY();
        return new Vector2(vx, vy);
    }
}
