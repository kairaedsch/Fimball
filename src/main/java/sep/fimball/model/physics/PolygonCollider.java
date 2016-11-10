package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;

import java.util.List;

/**
 * Implementierung eines Colliders, welcher die Form eines Polygons hat.
 */
public class PolygonCollider extends Collider
{
    /**
     * Die Kanten des Polygons, die den Collider formen.
     */
    private List<Vector2> vertices;

    /**
     * Erstellt einen neuen Collider in Polygonform.
     * @param vertices
     * @param layer
     * @param force
     * @param animation
     */
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
