package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ObjectProperty;
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

        positionProperty = new SimpleObjectProperty<>();
        positionProperty.bind(viewModel.positionProperty());
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

        Image image;
        ElementImage elementImage = viewModel.animationFramePathProperty().get();
        double rotation = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());
        if (imageLayer == ImageLayer.TOP) image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.TOP, (int) viewModel.rotationProperty().get()));
        else image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, (int) viewModel.rotationProperty().get()));

        graphicsContext.save(); // saves the current state on stack, including the current transform
        if(rotation != 0) rotate(graphicsContext, rotation, x + image.getWidth() / 2, y + image.getHeight() / 2);

        if(viewModel.isSelectedProperty().get())
        {
            double plus = +0.5 * Config.pixelsPerGridUnit;
            graphicsContext.setLineWidth(Config.pixelsPerGridUnit);
            graphicsContext.strokeRect((int) (x * Config.pixelsPerGridUnit) - plus, (int) (y * Config.pixelsPerGridUnit) - plus, image.getWidth() + plus * 2, image.getHeight() + plus * 2);
        }

        graphicsContext.drawImage(image, (int) (x * Config.pixelsPerGridUnit), (int) (y * Config.pixelsPerGridUnit), image.getWidth(), image.getHeight());
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