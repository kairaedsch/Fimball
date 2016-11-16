package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementierung eines Colliders, welcher die Form eines Polygons hat. Um Kollisionen korrekt erkennen zu können muss das Polygon konvex sein.
 */
public class PolygonColliderShape implements ColliderShape
{
    /**
     * Die Kanten des Polygons, die den Collider formen.
     */
    private List<Vector2> vertices;

    /**
     * Erstellt einen neuen Collider in Polygonform.
     * @param vertices
     */
    public PolygonColliderShape(List<Vector2> vertices)
    {
        this.vertices = vertices;
    }

    public List<Vector2> getVertices()
    {
        return vertices;
    }

    @Override
    public HitInfo calculateHitInfo(CircleColliderShape ball)
    {
        for (Vector2 axisVertex : vertices)
        {
            Vector2 axis = Vector2.sub(ball.getPosition(), axisVertex).normalized();

            List<Double> points = new ArrayList<>();
            for (Vector2 vertex : vertices)
            {
                points.add(Vector2.dot(vertex, axis));
            }
            points.sort(Comparator.naturalOrder());

            double ballCenter = Vector2.dot(ball.getPosition(), axis);
            double ballMin = ballCenter - ball.getRadius();
            double ballMax = ballCenter + ball.getRadius();

            double polyMin = points.get(0);
            double polyMax = points.get(points.size() - 1);

            // Do the projected areas intersect?
            if (ballMax > polyMin && ballMin < polyMax || polyMax > ballMin && polyMin < ballMax)
            {
                double overlapDistance = Math.min(ballMax, polyMax) - Math.max(ballMin, polyMin);
                return new HitInfo(true, Vector2.scale(axis, overlapDistance));
            }
        }

        return new HitInfo(false, null);
    }
}
