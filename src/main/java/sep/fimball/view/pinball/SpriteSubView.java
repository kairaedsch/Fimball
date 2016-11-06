package sep.fimball.view.pinball;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import sep.fimball.model.Vector2;
import sep.fimball.viewmodel.pinball.SpriteSubViewModel;

import java.io.File;

public class SpriteSubView
{
    private Image image;
    private DoubleProperty rotationProperty;
    private ObjectProperty<Vector2> positionProperty;
    private SpriteSubViewModel viewModel;

    public SpriteSubView(SpriteSubViewModel viewModel)
    {
        this.viewModel = viewModel;

        viewModel.framePathProperty().addListener((observable, oldValue, newValue) -> loadImage(newValue));
        loadImage(viewModel.framePathProperty().getValue());

        rotationProperty = new SimpleDoubleProperty();
        rotationProperty.bind(viewModel.rotationProperty());

        positionProperty = new SimpleObjectProperty<>();
        positionProperty.bind(viewModel.positionProperty());
    }

    private void loadImage(String imagePath)
    {
        File file = new File(imagePath);
        image = new Image(file.toURI().toString());
    }

    public void draw(GraphicsContext gc)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, rotationProperty.doubleValue(), x + image.getWidth() / 2, y + image.getHeight() / 2);
        gc.drawImage(image, x, y);
        gc.restore(); // back to original state (before rotation)
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

}