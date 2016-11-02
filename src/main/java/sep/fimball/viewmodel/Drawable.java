package sep.fimball.viewmodel;

import sep.fimball.model.Sprite;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class Drawable
{
    private Vector2 position;
    private Sprite sprite;

    public Drawable(Vector2 position, Sprite sprite)
    {
        this.position = position;
        this.sprite = sprite;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Sprite getSprite()
    {
        return sprite;
    }
}
