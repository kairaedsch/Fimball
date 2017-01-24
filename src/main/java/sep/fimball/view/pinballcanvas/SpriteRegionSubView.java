package sep.fimball.view.pinballcanvas;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.GraphicsContext;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

import javax.naming.directory.Attribute;
import java.util.List;
import java.util.Optional;

public class SpriteRegionSubView
{
    private SpriteSubView spriteSubView;

    /**
     * Der Listener für die regionHashes.
     */
    private Optional<ChangeListener<List<Long>>> regionHashListener;

    /**
     * Der Listener für die drawOrder.
     */
    private Optional<ChangeListener<Number>> drawOrderlistener;

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

    public Integer getDrawOrder()
    {
        return spriteSubView.drawOrderProperty().get();
    }

    public List<Long> getRegionHashes()
    {
        return spriteSubView.regionHashesProperty().get();
    }

    public void draw(RectangleDoubleByPoints canvasRectangle, GraphicsContext graphicsContext, ImageLayer imageLayer, DrawMode drawMode)
    {
        spriteSubView.draw(canvasRectangle, graphicsContext, imageLayer, drawMode);
    }
}
