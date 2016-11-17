package sep.fimball.general;

import javafx.scene.canvas.Canvas;
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

    public static void addDrawEntry(Vector2 position, Vector2 direction, Color color)
    {
        synchronized (drawEntries)
        {
            DrawEntry de = new DrawEntry();
            de.position = Vector2.scale(position, 15.0);
            de.direction = Vector2.scale(direction, 15.0);
            de.color = color;
            drawEntries.add(de);
        }
    }

    public static void clearDraw()
    {
        synchronized (drawEntries)
        {
            drawEntries.clear();
        }
    }

    public static void draw(GraphicsContext context)
    {
        synchronized (drawEntries)
        {
            for (DrawEntry entry : drawEntries)
            {
                context.setStroke(entry.color);
                context.strokeLine(entry.position.getX(), entry.position.getY(), entry.position.getX() + entry.direction.getX(), entry.position.getY() + entry.direction.getY());
            }
        }
    }
}
