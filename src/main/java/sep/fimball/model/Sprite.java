package sep.fimball.model;

import javafx.scene.image.Image;

public class Sprite implements Renderable
{
    private Image image;

    public Sprite(Image image)
    {
        this.image = image;
    }

    public Image getFrame()
    {
        return image;
    }
}