package sep.fimball.model.physics.collider;

import javafx.scene.paint.Color;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.model.physics.element.BallPhysicsElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
     * Erstellt einen neuen Collider in Polygonform.
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
    public HitInfo calculateHitInfo(BallPhysicsElement ball, Vector2 colliderObjectPosition, double rotation, Vector2 pivotPoint)
    {
        List<Vector2> rotatedVerts;

        if (rotation != 0)
        {
            rotatedVerts = rotate(rotation, pivotPoint);
        }
        else
        {
            rotatedVerts = vertices;
        }

        Vector2 globalBallPosition = Vector2.add(ball.getPosition(), ball.getCollider().getPosition());
        List<OverlapAxis> detectedOverlaps = new ArrayList<>();
        List<Vector2> ballAxisList = new ArrayList<>();
        Vector2 ballAxis;

        for (Vector2 vertex : rotatedVerts)
        {
            Vector2 globalVertexPosition = Vector2.add(vertex, colliderObjectPosition);
            ballAxisList.add(Vector2.sub(globalBallPosition, globalVertexPosition));
        }
        ballAxisList.sort(((o1, o2) -> o1.magnitude() <= o2.magnitude() ? -1 : 1));
        ballAxis = ballAxisList.get(0).normalized();

        List<Double> points = new ArrayList<>();

        for (Vector2 vertex : rotatedVerts)
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
            detectedOverlaps.add(new OverlapAxis(ballAxis, overlapDistance));
        }
        else
        {
            return new HitInfo(false, null);
        }

        for (int i = 0; i < rotatedVerts.size(); i++)
        {
            Vector2 currentAxis;

            if (i == rotatedVerts.size() - 1)
            {
                Vector2 vec = Vector2.sub(rotatedVerts.get(0), rotatedVerts.get(i));
                currentAxis = Vector2.createNormal(vec).normalized();
            }
            else
            {
                Vector2 vec = Vector2.sub(rotatedVerts.get(i + 1), rotatedVerts.get(i));
                currentAxis = Vector2.createNormal(vec).normalized();
            }
            Debug.addDrawVector(rotatedVerts.get(i), currentAxis, Color.YELLOW);
            List<Double> newPoints = new ArrayList<>();

            for (Vector2 vert : rotatedVerts)
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

                if (Vector2.dot(currentAxis, ballAxis) >= 0)
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
        Vector2 resultVector = Vector2.scale(detectedOverlaps.get(0).getAxis(), detectedOverlaps.get(0).getOverlap());

        return new HitInfo(true, resultVector);
    }

    @Override
    public RectangleDouble getBoundingBox(double rotation, Vector2 pivotPoint)
    {
        List<Vector2> rotatedVertices = rotate(rotation, pivotPoint);

        double minX = rotatedVertices.get(0).getX();
        double minY = rotatedVertices.get(0).getY();
        double maxX = rotatedVertices.get(0).getX();
        double maxY = rotatedVertices.get(0).getY();

        for (Vector2 vertex : rotatedVertices)
        {
            double vertexX = vertex.getX();
            double vertexY = vertex.getY();

            if (vertexX < minX)
            {
                minX = vertexX;
            }
            if (vertexX > maxX)
            {
                maxX = vertexX;
            }
            if (vertexY < minY)
            {
                minY = vertexY;
            }
            if (vertexY > maxY)
            {
                maxY = vertexY;
            }
        }
        Vector2 origin = new Vector2(minX, minY);
        Debug.addDrawVector(origin, new Vector2(maxX - minX, 0), Color.BROWN);
        Debug.addDrawVector(origin, new Vector2(0, maxY - minY), Color.BROWN);
        return new RectangleDouble(origin, maxX - minX, maxY - minY);
    }

    @Override
    public double getMaximumYPos(double rotation, Vector2 pivotPoint)
    {
        List<Vector2> newVertices;

        if (rotation != 0)
        {
            newVertices = rotate(rotation, pivotPoint);
        }
        else
        {
            newVertices = vertices;
        }
        double maxY = newVertices.get(0).getY();

        for (Vector2 vertex : newVertices)
        {
            if (vertex.getY() > maxY)
            {
                maxY = vertex.getY();
            }
        }
        return maxY;
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
        return vertices.stream().map(vec -> Vector2.rotate(vec, Math.toRadians(rotation), pivotPoint)).collect(Collectors.toList());
    }
}