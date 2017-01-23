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
import sep.fimball.viewmodel.window.pinballmachine.editor.DraggedElement;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.ElementImage;
import sep.fimball.model.physics.element.BasePhysicsElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse SpriteSubViewModel.
 */
public class SpriteSubViewModelTest
{
    /**
     * Das GameElement Mock.
     */
    @Mock
    private GameElement gameElement;

    /**
     * Das PlacedElement Mock des GameElement Mocks.
     */
    @Mock
    private PlacedElement placedElementOfGameElement;

    /**
     * Die Höhe des GameElement Mocks.
     */
    private DoubleProperty heightOfGameElement;

    /**
     * Erstelle GameElement Mock mit Mocks.
     */
    @Before
    public void setupGameElement()
    {
        // Erstelle das PlacedElement Mock
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getPivotPoint()).thenReturn(new Vector2());
        BaseElement baseElement = Mockito.mock(BaseElement.class);
        when(baseElement.getPhysics()).thenReturn(basePhysicsElement);
        placedElementOfGameElement = Mockito.mock(PlacedElement.class);
        when(placedElementOfGameElement.getBaseElement()).thenReturn(baseElement);
        when(placedElementOfGameElement.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2()));

        // Erstelle das BaseMediaElement Mock
        BaseMediaElement baseMediaElement = Mockito.mock(BaseMediaElement.class);
        when(baseMediaElement.getElementHeight()).thenReturn(1.0);
        ObjectProperty<ElementImage> elementImageProperty = new SimpleObjectProperty<>(null);
        when(baseMediaElement.elementImageProperty()).thenReturn(elementImageProperty);

        // Erstelle das GameElement Mock
        gameElement = Mockito.mock(GameElement.class);
        when(gameElement.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2()));
        when(gameElement.rotationProperty()).thenReturn(new SimpleDoubleProperty(0));
        heightOfGameElement = new SimpleDoubleProperty(0);
        when(gameElement.heightProperty()).thenReturn(heightOfGameElement);
        ObjectProperty<Optional<ElementImage>> currentAnimationProperty = new SimpleObjectProperty<>(Optional.empty());
        when(gameElement.currentAnimationProperty()).thenReturn(currentAnimationProperty);
        when(gameElement.drawOrderProperty()).thenReturn(new SimpleIntegerProperty(0));

        // Gebe das PlacedElement Mock in das GameElement Mock
        when(gameElement.getPlacedElement()).thenReturn(placedElementOfGameElement);

        // Gebe das BaseMediaElement Mock in das GameElement Mock
        when(gameElement.getMediaElement()).thenReturn(baseMediaElement);
    }

    /**
     * Überprüft die Korrektheit der Methode {@link SpriteSubViewModel#selectedProperty}.
     */
    @Test
    public void selectedProperty()
    {
        // SpriteSubViewModel von einer Spielsitzung
        {
            SpriteSubViewModel spriteSubViewModelGame = new SpriteSubViewModel(gameElement);
            assertThat("Ein SpriteSubViewModel von einer Spielsitzung ist niemals ausgewählt", spriteSubViewModelGame.selectedProperty().get(), is(false));
        }

        // SpriteSubViewModel von einer Editorsitzung
        {
            ListProperty<DraggedElement> listOfSelecedElements = new SimpleListProperty<>(FXCollections.observableArrayList());
            SpriteSubViewModel spriteSubViewModelEditor = new SpriteSubViewModel(gameElement, listOfSelecedElements);
            assertThat("Das PlacedElement des SpriteSubViewModel ist nicht in der Liste und deshalb nicht ausgewählt", spriteSubViewModelEditor.selectedProperty().get(), is(false));

            // Füge irgendein Element zur Liste hinzu
            listOfSelecedElements.add(Mockito.mock(DraggedElement.class));
            assertThat("Das PlacedElement des SpriteSubViewModel ist nicht in der Liste und deshalb nicht ausgewählt", spriteSubViewModelEditor.selectedProperty().get(), is(false));

            // Füge das PlacedElement des SpriteSubViewModels zur Liste hinzu
            listOfSelecedElements.add(new DraggedElement(placedElementOfGameElement));
            assertThat("Das PlacedElement des SpriteSubViewModel ist in der Liste und deshalb auch ausgewählt", spriteSubViewModelEditor.selectedProperty().get(), is(true));
        }
    }

    /**
     * Überprüft die Korrektheit der Methode {@link SpriteSubViewModel#scaleProperty}.
     */
    @Test
    public void scaleProperty()
    {
        SpriteSubViewModel spriteSubViewModel = new SpriteSubViewModel(gameElement);

        assertThat("Die Standardskalierung", spriteSubViewModel.scaleProperty().get(), is(1.0));

        heightOfGameElement.set(1.0);
        assertThat("Die Skalierung wächst ist linear", spriteSubViewModel.scaleProperty().get(), is(1.125));

        heightOfGameElement.set(2.0);
        assertThat("Die Skalierung wächst ist linear", spriteSubViewModel.scaleProperty().get(), is(1.25));

        heightOfGameElement.set(4.0);
        assertThat("Die Skalierung wächst ist linear", spriteSubViewModel.scaleProperty().get(), is(1.5));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link SpriteSubViewModel#visibilityProperty}.
     */
    @Test
    public void visibilityProperty()
    {
        // SpriteSubViewModel von einer Spielsitzung
        {
            SpriteSubViewModel spriteSubViewModelGame = new SpriteSubViewModel(gameElement);
            assertThat("Die Standardsichtbarkeit", spriteSubViewModelGame.visibilityProperty().get(), is(1.0));
        }

        // SpriteSubViewModel von einer Editorsitzung
        {
            // Erstelle ein SpriteSubViewModel von einem Rampenelement
            when(gameElement.getElementType()).thenReturn(BaseElementType.RAMP);
            ListProperty<DraggedElement> listOfSelecedElements = new SimpleListProperty<>(FXCollections.observableArrayList());
            SpriteSubViewModel spriteSubViewModelEditor = new SpriteSubViewModel(gameElement, listOfSelecedElements);

            assertThat("Die Standardsichtbarkeit", spriteSubViewModelEditor.visibilityProperty().get(), is(1.0));

            // Füge ein Element zur Liste hinzu
            listOfSelecedElements.add(Mockito.mock(DraggedElement.class));
            assertThat("Die Sichtbarkeit von Rampenelementen ist bei einer nicht leeren Liste reduziert", spriteSubViewModelEditor.visibilityProperty().get(), is(0.5));
        }
    }
}