package sep.fimball.view.general;

import sep.fimball.general.data.Vector2;

import static sep.fimball.general.data.DesignConfig.PIXELS_PER_GRID_UNIT;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class ViewUtil
{
    /**
     * Rechnet die gegebene Position auf dem Canvas auf die zugehörige Grid-Position um.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     * @param canvasPixel    Die Position auf dem Canvas.
     * @param canvasSize     Die Größe des Canvas.
     * @return Die Position auf dem Grid.
     */
    public static Vector2 canvasPixelToGridPos(Vector2 cameraPosition, double cameraZoom, Vector2 canvasPixel, Vector2 canvasSize)
    {
        return canvasPixel.minus(canvasSize.scale(0.5)).scale(1 / (PIXELS_PER_GRID_UNIT * cameraZoom)).plus(cameraPosition);
    }

    /**
     * Rechnet die gegebene Position auf dem Grid auf die zugehörige Position im Canvas um.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom Der Zoom der Kamera.
     * @param gridPos Die Position auf dem Grid.
     * @param canvasSize Die Größe des Canvas.
     * @return Die Position auf dem Canvas.
     */
    public static Vector2 gridToCanvasPixelPos(Vector2 cameraPosition, double cameraZoom, Vector2 gridPos, Vector2 canvasSize)
    {
        return gridPos.minus(cameraPosition).scale(PIXELS_PER_GRID_UNIT * cameraZoom).plus(canvasSize.scale(0.5));
    }
}
