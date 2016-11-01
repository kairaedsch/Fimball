package sep.fimball.model;

import javafx.scene.canvas.GraphicsContext;

public class WorldObject
{
    private Renderer renderer;
    private SoundPlayer player;
    private Transform transform;

    public WorldObject(Vector2 position, double rotation)
    {
        transform = new Transform(position, rotation);
    }

    public void update()
    {
        throw new UnsupportedOperationException();
    }

    public void draw(GraphicsContext gc)
    {
        if (renderer != null)
        {
            renderer.render(gc);
        }
    }
}