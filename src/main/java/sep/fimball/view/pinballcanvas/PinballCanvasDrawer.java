package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.RectangleDoubleOfPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.util.Optional;

import static sep.fimball.general.data.DesignConfig.pixelsPerGridUnit;

/**
 * Der PinballCanvasDrawer ist eine Hilfsklasse welche die Operationen auf dem GraphicsContext des Canvas ausführt.
 */
public class PinballCanvasDrawer
{
    /**
     * Die Canvas auf der gezeichnet wird.
     */
    private Canvas canvas;

    /**
     * Der Modus mit dem gezeichnet werden soll.
     */
    private DrawMode drawMode;

    /**
     * Die zu zeichnenden Sprites.
     */
    private ReadOnlyListProperty<SpriteSubView> sprites;

    /**
     * Erstellt einen neuen PinballCanvasDrawer.
     *
     * @param canvas   Die Canvas auf der gezeichnet wird.
     * @param drawMode Der Modus mit dem gezeichnet werden soll.
     * @param sprites Die zu zeichnenden Sprites.
     */
    public PinballCanvasDrawer(Canvas canvas, DrawMode drawMode, ReadOnlyListProperty<SpriteSubView> sprites)
    {
        this.canvas = canvas;
        this.drawMode = drawMode;
        this.sprites = sprites;
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     *
     * @param cameraPosition die Position der Kamera.
     * @param cameraZoom der Zoom der Kamera.
     * @param dragSelectionRect das eventuell vorhandene Auswahlfenster welches es durch "ziehen" erlaubt Elemente auszuwählen.
     */
    public void draw(Vector2 cameraPosition, double cameraZoom, Optional<RectangleDoubleOfPoints> dragSelectionRect)
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(DesignConfig.primaryColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        Vector2 translate = new Vector2(canvas.getWidth(), canvas.getHeight()).scale(0.5).minus(cameraPosition.scale(pixelsPerGridUnit * cameraZoom));
        graphicsContext.translate(translate.getX(), translate.getY());
        graphicsContext.scale(cameraZoom, cameraZoom);

        if (drawMode == DrawMode.EDITOR)
        {
            drawEditorGrid(cameraPosition, cameraZoom);
        }

        drawElements(graphicsContext);
        Debug.draw(graphicsContext);

        if(dragSelectionRect.isPresent())
        {
            graphicsContext.setFill(DesignConfig.secondaryColor);
            graphicsContext.setStroke(DesignConfig.secondaryColorDark);
            graphicsContext.setLineWidth(0.25 * pixelsPerGridUnit);

            Vector2 ori = dragSelectionRect.get().getOrigin().scale(pixelsPerGridUnit);
            Vector2 end = dragSelectionRect.get().getSize().scale(pixelsPerGridUnit);

            graphicsContext.setGlobalAlpha(0.5);
            graphicsContext.fillRect(ori.getX(), ori.getY(), end.getX(), end.getY());

            graphicsContext.setGlobalAlpha(1);
            graphicsContext.strokeRect(ori.getX(), ori.getY(), end.getX(), end.getY());
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
            spriteTop.draw(graphicsContext, ImageLayer.BOTTOM, drawMode);
        }
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(graphicsContext, ImageLayer.TOP, drawMode);
        }
    }

    /**
     * Zeichnet das Gitter des Editors auf den übergebenen GraphicsContext.
     *
     * @param cameraPosition die Position der Kamera.
     * @param cameraZoom der Zoom der Kamera.
     */
    private void drawEditorGrid(Vector2 cameraPosition, double cameraZoom)
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.save();
        Vector2 gridStart = canvasPosToGridPos(cameraPosition, cameraZoom, 0, 0).scale(pixelsPerGridUnit);
        Vector2 gridEnd = canvasPosToGridPos(cameraPosition, cameraZoom, canvas.getWidth(), canvas.getHeight()).scale(pixelsPerGridUnit);
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
     * Rechnet die durch die {@code x} und {@code y} gegebene Position auf dem Canvas auf die zugehörige Grid-Position um.
     *
     * @param cameraPosition die Position der Kamera.
     * @param cameraZoom der Zoom der Kamera.
     * @param x Der x-Wert der Position auf dem Canvas.
     * @param y Der y-Wert der Position auf dem Canvas.
     * @return Die Position auf dem Grid.
     */
    public Vector2 canvasPosToGridPos(Vector2 cameraPosition, double cameraZoom, double x, double y)
    {
        Vector2 posToMiddle = new Vector2(x, y).minus(new Vector2(canvas.getWidth(), canvas.getHeight()).scale(0.5));
        return posToMiddle.scale(1 / (pixelsPerGridUnit * cameraZoom)).plus(cameraPosition);
    }
}
