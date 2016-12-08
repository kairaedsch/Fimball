package sep.fimball.view.pinballcanvas;

import org.junit.Rule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

/**
 * Testet, ob in der Klasse {@link sep.fimball.view.pinballcanvas.PinballCanvasSubView} das ViewModel ordnungsgemäß geändert werden kann.
 */
public class PinballCanvasSubViewTest
{
    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    /**
     * Testet, ob bei Tausch des ViewModels alle Bindungen richtig hergestellt werden.
     */
    @Test(timeout = 10000)
    public void testSetViewModel() {

    }
}
