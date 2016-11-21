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
import sep.fimball.general.Debug;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;

import java.util.Observer;

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

    /**
     * Die Stärke des Zooms der Kamera, die die Größe der Flipperautomaten-Elemente bestimmt.
     */
    private SimpleDoubleProperty cameraZoom;

    /**
     * Das zum PinballCanvasSubView gehörende PinballCanvasViewModel.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        this.pinballCanvasViewModel = pinballCanvasViewModel;
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), SpriteSubView::new);

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(pinballCanvasViewModel.cameraPositionProperty());

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(pinballCanvasViewModel.cameraZoomProperty());

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.notifyToRedraw(redrawObserver);

        Region parent = (Region) canvas.getParent();
        canvas.widthProperty().bind(parent.widthProperty());
        canvas.heightProperty().bind(parent.heightProperty());
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     */
    private void redraw()
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Config.baseColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        graphicsContext.translate((int) (canvas.getWidth() / 2d - cameraPosition.get().getX() * Config.pixelsPerGridUnit * cameraZoom.get()), (int) (canvas.getHeight() / 2d - cameraPosition.get().getY() * Config.pixelsPerGridUnit * cameraZoom.get()));

        graphicsContext.scale(cameraZoom.get(), cameraZoom.get());

        if(pinballCanvasViewModel.isEditorMode())
        {
            graphicsContext.setStroke(Config.baseColorLight);
            Vector2 gridStart = canvasPosToGridPos(0, 0).scale(Config.pixelsPerGridUnit);
            Vector2 gridEnd = canvasPosToGridPos(canvas.getWidth(), canvas.getHeight()).scale(Config.pixelsPerGridUnit);
            for (int gx = (int) gridStart.getX() - (int) gridStart.getX() % Config.pixelsPerGridUnit; gx <= gridEnd.getX(); gx += Config.pixelsPerGridUnit)
            {
                graphicsContext.strokeLine(gx, gridStart.getY(), gx, gridEnd.getY());
            }
            for (int gy = (int) gridStart.getY() - (int) gridStart.getY() % Config.pixelsPerGridUnit; gy <= gridEnd.getY(); gy += Config.pixelsPerGridUnit)
            {
                graphicsContext.strokeLine(gridStart.getX(), gy, gridEnd.getX(), gy);
            }
        }

        graphicsContext.setStroke(new Color(Config.contraColor.getRed(), Config.contraColor.getGreen(), Config.contraColor.getBlue(), 0.25));
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
    }

    /**
     * Benachrightigt das {@code pinballCanvasViewModel}, dass der Spieler an einer bestimmten Stelle im Grid geklickt hat.
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mouseClickedOnGame(mousePosToGridPos(mouseEvent));
    }

    /**
     * Benachrightigt das {@code pinballCanvasViewModel}, dass der Spieler die Mausstaste  einer bestimmten Stelle im Grid gedrückt hat.
     * @param mouseEvent
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mousePressedOnGame(mousePosToGridPos(mouseEvent));
    }

    /**
     * Berechnet die Position des MouseEvents auf dem Grid.
     * @param mouseEvent Das MouseEvent, dessen Position berechnet werden soll.
     * @return Die Position des MouseEvents auf dem Grid.
     */
    private Vector2 mousePosToGridPos(MouseEvent mouseEvent)
    {
        return canvasPosToGridPos(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * Rechnet die durch die {@code x} und {@code y} gegebene Position auf dem Canvas auf die zugehörige Gridposition um.
     * @param x Der x-Wert der Position auf dem Canvas.
     * @param y Der y-Wert der Position auf dem Canvas.
     * @return Die Position auf dem Grid.
     */
    private Vector2 canvasPosToGridPos(double x, double y)
    {
        Vector2 posToMiddle = new Vector2(x - canvas.getWidth() / 2.0, y - canvas.getHeight() / 2.0);

        Vector2 posOnGrid = new Vector2();
        posOnGrid.setX(posToMiddle.getX() / (Config.pixelsPerGridUnit * cameraZoom.get()) + cameraPosition.get().getX());
        posOnGrid.setY(posToMiddle.getY() / (Config.pixelsPerGridUnit * cameraZoom.get()) + cameraPosition.get().getY());
        return posOnGrid;
    }
}
