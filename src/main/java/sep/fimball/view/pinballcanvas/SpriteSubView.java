package sep.fimball.view.pinballcanvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import sep.fimball.general.data.Vector2;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

import java.io.File;

/**
 * Die SpriteSubView ist für das Zeichnen eines Flipperautomaten-Elements zuständig.
 */
public class SpriteSubView
{
    /**
     * Das Bild des Sprites.
     */
    private Image image;

    /**
     * Die Drehung des Sprites.
     */
    private DoubleProperty rotationProperty;

    /**
     * Die Position des Sprites.
     */
    private ObjectProperty<Vector2> positionProperty;

    /**
     * Das zur SpriteSubView gehörende SpriteSubViewModel.
     */
    private SpriteSubViewModel viewModel;

    /**
     * Erzeugt eine neue SpriteSubView mit zugehörigem SpriteSubViewModel und bindet sich an dieses.
     *
     * @param viewModel Das zu setzende SpriteSubViewModel.
     */
    SpriteSubView(SpriteSubViewModel viewModel)
    {
        this.viewModel = viewModel;

        viewModel.animationFramePathProperty().addListener((observable, oldValue, newValue) -> loadImage(newValue));
        loadImage(viewModel.animationFramePathProperty().getValue());

        rotationProperty = new SimpleDoubleProperty();
        rotationProperty.bind(viewModel.rotationProperty());

        positionProperty = new SimpleObjectProperty<>();
        positionProperty.bind(viewModel.positionProperty());
    }

    /**
     * Lädt das im {@imagePath} gespeicherte Bild.
     *
     * @param imagePath Der Pfad zum Bild.
     */
    private void loadImage(String imagePath)
    {
        File file = new File(imagePath);
        image = new Image(file.toURI().toString());
    }

    /**
     * Zeichnet sich auf das übergebene GraphicsContext-Objekt.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die View sich zeichnen soll.
     */
    void draw(GraphicsContext graphicsContext)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        graphicsContext.save(); // saves the current state on stack, including the current transform
        rotate(graphicsContext, rotationProperty.doubleValue(), x + image.getWidth() / 2, y + image.getHeight() / 2);
        graphicsContext.drawImage(image, x, y);
        graphicsContext.restore(); // back to original state (before rotation)
    }

    /**
     * TODO
     *
     * @param gc Der GraphicsContext, auf dem rotiert wird.
     * @param angle Die Gradanzahl, um die rotiert wird.
     * @param px TODO
     * @param py TODO
     */
    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}