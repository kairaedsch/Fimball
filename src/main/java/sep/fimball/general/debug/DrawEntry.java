package sep.fimball.general.debug;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;

import java.util.List;

/**
 * Debug Klasse welche nur für das Debugging der Physik relevant ist und deshalb kein JavaDoc enthält.
 */
public class DrawEntry
{
    /**
     * Debug stuff.
     */
    public DrawEntry()
    {
    }

    /**
     * Debug stuff.
     */
    public String type;
    /**
     * Debug stuff.
     */
    public Vector2 position;
    /**
     * Debug stuff.
     */
    public List<Vector2> positions;
    /**
     * Debug stuff.
     */
    public double radius;
    /**
     * Debug stuff.
     */
    public Vector2 direction;
    /**
     * Debug stuff.
     */
    public Color color;
    /**
     * Debug stuff.
     */
    public long creationTime;
}
