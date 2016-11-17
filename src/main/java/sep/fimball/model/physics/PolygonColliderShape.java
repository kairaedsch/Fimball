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

    /**
     * Gibt die Kanten des Polygons, die den Collider formen, zurück.
     * @return Die Kanten des Polygons, die den Collider formen.
     */
    public List<Vector2> getVertices()
    {
        return vertices;
    }

    @Override
    public HitInfo calculateHitInfo(BallElement ball, Vector2 colliderObjectPosition)
    {
        Vector2 globalBallPosition = Vector2.add(ball.getPosition(), ball.getCollider().getPosition());
        List<Vector2> detectedOverlaps = new ArrayList<>();

        for (Vector2 axisVertex : vertices)
        {
            Vector2 globalAxis = Vector2.add(axisVertex, colliderObjectPosition);
            Vector2 axis = Vector2.sub(globalBallPosition, globalAxis).normalized();

            List<Double> points = new ArrayList<>();
            for (Vector2 vertex : vertices)
            {
                Vector2 globalVertex = Vector2.add(vertex, colliderObjectPosition);
                points.add(Vector2.dot(globalVertex, axis));
            }
            points.sort(Comparator.naturalOrder());

            double ballCenter = Vector2.dot(globalBallPosition, axis);
            double ballMin = ballCenter - ball.getCollider().getRadius();
            double ballMax = ballCenter + ball.getCollider().getRadius();

            double polyMin = points.get(0);
            double polyMax = points.get(points.size() - 1);

            // Do the projected areas intersect?
            if (ballMax > polyMin && ballMin < polyMax || polyMax > ballMin && polyMin < ballMax)
            {
                double overlapDistance = Math.min(ballMax, polyMax) - Math.max(ballMin, polyMin);
                detectedOverlaps.add(Vector2.scale(axis, overlapDistance));
            }
            else
            {
                return new HitInfo(false, null);
            }
        }
        detectedOverlaps.sort(((o1, o2) -> o1.magnitude() <= o2.magnitude() ? -1 : 1));

        return new HitInfo(true, detectedOverlaps.get(0));
    }
}
