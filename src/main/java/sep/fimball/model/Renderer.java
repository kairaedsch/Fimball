package sep.fimball.model;

import javafx.scene.canvas.GraphicsContext;

public class Renderer
{
    private Renderable renderable;

    public Renderer(Renderable renderable)
    {
        this.renderable = renderable;
    }

    public void render(GraphicsContext gc)
    {
        throw new UnsupportedOperationException();
    }
}