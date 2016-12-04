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
 * Die SpriteSubView ist für das Zeichnen eines Flipperautomaten-Elements
 * zuständig.
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
     * , Erzeugt eine neue SpriteSubView mit zugehörigem SpriteSubViewModel und
     * bindet sich an dieses.
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
     * @param graphicsContext Der GraphicsContext, auf dem die View sich
     *                        zeichnen soll.
     * @param imageLayer      Gibt an, ob das Sprite sein Top- oder Bottom-Image
     *                        zeichnen soll.
     */
    void draw(GraphicsContext graphicsContext, ImageLayer imageLayer)
    {
        ElementImageViewModel elementImage = viewModel.animationFramePathProperty().get();

        //Berechne den double Rest der bei mod rotationAccuracy bleibt
        double rotationRest = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());
        Image image = ImageCache.getInstance().getImage(elementImage.getImagePath(imageLayer, (int) viewModel.rotationProperty().get()));

        graphicsContext.save();

        setupDrawLocation(graphicsContext, rotationRest);

        if (imageLayer == ImageLayer.TOP)
        {
            drawImage(graphicsContext, imageLayer, image);
        }
        if (viewModel.isSelectedProperty().get())
        {
            drawBorder(graphicsContext, imageLayer, image);
        }
        if (imageLayer == ImageLayer.BOTTOM)
        {
            drawImage(graphicsContext, imageLayer, image);
        }

        graphicsContext.restore();
    }

    /**
     * Zeichnet das Bild auf den angegebenen GraphicsContext.
     *
     * @param graphicsContext Der GraphicsContext, auf den das Bild gezeichnet werden soll.
     * @param imageLayer      Bestimmt, ob das Bild sich auf der oberen oder unteren Bildebene befinden soll.
     * @param image           Das zu zeichnende Bild.
     */
    private void drawImage(GraphicsContext graphicsContext, ImageLayer imageLayer, Image image)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        if (imageLayer == ImageLayer.TOP)
        {
            graphicsContext.drawImage(image, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, image.getWidth(), image.getHeight());
        }
        else
        {
            graphicsContext.drawImage(image, x * Config.pixelsPerGridUnit, y * Config.pixelsPerGridUnit, image.getWidth(), image.getHeight());
        }
    }

    /**
     * Bereitet das GraphicsContext für das Zeichnen vor.
     *
     * @param graphicsContext Der GraphicsContext, auf den das Bild gezeichnet werden soll.
     * @param rotation        Der Winkel, um den das Bild vor der Zeichnung gedreht werden soll.
     */
    private void setupDrawLocation(GraphicsContext graphicsContext, double rotation)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();
        Vector2 pivot = viewModel.pivotPointProperty().get().clone();
        int picRotate = (int) (viewModel.rotationProperty().get() - rotation) % 360;

        if (viewModel.getLocalCoords().containsKey(picRotate))
        {
            Vector2 localCoords = viewModel.getLocalCoords().get(picRotate);
            graphicsContext.translate(localCoords.getX() * Config.pixelsPerGridUnit, localCoords.getY() * Config.pixelsPerGridUnit);
            pivot = pivot.plus(localCoords.scale(-1));
        }

        if (rotation != 0)
        {
            rotate(graphicsContext, rotation, pivot.plus(new Vector2(x, y)).scale(Config.pixelsPerGridUnit));
        }
    }

    /**
     * Zeichnet einen Rahmen um {@code image}.
     *
     * @param graphicsContext Der GraphicsContext, in dem der Rahmen gezeichnet werden soll.
     * @param imageLayer      Bestimmt, ob der Rahmen auf der oberen oder unteren Bildebene gezeichnet wird.
     * @param image           Das Bild, das umrahmt werden soll.
     */
    private void drawBorder(GraphicsContext graphicsContext, ImageLayer imageLayer, Image image)
    {
        double x = positionProperty.get().getX();
        double y = positionProperty.get().getY();

        final double borderBlinkRate = 1000.0;
        double borderWidth = Config.pixelsPerGridUnit * 0.25;
        double borderOffset = 0.5 * borderWidth;

        graphicsContext.setLineWidth(borderWidth);
        double effectTime = (System.currentTimeMillis() % borderBlinkRate) / borderBlinkRate;
        double effectValue = -2 * effectTime * (effectTime - 1);

        if (imageLayer == ImageLayer.TOP)
        {
            Color color = Config.complementColor.interpolate(Config.secondaryColor, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(x * Config.pixelsPerGridUnit - borderOffset, y * Config.pixelsPerGridUnit - borderOffset, image.getWidth() + borderOffset * 2, image.getHeight() + borderOffset * 2 - (viewModel.getElementHeight() * Config.pixelsPerGridUnit));
        }
        else
        {
            Color color = Config.complementColorDark.interpolate(Config.secondaryColorDark, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(x * Config.pixelsPerGridUnit - borderOffset, (y + viewModel.getElementHeight()) * Config.pixelsPerGridUnit - borderOffset, image.getWidth() + borderOffset * 2, image.getHeight() + borderOffset * 2 - (viewModel.getElementHeight() * Config.pixelsPerGridUnit));
        }
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