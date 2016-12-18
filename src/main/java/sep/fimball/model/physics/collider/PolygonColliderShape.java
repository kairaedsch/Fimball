package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementierung eines Colliders, welcher die Form eines Polygons hat. Um Kollisionen korrekt erkennen zu können muss das Polygon konvex sein.
 */
public class PolygonColliderShape implements ColliderShape
{
    /**
     * Die Eckpunkte des Polygons, die den Collider formen.
     */
    private List<Vector2> vertices;

    /**
     * Erstellt einen neuen Collider in der Form eines Polygons.
     *
     * @param vertices Die Ecken des Polygons.
     */
    public PolygonColliderShape(List<Vector2> vertices)
    {
        this.vertices = vertices;
    }

    /**
     * Gibt die Eckpunkte des Polygons, die den Collider formen, zurück.
     *
     * @return Die Eckpunkte des Polygons, die den Collider formen.
     */
    public List<Vector2> getVertices()
    {
        return vertices;
    }

    @Override
    public HitInfo calculateHitInfo(CircleColliderShape otherColliderShape, Vector2 otherColliderPosition, Vector2 currentColliderPosition, double rotation, Vector2 pivotPoint)
    {
        List<Vector2> rotatedVertices = rotate(rotation, pivotPoint);
        Vector2 globalBallPosition = otherColliderPosition.plus(otherColliderShape.getPosition());
        List<OverlapAxis> detectedOverlaps = new ArrayList<>();
        List<Vector2> ballAxisList = new ArrayList<>();
        Vector2 ballAxis;

        for (Vector2 vertex : rotatedVertices)
        {
            Vector2 globalVertexPosition = vertex.plus(currentColliderPosition);
            ballAxisList.add(globalBallPosition.minus(globalVertexPosition));
        }
        ballAxisList.sort(((o1, o2) -> o1.magnitude() <= o2.magnitude() ? -1 : 1));
        ballAxis = ballAxisList.get(0).normalized();

        List<Double> points = new ArrayList<>();

        for (Vector2 vertex : rotatedVertices)
        {
            Vector2 globalVertex = vertex.plus(currentColliderPosition);
            points.add(globalVertex.dot(ballAxis));
        }
        points.sort(Comparator.naturalOrder());

        double ballCenter = globalBallPosition.dot(ballAxis);
        double ballMin = ballCenter - otherColliderShape.getRadius();
        double ballMax = ballCenter + otherColliderShape.getRadius();

        double polyMin = points.get(0);
        double polyMax = points.get(points.size() - 1);

        // Do the projected areas intersect?
        if (ballMax > polyMin && ballMin < polyMax || polyMax > ballMin && polyMin < ballMax)
        {
            double overlapDistance = Math.min(ballMax, polyMax) - Math.max(ballMin, polyMin);
            detectedOverlaps.add(new OverlapAxis(ballAxis, overlapDistance));
        }
        else
        {
            return new HitInfo(false, null);
        }

        for (int i = 0; i < rotatedVertices.size(); i++)
        {
            Vector2 currentAxis;

            if (i == rotatedVertices.size() - 1)
            {
                Vector2 vec = rotatedVertices.get(0).minus(rotatedVertices.get(i));
                currentAxis = vec.normal().normalized();
            }
            else
            {
                Vector2 vec = rotatedVertices.get(i + 1).minus(rotatedVertices.get(i));
                currentAxis = vec.normal().normalized();
            }
            //Debug.addDrawVector(rotatedVertices.get(i).plus(colliderObjectPosition), currentAxis, Color.YELLOW);
            List<Double> newPoints = new ArrayList<>();

            for (Vector2 vertices : rotatedVertices)
            {
                Vector2 globalVertices = vertices.plus(currentColliderPosition);
                newPoints.add(globalVertices.dot(currentAxis));
            }
            newPoints.sort(Comparator.naturalOrder());

            double circleCenter = globalBallPosition.dot(currentAxis);
            double ballMinimum = circleCenter - otherColliderShape.getRadius();
            double ballMaximum = circleCenter + otherColliderShape.getRadius();

            double polygonMin = newPoints.get(0);
            double polygonMax = newPoints.get(newPoints.size() - 1);

            // Do the projected areas intersect?
            if (ballMaximum > polygonMin && ballMinimum < polygonMax || polygonMax > ballMinimum && polygonMin < ballMaximum)
            {
                double overlapDistance = Math.min(ballMaximum, polygonMax) - Math.max(ballMinimum, polygonMin);

                if (currentAxis.dot(ballAxis) >= 0)
                {
                    detectedOverlaps.add(new OverlapAxis(currentAxis, overlapDistance));
                }
            }
            else
            {
                return new HitInfo(false, null);
            }
        }

        detectedOverlaps.sort(((o1, o2) -> o1.getOverlap() <= o2.getOverlap() ? -1 : 1));
        Vector2 resultVector = detectedOverlaps.get(0).getAxis().scale(detectedOverlaps.get(0).getOverlap());
        return new HitInfo(true, resultVector);
    }

    @Override
    public Vector2 getExtremePos(double rotation, Vector2 pivotPoint, boolean max)
    {
        List<Vector2> rotatedVertices = rotate(rotation, pivotPoint);
        Optional<Vector2> extremeVal = rotatedVertices.stream().reduce(max ? Vector2::maxComponents : Vector2::minComponents);
        if (extremeVal.isPresent())
            return extremeVal.get();
        else
            throw new IllegalStateException("No vectors found.");
    }

    /**
     * Rotiert alle Vertices um einen Punkt.
     *
     * @param rotation   Der Winkel in Grad, um den die Vertices rotiert werden.
     * @param pivotPoint Der Punkt, um den rotiert wird.
     * @return Die Liste von rotierten Eckpunkten.
     */
    private List<Vector2> rotate(double rotation, Vector2 pivotPoint)
    {
        return vertices.stream().map(vec -> vec.rotate(Math.toRadians(rotation), pivotPoint)).collect(Collectors.toList());
    }
}
