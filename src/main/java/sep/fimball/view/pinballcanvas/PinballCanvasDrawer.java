package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sep.fimball.general.data.*;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.awt.*;
import java.util.Optional;

import static sep.fimball.general.data.DesignConfig.*;
import static sep.fimball.view.general.ViewUtil.canvasPixelToGridPos;
import static sep.fimball.viewmodel.pinballcanvas.DrawMode.EDITOR;
import static sep.fimball.viewmodel.pinballcanvas.DrawMode.GAME;

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
     * Der Rand des Automaten.
     */
    private RectangleDouble boundingBox;

    /**
     * Der für das Zeichnen der Sprites zuständige SpritesRegionsDrawer.
     */
    private SpritesRegionDrawer spritesRegionDrawer;

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

        spritesRegionDrawer = new SpritesRegionDrawer(this, drawMode, sprites);
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

        graphicsContext.setFill(drawMode == GAME ? PRIMARY_COLOR_LIGHT : PRIMARY_COLOR);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.save();
        Vector2 translate = new Vector2(canvas.getWidth(), canvas.getHeight()).scale(0.5).minus(cameraPosition.scale(PIXELS_PER_GRID_UNIT * cameraZoom));
        graphicsContext.translate(translate.getX(), translate.getY());
        graphicsContext.scale(cameraZoom, cameraZoom);

        if (drawMode == EDITOR)
        {
            drawEditorGrid(cameraPosition, cameraZoom);
        }

        if (drawMode == GAME)
        {
            drawBoundingBox(graphicsContext, ImageLayer.BOTTOM);
        }

        spritesRegionDrawer.drawElements(cameraPosition, cameraZoom, graphicsContext);

        if (drawMode == GAME)
        {
            drawBoundingBox(graphicsContext, ImageLayer.TOP);
        }

        dragSelectionRect.ifPresent(rectangleDoubleByPoints -> drawSelectionRect(rectangleDoubleByPoints, graphicsContext));

        graphicsContext.restore();
    }

    /**
     * Zeichnet den Rahmen des Automaten.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die Grafik gezeichnet werden sollen.
     * @param imageLayer Gibt an, ob der Obere oder untere Teil gezeichnet werden soll.
     */
    private void drawBoundingBox(GraphicsContext graphicsContext, ImageLayer imageLayer)
    {
        Vector2 ori = boundingBox.getOrigin().scale(PIXELS_PER_GRID_UNIT);
        Vector2 end = boundingBox.getOrigin().plus(boundingBox.getSize()).scale(PIXELS_PER_GRID_UNIT);

        double borderWidth = PIXELS_PER_GRID_UNIT * 2;
        double borderHeight = PIXELS_PER_GRID_UNIT * 25;
        double borderDepth = PIXELS_PER_GRID_UNIT * (Config.MACHINE_BOX_MARGIN.getY() - 1);
        double footHeight = PIXELS_PER_GRID_UNIT * 50 + borderHeight;
        double headHeight = PIXELS_PER_GRID_UNIT * 50 + borderHeight;
        double headWidth = PIXELS_PER_GRID_UNIT * 20;
        double headInset = PIXELS_PER_GRID_UNIT * 5;
        double textInset = PIXELS_PER_GRID_UNIT * 5;
        double textHeight = PIXELS_PER_GRID_UNIT * 17;
        double textWidth = PIXELS_PER_GRID_UNIT * 92;

        if (imageLayer == ImageLayer.BOTTOM)
        {
            // Spiel Hintergrund
            graphicsContext.setFill(PRIMARY_COLOR);
            graphicsContext.fillRect(ori.getX(), ori.getY(), end.getX() - ori.getX(), end.getY() - ori.getY());

            // Rahmen 3D Effekt oben, muss unter dem Ball gezeichnet werden.
            graphicsContext.setLineWidth(borderWidth);
            graphicsContext.setStroke(SECONDARY_COLOR_DARK);
            graphicsContext.setFill(SECONDARY_COLOR_DARK);
            graphicsContext.fillRect(ori.getX() - borderWidth / 2D, ori.getY() - borderWidth / 2D, end.getX() - ori.getX() + borderWidth, borderDepth);
        }

        if (imageLayer == ImageLayer.TOP)
        {
            // Kopf
            graphicsContext.setLineWidth(borderWidth);
            // Kopf oben
            graphicsContext.setFill(SECONDARY_COLOR);
            graphicsContext.fillRect(ori.getX() - borderWidth, ori.getY() - headHeight - headWidth, end.getX() - ori.getX() + borderWidth * 2, headWidth);
            // Kopf unten
            graphicsContext.setFill(SECONDARY_COLOR_DARK);
            graphicsContext.fillRect(ori.getX() - borderWidth, ori.getY() - headHeight, end.getX() - ori.getX() + borderWidth * 2, headHeight);
            // Kopf innen
            graphicsContext.setFill(PRIMARY_COLOR);
            graphicsContext.fillRect(ori.getX() - borderWidth + headInset, ori.getY() - headHeight + headInset, end.getX() - (ori.getX() - borderWidth * 2) - headInset * 2, headHeight - headInset * 2 - borderWidth);
            // Text
            graphicsContext.setFill(PRIMARY_COLOR_LIGHT_LIGHT);
            graphicsContext.setFont(Font.font("Arial", textHeight * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0));
            Vector2 textPosLeft = new Vector2(ori.getX() - borderWidth + headInset + textInset, ori.getY() - headHeight + headInset + textInset + textHeight);
            Vector2 textPosMiddle = textPosLeft.plus(new Vector2(end.minus(ori).scale(0.5D).getX(), 0)).minus(new Vector2(textWidth / 2D, 0));
            graphicsContext.fillText("FimBall", textPosMiddle.getX(), textPosMiddle.getY());
            // Kopf innen unten
            // graphicsContext.setFill(PRIMARY_COLOR_DARK);
            // Vector2 afterText = new Vector2(ori.getX() - borderWidth + headInset, ori.getY() - headHeight + headInset + textInset + textHeight + textInset);
            // graphicsContext.fillRect(afterText.getX(), afterText.getY(), (end.getX() + borderWidth - headInset) - afterText.getX(), (ori.getY() - borderWidth) - afterText.getY() - headInset);
            // Kopf innen mitte
            // graphicsContext.setFill(SECONDARY_COLOR_DARK);
            // graphicsContext.fillRect(afterText.getX(), afterText.getY(), (end.getX() + borderWidth - headInset) - afterText.getX(), headMiddleHeight);

            // Schatten
            graphicsContext.setFill(PRIMARY_COLOR);
            graphicsContext.setGlobalAlpha(0.5);
            graphicsContext.fillRect(ori.getX(), end.getY() + borderWidth, end.getX() - ori.getX(), footHeight + borderWidth);
            graphicsContext.setGlobalAlpha(1);

            // Beine
            graphicsContext.setLineWidth(borderWidth * 2);
            graphicsContext.setStroke(SECONDARY_COLOR_DARK);
            graphicsContext.strokeRect(ori.getX(), end.getY() + borderWidth, 0, footHeight);
            graphicsContext.strokeRect(end.getX(), end.getY() + borderWidth, 0, footHeight);

            // Rahmen 3D Effekt unten
            graphicsContext.setLineWidth(borderWidth);
            graphicsContext.setFill(SECONDARY_COLOR_DARK);
            graphicsContext.fillRect(ori.getX() - borderWidth, end.getY(), end.getX() - ori.getX() + borderWidth * 2D, borderHeight);

            // Rahmen um den gesamten Automaten
            graphicsContext.setStroke(SECONDARY_COLOR);
            graphicsContext.strokeRect(ori.getX() - borderWidth / 2D, ori.getY() - borderWidth / 2D, end.getX() - ori.getX() + borderWidth, end.getY() - ori.getY() + borderWidth);
        }
    }

    /**
     * Zeichnet die Markierung, die ein im Editor ausgewähltes Objekt umgibt.
     *
     * @param dragSelectionRect Bestimmt die Position und Größe der Markierung.
     * @param graphicsContext   Der GraphicsContext, auf dem gezeichnet werden soll.
     */
    private void drawSelectionRect(RectangleDoubleByPoints dragSelectionRect, GraphicsContext graphicsContext)
    {
        graphicsContext.setFill(SECONDARY_COLOR);
        graphicsContext.setStroke(SECONDARY_COLOR_DARK);
        graphicsContext.setLineWidth(0.25 * PIXELS_PER_GRID_UNIT);

        Vector2 ori = dragSelectionRect.getOrigin().scale(PIXELS_PER_GRID_UNIT);
        Vector2 end = dragSelectionRect.getSize().scale(PIXELS_PER_GRID_UNIT);

        graphicsContext.setGlobalAlpha(0.5);
        graphicsContext.fillRect(ori.getX(), ori.getY(), end.getX(), end.getY());

        graphicsContext.setGlobalAlpha(1);
        graphicsContext.strokeRect(ori.getX(), ori.getY(), end.getX(), end.getY());
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
        Vector2 gridStart = getTopLeftCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);
        Vector2 gridEnd = getBottomRightCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);

        for (int gridX = (int) gridStart.getX() - (int) gridStart.getX() % PIXELS_PER_GRID_UNIT; gridX <= gridEnd.getX(); gridX += PIXELS_PER_GRID_UNIT)
        {
            // Wähle die Linienfarbe und -stärke so, dass jede zweite Linie eine dünnere Linie ist.
            Color lineColor = Math.abs(gridX) % (PIXELS_PER_GRID_UNIT * 2) == 0 ? PRIMARY_COLOR_LIGHT_LIGHT : PRIMARY_COLOR_LIGHT;
            int lineWidth = Math.abs(gridX) % (PIXELS_PER_GRID_UNIT * 2) == 0 ? 2 : 1;

            graphicsContext.setStroke(lineColor);
            graphicsContext.setLineWidth(lineWidth);
            graphicsContext.strokeLine(gridX, gridStart.getY(), gridX, gridEnd.getY());
        }

        for (int gridY = (int) gridStart.getY() - (int) gridStart.getY() % PIXELS_PER_GRID_UNIT; gridY <= gridEnd.getY(); gridY += PIXELS_PER_GRID_UNIT)
        {
            // Wähle die Linienfarbe und -stärke so, dass jede zweite Linie eine dickere Linie ist.
            Color lineColor = Math.abs(gridY) % (PIXELS_PER_GRID_UNIT * 2) == PIXELS_PER_GRID_UNIT ? PRIMARY_COLOR_LIGHT_LIGHT : PRIMARY_COLOR_LIGHT;
            int lineWidth = Math.abs(gridY) % (PIXELS_PER_GRID_UNIT * 2) == PIXELS_PER_GRID_UNIT ? 2 : 1;

            graphicsContext.setStroke(lineColor);
            graphicsContext.setLineWidth(lineWidth);
            graphicsContext.strokeLine(gridStart.getX(), gridY, gridEnd.getX(), gridY);
        }

        graphicsContext.restore();
    }

    /**
     * Berechnet die Position der linken oberen Ecke des Canvas in Grideinheiten aus.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     * @return Die Position der linken oberen Ecke des Canvas in Grideinheiten.
     */
    Vector2 getTopLeftCornerOfCanvas(Vector2 cameraPosition, double cameraZoom)
    {
        return canvasPixelToGridPos(cameraPosition, cameraZoom, new Vector2(0, 0), new Vector2(canvas.getWidth(), canvas.getHeight()));
    }

    /**
     * Berechnet die Position der rechte untere Ecke des Canvas in Grideinheiten aus.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     * @return Die Position der linken oberen Ecke des Canvas in Grideinheiten.
     */
    Vector2 getBottomRightCornerOfCanvas(Vector2 cameraPosition, double cameraZoom)
    {
        Vector2 canvasSize = new Vector2(canvas.getWidth(), canvas.getHeight());
        return canvasPixelToGridPos(cameraPosition, cameraZoom, canvasSize, canvasSize);
    }
}
