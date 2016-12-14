package sep.fimball.general.debug;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.PolygonColliderShape;
import sep.fimball.model.physics.element.PhysicsElement;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Debug Klasse welche nur für das Debugging der Physik relevant ist und deshalb kein JavaDoc enthält.
 */
public class Debug
{
    public static final boolean ENABLED = true;

    private static List<DrawEntry> drawEntries = new ArrayList<>();
    private static final long LIFE_TIME_MS = 1000;

    public static void addDrawVector(Vector2 position, Vector2 direction, Color color)
    {
        synchronized (drawEntries)
        {
            DrawEntry de = new DrawEntry();
            de.type = "vector";
            de.position = position;
            de.direction = direction;
            de.color = color;
            de.creationTime = System.currentTimeMillis();
            drawEntries.add(de);
        }
    }

    public static void addDrawCircle(Vector2 position, double radius, Color color)
    {
        synchronized (drawEntries)
        {
            DrawEntry de = new DrawEntry();
            de.type = "circle";
            de.position = position;
            de.radius = radius;
            de.color = color;
            de.creationTime = System.currentTimeMillis();
            drawEntries.add(de);
        }
    }

    public static void addDrawPoly(List<Vector2> positions, Color color)
    {
        synchronized (drawEntries)
        {
            DrawEntry de = new DrawEntry();
            de.type = "poly";
            de.positions = positions;
            de.color = color;
            de.creationTime = System.currentTimeMillis();
            drawEntries.add(de);
        }
    }

    private static void drawVector(GraphicsContext context, Vector2 pos, Vector2 dir)
    {
        double width = 6.0;
        double length = 3.0;
        context.strokeLine(pos.getX() * Config.pixelsPerGridUnit, pos.getY() * Config.pixelsPerGridUnit, (pos.getX() + dir.getX() * length) * Config.pixelsPerGridUnit, (pos.getY() + dir.getY() * length) * Config.pixelsPerGridUnit);
        context.fillOval((pos.getX() + dir.getX() * length) * Config.pixelsPerGridUnit - width * 0.5, (pos.getY() + dir.getY() * length) * Config.pixelsPerGridUnit - width * 0.5, width, width);
        context.setFill(Color.BLACK);
        context.fillOval(pos.getX() * Config.pixelsPerGridUnit - width * 0.5, pos.getY() * Config.pixelsPerGridUnit - width * 0.5, width, width);
    }

    private static void drawCircle(GraphicsContext context, Vector2 pos, double radius)
    {
        context.strokeOval((pos.getX() - radius) * Config.pixelsPerGridUnit, (pos.getY() - radius) * Config.pixelsPerGridUnit, radius * 2.0 * Config.pixelsPerGridUnit, radius * 2.0 * Config.pixelsPerGridUnit);
    }

    private static void drawCircle(GraphicsContext context, Vector2 pos, double radius, double rotation, Vector2 pivotPoint)
    {
        Vector2 rotatedPos = pos.rotate(rotation, pivotPoint);
        drawCircle(context, rotatedPos, radius);
    }

    private static void drawPolygon(GraphicsContext context, List<Vector2> vertices, Vector2 offset)
    {
        if (offset == null)
            offset = new Vector2();

        double[] xPoints = new double[vertices.size()];
        double[] yPoints = new double[vertices.size()];

        for (int i = 0; i < vertices.size(); i++)
        {
            xPoints[i] = (vertices.get(i).getX() + offset.getX()) * Config.pixelsPerGridUnit;
            yPoints[i] = (vertices.get(i).getY() + offset.getY()) * Config.pixelsPerGridUnit;
        }

        context.strokePolygon(xPoints, yPoints, vertices.size());
    }

    private static void drawPolygon(GraphicsContext context, List<Vector2> verts, Vector2 offset, double rotation, Vector2 pivotPoint)
    {
        List<Vector2> rotatedVerts = verts.stream().map(v2 -> v2.rotate(rotation, pivotPoint)).collect(Collectors.toList());
        drawPolygon(context, rotatedVerts, offset);
    }

    public static void draw(GraphicsContext context)
    {
        if (!ENABLED)
            return;

        context.setStroke(Color.BLACK);

        synchronized (drawEntries)
        {
            long time = System.currentTimeMillis();
            drawEntries.removeIf(drawEntry -> drawEntry.creationTime != -1 && (drawEntry.creationTime + LIFE_TIME_MS <= time));

            context.save();
            PhysicsElement.thisIsForDebug.forEach((WeakReference<PhysicsElement> wpe) ->
            {
                PhysicsElement<GameElement> pe = wpe.get();
                pe.getColliders().forEach((Collider col) ->
                {
                    col.getShapes().forEach((ColliderShape shape) ->
                    {
                        if (shape.getClass() == CircleColliderShape.class)
                        {
                            CircleColliderShape circle = (CircleColliderShape) shape;
                            drawCircle(context, circle.getPosition().plus(pe.getPosition()), circle.getRadius(), Math.toRadians(pe.getRotation()), pe.getBasePhysicsElement().getPivotPoint().plus(pe.getPosition()));
                        }
                        else if (shape.getClass() == PolygonColliderShape.class)
                        {
                            PolygonColliderShape poly = (PolygonColliderShape) shape;
                            drawPolygon(context, poly.getVertices(), pe.getPosition(), Math.toRadians(pe.getRotation()), pe.getBasePhysicsElement().getPivotPoint());
                        }
                    });
                });
            });

            for (DrawEntry entry : drawEntries)
            {
                context.setStroke(entry.color);
                context.setFill(entry.color);

                switch (entry.type)
                {
                    case "vector":
                        drawVector(context, entry.position, entry.direction);
                        break;
                    case "circle":
                        drawCircle(context, entry.position, entry.radius);
                        break;
                    case "poly":
                        drawPolygon(context, entry.positions, entry.position);
                        break;
                    default:
                        System.err.println("Invalid debug shape");
                }
            }
            context.restore();
        }
    }
}
