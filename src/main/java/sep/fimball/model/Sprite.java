package sep.fimball.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class Sprite
{
    private Image image;
    private StringProperty imagePathProperty;

    public Sprite()
    {
        imagePathProperty.addListener((observable, oldValue, newValue) ->
        {
            loadImage();
        });
    }

    private void loadImage()
    {
        File file = new File(imagePathProperty.get());
        image = new Image(file.toURI().toString());
    }
}