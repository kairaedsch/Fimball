package sep.fimball.view.pinballcanvas;

import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.GraphicsContext;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import java.util.List;
import java.util.Optional;

/**
 * Umschließt eine SpriteSubView und speichert dazu die Listener für den SpriteRegionDrawer.
 */
public class SpriteRegionSubView
{
    /**
     * Die umschlossene SpriteSubView.
     */
    private SpriteSubView spriteSubView;

    /**
     * Der Listener für die regionHashes.
     */
    private Optional<ChangeListener<List<Long>>> regionHashListener;

    /**
     * Der Listener für die drawOrder.
     */
    private Optional<ChangeListener<Number>> drawOrderlistener;

    /**
     * Erstellt eine neue SpriteRegionSubView.
     *
     * @param spriteSubView Die umschlossene SpriteSubView.
     */
    public SpriteRegionSubView(SpriteSubView spriteSubView)
    {
        this.spriteSubView = spriteSubView;

        this.regionHashListener = Optional.empty();
        this.drawOrderlistener = Optional.empty();
    }


    /**
     * Setzt die Listener für den regionHash und die drawOrder.
     *
     * @param regionHashListener Den regionHash Listener.
     * @param drawOrderlistener  Den drawOrder Listener.
     */
    public void setDrawListener(ChangeListener<List<Long>> regionHashListener, ChangeListener<Number> drawOrderlistener)
    {
        boolean cleared = clearDrawListener();
        if (cleared) System.err.println("Warning: cleared changelistener on set: " + this);
        this.regionHashListener = Optional.of(regionHashListener);
        this.drawOrderlistener = Optional.of(drawOrderlistener);
        spriteSubView.regionHashesProperty().addListener(regionHashListener);
        spriteSubView.drawOrderProperty().addListener(drawOrderlistener);
    }

    /**
     * Setzt die drawListener zurück.
     *
     * @return Ob Listener entfernt wurden.
     */
    public boolean clearDrawListener()
    {
        if (regionHashListener.isPresent() || drawOrderlistener.isPresent())
        {
            regionHashListener.ifPresent((l) -> spriteSubView.regionHashesProperty().removeListener(l));
            drawOrderlistener.ifPresent((l) -> spriteSubView.drawOrderProperty().removeListener(l));
            this.regionHashListener = Optional.empty();
            this.drawOrderlistener = Optional.empty();
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Gibt die Reihenfolge beim Zeichnen des umschlossenen SpriteSubView zurück.
     *
     * @return Die Reihenfolge beim Zeichnen.
     */
    public Integer getDrawOrder()
    {
        return spriteSubView.drawOrderProperty().get();
    }

    /**
     * Gitb die Hashes der Regionen, in welchen sich das umschlossenen SpriteSubView befindet, zurück.
     *
     * @return Die Hashes der Regionen, in welchen sich das umschlossenen SpriteSubView befindet.
     */
    public List<Long> getRegionHashes()
    {
        return spriteSubView.regionHashesProperty().get();
    }

    /**
     * Zeichnet das umschlossenen SpriteSubView auf das übergebene GraphicsContext-Objekt.
     *
     * @param canvasRectangle Bereich des Canvas.
     * @param graphicsContext Der GraphicsContext, auf dem das umschlossenen SpriteSubView sich zeichnen soll.
     * @param imageLayer      Gibt an, ob das umschlossenen SpriteSubView sein Top- oder Bottom-Image zeichnen soll.
     * @param drawMode        Der Modus in dem gezeichnet werden soll.
     */
    public void draw(RectangleDoubleByPoints canvasRectangle, GraphicsContext graphicsContext, ImageLayer imageLayer, DrawMode drawMode)
    {
        spriteSubView.draw(canvasRectangle, graphicsContext, imageLayer, drawMode);
    }
}
