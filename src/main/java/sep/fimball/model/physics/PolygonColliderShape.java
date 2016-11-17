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
        List<Vector2> ballAxisList = new ArrayList<>();
        Vector2 ballAxis;

        for (Vector2 vertex : vertices)
        {
            Vector2 axis = Vector2.add(vertex, colliderObjectPosition);
            ballAxisList.add(Vector2.sub(globalBallPosition, axis));
        }
        ballAxisList.sort(((o1, o2) -> o1.magnitude() <= o2.magnitude() ? -1 : 1));
        ballAxis = ballAxisList.get(0).normalized();

        List<Double> points = new ArrayList<>();

        for (Vector2 vertex : vertices)
        {
            Vector2 globalVertex = Vector2.add(vertex, colliderObjectPosition);
            points.add(Vector2.dot(globalVertex, ballAxis));
        }
        points.sort(Comparator.naturalOrder());

        double ballCenter = Vector2.dot(globalBallPosition, ballAxis);
        double ballMin = ballCenter - ball.getCollider().getRadius();
        double ballMax = ballCenter + ball.getCollider().getRadius();

        double polyMin = points.get(0);
        double polyMax = points.get(points.size() - 1);

        // Do the projected areas intersect?
        if (ballMax > polyMin && ballMin < polyMax || polyMax > ballMin && polyMin < ballMax)
        {
            double overlapDistance = Math.min(ballMax, polyMax) - Math.max(ballMin, polyMin);
            detectedOverlaps.add(Vector2.scale(ballAxis, overlapDistance));
        }
        else
        {
            return new HitInfo(false, null);
        }

        for (int i = 0; i < vertices.size(); i++)
        {
            Vector2 currentAxis;

            if (i == vertices.size() - 1)
            {
                Vector2 vec = Vector2.sub(vertices.get(0), vertices.get(i));
                currentAxis = Vector2.createNormal(vec).normalized();
            }
            else
            {
                Vector2 vec = Vector2.sub(vertices.get(i+1), vertices.get(i));
                currentAxis = Vector2.createNormal(vec).normalized();
            }
            List<Double> newPoints = new ArrayList<>();

            for (Vector2 vert : vertices)
            {
                Vector2 globalVert = Vector2.add(vert, colliderObjectPosition);
                newPoints.add(Vector2.dot(globalVert, currentAxis));
            }
            newPoints.sort(Comparator.naturalOrder());

            double circleCenter = Vector2.dot(globalBallPosition, currentAxis);
            double ballMinimum = circleCenter - ball.getCollider().getRadius();
            double ballMaximum = circleCenter + ball.getCollider().getRadius();

            double polygonMin = newPoints.get(0);
            double polygonMax = newPoints.get(newPoints.size() - 1);

            // Do the projected areas intersect?
            if (ballMaximum > polygonMin && ballMinimum < polygonMax || polygonMax > ballMinimum && polygonMin < ballMaximum)
            {
                double overlapDistance = Math.min(ballMaximum, polygonMax) - Math.max(ballMinimum, polygonMin);
                detectedOverlaps.add(Vector2.scale(currentAxis, overlapDistance));
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
