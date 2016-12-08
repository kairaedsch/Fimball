package sep.fimball.view.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
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
    private SimpleBooleanProperty softCamera;

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

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.addRedrawObserver(redrawObserver);

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());

        softCamera = new SimpleBooleanProperty();
        softCamera.bind(pinballCanvasViewModel.editorModeProperty().not());
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     */
    private void redraw()
    {
        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);
        double camFollowSpeed = softCamera.get() ? 500.0 : 50;
        double camFollowStep = delta / camFollowSpeed;
        camFollowStep = Math.max(Math.min(camFollowStep, 1), 0);
        double cameraZoomSpeed = 50.0;
        double camZoomStep = delta / cameraZoomSpeed;
        camZoomStep = Math.max(Math.min(camZoomStep, 1), 0);
        softCameraPosition = softCameraPosition.lerp(cameraPosition.get(), camFollowStep);
        softCameraZoom = softCameraZoom * (1 - camZoomStep) + cameraZoom.get() * camZoomStep;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(DesignConfig.primaryColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        graphicsContext.translate(canvas.getWidth() / 2.0 - softCameraPosition.getX() * pixelsPerGridUnit * softCameraZoom, canvas.getHeight() / 2.0 - softCameraPosition.getY() * pixelsPerGridUnit * softCameraZoom);
        graphicsContext.scale(softCameraZoom, softCameraZoom);
        if (pinballCanvasViewModel.editorModeProperty().get())
        {
            drawEditorGrid(graphicsContext);
        }
        drawElements(graphicsContext);
        graphicsContext.restore();
        lastDraw = currentDraw;
    }

    /**
     * Zeichnet das Gitter des Editors auf den übergebenen GraphicsContext.
     *
     * @param graphicsContext Der GraphicsContext, auf dem gezeichnet wird.
     */
    private void drawEditorGrid(GraphicsContext graphicsContext)
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
                lineColor = DesignConfig.primaryColorLightLight;
                lineWidth = 2;
            }
            else
            {
                lineColor = DesignConfig.primaryColorLight;
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
                lineColor = DesignConfig.primaryColorLightLight;
                lineWidth = 2;
            }
            else
            {
                lineColor = DesignConfig.primaryColorLight;
                lineWidth = 1;
            }

            graphicsContext.setStroke(lineColor);
            graphicsContext.setLineWidth(lineWidth);
            graphicsContext.strokeLine(gridStart.getX(), gridY, gridEnd.getX(), gridY);
        }
        graphicsContext.restore();
    }

    /**
     * Zeichnet jedes Spielelement auf den übergebenen GraphicsContext.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die Spielelemente gezeichnet werden sollen.
     */
    private void drawElements(GraphicsContext graphicsContext)
    {
        for (SpriteSubView spriteTop : sprites)
        {
            spriteTop.draw(graphicsContext, ImageLayer.BOTTOM);
        }
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(graphicsContext, ImageLayer.TOP);
        }
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
