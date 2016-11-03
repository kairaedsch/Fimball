package sep.fimball.model;

public class Camera
{
    private Vector2 position;
    private Vector2 viewAngle;

    public Camera(Vector2 position, Vector2 viewAngle)
    {
        this.position = position;
        this.viewAngle = viewAngle;
    }
}