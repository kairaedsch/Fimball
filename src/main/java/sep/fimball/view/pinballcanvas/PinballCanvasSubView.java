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

        int interv = 200;
        double n = (delta * 1.0) / interv;

        int interv2 = 50;
        double n2 = (delta * 1.0) / interv2;

        softCameraPosition = softCameraPosition.lerp(cameraPosition.get(), n);
        softCameraZoom = softCameraZoom * (1 - n2) + cameraZoom.get() * n2;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Config.primaryColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        graphicsContext.translate(canvas.getWidth() / 2d - softCameraPosition.getX() * pixelsPerGridUnit * softCameraZoom, canvas.getHeight() / 2d - softCameraPosition.getY() * pixelsPerGridUnit * softCameraZoom);

        graphicsContext.scale(softCameraZoom, softCameraZoom);

        if (pinballCanvasViewModel.isEditorMode())
        {
            graphicsContext.save();
            Vector2 gridStart = canvasPosToGridPos(0, 0).scale(pixelsPerGridUnit);
            Vector2 gridEnd = canvasPosToGridPos(canvas.getWidth(), canvas.getHeight()).scale(pixelsPerGridUnit);
            for (int gx = (int) gridStart.getX() - (int) gridStart.getX() % pixelsPerGridUnit; gx <= gridEnd.getX(); gx += pixelsPerGridUnit)
            {
                Color color;
                int width;
                if (Math.abs(gx) % (pixelsPerGridUnit * 2) == 0)
                {
                    color = Config.primaryColorLightLight;
                    width = 2;
                }
                else
                {
                    color = Config.primaryColorLight;
                    width = 1;
                }
                graphicsContext.setStroke(color);
                graphicsContext.setLineWidth(width);
                graphicsContext.strokeLine(gx, gridStart.getY(), gx, gridEnd.getY());
            }
            for (int gy = (int) gridStart.getY() - (int) gridStart.getY() % pixelsPerGridUnit; gy <= gridEnd.getY(); gy += pixelsPerGridUnit)
            {
                Color color;
                int width;
                if (Math.abs(gy) % (pixelsPerGridUnit * 2) == pixelsPerGridUnit)
                {
                    color = Config.primaryColorLightLight;
                    width = 2;
                }
                else
                {
                    color = Config.primaryColorLight;
                    width = 1;
                }
                graphicsContext.setStroke(color);
                graphicsContext.setLineWidth(width);
                graphicsContext.strokeLine(gridStart.getX(), gy, gridEnd.getX(), gy);
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
