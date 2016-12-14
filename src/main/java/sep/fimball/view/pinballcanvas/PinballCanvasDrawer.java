package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;

import static sep.fimball.general.data.Config.pixelsPerGridUnit;

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
     * Erstellt einen neuen PinballCanvasDrawer.
     *
     * @param canvas   Die Canvas auf der gezeichnet wird.
     * @param drawMode Der Modus mit dem gezeichnet werden soll.
     */
    public PinballCanvasDrawer(Canvas canvas, DrawMode drawMode)
    {
        this.canvas = canvas;
        this.drawMode = drawMode;
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     */
    public void draw(ListProperty<SpriteSubView> sprites, Vector2 cameraPosition, double cameraZoom)
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
            drawEditorGrid(canvas, cameraPosition, cameraZoom);
        }

        drawElements(graphicsContext, sprites);
        Debug.draw(graphicsContext);
        graphicsContext.restore();
    }

    /**
     * Zeichnet jedes Spielelement auf den übergebenen GraphicsContext.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die Spielelemente gezeichnet werden sollen.
     */
    private void drawElements(GraphicsContext graphicsContext, ListProperty<SpriteSubView> sprites)
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
     */
    private void drawEditorGrid(Canvas canvas, Vector2 cameraPosition, double cameraZoom)
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
