package sep.fimball.general;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;

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
            de.position = Vector2.scale(position, 30.0);
            de.direction = Vector2.scale(direction, 30.0);
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
            de.position = Vector2.scale(position, 30.0);
            de.radius = radius * 30.0;
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

    public static void draw(GraphicsContext context)
    {
        synchronized (drawEntries)
        {
            long time = System.currentTimeMillis();
            drawEntries.removeIf(drawEntry -> drawEntry.creationTime != -1 && (drawEntry.creationTime + LIFE_TIME_MS <= time));

            for (DrawEntry entry : drawEntries)
            {
                context.setStroke(entry.color);
                context.setFill(entry.color);

                switch (entry.type)
                {
                    case "vector":
                        context.strokeLine(entry.position.getX(), entry.position.getY(), entry.position.getX() + entry.direction.getX(), entry.position.getY() + entry.direction.getY());
                        context.fillOval(entry.position.getX() + entry.direction.getX() - 1.5, entry.position.getY() + entry.direction.getY() - 1.5, 6.0, 6.0);
                        context.setFill(Color.BLACK);
                        context.fillOval(entry.position.getX(), entry.position.getY(), 6.0, 6.0);
                        break;
                    case "circle":
                        context.strokeOval(entry.position.getX() - entry.radius, entry.position.getY() - entry.radius, entry.position.getX() + entry.radius, entry.position.getY() + entry.radius);
                        break;
                    case "poly":

                        //context.strokePolygon();
                        break;
                    default:
                        System.err.println("Invalid debug shape");
                }
            }
        }
    }
}
