package sep.fimball.viewmodel.pinballcanvas;

import javafx.scene.image.WritableImage;

/**
 * Der ViewScreenshotCreater kann ein Bild von der View erstellen.
 */
public interface ViewScreenshotCreater
{
    /**
     * Erstellt ein Bild von der View.
     *
     * @return Ein Bild von der View.
     */
    WritableImage drawToImage();
}
