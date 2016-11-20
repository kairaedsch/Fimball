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

    /**
     * Setzt das zur PinballCanvasSubView gehörende PinballCanvasViewModel.
     *
     * @param pinballCanvasViewModel Das zu setzende PinballCanvasViewModel.
     */
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
            Vector2 gridStart = mousePosToGridPos(0, 0).scale(Config.pixelsPerGridUnit);
            Vector2 gridEnd = mousePosToGridPos(canvas.getWidth(), canvas.getHeight()).scale(Config.pixelsPerGridUnit);
            for (int gx = (int) gridStart.getX() - (int) gridStart.getX() % Config.pixelsPerGridUnit; gx <= gridEnd.getX(); gx += Config.pixelsPerGridUnit)
            {
                graphicsContext.strokeLine(gx, gridStart.getY(), gx, gridEnd.getY());
            }
            for (int gy = (int) gridStart.getY() - (int) gridStart.getY() % Config.pixelsPerGridUnit; gy <= gridEnd.getY(); gy += Config.pixelsPerGridUnit)
            {
                graphicsContext.strokeLine(gridStart.getX(), gy, gridEnd.getX(), gy);
            }
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
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mouseClickedOnGame(mousePosToGridPos(mouseEvent));
    }

    public void mousePressed(MouseEvent mouseEvent)
    {
        pinballCanvasViewModel.mousePressedOnGame(mousePosToGridPos(mouseEvent));
    }

    private Vector2 mousePosToGridPos(MouseEvent mouseEvent)
    {
        return mousePosToGridPos(mouseEvent.getX(), mouseEvent.getY());
    }

    private Vector2 mousePosToGridPos(double x, double y)
    {
        Vector2 posToMiddle = new Vector2(x - canvas.getWidth() / 2.0, y - canvas.getHeight() / 2.0);

        Vector2 posOnGrid = new Vector2();
        posOnGrid.setX(posToMiddle.getX() / (Config.pixelsPerGridUnit * cameraZoom.get()) + cameraPosition.get().getX());
        posOnGrid.setY(posToMiddle.getY() / (Config.pixelsPerGridUnit * cameraZoom.get()) + cameraPosition.get().getY());
        return posOnGrid;
    }
}
