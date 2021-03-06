package sep.fimball.view.pinballcanvas;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.viewmodel.pinballcanvas.DrawMode;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasGameViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

/**
 * Testet, ob die Klassen {@link sep.fimball.view.pinballcanvas.PinballCanvasSubView} und {@link sep.fimball.view.pinballcanvas.PinballCanvasDrawer} ordnungsgemäß zeichen.
 */
public class PinballCanvasSubViewTest
{
    /**
     * Lässt den Test im JavaFX-Thread ausführen.
     */
    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    private Vector2 cameraPosition = new Vector2(1, 1);

    @Mock
    private PinballCanvasGameViewModel viewModelMock;
    @Mock
    private Canvas canvasMock;
    @Mock
    private Region parentMock;
    @Mock
    private GraphicsContext graphicsContextMock;
    @Mock
    private SpriteSubView spriteSubViewMock;

    private Color backgroundColor;
    private double[] fillRectArguments = new double[4];
    private ArrayList<Color> gridLineColors = new ArrayList<>();
    private ArrayList<Double> lineWidths = new ArrayList<>();
    private ArrayList<Double> strokeLineArguments = new ArrayList<>();

    /**
     * Testet, ob das Neuzeichnen der View nach Änderungen am ViewModel funktioniert.
     *
     * @throws NoSuchFieldException      Falls das Attribut canvas in PinballCanvasSubView nicht existiert.
     * @throws NoSuchMethodException     Falls die Methode redraw() nicht in PinballCanvasSubView existiert.
     * @throws IllegalAccessException    Falls die Methode redraw() nicht aufgerufen werden darf.
     * @throws InvocationTargetException Falls die Methode redraw() eine Exception wirft.
     */
    @Test (timeout = 10000)
    public void testRedraw() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        MockitoAnnotations.initMocks(this);
        Mockito.when(viewModelMock.spriteSubViewModelsProperty()).thenReturn(new SimpleListProperty<>());
        Mockito.when(viewModelMock.cameraPositionProperty()).thenReturn(new SimpleObjectProperty<>(cameraPosition));
        double cameraZoom = 2;
        Mockito.when(viewModelMock.cameraZoomProperty()).thenReturn(new SimpleDoubleProperty(cameraZoom));
        Mockito.when(viewModelMock.getDrawMode()).thenReturn(DrawMode.EDITOR);
        Mockito.when(viewModelMock.getBoundingBox()).thenReturn(null);
        Mockito.when(viewModelMock.cursorProperty()).thenReturn(new SimpleObjectProperty<>());
        Mockito.when(canvasMock.getParent()).thenReturn(parentMock);
        double canvasWidth = 300;
        Mockito.when(canvasMock.widthProperty()).thenReturn(new SimpleDoubleProperty(canvasWidth));
        double canvasHeight = 200;
        Mockito.when(canvasMock.heightProperty()).thenReturn(new SimpleDoubleProperty(canvasHeight));
        Mockito.when(parentMock.widthProperty()).thenReturn(new SimpleDoubleProperty(canvasWidth));
        Mockito.when(parentMock.heightProperty()).thenReturn(new SimpleDoubleProperty(canvasHeight));
        Mockito.when(canvasMock.getHeight()).thenReturn(canvasHeight);
        Mockito.when(canvasMock.getWidth()).thenReturn(canvasWidth);
        Mockito.when(canvasMock.getGraphicsContext2D()).thenReturn(graphicsContextMock);
        Mockito.when(canvasMock.cursorProperty()).thenReturn(new SimpleObjectProperty<>());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            backgroundColor = invocation.getArgument(0);
            return null;
        }).when(graphicsContextMock).setFill(any());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 4; i++)
            {
                fillRectArguments[i] = invocation.getArgument(i);
            }
            return null;
        }).when(graphicsContextMock).fillRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            gridLineColors.add(invocation.getArgument(0));
            return null;
        }).when(graphicsContextMock).setStroke(any());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            for (int i = 0; i < 4; i++)
            {
                strokeLineArguments.add(invocation.getArgument(i));
            }
            return null;
        }).when(graphicsContextMock).strokeLine(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            lineWidths.add(invocation.getArgument(0));
            return null;
        }).when(graphicsContextMock).setLineWidth(anyDouble());

        PinballCanvasSubView pinballCanvasSubView = new PinballCanvasSubView();
        Field canvasField = PinballCanvasSubView.class.getDeclaredField("canvas");
        canvasField.setAccessible(true);
        canvasField.set(pinballCanvasSubView, canvasMock);
        Field spritesField = PinballCanvasSubView.class.getDeclaredField("sprites");
        spritesField.setAccessible(true);
        spritesField.set(pinballCanvasSubView, new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<SpriteSubView>()
        {{
            add(spriteSubViewMock);
        }})));
        pinballCanvasSubView.setViewModel(viewModelMock);
        Method redraw = PinballCanvasSubView.class.getDeclaredMethod("redraw");
        redraw.setAccessible(true);
        redraw.invoke(pinballCanvasSubView);

        assertThat(DesignConfig.PRIMARY_COLOR, equalTo(backgroundColor));
        double edgeFirstComponent = fillRectArguments[0];
        double edgeSecondComponent = fillRectArguments[1];
        double width = fillRectArguments[2];
        double height = fillRectArguments[3];
        assertEquals(edgeFirstComponent, 0, 0);
        assertEquals(edgeSecondComponent, 0, 0);
        assertEquals(width, canvasWidth, 0);
        assertEquals(height, canvasHeight, 0);
        assertThat("The colors of two adjacent lines should not match!", gridLineColors.get(0), not(equalTo(gridLineColors.get(1))));
        if (gridLineColors.get(2) != null)
        {
            assertThat("The colors should alternate every two steps!", gridLineColors.get(0), equalTo(gridLineColors.get(2)));
        }
        assertThat("The width of two adjacent lines should not match!", lineWidths.get(0), not(equalTo(lineWidths.get(1))));
        if (lineWidths.get(2) != null)
        {
            assertThat("The width of the lines should alternate every two steps!", lineWidths.get(0), equalTo(lineWidths.get(2)));
        }
        assertEquals(strokeLineArguments.get(0), strokeLineArguments.get(2), 0);
        assertNotEquals(strokeLineArguments.get(1), strokeLineArguments.get(3), 0);
    }

    /**
     * Leert die Zwischenspeicher der Parameter.
     */
    @After
    public void resetAttributes()
    {
        backgroundColor = null;
        fillRectArguments = new double[4];
        gridLineColors = new ArrayList<>();
        lineWidths = new ArrayList<>();
    }
}
