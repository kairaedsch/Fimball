package sep.fimball.general.debug;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAsuro on 17.11.2016.
 */
public class Debug
{
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
            de.creationTime = -1;
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
            de.creationTime = -1;
            drawEntries.add(de);
        }
    }

    private static void drawVector(GraphicsContext context, Vector2 pos, Vector2 dir)
    {
        context.strokeLine(pos.getX() * Config.pixelsPerGridUnit, pos.getY() * Config.pixelsPerGridUnit, (pos.getX() + dir.getX()) * Config.pixelsPerGridUnit, (pos.getY() + dir.getY()) * Config.pixelsPerGridUnit);
        context.fillOval((pos.getX() + dir.getX()) * Config.pixelsPerGridUnit - 1.5, (pos.getY() + dir.getY()) * Config.pixelsPerGridUnit - 1.5, 6.0, 6.0);
        context.setFill(Color.BLACK);
        context.fillOval(pos.getX() * Config.pixelsPerGridUnit, pos.getY() * Config.pixelsPerGridUnit, 6.0, 6.0);
    }

    private static void drawCircle(GraphicsContext context, Vector2 pos, double radius)
    {
        radius = 2.0;
        context.strokeOval((pos.getX() - radius) * Config.pixelsPerGridUnit, (pos.getY() - radius) * Config.pixelsPerGridUnit, radius * 2.0 * Config.pixelsPerGridUnit, radius * 2.0 * Config.pixelsPerGridUnit);
    }

    public static void draw(GraphicsContext context)
    {
        synchronized (drawEntries)
        {
            long time = System.currentTimeMillis();
            drawEntries.removeIf(drawEntry -> drawEntry.creationTime != -1 && (drawEntry.creationTime + LIFE_TIME_MS <= time));

            context.save();
            PhysicsElement.thisIsForDebug.forEach((PhysicsElement pe) -> ((PhysicsElement<GameElement>)pe).getColliders().forEach((Collider col) -> col.getShapes().stream().filter(shape -> shape.getClass().equals(CircleColliderShape.class)).forEach((ColliderShape shape) ->
            {
                CircleColliderShape circle = (CircleColliderShape)shape;
                drawCircle(context, circle.getPosition().plus(pe.getPosition()), circle.getRadius());
            })));

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

                        //context.strokePolygon();
                        break;
                    default:
                        System.err.println("Invalid debug shape");
                }
            }
            context.restore();
        }
    }
}
