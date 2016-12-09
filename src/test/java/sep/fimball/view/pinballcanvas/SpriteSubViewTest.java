package sep.fimball.view.pinballcanvas;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.HashMap;

/**
 * Testet, ob in {@link sep.fimball.view.pinballcanvas.SpriteSubView} wie erwartet gezeichnet wird.
 */
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
    ElementImageViewModel elementImageMock;
    @Mock
    GraphicsContext graphicsContextMock;
    @Mock
    ImageCache imageCacheMock;
    @Mock
    Image imageMock;

    /**
     * Testet das Zeichnen der unteren Bildebene.
     */
    @Test(timeout = 10000)
    public void drawBottomLayerTest() {
        MockitoAnnotations.initMocks(this);
        double[] translationArguments = new double[2];
        double[] transformationArguments = new double[6];
        Image[] drawnImages = new Image[1];
        double[] drawImageDoubleArguments = new double[4];
        double[] borderArguments = new double[5];
        localCoords.put(0, localCoordinates);

        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImageMock));
        Mockito.when(elementImageMock.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCacheMock.getImage(any())).thenReturn(imageMock);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoords()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(SCALE));
        Mockito.when(imageMock.getWidth()).thenReturn(IMAGE_WIDTH);
        Mockito.when(imageMock.getHeight()).thenReturn(IMAGE_HEIGHT);
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
            for (int i = 0; i < 4; i++)
            {
                borderArguments[i + 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock, imageCacheMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.BOTTOM);

        assertEquals(Config.pixelsPerGridUnit, translationArguments[0], 0.0);
        assertEquals(Config.pixelsPerGridUnit, translationArguments[1], 0.0);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * Config.pixelsPerGridUnit, (pivot.getY() - localCoordinates.getY() + position.getY()) * Config.pixelsPerGridUnit);
        assertEquals(r.getMxx(), transformationArguments[0], 0.0);
        assertEquals(r.getMyx(), transformationArguments[1], 0.0);
        assertEquals(r.getMxy(), transformationArguments[2], 0.0);
        assertEquals(r.getMyy(), transformationArguments[3], 0.0);
        assertEquals(r.getTx(), transformationArguments[4], 0.0);
        assertEquals(r.getTy(), transformationArguments[5], 0.0);
        assertThat(drawnImages[0], equalTo(imageMock));
        assertEquals(drawImageDoubleArguments[0], (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[1], (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[2], IMAGE_WIDTH * SCALE + Config.antiGraphicStripesExtraSize * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[3], IMAGE_HEIGHT * SCALE + Config.antiGraphicStripesExtraSize * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[0], Config.pixelsPerGridUnit * 0.25, 0.0);
        assertEquals(borderArguments[1], (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[2], (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit + ELEMENT_HEIGHT) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[3], IMAGE_WIDTH * SCALE + 0.25 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[4], IMAGE_HEIGHT * SCALE + 0.25 * Config.pixelsPerGridUnit - ELEMENT_HEIGHT * Config.pixelsPerGridUnit, 0.0);
    }

    /**
     * Testet das Zeichnen der oberen Bildebene.
     */
    @Test(timeout = 10000)
    public void drawTopLayerTest() {
        MockitoAnnotations.initMocks(this);
        double[] translationArguments = new double[2];
        double[] transformationArguments = new double[6];
        Image[] drawnImages = new Image[1];
        double[] drawImageDoubleArguments = new double[4];
        double[] borderArguments = new double[5];
        localCoords.put(0, localCoordinates);

        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImageMock));
        Mockito.when(elementImageMock.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCacheMock.getImage(any())).thenReturn(imageMock);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoords()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(SCALE));
        Mockito.when(imageMock.getWidth()).thenReturn(IMAGE_WIDTH);
        Mockito.when(imageMock.getHeight()).thenReturn(IMAGE_HEIGHT);
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
            for (int i = 0; i < 4; i++)
            {
                borderArguments[i + 1] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock, imageCacheMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.TOP);

        assertEquals(Config.pixelsPerGridUnit, translationArguments[0], 0.0);
        assertEquals(Config.pixelsPerGridUnit, translationArguments[1], 0.0);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * Config.pixelsPerGridUnit, (pivot.getY() - localCoordinates.getY() + position.getY()) * Config.pixelsPerGridUnit);
        assertEquals(r.getMxx(), transformationArguments[0], 0.0);
        assertEquals(r.getMyx(), transformationArguments[1], 0.0);
        assertEquals(r.getMxy(), transformationArguments[2], 0.0);
        assertEquals(r.getMyy(), transformationArguments[3], 0.0);
        assertEquals(r.getTx(), transformationArguments[4], 0.0);
        assertEquals(r.getTy(), transformationArguments[5], 0.0);
        assertThat(drawnImages[0], equalTo(imageMock));
        assertEquals(drawImageDoubleArguments[0], (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[1], (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit - Config.antiGraphicStripesExtraSize) * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[2], IMAGE_WIDTH * SCALE + Config.antiGraphicStripesExtraSize * Config.pixelsPerGridUnit, 0.0);
        assertEquals(drawImageDoubleArguments[3], IMAGE_HEIGHT * SCALE + Config.antiGraphicStripesExtraSize * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[0], Config.pixelsPerGridUnit * 0.25, 0.0);
        assertEquals(borderArguments[1], (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[2], (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 /Config.pixelsPerGridUnit) * Config.pixelsPerGridUnit - 0.125 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[3], IMAGE_WIDTH * SCALE + 0.25 * Config.pixelsPerGridUnit, 0.0);
        assertEquals(borderArguments[4], IMAGE_HEIGHT * SCALE + 0.25 * Config.pixelsPerGridUnit - ELEMENT_HEIGHT * Config.pixelsPerGridUnit, 0.0);
    }
}
