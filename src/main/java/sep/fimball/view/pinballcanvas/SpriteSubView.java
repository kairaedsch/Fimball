package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import sep.fimball.general.data.*;
import sep.fimball.general.util.RegionHashConverter;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

import java.util.List;

/**
 * Die SpriteSubView ist für das Zeichnen eines Flipperautomaten-Elements
 * zuständig.
 */
public class SpriteSubView
{
    /**
     * Das zur SpriteSubView gehörende SpriteSubViewModel.
     */
    private SpriteSubViewModel viewModel;

    private ImageCache imageCache;

    private double rotationRest;

    private Vector2 position;

    private Vector2 size;

    private Image defaultImageTop;

    private Image defaultImageBottom;

    private ObjectProperty<List<Long>> regionHashes;

    private RectangleDoubleByPoints drawArea;

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
        this.regionHashes = new SimpleObjectProperty<>();

        viewModel.positionProperty().addListener((observable, oldValue, newValue) -> calculateValues());
        viewModel.rotationProperty().addListener((observable, oldValue, newValue) -> calculateValues());
        viewModel.scaleProperty().addListener((observable, oldValue, newValue) -> calculateValues());
        calculateValues();
    }

    private void calculateValues()
    {
        defaultImageTop = imageCache.getImage(viewModel.animationFramePathProperty().get().getImagePath(ImageLayer.TOP, (int) viewModel.rotationProperty().get()));
        defaultImageBottom = imageCache.getImage(viewModel.animationFramePathProperty().get().getImagePath(ImageLayer.BOTTOM, (int) viewModel.rotationProperty().get()));

        ElementImageViewModel elementImage = viewModel.animationFramePathProperty().get();
        rotationRest = elementImage.getRestRotation((int) viewModel.rotationProperty().get()) + (viewModel.rotationProperty().get() - (int) viewModel.rotationProperty().get());

        size = new Vector2(defaultImageTop.getWidth(), defaultImageTop.getHeight()).scale(viewModel.scaleProperty().get());

        position = viewModel.positionProperty().get().scale(DesignConfig.PIXELS_PER_GRID_UNIT).plus(size.scale(1 / viewModel.scaleProperty().get()).minus(size).scale(0.5));

        // TODO ugly - repeated code
        int picRotate = (int) (viewModel.rotationProperty().get() - rotationRest) % 360;

        // TODO ugly - repeated code
        Vector2 localCoordinates;
        if (viewModel.getLocalCoordinates().containsKey(picRotate))
        {
            localCoordinates = viewModel.getLocalCoordinates().get(picRotate);
        }
        else
        {
            localCoordinates = new Vector2();
        }

        Vector2 min = position.scale(1.0 / DesignConfig.PIXELS_PER_GRID_UNIT).plus(localCoordinates);
        Vector2 max = size.plus(position).scale(1.0 / DesignConfig.PIXELS_PER_GRID_UNIT).plus(localCoordinates);

        regionHashes.set(RegionHashConverter.gameAreaToRegionHashes(min, max, Config.DRAW_REGION_SIZE));

        // TODO ugly
        drawArea = new RectangleDoubleByPoints(position.scale(1.0 / DesignConfig.PIXELS_PER_GRID_UNIT).plus(localCoordinates), size.plus(position).scale(1.0 / DesignConfig.PIXELS_PER_GRID_UNIT).plus(localCoordinates));
    }

    /**
     * Zeichnet sich auf das übergebene GraphicsContext-Objekt.
     *
     * @param graphicsContext Der GraphicsContext, auf dem die View sich
     *                        zeichnen soll.
     * @param imageLayer      Gibt an, ob das Sprite sein Top- oder Bottom-Image
     *                        zeichnen soll.
     * @param drawMode        Der Modus in dem gezeichnet werden soll.
     */
    boolean draw(RectangleDoubleByPoints canvasRectangle, GraphicsContext graphicsContext, ImageLayer imageLayer, DrawMode drawMode)
    {
        if(!canvasRectangle.intersectsWith(drawArea)) return false;
        graphicsContext.save();

        setupDrawLocation(graphicsContext, rotationRest);

        Image image;
        ElementImageViewModel elementImageViewModel = viewModel.animationFramePathProperty().get();
        if (elementImageViewModel.isAnimating())
        {
            image = imageCache.getImage(elementImageViewModel.getImagePath(imageLayer, (int) viewModel.rotationProperty().get(), System.currentTimeMillis()));
        }
        else
        {
            image = imageLayer == ImageLayer.TOP ? defaultImageTop : defaultImageBottom;
        }

        if (imageLayer == ImageLayer.TOP)
        {
            drawImage(graphicsContext, image, drawMode, position, size);
        }
        if (viewModel.selectedProperty().get() && drawMode == DrawMode.EDITOR)
        {
            drawImageBorder(graphicsContext, imageLayer, position, size);
        }
        if (imageLayer == ImageLayer.BOTTOM)
        {
            drawImage(graphicsContext, image, drawMode, position, size);
        }
        graphicsContext.restore();

        return true;
    }

    /**
     * Bereitet das GraphicsContext für das Zeichnen vor.
     *
     * @param graphicsContext Der GraphicsContext, auf den das Bild gezeichnet werden soll.
     * @param rotation        Der Winkel, um den das Bild vor der Zeichnung gedreht werden soll.
     */
    private void setupDrawLocation(GraphicsContext graphicsContext, double rotation)
    {
        Vector2 pivot = viewModel.pivotPointProperty().get();
        int picRotate = (int) (viewModel.rotationProperty().get() - rotation) % 360;

        if (viewModel.getLocalCoordinates().containsKey(picRotate))
        {
            Vector2 localCoordinates = viewModel.getLocalCoordinates().get(picRotate);
            graphicsContext.translate(localCoordinates.getX() * DesignConfig.PIXELS_PER_GRID_UNIT, localCoordinates.getY() * DesignConfig.PIXELS_PER_GRID_UNIT);
            pivot = pivot.plus(localCoordinates.scale(-1));
        }

        if (rotation != 0)
        {
            rotate(graphicsContext, rotation, pivot.scale(DesignConfig.PIXELS_PER_GRID_UNIT).plus(position));
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
     * @param drawMode        Der Modus in dem gezeichnet werden soll.
     * @param position        Die Position an die das Bild gezeichnet werden soll.
     * @param size            Die Größe in der die Bilder gezeichnet werden sollen.
     */
    private void drawImage(GraphicsContext graphicsContext, Image image, DrawMode drawMode, Vector2 position, Vector2 size)
    {
        graphicsContext.save();
        graphicsContext.setGlobalAlpha(drawMode == DrawMode.SCREENSHOT ? 1 : viewModel.visibilityProperty().get());

        double x = position.getX() - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * DesignConfig.PIXELS_PER_GRID_UNIT;
        double y = position.getY() - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * DesignConfig.PIXELS_PER_GRID_UNIT;
        double w = size.getX() + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT;
        double h = size.getY() + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT;

        graphicsContext.drawImage(image, x, y, w, h);
        graphicsContext.restore();
    }

    /**
     * Zeichnet einen Rahmen um {@code image}.
     *
     * @param graphicsContext Der GraphicsContext, in dem der Rahmen gezeichnet werden soll.
     * @param imageLayer      Bestimmt, ob der Rahmen für ein Bild der oberen/unteren Bildebene gezeichnet werden soll.
     * @param position        Die Position des Bildes.
     * @param size            Die Größe des Bildes.
     */
    private void drawImageBorder(GraphicsContext graphicsContext, ImageLayer imageLayer, Vector2 position, Vector2 size)
    {
        double borderWidth = DesignConfig.PIXELS_PER_GRID_UNIT * 0.25;
        double borderOffset = 0.5 * borderWidth;
        double effectTime = (System.currentTimeMillis() % DesignConfig.BORDER_BLINK_RATE) / DesignConfig.BORDER_BLINK_RATE;
        double effectValue = -2 * effectTime * (effectTime - 1);

        graphicsContext.setLineWidth(borderWidth);

        if (imageLayer == ImageLayer.TOP)
        {
            Color color = DesignConfig.COMPLEMENT_COLOR.interpolate(DesignConfig.SECONDARY_COLOR, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(position.getX() - borderOffset, position.getY() - borderOffset, size.getX() + borderOffset * 2, size.getY() + borderOffset * 2 - (viewModel.getElementHeight() * DesignConfig.PIXELS_PER_GRID_UNIT));
        }
        else
        {
            Color color = DesignConfig.COMPLEMENT_COLOR_DARK.interpolate(DesignConfig.SECONDARY_COLOR_DARK, effectValue);
            graphicsContext.setStroke(color);
            graphicsContext.strokeRect(position.getX() - borderOffset, position.getY() + viewModel.getElementHeight() * DesignConfig.PIXELS_PER_GRID_UNIT - borderOffset, size.getX() + borderOffset * 2, size.getY() + borderOffset * 2 - (viewModel.getElementHeight() * DesignConfig.PIXELS_PER_GRID_UNIT));
        }
    }

    public ObjectProperty<List<Long>> regionHashesProperty()
    {
        return regionHashes;
    }

    /**
     * Gibt die Reihenfolge beim Zeichnen dieses SpriteSubView zurück.
     *
     * @return Die Reihenfolge beim Zeichnen.
     */
    public int getDrawOrder()
    {
        return viewModel.drawOrderProperty().get();
    }

    public Vector2 getPosition()
    {
        return position;
    }
}