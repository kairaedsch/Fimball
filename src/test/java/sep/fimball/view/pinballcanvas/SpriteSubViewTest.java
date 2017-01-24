package sep.fimball.view.pinballcanvas;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.ElementImageViewModel;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.viewmodel.pinballcanvas.SpriteSubViewModel;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;

/**
 * Testet, ob in {@link sep.fimball.view.pinballcanvas.SpriteSubView} wie erwartet gezeichnet wird. Noch nicht auf die Leistungsoptimierungen angepasst.
 */
@Ignore
public class SpriteSubViewTest
{
    /**
     * Wird benötigt um eine korrekte Ausführung auf dem JavaFX Thread zu garantieren.
     */
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

    private double firstTranslationVectorCoordinate;
    private double secondTranslationVectorCoordinate;
    private double[] transformationArguments = new double[6];
    private Image drawnImage;
    private double firstImagePositionCoordinate;
    private double secondImagePositionCoordinate;
    private double imageWidth;
    private double imageHeight;
    private double borderLineWidth;
    private double firstRectPositionCoordinate;
    private double secondRectPositionCoordinate;
    private double rectWidth;
    private double rectHeight;

    @Mock
    private SpriteSubViewModel spriteSubViewModelMock;
    @Mock
    private ElementImageViewModel elementImageMock;
    @Mock
    private GraphicsContext graphicsContextMock;
    @Mock
    private ImageCache imageCacheMock;
    @Mock
    private Image imageMock;

    /**
     * Testet das Zeichnen der unteren Bildebene.
     */
    @Test (timeout = 10000)
    public void drawBottomLayerTest()
    {
        MockitoAnnotations.initMocks(this);
        localCoords.put(0, localCoordinates);

        setupMockedMethods();

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock, imageCacheMock);
        spriteSubView.draw(new RectangleDoubleByPoints(new Vector2(-100, -100), new Vector2(100, 100)), graphicsContextMock, ImageLayer.BOTTOM, DrawMode.EDITOR);

        assertEquals(firstTranslationVectorCoordinate, DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondTranslationVectorCoordinate, DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * DesignConfig.PIXELS_PER_GRID_UNIT, (pivot.getY() - localCoordinates.getY() + position.getY()) * DesignConfig.PIXELS_PER_GRID_UNIT);
        assertEquals(r.getMxx(), transformationArguments[0], 0.0);
        assertEquals(r.getMyx(), transformationArguments[1], 0.0);
        assertEquals(r.getMxy(), transformationArguments[2], 0.0);
        assertEquals(r.getMyy(), transformationArguments[3], 0.0);
        assertEquals(r.getTx(), transformationArguments[4], 0.0);
        assertEquals(r.getTy(), transformationArguments[5], 0.0);
        assertThat(drawnImage, equalTo(imageMock));
        assertEquals(firstImagePositionCoordinate, (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE) * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondImagePositionCoordinate, (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE) * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(imageWidth, IMAGE_WIDTH * SCALE + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(imageHeight, IMAGE_HEIGHT * SCALE + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(borderLineWidth, DesignConfig.PIXELS_PER_GRID_UNIT * 0.25, 0.0);
        assertEquals(firstRectPositionCoordinate, (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT) * DesignConfig.PIXELS_PER_GRID_UNIT - 0.125 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondRectPositionCoordinate, (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT + ELEMENT_HEIGHT) * DesignConfig.PIXELS_PER_GRID_UNIT - 0.125 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(rectWidth, IMAGE_WIDTH * SCALE + 0.25 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(rectHeight, IMAGE_HEIGHT * SCALE + 0.25 * DesignConfig.PIXELS_PER_GRID_UNIT - ELEMENT_HEIGHT * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
    }

    /**
     * Testet das Zeichnen der oberen Bildebene.
     */
    @Test (timeout = 10000)
    public void drawTopLayerTest()
    {
        MockitoAnnotations.initMocks(this);
        localCoords.put(0, localCoordinates);

        setupMockedMethods();

        SpriteSubView spriteSubView = new SpriteSubView(spriteSubViewModelMock, imageCacheMock);
        spriteSubView.draw(new RectangleDoubleByPoints(new Vector2(-100, -100), new Vector2(100, 100)), graphicsContextMock, ImageLayer.TOP, DrawMode.EDITOR);

        assertEquals(firstTranslationVectorCoordinate, DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondTranslationVectorCoordinate, DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        Rotate r = new Rotate(ROTATION, (pivot.getX() - localCoordinates.getX() + position.getX()) * DesignConfig.PIXELS_PER_GRID_UNIT, (pivot.getY() - localCoordinates.getY() + position.getY()) * DesignConfig.PIXELS_PER_GRID_UNIT);
        assertEquals(r.getMxx(), transformationArguments[0], 0.0);
        assertEquals(r.getMyx(), transformationArguments[1], 0.0);
        assertEquals(r.getMxy(), transformationArguments[2], 0.0);
        assertEquals(r.getMyy(), transformationArguments[3], 0.0);
        assertEquals(r.getTx(), transformationArguments[4], 0.0);
        assertEquals(r.getTy(), transformationArguments[5], 0.0);
        assertThat(drawnImage, equalTo(imageMock));
        assertEquals(firstImagePositionCoordinate, (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE) * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondImagePositionCoordinate, (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT - DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE) * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        // double w = size.getX() + Config.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * Config.PIXELS_PER_GRID_UNIT :: size = IMAGE_WIDTH * SCALE, SpriteSubView:146
        assertEquals(imageWidth, IMAGE_WIDTH * SCALE + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        // double h = size.getY() + Config.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * Config.PIXELS_PER_GRID_UNIT; :: size = IMAGE_HEIGHT * SCALE, SpriteSubView:147
        assertEquals(imageWidth, IMAGE_HEIGHT * SCALE + DesignConfig.ANTI_GRAPHIC_STRIPES_EXTRA_SIZE * 2 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(borderLineWidth, DesignConfig.PIXELS_PER_GRID_UNIT * 0.25, 0.0);
        assertEquals(firstRectPositionCoordinate, (position.getX() + (IMAGE_WIDTH - IMAGE_WIDTH * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT) * DesignConfig.PIXELS_PER_GRID_UNIT - 0.125 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        assertEquals(secondRectPositionCoordinate, (position.getY() + (IMAGE_HEIGHT - IMAGE_HEIGHT * SCALE) * 0.5 / DesignConfig.PIXELS_PER_GRID_UNIT) * DesignConfig.PIXELS_PER_GRID_UNIT - 0.125 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        // size.getX() + borderOffset * 2 :: size = IMAGE_WIDTH * SCALE, borderOffset * 2 = 0.25 * Config.PIXELS_PER_GRID_UNIT, SpriteSubView:171
        assertEquals(rectWidth, IMAGE_WIDTH * SCALE + 0.25 * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
        //size.getY() + borderOffset * 2 - (viewModel.getElementHeight() * Config.PIXELS_PER_GRID_UNIT, SpriteSubView:171
        assertEquals(rectHeight, IMAGE_HEIGHT * SCALE + 0.25 * DesignConfig.PIXELS_PER_GRID_UNIT - ELEMENT_HEIGHT * DesignConfig.PIXELS_PER_GRID_UNIT, 0.0);
    }

    /**
     * Weist den gemockten Methoden Rückgabewerte zu.
     */
    private void setupMockedMethods()
    {
        Mockito.when(spriteSubViewModelMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(spriteSubViewModelMock.animationFramePathProperty()).thenReturn(new SimpleObjectProperty<>(elementImageMock));
        Mockito.when(elementImageMock.getRestRotation(anyInt())).thenReturn(ROTATION);
        Mockito.when(imageCacheMock.getImage(any())).thenReturn(imageMock);
        Mockito.when(spriteSubViewModelMock.pivotPointProperty()).thenReturn(new SimpleObjectProperty<>(pivot));
        Mockito.when(spriteSubViewModelMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(ROTATION));
        Mockito.when(spriteSubViewModelMock.getLocalCoordinates()).thenReturn(localCoords);
        Mockito.when(spriteSubViewModelMock.scaleProperty()).thenReturn(new SimpleDoubleProperty(SCALE));
        Mockito.when(imageMock.getWidth()).thenReturn(IMAGE_WIDTH);
        Mockito.when(imageMock.getHeight()).thenReturn(IMAGE_HEIGHT);
        Mockito.when(spriteSubViewModelMock.selectedProperty()).thenReturn(new SimpleBooleanProperty(true));
        Mockito.when(spriteSubViewModelMock.getElementHeight()).thenReturn(ELEMENT_HEIGHT);
        Mockito.when(spriteSubViewModelMock.visibilityProperty()).thenReturn(new SimpleDoubleProperty(1.0));
        Mockito.when(spriteSubViewModelMock.drawOrderProperty()).thenReturn(new SimpleIntegerProperty(0));

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            firstTranslationVectorCoordinate = invocation.getArgument(0);
            secondTranslationVectorCoordinate = invocation.getArgument(1);
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
            drawnImage = invocation.getArgument(0);
            firstImagePositionCoordinate = invocation.getArgument(1);
            secondImagePositionCoordinate = invocation.getArgument(2);
            imageWidth = invocation.getArgument(3);
            imageHeight = invocation.getArgument(4);
            return null;
        }).when(graphicsContextMock).drawImage(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            borderLineWidth = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setLineWidth(anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            firstRectPositionCoordinate = invocation.getArgument(0);
            secondRectPositionCoordinate = invocation.getArgument(1);
            rectWidth = invocation.getArgument(2);
            rectHeight = invocation.getArgument(3);
            return null;
        }).when(graphicsContextMock).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
}
