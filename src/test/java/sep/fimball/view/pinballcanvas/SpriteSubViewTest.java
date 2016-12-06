package sep.fimball.view.pinballcanvas;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.*;

/**
 * Tests the drawing functionality of {@link sep.fimball.view.pinballcanvas.SpriteSubView}.
 */
public class SpriteSubViewTest
{
    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    private final double ROTATION = 1;
    private Vector2 position = new Vector2(10, 10);
    private Vector2 pivot = new Vector2(2, 2);
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
     * Tests whether the image is drawn correctly on the GraphicsContext in the given scenario or not.
     */
    @Test(timeout = 10000)
    public void drawTest() {
        MockitoAnnotations.initMocks(this);
        double[] translationArguments = new double[2];
        double[] transformationArguments = new double[6];
        Image[] drawnImages = new Image[1];
        double[] drawImageDoubleArguments = new double[4];
        double[] borderArguments = new double[5];
        Color[] borderColor = new Color[1];
        localCoords.put(0, new Vector2(1, 1));

        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImage));
        Mockito.when(elementImage.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCache.getImage(any())).thenReturn(image);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoords()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(3));
        Mockito.when(image.getWidth()).thenReturn(10.0);
        Mockito.when(image.getHeight()).thenReturn(10.0);
        Mockito.when(spriteSubViewModelMock.isSelectedProperty()).thenReturn(new SimpleBooleanProperty(true));

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                translationArguments[0] = invocation.getArgument(0);
                translationArguments[1] = invocation.getArgument(1);
                return null;
            }
        }).when(graphicsContextMock).translate(anyDouble(), anyDouble());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                for (int i = 0; i < 6; i++)
                {
                    transformationArguments[i] = invocation.getArgument(i);
                }
                return null;
            }
        }).when(graphicsContextMock).transform(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                drawnImages[0] = invocation.getArgument(0);
                for (int i = 1; i < 5; i++)
                {
                    drawImageDoubleArguments[i - 1] = invocation.getArgument(i);
                }
                return null;
            }
        }).when(graphicsContextMock).drawImage(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                borderArguments[0] = invocation.getArgument(0);
                return null;
            }
        }).when(graphicsContextMock).setLineWidth(anyDouble());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                borderColor[0] = invocation.getArgument(0);
                return null;
            }
        }).when(graphicsContextMock).setStroke(any());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                for (int i = 0; i < 4; i++)
                {
                    borderArguments[i + 1] = invocation.getArgument(i);
                }
                return null;
            }
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock);
        spriteSubView.draw(graphicsContextMock, ImageLayer.BOTTOM, imageCache);

        //TODO insert assertions
    }
}
