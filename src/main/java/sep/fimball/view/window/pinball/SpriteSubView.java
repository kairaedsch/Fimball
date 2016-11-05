package sep.fimball.view.window.pinball;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import sep.fimball.model.Vector2;
import sep.fimball.viewmodel.window.pinball.SpriteViewModel;

import java.io.File;

public class SpriteSubView
{
    private Image image;
    private StringProperty imagePathProperty;
    private DoubleProperty rotationProperty;
    private ObjectProperty<Vector2> positionProperty;
    private SpriteViewModel viewModel;

    public SpriteSubView(SpriteViewModel viewModel)
    {
        this.viewModel = viewModel;

        imagePathProperty = new SimpleStringProperty();
        imagePathProperty.addListener((observable, oldValue, newValue) -> loadImage());
        imagePathProperty.bind(viewModel.getFramePath()); // TODO wird der listener instant aufgerufen?

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