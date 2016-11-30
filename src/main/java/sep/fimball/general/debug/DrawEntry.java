package sep.fimball.general.debug;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;

import java.util.List;

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
