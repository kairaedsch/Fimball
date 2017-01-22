package sep.fimball.view.general;

import sep.fimball.general.data.Vector2;

import static sep.fimball.general.data.DesignConfig.PIXELS_PER_GRID_UNIT;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class ViewUtil
{
    /**
     * Rechnet die durch die {@code x} und {@code y} gegebene Position auf dem Canvas auf die zugehörige Grid-Position um.
     *
     * @param cameraPosition Die Position der Kamera.
     * @param cameraZoom     Der Zoom der Kamera.
     * @param pos            Die Position auf dem Canvas.
     * @param canvasSize     Die Größe des Canvas.
     * @return Die Position auf dem Grid.
     */
    public static Vector2 canvasPosToGridPos(Vector2 cameraPosition, double cameraZoom, Vector2 pos, Vector2 canvasSize)
    {
        Vector2 posToMiddle = pos.minus(canvasSize.scale(0.5));
        return posToMiddle.scale(1 / (PIXELS_PER_GRID_UNIT * cameraZoom)).plus(cameraPosition);
    }
}
