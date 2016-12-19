package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.ElementImage;
import sep.fimball.model.physics.element.BasePhysicsElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by kaira on 19.12.2016.
 */
public class SpriteSubViewModelTest
{
    @Mock
    private GameElement gameElement;

    @Before
    public void setupGameElement()
    {
        gameElement = Mockito.mock(GameElement.class);

        ObjectProperty<Vector2> position = new SimpleObjectProperty<>(new Vector2());
        when(gameElement.positionProperty()).thenReturn(position);

        DoubleProperty height = new SimpleDoubleProperty(1);
        when(gameElement.heightProperty()).thenReturn(height);

        DoubleProperty rotation = new SimpleDoubleProperty(0);
        when(gameElement.rotationProperty()).thenReturn(rotation);

        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getPivotPoint()).thenReturn(new Vector2());
        BaseElement baseElement = Mockito.mock(BaseElement.class);
        when(baseElement.getPhysics()).thenReturn(basePhysicsElement);
        PlacedElement placedElement = Mockito.mock(PlacedElement.class);
        when(placedElement.getBaseElement()).thenReturn(baseElement);
        when(gameElement.getPlacedElement()).thenReturn(placedElement);

        BaseMediaElement baseMediaElement = Mockito.mock(BaseMediaElement.class);
        when(baseMediaElement.getElementHeight()).thenReturn(1.0);
        ObjectProperty<ElementImage> elementImageProperty = new SimpleObjectProperty<>(null);
        when(baseMediaElement.elementImageProperty()).thenReturn(elementImageProperty);
        when(gameElement.getMediaElement()).thenReturn(baseMediaElement);

        ObjectProperty<Optional<Animation>> currentAnimationProperty = new SimpleObjectProperty<>(Optional.empty());
        when(gameElement.currentAnimationProperty()).thenReturn(currentAnimationProperty);
    }

    @Test
    public void animationFramePathProperty() throws Exception
    {

    }

    @Test
    public void selectedProperty() throws Exception
    {

    }

    @Test
    public void scaleProperty() throws Exception
    {

    }

    @Test
    public void visibilityProperty() throws Exception
    {
        SpriteSubViewModel spriteSubViewModel = new SpriteSubViewModel(gameElement);
        assertThat(spriteSubViewModel.visibilityProperty().get(), is(1.0));
    }
}