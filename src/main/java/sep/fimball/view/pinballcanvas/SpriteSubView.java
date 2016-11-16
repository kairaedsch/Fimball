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
import sep.fimball.model.ElementImage;
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
     * @param elementImage Der Pfad zum Bild.
     */
    private void loadImage(ElementImage elementImage)
    {
        File topFile = new File(elementImage.getImagePath(ImageLayer.TOP, 0));
        topImage = new Image(topFile.toURI().toString());
        File bottomFile = new File(elementImage.getImagePath(ImageLayer.BOTTOM, 0));
        bottomImage = new Image(bottomFile.toURI().toString());
    }

    /**
     * Zeichnet sich auf das übergebene GraphicsContext-Objekt.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die View sich zeichnen soll.
     */
    void draw(GraphicsContext graphicsContext, ImageLayer imageLayer, double zoom)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        graphicsContext.save(); // saves the current state on stack, including the current transform
        rotate(graphicsContext, rotationProperty.doubleValue(), x + bottomImage.getWidth() / 2, y + bottomImage.getHeight() / 2);

        if (imageLayer == ImageLayer.TOP)
        {
            graphicsContext.drawImage(topImage, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, topImage.getWidth() * zoom, topImage.getHeight() * zoom);
        }
        else
        {
            graphicsContext.drawImage(bottomImage, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, topImage.getWidth() * zoom, topImage.getHeight() * zoom);
        }
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