package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;

/**
 * Berechnet einen Bereich des Spielfelds der angezeigt werden soll.
 */
public class Camera
{
    /**
     * Die Zeit, zu der sich dieses Objekt zuletzt neu gezeichnet hat.
     */
    private long lastDraw;

    /**
     * Der Modus in dem gezeichnet werden soll.
     */
    private DrawMode drawMode;

    /**
     * Die Position der Kamera, die den Ausschnitt des Flipperautomaten angibt, der gezeichnet werden soll.
     */
    private SimpleObjectProperty<Vector2> cameraPosition;

    /**
     * Die Stärke des Zooms der Kamera, die die Größe der Flipperautomaten-Elemente bestimmt.
     */
    private SimpleDoubleProperty cameraZoom;

    /**
     * Die Position der Kamera welche der eigentlichen Kamera etwas langsamer folgt um ein angenehmeres Spielgefühl zu erzeugen.
     */
    private Vector2 softCameraPosition;

    /**
     * Der Zoom der Kamera welcher den eigentlichen Zoom etwas langsamer folgt.
     */
    private double softCameraZoom;

    public Camera(DrawMode drawMode, ReadOnlyObjectProperty<Vector2> cameraPositionViewModel, ReadOnlyDoubleProperty cameraZoomViewModel)
    {
        this.drawMode = drawMode;
        lastDraw = System.currentTimeMillis();

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(cameraPositionViewModel);
        softCameraPosition = cameraPosition.get();

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(cameraZoomViewModel);
        softCameraZoom = cameraZoom.get();
    }

    public void updatePosition(double canvasWidth, double canvasHeight)
    {
        double defaultCamFollowSpeed = drawMode == DrawMode.GAME ? 500 : 50;
        double maximumCamFollowSpeed = 1;
        double cameraFollowSpeed = defaultCamFollowSpeed;
        double cameraZoomSpeed = 50;

        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);

        if (canvasWidth > 0 && canvasHeight > 0)
        {
            Vector2 cameraOffset = cameraPosition.get().minus(softCameraPosition);
            double maxmimumBallOffsetX = canvasWidth / 2 / DesignConfig.PIXELS_PER_GRID_UNIT;
            double maxmimumBallOffsetY = canvasHeight / 2 / DesignConfig.PIXELS_PER_GRID_UNIT;
            double xOffsetPercentage = Math.min(Math.abs(cameraOffset.getX()) / maxmimumBallOffsetX, 1);
            double yOffsetPercentage = Math.min(Math.abs(cameraOffset.getY()) / maxmimumBallOffsetY, 1);
            cameraFollowSpeed = defaultCamFollowSpeed - ((defaultCamFollowSpeed - maximumCamFollowSpeed) * Math.max(xOffsetPercentage, yOffsetPercentage));
        }

        double camFollowStep = delta / cameraFollowSpeed;
        camFollowStep = Math.max(Math.min(camFollowStep, 1), 0);

        double camZoomStep = delta / cameraZoomSpeed;
        camZoomStep = Math.max(Math.min(camZoomStep, 1), 0);

        softCameraPosition = softCameraPosition.smoothLerp(cameraPosition.get(), camFollowStep);
        softCameraZoom = softCameraZoom * (1 - camZoomStep) + cameraZoom.get() * camZoomStep;

        lastDraw = currentDraw;
    }

    public Vector2 getSoftCameraPosition()
    {
        return softCameraPosition;
    }

    public double getSoftCameraZoom()
    {
        return softCameraZoom;
    }
}
