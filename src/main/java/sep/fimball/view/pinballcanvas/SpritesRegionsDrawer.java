package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import sep.fimball.general.data.*;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.general.util.RegionHashConverter;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.util.*;

import static sep.fimball.general.data.Config.DRAW_ORDER_AMOUNT;

/**
 * Der SpritesRegionsDrawer zeichnet Sprites und verwendet dafür den RegionHashConverter um das zeichnen zu optimieren.
 */
class SpritesRegionsDrawer
{
    /**
     * Der zun diesem SpritesRegionsDrawer zugehörige PinballCanvasDrawer.
     */
    private PinballCanvasDrawer pinballCanvasDrawer;

    /**
     * Der Modus mit dem gezeichnet werden soll.
     */
    private final DrawMode drawMode;

    /**
     * Teilt das Spielfeld in sogenannte "pots" auf. Dabei besteht eine "region" aus "pots", welche Sprites speichert.
     * Diese Sprites liegen dabei sortiert nach ihrer DrawOrder vor. Jeder "pot" in einem "pots" steht demnach für alle Sprites die die selbe drawOrder haben.
     */
    private Map<Long, List<SpriteSubView>[]> globalRegion;

    /**
     * Erstellt einen neuen SpritesRegionsDrawer.
     *
     * @param pinballCanvasDrawer Der zun diesem SpritesRegionsDrawer zugehörige PinballCanvasDrawer.
     * @param drawMode            Der Modus mit dem gezeichnet werden soll.
     * @param sprites             Die zu zeichnenden Sprites.
     */
    SpritesRegionsDrawer(PinballCanvasDrawer pinballCanvasDrawer, DrawMode drawMode, ReadOnlyListProperty<SpriteSubView> sprites)
    {
        this.pinballCanvasDrawer = pinballCanvasDrawer;
        this.drawMode = drawMode;
        globalRegion = new HashMap<>();

        // Bindet die globalRegion an die Sprites
        ObservableList<SpriteSubView> listPropertyConverted = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(listPropertyConverted, sprites, sprite -> sprite, sprite ->
        {
            // Wird ausgeführt, wenn ein Element hinzugefügt werden soll
            addSpriteRegions(sprite, sprite.regionHashesProperty().get());
            sprite.regionHashesProperty().addListener((x, oldHashes, newHashes) -> updateSpritesRegions(sprite, oldHashes, newHashes));
            sprite.drawOrderProperty().addListener((x, xxx, xxxx) -> updateSpritesRegions(sprite, sprite.regionHashesProperty().get(), sprite.regionHashesProperty().get()));
        }, sprite ->
        {
            // Wird ausgeführt, wenn ein Element entfernt werden soll
            removeSpriteRegions(sprite, sprite.regionHashesProperty().get());
        });
    }

    /**
     * Aktualisiert die globalRegion mit dem übergebenen sprite und seinen Regions.
     *
     * @param sprite    Das zu aktualisierende sprite.
     * @param oldRegion Die alte region des sprites.
     * @param newRegion Die neue region der Sprite.
     */
    private void updateSpritesRegions(SpriteSubView sprite, List<Long> oldRegion, List<Long> newRegion)
    {
        removeSpriteRegions(sprite, oldRegion);
        addSpriteRegions(sprite, newRegion);
    }

    /**
     * Fügt das sprite zu der übergebenen region der globalRegion hinzu.
     *
     * @param sprite Das hinzuzufügende sprite.
     * @param region Die region, in welcher das sprite hinzugefügt werden soll.
     */
    private void addSpriteRegions(SpriteSubView sprite, List<Long> region)
    {
        for (Long potsHash : region)
        {
            List<SpriteSubView>[] pots = globalRegion.get(potsHash);
            if (pots == null)
            {
                pots = new List[DRAW_ORDER_AMOUNT];
                for (int d = 0; d < DRAW_ORDER_AMOUNT; d++)
                {
                    pots[d] = new ArrayList<>();
                }
                globalRegion.put(potsHash, pots);
            }
            pots[sprite.getDrawOrder()].add(sprite);
        }
    }

    /**
     * Entfernt das sprite aus der übergebenen region der globalRegion.
     *
     * @param sprite Das zu entfernende sprite.
     * @param region Die region, in welcher das sprite entfernt werden soll.
     */
    private void removeSpriteRegions(SpriteSubView sprite, List<Long> region)
    {
        for (Long pots : region)
        {
            for (int d = 0; d < DRAW_ORDER_AMOUNT; d++)
            {
                globalRegion.get(pots)[d].remove(sprite);
            }
        }
    }

    /**
     * Zeichnet jedes sichtbare sprite auf den übergebenen GraphicsContext.
     *
     * @param cameraPosition  Die Position der Kamera.
     * @param cameraZoom      Der Zoom der Kamera.
     * @param graphicsContext Der GraphicsContext, auf dem die Spielelemente gezeichnet werden sollen.
     */
    void drawElements(Vector2 cameraPosition, double cameraZoom, GraphicsContext graphicsContext)
    {
        // Berechnet die Position des Canvases auf dem Spielfeld
        Vector2 canvasTopLeft = pinballCanvasDrawer.getTopLeftCornerOfCanvas(cameraPosition, cameraZoom);
        Vector2 canvasBottomRight = pinballCanvasDrawer.getBottomRightCornerOfCanvas(cameraPosition, cameraZoom);

        // Berechne die Bereiche vom Canvas
        List<Long> regionOfCanvas = RegionHashConverter.gameAreaToRegionHashes(canvasTopLeft, canvasBottomRight, Config.DRAW_REGION_SIZE);
        RectangleDoubleByPoints canvasRectangle = new RectangleDoubleByPoints(canvasTopLeft, canvasBottomRight);

        // Male die Sprites
        drawElements(canvasRectangle, graphicsContext, regionOfCanvas, ImageLayer.BOTTOM);
        drawElements(canvasRectangle, graphicsContext, regionOfCanvas, ImageLayer.TOP);
    }

    /**
     * Zeichnet jedes sichtbare sprite auf den übergebenen GraphicsContext.
     *
     * @param canvasRectangle Der Bereich des Canvases.
     * @param graphicsContext Der GraphicsContext, auf dem die Spielelemente gezeichnet werden sollen.
     * @param regionOfCanvas  Die region des Canvases.
     * @param imageLayer      Gibt an, ob die Sprites ihre Top- oder Bottom-Images zeichnen sollen.
     */
    private void drawElements(RectangleDoubleByPoints canvasRectangle, GraphicsContext graphicsContext, List<Long> regionOfCanvas, ImageLayer imageLayer)
    {
        // Male mit der drawOrder von unten nach oben
        for (int d = 0; d < DRAW_ORDER_AMOUNT; d++)
        {
            // Gehe durch jeden pots des Canvases
            for (Long potsHash : regionOfCanvas)
            {
                // Zeichne die Sprites im pot der richtigen drawOrder
                List<SpriteSubView>[] pots = globalRegion.get(potsHash);
                if (pots != null)
                {
                    pots[d].forEach(sprite -> sprite.draw(canvasRectangle, graphicsContext, imageLayer, drawMode));
                }
            }
        }
    }
}
