package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementType;
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

    @Mock
    private PlacedElement placedElementOfGameElement;

    private DoubleProperty heightOfGameElement;

    @Before
    public void setupGameElement()
    {
        gameElement = Mockito.mock(GameElement.class);

        ObjectProperty<Vector2> position = new SimpleObjectProperty<>(new Vector2());
        when(gameElement.positionProperty()).thenReturn(position);

        heightOfGameElement = new SimpleDoubleProperty(0);
        when(gameElement.heightProperty()).thenReturn(heightOfGameElement);

        DoubleProperty rotation = new SimpleDoubleProperty(0);
        when(gameElement.rotationProperty()).thenReturn(rotation);

        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getPivotPoint()).thenReturn(new Vector2());
        BaseElement baseElement = Mockito.mock(BaseElement.class);
        when(baseElement.getPhysics()).thenReturn(basePhysicsElement);
        placedElementOfGameElement = Mockito.mock(PlacedElement.class);
        when(placedElementOfGameElement.getBaseElement()).thenReturn(baseElement);
        when(gameElement.getPlacedElement()).thenReturn(placedElementOfGameElement);

        BaseMediaElement baseMediaElement = Mockito.mock(BaseMediaElement.class);
        when(baseMediaElement.getElementHeight()).thenReturn(1.0);
        ObjectProperty<ElementImage> elementImageProperty = new SimpleObjectProperty<>(null);
        when(baseMediaElement.elementImageProperty()).thenReturn(elementImageProperty);
        when(gameElement.getMediaElement()).thenReturn(baseMediaElement);

        ObjectProperty<Optional<Animation>> currentAnimationProperty = new SimpleObjectProperty<>(Optional.empty());
        when(gameElement.currentAnimationProperty()).thenReturn(currentAnimationProperty);
    }

    @Test
    public void selectedProperty() throws Exception
    {
        SpriteSubViewModel spriteSubViewModelGame = new SpriteSubViewModel(gameElement);
        assertThat(spriteSubViewModelGame.selectedProperty().get(), is(false));

        ListProperty<PlacedElement> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        SpriteSubViewModel spriteSubViewModelEditor = new SpriteSubViewModel(gameElement, list);
        assertThat(spriteSubViewModelEditor.selectedProperty().get(), is(false));

        list.add(Mockito.mock(PlacedElement.class));
        assertThat(spriteSubViewModelEditor.selectedProperty().get(), is(false));

        list.add(placedElementOfGameElement);
        assertThat(spriteSubViewModelEditor.selectedProperty().get(), is(true));
    }

    @Test
    public void scaleProperty() throws Exception
    {
        SpriteSubViewModel spriteSubViewModel = new SpriteSubViewModel(gameElement);
        assertThat(spriteSubViewModel.scaleProperty().get(), is(1.0));

        heightOfGameElement.set(1.0);
        assertThat(spriteSubViewModel.scaleProperty().get(), is(1.125));

        heightOfGameElement.set(2.0);
        assertThat(spriteSubViewModel.scaleProperty().get(), is(1.25));

        heightOfGameElement.set(4.0);
        assertThat(spriteSubViewModel.scaleProperty().get(), is(1.5));
    }

    @Test
    public void visibilityProperty() throws Exception
    {
        SpriteSubViewModel spriteSubViewModelGame = new SpriteSubViewModel(gameElement);
        assertThat(spriteSubViewModelGame.visibilityProperty().get(), is(1.0));

        when(gameElement.getElementType()).thenReturn(BaseElementType.RAMP);
        ListProperty<PlacedElement> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        SpriteSubViewModel spriteSubViewModelEditor = new SpriteSubViewModel(gameElement, list);
        assertThat(spriteSubViewModelEditor.visibilityProperty().get(), is(1.0));

        list.add(Mockito.mock(PlacedElement.class));
        assertThat(spriteSubViewModelEditor.visibilityProperty().get(), is(0.5));
    }
}