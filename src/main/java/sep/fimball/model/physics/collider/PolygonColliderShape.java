package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

import java.util.*;
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
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public HitInfo calculateHitInfo(CircleColliderShape otherColliderShape, Vector2 otherColliderPosition, Vector2 currentColliderPosition, double rotation, Vector2 pivotPoint)
    {
        List<OverlapAxis> detectedOverlaps = new ArrayList<>();
        List<Vector2> rotatedVertices = rotate(rotation, pivotPoint);

        Vector2 otherColliderGlobalPosition = otherColliderPosition.plus(otherColliderShape.getPosition());
        Vector2 otherColliderAxis = shortestPathVertexToCircleCollider(rotatedVertices, currentColliderPosition, otherColliderGlobalPosition);
        List<Double> projectedVertices = projectVerticesOnAxis(rotatedVertices, currentColliderPosition, otherColliderAxis);
        Optional<OverlapAxis> edgeOverlap = checkIfProjectionsIntersect(projectedVertices, otherColliderAxis, otherColliderGlobalPosition, otherColliderShape.getRadius());

        if (!edgeOverlap.isPresent())
        {
            return new HitInfo(false, null);
        }
        else
        {
            detectedOverlaps.add(edgeOverlap.get());
        }

        for (int i = 0; i < rotatedVertices.size(); i++)
        {
            Vector2 currentAxis = getVertexNormal(rotatedVertices, i);
            List<Double> projectedVerts = projectVerticesOnAxis(rotatedVertices, currentColliderPosition, currentAxis);
            Optional<OverlapAxis> vertexOverlap = checkIfProjectionsIntersect(projectedVerts, currentAxis, otherColliderGlobalPosition, otherColliderShape.getRadius());

            if (!vertexOverlap.isPresent())
            {
                return new HitInfo(false, null);
            }
            else
            {
                if (currentAxis.dot(otherColliderAxis) >= 0)
                {
                    detectedOverlaps.add(vertexOverlap.get());
                }
            }
        }
        Vector2 minimumOverlap = getMinimumOverlap(detectedOverlaps);
        return new HitInfo(true, minimumOverlap);
    }

    private Vector2 shortestPathVertexToCircleCollider(List<Vector2> rotatedVertices, Vector2 colliderPosition, Vector2 circleColliderPosition)
    {
        List<Vector2> ballAxisList = new ArrayList<>();

        for (Vector2 vertex : rotatedVertices)
        {
            Vector2 globalVertexPosition = vertex.plus(colliderPosition);
            ballAxisList.add(circleColliderPosition.minus(globalVertexPosition));
        }
        ballAxisList.sort(((o1, o2) -> o1.magnitude() <= o2.magnitude() ? -1 : 1));
        return ballAxisList.get(0).normalized();
    }

    private List<Double> projectVerticesOnAxis(List<Vector2> vertices, Vector2 colliderPosition, Vector2 axis)
    {
        List<Double> points = new ArrayList<>();

        for (Vector2 vertex : vertices)
        {
            Vector2 globalVertex = vertex.plus(colliderPosition);
            points.add(globalVertex.dot(axis));
        }
        points.sort(Comparator.naturalOrder());
        return points;
    }

    private Optional<OverlapAxis> checkIfProjectionsIntersect(List<Double> projectedVertices, Vector2 axis, Vector2 circleColliderPosition, double circleColliderRadius)
    {
        double ballCenter = circleColliderPosition.dot(axis);
        double ballMin = ballCenter - circleColliderRadius;
        double ballMax = ballCenter + circleColliderRadius;

        double polyMin = projectedVertices.get(0);
        double polyMax = projectedVertices.get(projectedVertices.size() - 1);

        // Do the projected areas intersect?
        if (ballMax > polyMin && ballMin < polyMax || polyMax > ballMin && polyMin < ballMax)
        {
            double overlapDistance = Math.min(ballMax, polyMax) - Math.max(ballMin, polyMin);
            return Optional.of(new OverlapAxis(axis, overlapDistance));
        }
        else
        {
            return Optional.empty();
        }
    }

    private Vector2 getVertexNormal(List<Vector2> rotatedVertices, int vertexIndex)
    {
        if (vertexIndex == rotatedVertices.size() - 1)
        {
            Vector2 vec = rotatedVertices.get(0).minus(rotatedVertices.get(vertexIndex));
            return vec.normal().normalized();
        }
        else
        {
            Vector2 vec = rotatedVertices.get(vertexIndex + 1).minus(rotatedVertices.get(vertexIndex));
            return vec.normal().normalized();
        }
    }

    private Vector2 getMinimumOverlap(List<OverlapAxis> overlaps)
    {
        overlaps.sort(((o1, o2) -> o1.getOverlap() <= o2.getOverlap() ? -1 : 1));
        return overlaps.get(0).getAxis().scale(overlaps.get(0).getOverlap());
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
