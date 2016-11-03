package sep.fimball.model;

import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Sprite implements Renderable
{
    private Image image;

    public Sprite(Image image)
    {
        this.image = image;
    }

    public Sprite getFrame()
    {
        throw new NotImplementedException();
    }
}