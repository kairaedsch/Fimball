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
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
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
        ElementImageViewModel elementImage = viewModel.animationFramePathProperty().get();
        double rotation = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());
        if (imageLayer == ImageLayer.TOP)
            image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.TOP, (int) viewModel.rotationProperty().get()));
        else
            image = ImageCache.getInstance().getImage(elementImage.getImagePath(ImageLayer.BOTTOM, (int) viewModel.rotationProperty().get()));

        graphicsContext.save(); // saves the current state on stack, including the current transform

        Vector2 pivot = viewModel.pivotPointProperty().get().clone();
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
           rotate(graphicsContext, viewModel.rotationProperty().get(), pivot.plus(new Vector2(x, y)).scale(Config.pixelsPerGridUnit));

        if(imageLayer == ImageLayer.TOP) graphicsContext.drawImage(image, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, image.getWidth(), image.getHeight());

        if (viewModel.isSelectedProperty().get())
        {
            double width = Config.pixelsPerGridUnit * 0.25;
            double plus = +0.5 * width;
            graphicsContext.setLineWidth(width);

            double time = (System.currentTimeMillis() % 1000) / 1000.0;
            double trans = -2 * time * (time - 1);

            if(imageLayer == ImageLayer.TOP)
            {
                Color color = Config.complementColor.interpolate(Config.secondaryColor, trans);
                graphicsContext.setStroke(new Color(color.getRed(), color.getGreen(), color.getBlue(), 1));
                graphicsContext.strokeRect(x * Config.pixelsPerGridUnit - plus, y * Config.pixelsPerGridUnit - plus, image.getWidth() + plus * 2, image.getHeight() + plus * 2 - Config.pixelsPerGridUnit);
            }
            else
            {
                Color color = Config.complementColorDark.interpolate(Config.secondaryColorDark, trans);
                graphicsContext.setStroke(new Color(color.getRed(), color.getGreen(), color.getBlue(), 1));
                graphicsContext.strokeRect(x * Config.pixelsPerGridUnit - plus, (y + 1) * Config.pixelsPerGridUnit - plus, image.getWidth() + plus * 2, image.getHeight() + plus * 2 - Config.pixelsPerGridUnit);
            }
        }

        if(imageLayer == ImageLayer.BOTTOM) graphicsContext.drawImage(image, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, image.getWidth(), image.getHeight());

        //graphicsContext.setFill(Color.GRAY);
        //graphicsContext.fillRect((int) ((x + pivot.getX()) * Config.pixelsPerGridUnit), (int) ((y + pivot.getY()) * Config.pixelsPerGridUnit), 10, 10);

        graphicsContext.restore(); // back to original state (before rotation)
    }

    /**
     * Dreht das Sprite um den gegebenen Winkel am Pivot-Punkt.
     *
     * @param gc         Der GraphicsContext, auf dem rotiert wird.
     * @param angle      Die Grad-Zahl, um die rotiert wird.
     * @param pivotPoint Der Punkt, um den rotiert werden soll.
     */
    private void rotate(GraphicsContext gc, double angle, Vector2 pivotPoint)
    {
        Rotate r = new Rotate(angle, pivotPoint.getX(), pivotPoint.getY());
        gc.transform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}