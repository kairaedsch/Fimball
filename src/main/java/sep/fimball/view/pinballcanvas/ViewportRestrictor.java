package sep.fimball.view.pinballcanvas;

import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;

public class ViewportRestrictor
{
    private Vector2 minDimensions;
    private Vector2 maxDimensions;
    private double defaultZoom;

    public ViewportRestrictor(double defaultZoom)
    {
        this.minDimensions = new Vector2(DesignConfig.MINIMUM_PREVIEW_WIDTH, DesignConfig.MINIMUM_PREVIEW_HEIGHT);
        this.maxDimensions = new Vector2(DesignConfig.MAXIMUM_PREVIEW_WIDTH, DesignConfig.MAXIMUM_PREVIEW_HEIGHT);
        this.defaultZoom = defaultZoom;
    }

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

    public static class RestrictedViewport
    {
        private RectangleDouble restrictedRectangle;
        private double restrictedCameraScale;

        public RestrictedViewport(RectangleDouble restrictedRectangle, double restrictedCameraScale)
        {
            this.restrictedRectangle = restrictedRectangle;
            this.restrictedCameraScale = restrictedCameraScale;
        }

        public RectangleDouble getRestrictedRectangle()
        {
            return restrictedRectangle;
        }

        public double getRestrictedCameraScale()
        {
            return restrictedCameraScale;
        }
    }
}
