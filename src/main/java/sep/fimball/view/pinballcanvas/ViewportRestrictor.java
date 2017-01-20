package sep.fimball.view.pinballcanvas;

import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse wird verwendet um die Größe einer Zeichenfläche zwischen einem Minimum und Maximum zu halten.
 */
public class ViewportRestrictor
{
    /**
     * Die minimale Größe der Ansicht.
     */
    private Vector2 minDimensions;

    /**
     * Die maximale Größe der Ansicht.
     */
    private Vector2 maxDimensions;

    /**
     * Der Standard-Zoom.
     */
    private double defaultZoom;

    /**
     * Erzeugt einen neuen ViewportRestrictor um die Größe einer Zeichenfläche zu beschränken.
     *
     * @param defaultZoom Der Standard Zoom der Zeichenfläche.
     */
    public ViewportRestrictor(double defaultZoom)
    {
        this.minDimensions = new Vector2(DesignConfig.MINIMUM_PREVIEW_WIDTH, DesignConfig.MINIMUM_PREVIEW_HEIGHT);
        this.maxDimensions = new Vector2(DesignConfig.MAXIMUM_PREVIEW_WIDTH, DesignConfig.MAXIMUM_PREVIEW_HEIGHT);
        this.defaultZoom = defaultZoom;
    }

    /**
     * Beschränkt eine Zeichenfläche so dass sie zwischen Minimum und Maximum liegt.
     *
     * @param rectangle Die Zeichenfläche welche eventuell beschränkt werden muss.
     * @return Eine Zeichenfläche welche zwischen Minimum und Maximum liegt.
     */
    public RestrictedViewport restrictRectangle(RectangleDouble rectangle)
    {
        if (rectangle.getWidth() < minDimensions.getX() || rectangle.getHeight() < minDimensions.getY())
        {
            return restrictTooSmallRectangle(rectangle);
        }
        if (rectangle.getWidth() > maxDimensions.getX() || rectangle.getHeight() > maxDimensions.getY())
        {
            return restrictTooLargeRectangle(rectangle);
        }
        return new RestrictedViewport(rectangle, defaultZoom);
    }

    /**
     * Beschränkt eine zu kleine Zeichenfläche auf das Minimum.
     *
     * @param rectangle Die Größe der zu kleinen Zeichenfläche.
     * @return Eine auf das Minimum beschränkte Zeichenfläche.
     */
    private RestrictedViewport restrictTooSmallRectangle(RectangleDouble rectangle)
    {
        double newOriginX = rectangle.getOrigin().getX();
        double newOriginY = rectangle.getOrigin().getY();
        double newWidth = rectangle.getWidth();
        double newHeight = rectangle.getHeight();

        if (rectangle.getWidth() < minDimensions.getX())
        {
            double scaleX = (minDimensions.getX() / rectangle.getWidth());
            newWidth = rectangle.getWidth() * scaleX;
            newOriginX = rectangle.getOrigin().getX() - (newWidth - rectangle.getWidth()) / 2.0;
        }

        if (rectangle.getHeight() < minDimensions.getY())
        {
            double scaleY = (minDimensions.getY() / rectangle.getHeight());
            newHeight = rectangle.getHeight() * scaleY;
            newOriginY = rectangle.getOrigin().getY() - (newHeight - rectangle.getHeight()) / 2.0;
        }
        RectangleDouble newRectangle = new RectangleDouble(new Vector2(newOriginX, newOriginY), newWidth, newHeight);
        return new RestrictedViewport(newRectangle, defaultZoom);
    }

    /**
     * Beschränkt eine Zeichenfläche welche zu groß ist auf das Maximum.
     *
     * @param rectangle Die Größe der zu großen Zeichenfläche.
     * @return Eine auf das Maximum beschränkte Zeichenfläche.
     */
    private RestrictedViewport restrictTooLargeRectangle(RectangleDouble rectangle)
    {
        double scaleX = (maxDimensions.getX() / rectangle.getWidth());
        double scaleY = (maxDimensions.getY() / rectangle.getHeight());
        double cameraScale = Math.min(scaleX, scaleY);
        double newBorder = Math.max(rectangle.getWidth() * cameraScale, rectangle.getHeight() * cameraScale);
        double newOriginX = rectangle.getOrigin().getX() + (rectangle.getWidth() - newBorder) / 2.0;
        double newOriginY = rectangle.getOrigin().getY() + (rectangle.getHeight() - newBorder) / 2.0;
        RectangleDouble newRectangle = new RectangleDouble(new Vector2(newOriginX, newOriginY), newBorder, newBorder);
        return new RestrictedViewport(newRectangle, cameraScale);
    }

    /**
     * Diese Klasse stellt eine beschränkte Zeichenfläche welche durch Größe und Zoom gegeben ist dar.
     */
    public static class RestrictedViewport
    {
        /**
         * Die beschränkte Zeichenfläche.
         */
        private RectangleDouble restrictedRectangle;

        /**
         * Der Zoom der beschränkten Zeichenfläche.
         */
        private double restrictedCameraScale;

        /**
         * Erzeugt eine neue beschränkte Zeichenfläche.
         *
         * @param restrictedRectangle Die neue Größe der Zeichenfläche.
         * @param restrictedCameraScale Der neue Zoom der Zeichenfläche.
         */
        public RestrictedViewport(RectangleDouble restrictedRectangle, double restrictedCameraScale)
        {
            this.restrictedRectangle = restrictedRectangle;
            this.restrictedCameraScale = restrictedCameraScale;
        }

        /**
         * Gibt die Größe der beschränkten Zeichenfläche zurück.
         *
         * @return Die Größe der beschränkten Zeichenfläche.
         */
        public RectangleDouble getRestrictedRectangle()
        {
            return restrictedRectangle;
        }

        /**
         * Gibt den Zoom der beschränkten Zeichenfläche zurück.
         *
         * @return Der Zoom der beschränkten Zeichenfläche.
         */
        public double getRestrictedCameraScale()
        {
            return restrictedCameraScale;
        }
    }
}
