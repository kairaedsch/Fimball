package sep.fimball.model;

import sep.fimball.general.data.Vector2;

import java.util.List;

/**
 * Created by alexcekay on 03.11.16.
 */
public class PolygonCollider extends Collider
{
    private List<Vector2> vertecies;

    public PolygonCollider(List<Vector2> vertecies, WorldLayer layer, PhysicsForce colliderType)
    {
        super(layer, colliderType);
        this.vertecies = vertecies;
    }

    public List<Vector2> getVertecies()
    {
        return vertecies;
    }
}
