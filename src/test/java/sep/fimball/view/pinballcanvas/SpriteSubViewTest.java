package sep.fimball.view.pinballcanvas;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.mockito.Mock;
import org.mockito.Mockito;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

import static org.mockito.ArgumentMatchers.anyString;

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
    @Mock
    ImageCache imageCache;
    @Mock
    Image image;

    public void drawTest() {
        ReadOnlyDoubleProperty rotation = new SimpleDoubleProperty();
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(rotation);
        Mockito.when(ImageCache.getInstance().getImage(anyString())).thenReturn(image);

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.BOTTOM);
    }
}
