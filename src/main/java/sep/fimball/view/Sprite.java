package sep.fimball.view;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import sep.fimball.model.Vector2;
import sep.fimball.viewmodel.SpriteViewModel;

import java.io.File;

public class Sprite
{
    private Image image;
    private StringProperty imagePathProperty;
    private DoubleProperty rotationProperty;
    private ObjectProperty<Vector2> positionProperty;
    private SpriteViewModel viewModel;

    public Sprite(SpriteViewModel viewModel)
    {
        this.viewModel = viewModel;

        imagePathProperty = new SimpleStringProperty();
        imagePathProperty.addListener((observable, oldValue, newValue) -> loadImage());
        imagePathProperty.bind(viewModel.getAnimation()); // TODO wird der listener instant aufgerufen?

        rotationProperty = new SimpleDoubleProperty();
        rotationProperty.bind(viewModel.getRotation());

        positionProperty = new SimpleObjectProperty<>();
        positionProperty.bind(viewModel.getPosition());
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