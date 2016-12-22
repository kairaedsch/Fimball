package sep.fimball.viewmodel.pinballcanvas;

import javafx.scene.image.WritableImage;

/**
 * Der ViewScreenshotCreator kann ein Bild von der View erstellen.
 */
public interface ViewScreenshotCreator
{
    /**
     * Erstellt ein Bild von der View.
     *
     * @return Ein Bild von der View.
     */
    WritableImage drawToImage();
}
