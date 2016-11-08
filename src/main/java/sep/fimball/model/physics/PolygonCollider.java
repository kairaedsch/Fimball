package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;
import sep.fimball.model.WorldLayer;

import java.util.List;

/**
 * Created by alexcekay on 03.11.16.
 */
public class PolygonCollider extends Collider
{
    private List<Vector2> vertices;

    public PolygonCollider(List<Vector2> vertices, WorldLayer layer, PhysicsForce force, Animation animation)
    {
        super(layer, force, animation);
        this.vertices = vertices;
    }

    public List<Vector2> getVertices()
    {
        return vertices;
    }
}
