package sep.fimball.general;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;

import java.util.List;

/**
 * Created by TheAsuro on 17.11.2016.
 */
public class DrawEntry
{
    public DrawEntry()
    {
    }

    public String type;
    public Vector2 position;
    public List<Vector2> positions;
    public double radius;
    public Vector2 direction;
    public Color color;
    public long creationTime;
}
