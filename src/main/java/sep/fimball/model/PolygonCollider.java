package sep.fimball.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexcekay on 03.11.16.
 */
public class PolygonCollider extends Collider
{
    private List<Vector2> vertecies;

    public PolygonCollider(List<Vector2> vertecies, WorldLayer layer, ColliderType colliderType)
    {
        super(layer, colliderType);
        this.vertecies = vertecies;
    }

    public List<Vector2> getVertecies()
    {
        return vertecies;
    }
}