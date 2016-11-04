package sep.fimball.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import sep.fimball.model.Vector2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class Sprite
{
    private Image image;
    private StringProperty imagePathProperty;
    private DoubleProperty rotationProperty;
    private ObjectProperty<Vector2> positionProperty;

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

    private void draw()
    {
        //Todo: Draw the sprite
    }
}