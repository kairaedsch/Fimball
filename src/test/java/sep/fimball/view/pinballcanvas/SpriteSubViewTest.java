package sep.fimball.view.pinballcanvas;

import javafx.scene.canvas.GraphicsContext;
import org.mockito.Mock;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

/**
 * Created by marc on 04.12.16.
 */
public class SpriteSubViewTest
{
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();
    @Mock
    SpriteSubViewModel spriteSubViewModelMock;
    @Mock
    GraphicsContext graphicsContextMock;

    public void drawTest() {


        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.BOTTOM.TOP);
    }
}
