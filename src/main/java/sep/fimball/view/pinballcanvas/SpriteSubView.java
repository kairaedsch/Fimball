package sep.fimball.view.pinballcanvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.media.ElementImage;
import sep.fimball.view.ImageCache;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

/**
 * Die SpriteSubView ist für das Zeichnen eines Flipperautomaten-Elements zuständig.
 */
public class SpriteSubView
{
    /**
     * Das Bild des Sprites.
     */
    private Image topImage;

    private Image bottomImage;

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

        rotationProperty = new SimpleDoubleProperty();

        positionProperty = new SimpleObjectProperty<>();
        positionProperty.bind(viewModel.positionProperty());

        viewModel.animationFramePathProperty().addListener((observable, oldValue, newValue) -> loadImage());
        viewModel.rotationProperty().addListener((observable, oldValue, newValue) -> loadImage());
        loadImage();
    }

    /**
     * Lädt das im {@imagePath} gespeicherte Bild.
     */
    private void loadImage()
    {
        double rotation = viewModel.rotationProperty().get();
        ElementImage elementImage = viewModel.animationFramePathProperty().get();

        topImage = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.TOP, (int) rotation));
        bottomImage = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, (int) rotation));

        rotationProperty.setValue(elementImage.getRestRotation((int) rotation) + (rotation - (int) rotation));
    }

    /**
     * Zeichnet sich auf das übergebene GraphicsContext-Objekt.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die View sich zeichnen soll.
     */
    void draw(GraphicsContext graphicsContext, ImageLayer imageLayer)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        graphicsContext.save(); // saves the current state on stack, including the current transform
        if(rotationProperty.get() != 0) rotate(graphicsContext, rotationProperty.doubleValue(), x + bottomImage.getWidth() / 2, y + bottomImage.getHeight() / 2);

        Image image;
        if (imageLayer == ImageLayer.TOP) image = topImage;
        else image = bottomImage;

        graphicsContext.drawImage(image, (int) (x * Config.pixelsPerGridUnit), (int) (y * Config.pixelsPerGridUnit), topImage.getWidth(), topImage.getHeight());
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
        gc.transform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}