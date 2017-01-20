package sep.fimball.model.editor;

import javafx.scene.image.WritableImage;
import sep.fimball.viewmodel.pinballcanvas.ViewScreenshotCreator;


/**
 * Diese Klasse stellt einen ViewScreenshotCreator für Testzwecke zur Verfügung.
 */
public class TestScreenshotCreator implements ViewScreenshotCreator
{
    /**
     * {@inheritDoc}
     */
    @Override
    public WritableImage getScreenshot()
    {
        return new WritableImage(1, 1);
    }
}
