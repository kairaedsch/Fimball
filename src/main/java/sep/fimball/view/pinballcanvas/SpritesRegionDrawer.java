package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
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
class SpritesRegionDrawer
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
    private Map<Long, List<SpriteRegionSubView>[]> globalRegion;

    /**
     * Erstellt einen neuen SpritesRegionsDrawer.
     *
     * @param pinballCanvasDrawer Der zun diesem SpritesRegionsDrawer zugehörige PinballCanvasDrawer.
     * @param drawMode            Der Modus mit dem gezeichnet werden soll.
     * @param sprites             Die zu zeichnenden Sprites.
     */
    SpritesRegionDrawer(PinballCanvasDrawer pinballCanvasDrawer, DrawMode drawMode, ReadOnlyListProperty<SpriteSubView> sprites)
    {
        this.pinballCanvasDrawer = pinballCanvasDrawer;
        this.drawMode = drawMode;
        globalRegion = new HashMap<>();

        // Bindet die globalRegion an die Sprites
        ObservableList<SpriteRegionSubView> listPropertyConverted = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.<SpriteRegionSubView, SpriteSubView>bindAndConvertList(listPropertyConverted, sprites, SpriteRegionSubView::new, sprite ->
                {
                    // Wird ausgeführt, wenn ein Element hinzugefügt werden soll
                    addSpriteRegions(sprite, sprite.getRegionHashes());
                    ChangeListener<List<Long>> regionHashListener = (x, oldRegion, newRegion) -> updateSpritesRegions(sprite, oldRegion, newRegion, sprite.getDrawOrder());
                    ChangeListener<Number> drawOrderlistener = (x, oldDrawOrder, xx) -> updateSpritesRegions(sprite, sprite.getRegionHashes(), sprite.getRegionHashes(), oldDrawOrder.intValue());
                    sprite.setDrawListener(regionHashListener, drawOrderlistener);
                }, sprite ->
                {
                    // Wird ausgeführt, wenn ein Element entfernt werden soll
                    removeSpriteRegions(sprite, sprite.getRegionHashes(), sprite.getDrawOrder());
                    sprite.clearDrawListener();
                },
                // Wird ausgeführt, wenn alle Elemente entfernt werden sollen
                () ->
                {
                    listPropertyConverted.forEach(SpriteRegionSubView::clearDrawListener);
                    clearSpriteRegions();
                }, Optional.empty());
    }

    /**
     * Aktualisiert die globalRegion mit dem übergebenen sSprite und seinen Regions.
     *
     * @param sprite       Das zu aktualisierende Sprite.
     * @param oldRegion    Die alte region des Sprites.
     * @param newRegion    Die neue region der Sprite.
     * @param oldDrawOrder Die alte Zeichenreihenfolge es Sprites.
     */
    private void updateSpritesRegions(SpriteRegionSubView sprite, List<Long> oldRegion, List<Long> newRegion, int oldDrawOrder)
    {
        removeSpriteRegions(sprite, oldRegion, oldDrawOrder);
        addSpriteRegions(sprite, newRegion);
    }

    /**
     * Fügt das sprite zu der übergebenen region der globalRegion hinzu.
     *
     * @param sprite Das hinzuzufügende Sprite.
     * @param region Die region, in welcher das sprite hinzugefügt werden soll.
     */
    private void addSpriteRegions(SpriteRegionSubView sprite, List<Long> region)
    {
        for (Long potsHash : region)
        {
            List<SpriteRegionSubView>[] pots = globalRegion.get(potsHash);
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
     * @param sprite    Das zu entfernende sprite.
     * @param region    Die region, in welcher das Sprite entfernt werden soll.
     * @param drawOrder Die vielleicht alte Zeichenreihenfolge des Sprites.
     */
    private void removeSpriteRegions(SpriteRegionSubView sprite, List<Long> region, int drawOrder)
    {
        for (Long potsHash : region)
        {
            boolean success = false;
            List<SpriteRegionSubView>[] lists = globalRegion.get(potsHash);
            if(lists != null)
            {
                success = lists[drawOrder].remove(sprite);
            }
            if (!success)
            {
                System.out.println("NOT REMOVED sprite: " + sprite + " potsHash: " + potsHash + " drawOrder: " + drawOrder + " T: " + Thread.currentThread().getId() + " P: " + this);
                System.err.println("Warning in RegionDrawer: could not remove");
                for (Long aLong : region)
                {
                    System.err.println("sprite: " + sprite + " potsHash: " + aLong + " drawOrder: " + drawOrder);
                }
            }
        }
    }

    /**
     * Setzt die globalRegion zurück.
     */
    private void clearSpriteRegions()
    {
        globalRegion.clear();
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
                List<SpriteRegionSubView>[] pots = globalRegion.get(potsHash);
                if (pots != null)
                {
                    pots[d].forEach(sprite -> sprite.draw(canvasRectangle, graphicsContext, imageLayer, drawMode));
                }
            }
        }
    }
}
