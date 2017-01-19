package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.*;
import sep.fimball.general.util.RegionHashConverter;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.util.*;

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
     * Der Rand des Automaten.
     */
    private RectangleDouble boundingBox;

    /**
     * TODO Kai
     */
    private Map<Long, Map<Integer, List<SpriteSubView>>> spritesRegions;

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

        spritesRegions = new HashMap<>();

        List<SpriteSubView> listPropertyConverted = new ArrayList<>();
        ListChangeListener<SpriteSubView> listChangeListener = (change) ->
        {
            if (change == null || sprites.isEmpty())
            {
                if (change == null || sprites.isEmpty())
                {
                    listPropertyConverted.clear();
                    for (SpriteSubView original : sprites)
                    {
                        listPropertyConverted.add(original);
                    }
                    spritesRegions.clear();
                    for (SpriteSubView sprite : sprites)
                    {
                        sprite.regionHashesProperty().addListener((x, oldHashes, newHashes) -> updateSpritesRegions(sprite, oldHashes, newHashes));
                        addSpriteRegions(sprite, sprite.regionHashesProperty().get());
                    }
                }
                else
                {
                    while (change.next())
                    {
                        if (change.wasRemoved())
                        {
                            if (change.getFrom() == change.getTo())
                            {
                                SpriteSubView sprite = listPropertyConverted.get(change.getFrom());
                                removeSpriteRegions(sprite, sprite.regionHashesProperty().get());

                                listPropertyConverted.remove(change.getFrom());
                            }
                            else
                            {
                                for (int p = change.getFrom(); p < change.getTo(); p++)
                                {
                                    SpriteSubView sprite = listPropertyConverted.get(change.getFrom());
                                    removeSpriteRegions(sprite, sprite.regionHashesProperty().get());

                                    listPropertyConverted.remove(change.getFrom());
                                }
                            }
                        }
                        if (change.wasAdded())
                        {
                            for (int p = change.getFrom(); p < change.getTo(); p++)
                            {
                                SpriteSubView sprite = sprites.get(p);
                                sprite.regionHashesProperty().addListener((x, oldHashes, newHashes) -> updateSpritesRegions(sprite, oldHashes, newHashes));
                                addSpriteRegions(sprite, sprite.regionHashesProperty().get());

                                listPropertyConverted.add(p, sprite);
                            }
                        }
                        if (change.wasPermutated())
                        {
                            HashMap<Integer, SpriteSubView> map = new HashMap<>();
                            for (int p = change.getFrom(); p < change.getTo(); p++)
                            {
                                int newPos = change.getPermutation(p);
                                if (p != newPos)
                                {
                                    map.put(newPos, listPropertyConverted.get(newPos));
                                    if (map.containsKey(p))
                                        listPropertyConverted.set(newPos, map.get(p));
                                    else
                                        listPropertyConverted.set(newPos, listPropertyConverted.get(p));
                                }
                            }
                        }
                    }
                }
            }
        };
        sprites.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * @param sprite
     * @param oldRegions
     * @param newRegions
     */
    private void updateSpritesRegions(SpriteSubView sprite, List<Long> oldRegions, List<Long> newRegions)
    {
        removeSpriteRegions(sprite, oldRegions);
        addSpriteRegions(sprite, newRegions);
    }

    private void addSpriteRegions(SpriteSubView sprite, List<Long> newRegions)
    {
        for (Long pots : newRegions)
        {
            if (!spritesRegions.containsKey(pots))
            {
                spritesRegions.put(pots, new HashMap<>());
            }
            if (!spritesRegions.get(pots).containsKey(sprite.getDrawOrder()))
            {
                spritesRegions.get(pots).put(sprite.getDrawOrder(), new ArrayList<>());
            }
            spritesRegions.get(pots).get(sprite.getDrawOrder()).add(sprite);
        }
    }

    private void removeSpriteRegions(SpriteSubView sprite, List<Long> oldRegions)
    {
        for (Long pots : oldRegions)
        {
            spritesRegions.get(pots).get(sprite.getDrawOrder()).remove(sprite);
        }
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

        drawElements(cameraPosition, cameraZoom, graphicsContext);

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
        Vector2 canvasTopLeft = getTopLeftCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);
        Vector2 canvasBottomRight = getBottomRightCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);

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
    private void drawElements(Vector2 cameraPosition, double cameraZoom, GraphicsContext graphicsContext)
    {
        Vector2 canvasTopLeft = getTopLeftCornerOfCanvas(cameraPosition, cameraZoom);
        Vector2 canvasBottomRight = getBottomRightCornerOfCanvas(cameraPosition, cameraZoom);

        List<Long> spritesRegionsCanvas = RegionHashConverter.gameAreaToRegionHashes(canvasTopLeft, canvasBottomRight, Config.DRAW_REGION_SIZE);
        RectangleDoubleByPoints canvasRectangle = new RectangleDoubleByPoints(canvasTopLeft, canvasBottomRight);

        // TODO make better
        int start = -10;
        int end = 10;

        drawElements(canvasRectangle, graphicsContext, spritesRegionsCanvas, start, end, ImageLayer.BOTTOM);
        drawElements(canvasRectangle, graphicsContext, spritesRegionsCanvas, start, end, ImageLayer.TOP);
    }

    private void drawElements(RectangleDoubleByPoints canvasRectangle, GraphicsContext graphicsContext, List<Long> spritesRegionsCanvas, int start, int end, ImageLayer imageLayer)
    {
        for (Long newSpritesRegionsCanva : spritesRegionsCanvas)
        {
            for (int s = start; s <= end; s++)
            {
                Map<Integer, List<SpriteSubView>> spritePots = spritesRegions.get(newSpritesRegionsCanva);
                if (spritePots != null)
                {
                    List<SpriteSubView> spritePot = spritePots.get(s);
                    if (spritePot != null)
                    {
                        spritePot.forEach(sprite ->
                        {
                            sprite.draw(canvasRectangle, graphicsContext, imageLayer, drawMode);
                        });
                    }
                }
            }
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
        Vector2 gridStart = getTopLeftCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);
        Vector2 gridEnd = getBottomRightCornerOfCanvas(cameraPosition, cameraZoom).scale(PIXELS_PER_GRID_UNIT);

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

    private Vector2 getTopLeftCornerOfCanvas(Vector2 cameraPosition, double cameraZoom)
    {
        return canvasPosToGridPos(cameraPosition, cameraZoom, 0, 0);
    }

    private Vector2 getBottomRightCornerOfCanvas(Vector2 cameraPosition, double cameraZoom)
    {
        return canvasPosToGridPos(cameraPosition, cameraZoom, canvas.getWidth(), canvas.getHeight());
    }
}
