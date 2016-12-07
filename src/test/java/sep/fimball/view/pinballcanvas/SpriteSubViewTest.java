package sep.fimball.view.pinballcanvas;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import org.junit.Rule;
import org.junit.Test;
//import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
//import org.powermock.modules.junit4.PowerMockRunner;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests the drawing functionality of {@link sep.fimball.view.pinballcanvas.SpriteSubView}.
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(System.class)
public class SpriteSubViewTest
{
    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    private final double ROTATION = 1;
    private final double SCALE = 3;
    private final double IMAGE_HEIGHT = 10.0;
    private final double IMAGE_WIDTH = 10.0;
    private final double ELEMENT_HEIGHT = 5;
    private Vector2 position = new Vector2(10, 10);
    private Vector2 pivot = new Vector2(2, 2);
    private Vector2 localCoordinates = new Vector2(1, 1);
    private HashMap<Integer, Vector2> localCoords = new HashMap<>();

    @Mock
    SpriteSubViewModel spriteSubViewModelMock;
    @Mock
    ElementImageViewModel elementImage;
    @Mock
    GraphicsContext graphicsContextMock;
    @Mock
    ImageCache imageCache;
    @Mock
    Image image;

    /**
     * Tests whether the bottom layer of the image is drawn correctly on the GraphicsContext in the given scenario or not.
     */
    @Test(timeout = 10000)
    public void drawBottomLayerTest() {
        MockitoAnnotations.initMocks(this);
        double[] translationArguments = new double[2];
        double[] transformationArguments = new double[6];
        Image[] drawnImages = new Image[1];
        double[] drawImageDoubleArguments = new double[4];
        double[] borderArguments = new double[5];
        Color[] borderColor = new Color[1];
        localCoords.put(0, localCoordinates);

        //PowerMockito.spy(System.class);
        //PowerMockito.when(System.currentTimeMillis()).thenReturn(1000l);
        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImage));
        Mockito.when(elementImage.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCache.getImage(any())).thenReturn(image);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoords()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(SCALE));
        Mockito.when(image.getWidth()).thenReturn(IMAGE_WIDTH);
        Mockito.when(image.getHeight()).thenReturn(IMAGE_HEIGHT);
        Mockito.when(spriteSubViewModelMock.isSelectedProperty()).thenReturn(new SimpleBooleanProperty(true));
        Mockito.when(spriteSubViewModelMock.getElementHeight()).thenReturn(ELEMENT_HEIGHT);

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            translationArguments[0] = invocation.getArgument(0);
            translationArguments[1] = invocation.getArgument(1);
            return null;
        }).when(graphicsContextMock).translate(anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 6; i++)
            {
                transformationArguments[i] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).transform(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            drawnImages[0] = invocation.getArgument(0);
            for (int i = 1; i < 5; i++)
            {
                drawImageDoubleArguments[i - 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).drawImage(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            borderArguments[0] = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setLineWidth(anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            borderColor[0] = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setStroke(any());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 4; i++)
            {
                borderArguments[i + 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.BOTTOM, imageCache);

        assertTrue(Config.pixelsPerGridUnit == translationArguments[0]);
        assertTrue(Config.pixelsPerGridUnit == translationArguments[1]);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * Config.pixelsPerGridUnit, (pivot.getY() - localCoordinates.getY() + position.getY()) * Config.pixelsPerGridUnit);
        assertTrue(r.getMxx() == transformationArguments[0]);
        assertTrue(r.getMyx() == transformationArguments[1]);
        assertTrue(r.getMxy() == transformationArguments[2]);
        assertTrue(r.getMyy() == transformationArguments[3]);
        assertTrue(r.getTx() == transformationArguments[4]);
        assertTrue(r.getTy() == transformationArguments[5]);
        assertThat(drawnImages[0], equalTo(image));
        assertTrue(drawImageDoubleArguments[0] == (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit);
        assertTrue(drawImageDoubleArguments[1] == (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit);
        assertTrue(drawImageDoubleArguments[2] == IMAGE_WIDTH * SCALE);
        assertTrue(drawImageDoubleArguments[3] == IMAGE_HEIGHT * SCALE);
        assertTrue(borderArguments[0] == Config.pixelsPerGridUnit * 0.25);
        assertTrue(borderArguments[1] == (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[2] == (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit + ELEMENT_HEIGHT) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[3] == IMAGE_WIDTH * SCALE + 0.25 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[4] == IMAGE_HEIGHT * SCALE + 0.25 * Config.pixelsPerGridUnit - ELEMENT_HEIGHT * Config.pixelsPerGridUnit);
    }

    /**
     * Tests whether the top layer of the image is drawn correctly on the GraphicsContext in the given scenario or not.
     */
    @Test(timeout = 10000)
    public void drawTopLayerTest() {
        MockitoAnnotations.initMocks(this);
        double[] translationArguments = new double[2];
        double[] transformationArguments = new double[6];
        Image[] drawnImages = new Image[1];
        double[] drawImageDoubleArguments = new double[4];
        double[] borderArguments = new double[5];
        Color[] borderColor = new Color[1];
        localCoords.put(0, localCoordinates);

        //PowerMockito.spy(System.class);
        //PowerMockito.when(System.currentTimeMillis()).thenReturn(1000l);
        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImage));
        Mockito.when(elementImage.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCache.getImage(any())).thenReturn(image);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoords()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(SCALE));
        Mockito.when(image.getWidth()).thenReturn(IMAGE_WIDTH);
        Mockito.when(image.getHeight()).thenReturn(IMAGE_HEIGHT);
        Mockito.when(spriteSubViewModelMock.isSelectedProperty()).thenReturn(new SimpleBooleanProperty(true));
        Mockito.when(spriteSubViewModelMock.getElementHeight()).thenReturn(ELEMENT_HEIGHT);

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            translationArguments[0] = invocation.getArgument(0);
            translationArguments[1] = invocation.getArgument(1);
            return null;
        }).when(graphicsContextMock).translate(anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 6; i++)
            {
                transformationArguments[i] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).transform(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            drawnImages[0] = invocation.getArgument(0);
            for (int i = 1; i < 5; i++)
            {
                drawImageDoubleArguments[i - 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).drawImage(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            borderArguments[0] = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setLineWidth(anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            borderColor[0] = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setStroke(any());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 4; i++)
            {
                borderArguments[i + 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.TOP, imageCache);

        assertTrue(Config.pixelsPerGridUnit == translationArguments[0]);
        assertTrue(Config.pixelsPerGridUnit == translationArguments[1]);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * Config.pixelsPerGridUnit, (pivot.getY() - localCoordinates.getY() + position.getY()) * Config.pixelsPerGridUnit);
        assertTrue(r.getMxx() == transformationArguments[0]);
        assertTrue(r.getMyx() == transformationArguments[1]);
        assertTrue(r.getMxy() == transformationArguments[2]);
        assertTrue(r.getMyy() == transformationArguments[3]);
        assertTrue(r.getTx() == transformationArguments[4]);
        assertTrue(r.getTy() == transformationArguments[5]);
        assertThat(drawnImages[0], equalTo(image));
        assertTrue(drawImageDoubleArguments[0] == (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit);
        assertTrue(drawImageDoubleArguments[1] == (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit);
        assertTrue(drawImageDoubleArguments[2] == IMAGE_WIDTH * SCALE);
        assertTrue(drawImageDoubleArguments[3] == IMAGE_HEIGHT * SCALE);
        assertTrue(borderArguments[0] == Config.pixelsPerGridUnit * 0.25);
        assertTrue(borderArguments[1] == (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[2] == (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[3] == IMAGE_WIDTH * SCALE + 0.25 * Config.pixelsPerGridUnit);
        assertTrue(borderArguments[4] == IMAGE_HEIGHT * SCALE + 0.25 * Config.pixelsPerGridUnit - ELEMENT_HEIGHT * Config.pixelsPerGridUnit);
    }
}
