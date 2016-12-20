package sep.fimball.general.debug;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.DesignConfig;
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
 * TODO entfernen
 */
public class Debug
{
    /**
     * Debug stuff.
     */
    private static final boolean ENABLED = true;

    /**
     * Debug stuff.
     */
    private static List<DrawEntry> drawEntries = new ArrayList<>();

    /**
     * Debug stuff.
     */
    private static final Object monitor = new Object();

    /**
     * Debug stuff.
     */
    private static final long LIFE_TIME_MS = 1000;

    /**
     * Debug stuff.
     *
     * @param position Debug stuff.
     * @param direction Debug stuff.
     * @param color Debug stuff.
     */
    public static void addDrawVector(Vector2 position, Vector2 direction, Color color)
    {
        synchronized (monitor)
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

    /**
     * Debug stuff.
     *
     * @param position Debug stuff.
     * @param radius Debug stuff.
     * @param color Debug stuff.
     */
    public static void addDrawCircle(Vector2 position, double radius, Color color)
    {
        synchronized (monitor)
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

    /**
     * Debug stuff.
     *
     * @param positions Debug stuff.
     * @param color Debug stuff.
     */
    public static void addDrawPoly(List<Vector2> positions, Color color)
    {
        synchronized (monitor)
        {
            DrawEntry de = new DrawEntry();
            de.type = "poly";
            de.positions = positions;
            de.color = color;
            de.creationTime = System.currentTimeMillis();
            drawEntries.add(de);
        }
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     * @param pos Debug stuff.
     * @param dir Debug stuff.
     */
    private static void drawVector(GraphicsContext context, Vector2 pos, Vector2 dir)
    {
        double width = 6.0;
        double length = 3.0;
        context.strokeLine(pos.getX() * DesignConfig.PIXELS_PER_GRID_UNIT, pos.getY() * DesignConfig.PIXELS_PER_GRID_UNIT, (pos.getX() + dir.getX() * length) * DesignConfig.PIXELS_PER_GRID_UNIT, (pos.getY() + dir.getY() * length) * DesignConfig.PIXELS_PER_GRID_UNIT);
        context.fillOval((pos.getX() + dir.getX() * length) * DesignConfig.PIXELS_PER_GRID_UNIT - width * 0.5, (pos.getY() + dir.getY() * length) * DesignConfig.PIXELS_PER_GRID_UNIT - width * 0.5, width, width);
        context.setFill(Color.BLACK);
        context.fillOval(pos.getX() * DesignConfig.PIXELS_PER_GRID_UNIT - width * 0.5, pos.getY() * DesignConfig.PIXELS_PER_GRID_UNIT - width * 0.5, width, width);
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     * @param pos Debug stuff.
     * @param radius Debug stuff.
     */
    private static void drawCircle(GraphicsContext context, Vector2 pos, double radius)
    {
        context.strokeOval((pos.getX() - radius) * DesignConfig.PIXELS_PER_GRID_UNIT, (pos.getY() - radius) * DesignConfig.PIXELS_PER_GRID_UNIT, radius * 2.0 * DesignConfig.PIXELS_PER_GRID_UNIT, radius * 2.0 * DesignConfig.PIXELS_PER_GRID_UNIT);
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     * @param pos Debug stuff.
     * @param radius Debug stuff.
     * @param rotation Debug stuff.
     * @param pivotPoint Debug stuff.
     */
    private static void drawCircle(GraphicsContext context, Vector2 pos, double radius, double rotation, Vector2 pivotPoint)
    {
        Vector2 rotatedPos = pos.rotate(rotation, pivotPoint);
        drawCircle(context, rotatedPos, radius);
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     * @param vertices Debug stuff.
     * @param offset Debug stuff.
     */
    private static void drawPolygon(GraphicsContext context, List<Vector2> vertices, Vector2 offset)
    {
        if (offset == null)
            offset = new Vector2();

        double[] xPoints = new double[vertices.size()];
        double[] yPoints = new double[vertices.size()];

        for (int i = 0; i < vertices.size(); i++)
        {
            xPoints[i] = (vertices.get(i).getX() + offset.getX()) * DesignConfig.PIXELS_PER_GRID_UNIT;
            yPoints[i] = (vertices.get(i).getY() + offset.getY()) * DesignConfig.PIXELS_PER_GRID_UNIT;
        }

        context.strokePolygon(xPoints, yPoints, vertices.size());
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     * @param verts Debug stuff.
     * @param offset Debug stuff.
     * @param rotation Debug stuff.
     * @param pivotPoint Debug stuff.
     */
    private static void drawPolygon(GraphicsContext context, List<Vector2> verts, Vector2 offset, double rotation, Vector2 pivotPoint)
    {
        List<Vector2> rotatedVerts = verts.stream().map(v2 -> v2.rotate(rotation, pivotPoint)).collect(Collectors.toList());
        drawPolygon(context, rotatedVerts, offset);
    }

    /**
     * Debug stuff.
     *
     * @param context Debug stuff.
     */
    public static void draw(GraphicsContext context)
    {
        if (!ENABLED)
            return;

        context.setStroke(Color.BLACK);

        synchronized (monitor)
        {
            long time = System.currentTimeMillis();
            drawEntries.removeIf(drawEntry -> drawEntry.creationTime != -1 && (drawEntry.creationTime + LIFE_TIME_MS <= time));

            context.save();
            PhysicsElement.thisIsForDebug.forEach((WeakReference<PhysicsElement> wpe) ->
            {
                PhysicsElement<GameElement> pe = (PhysicsElement<GameElement>) wpe.get();
                pe.getColliders().forEach((Collider col) -> col.getShapes().forEach((ColliderShape shape) ->
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
                }));
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
