package sep.fimball.model;

public class GameElement
{
    private Vector2 position;
    private double rotation;
    private String[] frames;

    public void update()
    {
        throw new UnsupportedOperationException();
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
}