package sep.fimball.model;

import com.sun.javafx.geom.Rectangle;

public class Camera
{
    private Vector2 position;
    private Vector2 viewArea;

    public Camera(Vector2 position, Vector2 viewArea)
    {
        this.position = position;
        this.viewArea = viewArea;
    }

    public RectangleDouble getViewBox()
    {
        double width = viewArea.getX();
        double height = viewArea.getY();
        double originX = position.getX() - (width / 2);
        double originY = position.getY() - (height / 2);
        Vector2 origin = new Vector2(originX, originY);
        return new RectangleDouble(origin, width, height);
    }
}