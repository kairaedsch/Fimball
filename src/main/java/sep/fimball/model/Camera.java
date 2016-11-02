package sep.fimball.model;

import javafx.scene.canvas.GraphicsContext;

public class Camera
{
    public Camera(World targetWorld, Vector2 position)
    {
        super(position, 0.0); // camera rotation is always 0
    }

    public void drawWorld(GraphicsContext gc)
    {
        throw new UnsupportedOperationException();
    }
}