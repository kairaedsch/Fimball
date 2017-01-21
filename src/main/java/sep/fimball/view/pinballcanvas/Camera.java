package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.DoubleMath;

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

    /**
     * Erzeugt eine neue Kamera die dem Ball langsam folgt.
     *
     * @param cameraPositionViewModel Die eigentliche Position der "harten" Kamera welche dem Ball immer folgt.
     * @param cameraZoomViewModel Der eigentliche Zoom der "harten" Kamera
     */
    public Camera(ReadOnlyObjectProperty<Vector2> cameraPositionViewModel, ReadOnlyDoubleProperty cameraZoomViewModel)
    {
        lastDraw = System.currentTimeMillis();

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(cameraPositionViewModel);
        softCameraPosition = cameraPosition.get();

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(cameraZoomViewModel);
        softCameraZoom = cameraZoom.get();
    }

    /**
     * Aktualisiert die Position dieser Kamera abhängig von der Position und dem Zoom der "harten" Kamera.
     *
     * @param canvasWidth Die Breite des Canvas.
     * @param canvasHeight Die Höhe des Canvas.
     */
    public void updatePosition(double canvasWidth, double canvasHeight)
    {
        long currentDraw = System.currentTimeMillis();
        int delta = (int) (currentDraw - lastDraw);

        if (canvasWidth > 0 && canvasHeight > 0)
        {
            Vector2 targetPosition = cameraPosition.get();
            double maxDistance = Math.min(canvasWidth, canvasHeight) / DesignConfig.PIXELS_PER_GRID_UNIT;
            double distanceToTargetPercent = (softCameraPosition.minus(targetPosition).magnitude() / maxDistance) * cameraZoom.get();
            softCameraPosition = softCameraPosition.lerp(targetPosition, DoubleMath.clamp(0, 1, distanceToTargetPercent * delta * Config.CAMERA_FOLLOW_SPEED));
            softCameraZoom = DoubleMath.lerp(softCameraZoom, cameraZoom.get(), delta * Config.CAMERA_ZOOM_SPEED);
        }

        lastDraw = currentDraw;
    }

    /**
     * Gibt die Position der Kamera welche dem Ball langsam folgt zurück.
     *
     * @return Die Position der Kamera.
     */
    public Vector2 getSoftCameraPosition()
    {
        return softCameraPosition;
    }

    /**
     * Gibt den Zoom der Kamera welche dem Ball langsam folgt zurück.
     *
     * @return Der Zoom der Kamera
     */
    public double getSoftCameraZoom()
    {
        return softCameraZoom;
    }
}
