package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;

import java.util.List;

/**
 * Implementierung eines Colliders, welcher die Form eines Polygons hat. Um Kollisionen korrekt erkennen zu können muss das Polygon konvex sein.
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
        super(layer, force);
        this.vertices = vertices;
    }

    public List<Vector2> getVertices()
    {
        return vertices;
    }
}
