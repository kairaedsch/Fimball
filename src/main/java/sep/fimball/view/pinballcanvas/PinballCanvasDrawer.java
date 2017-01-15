package sep.fimball.view.pinballcanvas;

import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.*;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.util.Optional;

import static sep.fimball.general.data.DesignConfig.AUTOMATE_BORDER_WIDTH;
import static sep.fimball.general.data.DesignConfig.PIXELS_PER_GRID_UNIT;

/**
 * Der PinballCanvasDrawer ist eine Hilfsklasse, die die zum Zeichnen notwendigen Operationen auf dem GraphicsContext des Canvas ausführt.
 */
class PinballCanvasDrawer
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
     * Der Rand des Automaten.
     */
    private RectangleDouble boundingBox;

    /**
     * Erstellt einen neuen PinballCanvasDrawer.
     *
     * @param canvas      Die Canvas auf der gezeichnet wird.
     * @param drawMode    Der Modus mit dem gezeichnet werden soll.
     * @param sprites     Die zu zeichnenden Sprites.
     * @param boundingBox Der Rand des Automaten
     */
    PinballCanvasDrawer(Canvas canvas, DrawMode drawMode, ReadOnlyListProperty<SpriteSubView> sprites, RectangleDouble boundingBox)
    {
        this.canvas = canvas;
        this.drawMode = drawMode;
        this.boundingBox = boundingBox;
        this.sprites = new SimpleListProperty<>(sprites);
    }

    /**
     * Leert das Canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum Zeichnen übergeben wird.
     *
     * @param cameraPosition    Die Position der Kamera.
     * @param cameraZoom        Der Zoom der Kamera.
     * @param dragSelectionRect Das eventuell vorhandene Auswahlfenster welches es durch "ziehen" erlaubt Elemente auszuwählen.
     */
    void draw(Vector2 cameraPosition, double cameraZoom, Optional<RectangleDoubleByPoints> dragSelectionRect)
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(DesignConfig.PRIMARY_COLOR);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        Vector2 translate = new Vector2(canvas.getWidth(), canvas.getHeight()).scale(0.5).minus(cameraPosition.scale(PIXELS_PER_GRID_UNIT * cameraZoom));
        graphicsContext.translate(translate.getX(), translate.getY());
        graphicsContext.scale(cameraZoom, cameraZoom);

        if (drawMode == DrawMode.EDITOR)
        {
            drawEditorGrid(cameraPosition, cameraZoom);
        }

        drawElements(graphicsContext);

        dragSelectionRect.ifPresent(rectangleDoubleByPoints -> drawSelectionRect(rectangleDoubleByPoints, graphicsContext));

        if (drawMode == DrawMode.GAME)
        {
            drawBoundingBox(cameraPosition, cameraZoom, graphicsContext);
        }

        graphicsContext.restore();
    }

    /**
     * Zeichnet den Rahmen des Automaten.
     *
     * @param cameraPosition  Die Position der Kamera.
     * @param cameraZoom      Der Zoom der Kamera.
     * @param graphicsContext Der GraphicsContext, auf dem die Spielelemente gezeichnet werden sollen.
     */
    private void drawBoundingBox(Vector2 cameraPosition, double cameraZoom, GraphicsContext graphicsContext)
    {
        Vector2 canvasTopLeft = canvasPosToGridPos(cameraPosition, cameraZoom, 0, 0).scale(PIXELS_PER_GRID_UNIT);
        Vector2 canvasBottomRight = canvasPosToGridPos(cameraPosition, cameraZoom, canvas.getWidth(), canvas.getHeight()).scale(PIXELS_PER_GRID_UNIT);

        Vector2 ori = boundingBox.getOrigin().scale(PIXELS_PER_GRID_UNIT);
        Vector2 end = boundingBox.getSize().plus(boundingBox.getOrigin()).scale(PIXELS_PER_GRID_UNIT);

        graphicsContext.setFill(DesignConfig.PRIMARY_COLOR_LIGHT);
        graphicsContext.fillRect(canvasTopLeft.getX(), canvasTopLeft.getY(), canvasBottomRight.getX() - canvasTopLeft.getX(), ori.getY() - canvasTopLeft.getY());
        graphicsContext.fillRect(canvasTopLeft.getX(), canvasTopLeft.getY(), ori.getX() - canvasTopLeft.getX(), canvasBottomRight.getY() - canvasTopLeft.getY());
        graphicsContext.fillRect(canvasTopLeft.getX(), end.getY(), canvasBottomRight.getX() - canvasTopLeft.getX(), canvasBottomRight.getY() - end.getY());
        graphicsContext.fillRect(end.getX(), canvasTopLeft.getY(), canvasBottomRight.getX() - end.getX(), canvasBottomRight.getY() - canvasTopLeft.getY());

        double borderWidth = PIXELS_PER_GRID_UNIT * AUTOMATE_BORDER_WIDTH;
        graphicsContext.setLineWidth(borderWidth);

        graphicsContext.setStroke(DesignConfig.SECONDARY_COLOR);
        graphicsContext.strokeRect(ori.getX() - borderWidth / 2D, ori.getY() - borderWidth / 2D + PIXELS_PER_GRID_UNIT, end.getX() - ori.getX() + borderWidth, end.getY() - ori.getY() + borderWidth);

        graphicsContext.setStroke(DesignConfig.SECONDARY_COLOR_DARK);
        graphicsContext.strokeRect(ori.getX() - borderWidth / 2D, ori.getY() - borderWidth / 2D, end.getX() - ori.getX() + borderWidth, end.getY() - ori.getY() + borderWidth);
    }

    /**
     * Zeichnet die Markierung, die ein im Editor ausgewähltes Objekt umgibt.
     *
     * @param dragSelectionRect Bestimmt die Position und Größe der Markierung.
     * @param graphicsContext   Der GraphicsContext, auf dem gezeichnet werden soll.
     */
    private void drawSelectionRect(RectangleDoubleByPoints dragSelectionRect, GraphicsContext graphicsContext)
    {
        graphicsContext.setFill(DesignConfig.SECONDARY_COLOR);
        graphicsContext.setStroke(DesignConfig.SECONDARY_COLOR_DARK);
        graphicsContext.setLineWidth(0.25 * PIXELS_PER_GRID_UNIT);

        Vector2 ori = dragSelectionRect.getOrigin().scale(PIXELS_PER_GRID_UNIT);
        Vector2 end = dragSelectionRect.getSize().scale(PIXELS_PER_GRID_UNIT);

        graphicsContext.setGlobalAlpha(0.5);
        graphicsContext.fillRect(ori.getX(), ori.getY(), end.getX(), end.getY());

        graphicsContext.setGlobalAlpha(1);
        graphicsContext.strokeRect(ori.getX(), ori.getY(), end.getX(), end.getY());
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
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     */
    private void drawEditorGrid(Vector2 cameraPosition, double cameraZoom)
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.save();
        Vector2 gridStart = canvasPosToGridPos(cameraPosition, cameraZoom, 0, 0).scale(PIXELS_PER_GRID_UNIT);
        Vector2 gridEnd = canvasPosToGridPos(cameraPosition, cameraZoom, canvas.getWidth(), canvas.getHeight()).scale(PIXELS_PER_GRID_UNIT);

        for (int gridX = (int) gridStart.getX() - (int) gridStart.getX() % PIXELS_PER_GRID_UNIT; gridX <= gridEnd.getX(); gridX += PIXELS_PER_GRID_UNIT)
        {
            // Wähle die Linienfarbe und -stärke so, dass jede zweite Linie eine dünnere Linie ist.
            Color lineColor = Math.abs(gridX) % (PIXELS_PER_GRID_UNIT * 2) == 0 ? DesignConfig.PRIMARY_COLOR_LIGHT_LIGHT : DesignConfig.PRIMARY_COLOR_LIGHT;
            int lineWidth = Math.abs(gridX) % (PIXELS_PER_GRID_UNIT * 2) == 0 ? 2 : 1;

            graphicsContext.setStroke(lineColor);
            graphicsContext.setLineWidth(lineWidth);
            graphicsContext.strokeLine(gridX, gridStart.getY(), gridX, gridEnd.getY());
        }

        for (int gridY = (int) gridStart.getY() - (int) gridStart.getY() % PIXELS_PER_GRID_UNIT; gridY <= gridEnd.getY(); gridY += PIXELS_PER_GRID_UNIT)
        {
            // Wähle die Linienfarbe und -stärke so, dass jede zweite Linie eine dickere Linie ist.
            Color lineColor = Math.abs(gridY) % (PIXELS_PER_GRID_UNIT * 2) == PIXELS_PER_GRID_UNIT ? DesignConfig.PRIMARY_COLOR_LIGHT_LIGHT : DesignConfig.PRIMARY_COLOR_LIGHT;
            int lineWidth = Math.abs(gridY) % (PIXELS_PER_GRID_UNIT * 2) == PIXELS_PER_GRID_UNIT ? 2 : 1;

            graphicsContext.setStroke(lineColor);
            graphicsContext.setLineWidth(lineWidth);
            graphicsContext.strokeLine(gridStart.getX(), gridY, gridEnd.getX(), gridY);
        }

        graphicsContext.restore();
    }

    /**
     * Rechnet die durch die {@code x} und {@code y} gegebene Position auf dem Canvas auf die zugehörige Grid-Position um.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     * @param x              Der x-Wert der Position auf dem Canvas.
     * @param y              Der y-Wert der Position auf dem Canvas.
     * @return Die Position auf dem Grid.
     */
    Vector2 canvasPosToGridPos(Vector2 cameraPosition, double cameraZoom, double x, double y)
    {
        Vector2 posToMiddle = new Vector2(x, y).minus(new Vector2(canvas.getWidth(), canvas.getHeight()).scale(0.5));
        return posToMiddle.scale(1 / (PIXELS_PER_GRID_UNIT * cameraZoom)).plus(cameraPosition);
    }
}
