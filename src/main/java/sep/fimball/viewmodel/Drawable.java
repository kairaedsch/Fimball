package sep.fimball.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.Sprite;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class Drawable
{
    private Vector2 position;
    private double rotation;
    private SimpleStringProperty animationPath;

    public Drawable(Vector2 position, double rotation, String animationPath)
    {
        this.position = position;
        this.rotation = rotation;
        this.animationPath = new SimpleStringProperty(animationPath);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getRotation()
    {
        return rotation;
    }

    public SimpleStringProperty getAnimationPath()
    {
        return animationPath;
    }
}
