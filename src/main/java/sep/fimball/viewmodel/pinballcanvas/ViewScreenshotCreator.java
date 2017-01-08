package sep.fimball.viewmodel.pinballcanvas;

import javafx.scene.image.WritableImage;

/**
 * Der ViewScreenshotCreator kann ein Bild von der View erstellen.
 */
public interface ViewScreenshotCreator
{
    /**
     * Gibt ein Bild des aktuellen Zustands der View zurück.
     *
     * @return Ein Bild der View.
     */
    WritableImage getScreenshot();
}
