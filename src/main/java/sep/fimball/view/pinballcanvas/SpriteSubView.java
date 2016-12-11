package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.DesignConfig;
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
    final double BORDER_BLINK_RATE = 1000.0;
    /**
     * Die Position des Sprites.
     */
    private ObjectProperty<Vector2> positionProperty;

    /**
     * Das zur SpriteSubView gehörende SpriteSubViewModel.
     */
    private SpriteSubViewModel viewModel;

    /**
     * Der ImageCache des Programms.
     */
    private ImageCache imageCache;

    /**
     * Erzeugt eine neue SpriteSubView mit zugehörigem SpriteSubViewModel und
     * bindet sich an dieses.
     *
     * @param viewModel  Das zu setzende SpriteSubViewModel.
     * @param imageCache Der ImageCache.
     */
    SpriteSubView(SpriteSubViewModel viewModel, ImageCache imageCache)
    {
        this.viewModel = viewModel;
        this.imageCache = imageCache;

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
    void draw(GraphicsContext graphicsContext, ImageLayer imageLayer, boolean drawOnlyElements)
    {
        ElementImageViewModel elementImage = viewModel.animationFramePathProperty().get();
        double rotationRest = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());
        Image image = imageCache.getImage(elementImage.getImagePath(imageLayer, (int) viewModel.rotationProperty().get()));
        Vector2 position = positionProperty.get();
        Vector2 size = new Vector2(image.getWidth(), image.getHeight());

        graphicsContext.save();
        setupDrawLocation(graphicsContext, rotationRest);

        if (viewModel.scaleProperty().get() != 1)
        {
            double scale = viewModel.scaleProperty().get();
            Vector2 oldSize = size;
            size = size.scale(scale);
            position = position.plus(oldSize.minus(size).scale(0.5).scale(1.0 / Config.pixelsPerGridUnit));
        }

        if (imageLayer == ImageLayer.TOP)
        {
            drawImage(graphicsContext, image, position, size);
        }
        if (viewModel.isSelectedProperty().get() && !drawOnlyElements)
        {
            drawBorder(graphicsContext, imageLayer, position, size);
        }
        if (imageLayer == ImageLayer.BOTTOM)
        {
            drawImage(graphicsContext, image, position, size);
        }
        graphicsContext.restore();
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

    /**
     * Zeichnet das Bild auf den angegebenen GraphicsContext.
     *
     * @param graphicsContext Der GraphicsContext, auf den das Bild gezeichnet werden soll.
     * @param image           Das zu zeichnende Bild.
     */
    private void drawImage(GraphicsContext graphicsContext, Image image, Vector2 position, Vector2 size)
    {
        double x = (position.getX() - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit;
        double y = (position.getY() - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit;
        double w = size.getX() + Config.antiGraphicStripesExtraSize * 2 * Config.pixelsPerGridUnit;
        double h = size.getY() + Config.antiGraphicStripesExtraSize * 2 * Config.pixelsPerGridUnit;

        graphicsContext.drawImage(image, x, y, w, h);
    }

    /**
     * Zeichnet einen Rahmen um {@code image}.
     *
     * @param graphicsContext Der GraphicsContext, in dem der Rahmen gezeichnet werden soll.
     * @param imageLayer      Bestimmt, ob der Rahmen auf der oberen oder unteren Bildebene gezeichnet wird.
     */
    private void drawBorder(GraphicsContext graphicsContext, ImageLayer imageLayer, Vector2 position, Vector2 size)
    {
        double borderWidth = Config.pixelsPerGridUnit * 0.25;
        double borderOffset = 0.5 * borderWidth;
        double effectTime = (System.currentTimeMillis() % BORDER_BLINK_RATE) / BORDER_BLINK_RATE;
        double effectValue = -2 * effectTime * (effectTime - 1);

        graphicsContext.setLineWidth(borderWidth);

        if (imageLayer == ImageLayer.TOP)
        {
            Color color = DesignConfig.complementColor.interpolate(DesignConfig.secondaryColor, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(position.getX() * Config.pixelsPerGridUnit - borderOffset, position.getY() * Config.pixelsPerGridUnit - borderOffset, size.getX() + borderOffset * 2, size.getY() + borderOffset * 2 - (viewModel.getElementHeight() * Config.pixelsPerGridUnit));
        }
        else
        {
            Color color = DesignConfig.complementColorDark.interpolate(DesignConfig.secondaryColorDark, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(position.getX() * Config.pixelsPerGridUnit - borderOffset, (position.getY() + viewModel.getElementHeight()) * Config.pixelsPerGridUnit - borderOffset, size.getX() + borderOffset * 2, size.getY() + borderOffset * 2 - (viewModel.getElementHeight() * Config.pixelsPerGridUnit));
        }
    }
}