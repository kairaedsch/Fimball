package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
     * @param imageLayer      Gibt an ob das Sprite sein Top oder Bottom Image zeichnen soll.
     */
    void draw(GraphicsContext graphicsContext, ImageLayer imageLayer)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        Image image;
        ElementImage elementImage = viewModel.animationFramePathProperty().get();
        double rotation = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());
        if (imageLayer == ImageLayer.TOP)
            image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.TOP, (int) viewModel.rotationProperty().get()));
        else
            image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, (int) viewModel.rotationProperty().get()));

        graphicsContext.save(); // saves the current state on stack, including the current transform

        Vector2 pivot = viewModel.pivotPointProperty().get().add(new Vector2(0, 0));
        int picRotate = (int) (viewModel.rotationProperty().get() - rotation) % 360;
        if (picRotate == 270)
        {
            double div = (image.getWidth() / 2.0);
            double height = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, 180)).getWidth();
            rotate(graphicsContext, 90, new Vector2(x * Config.pixelsPerGridUnit + div, y * Config.pixelsPerGridUnit + div));
            y += (image.getWidth() - height) / Config.pixelsPerGridUnit;
            pivot = new Vector2(pivot.getX(), (height / Config.pixelsPerGridUnit) - pivot.getY());
        }
        else if (picRotate == 180)
        {
            double height = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, 90)).getWidth();
            rotate(graphicsContext, 180, new Vector2(x * Config.pixelsPerGridUnit + (image.getWidth() / 2.0), y * Config.pixelsPerGridUnit + (height / 2.0)));
            pivot = new Vector2((image.getWidth() / Config.pixelsPerGridUnit) - pivot.getX(), (height / Config.pixelsPerGridUnit) - pivot.getY());
        }
        else if (picRotate == 90)
        {
            double div = (image.getWidth() / 2.0);
            rotate(graphicsContext, -90, new Vector2(x * Config.pixelsPerGridUnit + div, y * Config.pixelsPerGridUnit + div));
            pivot = new Vector2((image.getWidth() / Config.pixelsPerGridUnit) - pivot.getX(), pivot.getY());
        }

        if (viewModel.rotationProperty().get() != 0)
           rotate(graphicsContext, viewModel.rotationProperty().get(), pivot.add(new Vector2(x, y)).scale(Config.pixelsPerGridUnit));

        if (viewModel.isSelectedProperty().get())
        {
            double plus = +0.5 * Config.pixelsPerGridUnit;
            graphicsContext.setLineWidth(Config.pixelsPerGridUnit);
            graphicsContext.strokeRect((int) (x * Config.pixelsPerGridUnit) - plus, (int) (y * Config.pixelsPerGridUnit) - plus, image.getWidth() + plus * 2, image.getHeight() + plus * 2);
        }

        graphicsContext.drawImage(image, (int) (x * Config.pixelsPerGridUnit), (int) (y * Config.pixelsPerGridUnit), image.getWidth(), image.getHeight());

        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect((int) ((x + pivot.getX()) * Config.pixelsPerGridUnit), (int) ((y + pivot.getY()) * Config.pixelsPerGridUnit), 10, 10);

        graphicsContext.restore(); // back to original state (before rotation)
    }

    /**
     * TODO
     *
     * @param gc         Der GraphicsContext, auf dem rotiert wird.
     * @param angle      Die Grad-Zahl, um die rotiert wird.
     * @param pivotPoint Der Punkt um den rotiert werden soll.
     */
    private void rotate(GraphicsContext gc, double angle, Vector2 pivotPoint)
    {
        Rotate r = new Rotate(angle, pivotPoint.getX(), pivotPoint.getY());
        gc.transform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}