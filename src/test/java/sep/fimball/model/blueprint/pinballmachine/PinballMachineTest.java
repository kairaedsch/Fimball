package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.WritableImage;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.*;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.physics.element.BasePhysicsElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static sep.fimball.VectorMatcher.matchesVector;

/**
 * Tests für die Klasse PinballMachine.
 */
public class PinballMachineTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link PinballMachine#getElementAt}.
     */
    @Test
    public void getElementAt()
    {
        // Erstelle PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), Collections.emptyList(), Mockito.mock(PinballMachineManager.class), true);

        // Füge PlacedElements in den Automaten ein
        PlacedElement placedElementOne = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(-2, -2));
        PlacedElement placedElementTwo = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(2, 2));
        assertThat("Element eins wird ausgewählt", pinballMachine.getElementAt(new Vector2()).get(), is(placedElementOne));

        // Füge PlacedElements in den Automaten ein
        PlacedElement placedElementOverOne = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(-1, -1));

        // Point auf die Position von "Element eins" und "Element über Element eins"
        assertThat("Element über element eins wird ausgewählt", pinballMachine.getElementAt(new Vector2()).get(), is(placedElementOverOne));

        // Point auf die Position von Element zwei
        assertThat("Element zwei wird ausgewählt", pinballMachine.getElementAt(new Vector2(4, 4)).get(), is(placedElementTwo));

        // Point in die Leere
        assertThat("Kein Element wird ausgewählt", pinballMachine.getElementAt(new Vector2(8, 8)).isPresent(), is(false));
    }

    /**
     * Überprüft Korrektheit der Methode {@link PinballMachine#addHighscore}.
     */
    @Test
    public void addHighscore()
    {
        // Erstelle einen PinballMachine mit voller Highscorelist
        ArrayList<Highscore> highscores = new ArrayList<>();
        for (int i = 0; i < Config.MAX_HIGHSCORES; i++)
        {
            highscores.add(new Highscore(25, "any"));
        }
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), highscores, Mockito.mock(PinballMachineManager.class), true);
        ReadOnlyListProperty<Highscore> realHighscores = pinballMachine.highscoreListProperty();

        // Füge einen schlechten Highscore hinzu
        pinballMachine.addHighscore(new Highscore(0, "bad Player"));
        assertThat("Der schlechte Spieler taucht nicht in der Liste auf", realHighscores.stream().anyMatch(highscore -> highscore.playerNameProperty().get().equals("bad Player")), is(false));

        // Füge gute Highscores hinzu und prüfe, ob diese ganz oben angezeigt werden
        pinballMachine.addHighscore(new Highscore(100, "first Player"));
        pinballMachine.addHighscore(new Highscore(75, "second Player"));
        pinballMachine.addHighscore(new Highscore(50, "third Player"));
        assertThat("Der beste Spieler ist erster", realHighscores.get(0).playerNameProperty().get().equals("first Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(1).playerNameProperty().get().equals("second Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(2).playerNameProperty().get().equals("third Player"), is(true));
    }

    /**
     * Überprüft Korrektheit der Methode {@link PinballMachine#getElementsAt}.
     */
    @Test
    public void getElementsAt()
    {
        // Erstelle leere PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), new ArrayList<>(), Mockito.mock(PinballMachineManager.class), true);

        // Füge PlacedElementMocks hinzu
        PlacedElement placedElementTop = getPlacedElementMock(BaseElementType.NORMAL, new Vector2(-5, -5), new Vector2(-1, -1), new Vector2(1, 1));
        PlacedElement placedElementBottom = getPlacedElementMock(BaseElementType.NORMAL, new Vector2(5, 5), new Vector2(-1, -1), new Vector2(1, 1));
        pinballMachine.addElement(placedElementTop);
        pinballMachine.addElement(placedElementBottom);

        // Wähle links oben aus
        {
            ListProperty<PlacedElement> elementsAt = pinballMachine.getElementsAt(new RectangleDoubleByPoints(new Vector2(-10, -10), new Vector2(-4, -4)));
            assertThat("ElementTop wurde ausgewählt", elementsAt.contains(placedElementTop), is(true));
            assertThat("ElementBottom wurde nicht ausgewählt", elementsAt.contains(placedElementBottom), is(false));
        }

        // Wähle rechts unten aus
        {
            ListProperty<PlacedElement> elementsAt = pinballMachine.getElementsAt(new RectangleDoubleByPoints(new Vector2(4, 4), new Vector2(10, 10)));
            assertThat("ElementTop wurde nicht ausgewählt", elementsAt.contains(placedElementTop), is(false));
            assertThat("ElementBottom wurde ausgewählt", elementsAt.contains(placedElementBottom), is(true));
        }

        // Wähle in der Mitte aus
        {
            ListProperty<PlacedElement> elementsAt = pinballMachine.getElementsAt(new RectangleDoubleByPoints(new Vector2(-3, -3), new Vector2(3, 3)));
            assertThat("ElementTop wurde nicht ausgewählt", elementsAt.contains(placedElementTop), is(false));
            assertThat("ElementBottom wurde nicht ausgewählt", elementsAt.contains(placedElementBottom), is(false));
        }
    }

    /**
     * Überprüft Korrektheit der Methode {@link PinballMachine#getBoundingBox}.
     */
    @Test
    public void getBoundingBox()
    {
        // Erstelle leere PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), new ArrayList<>(), Mockito.mock(PinballMachineManager.class), true);

        // Füge PlacedElementMocks hinzu
        pinballMachine.addElement(getPlacedElementMock(BaseElementType.NORMAL, new Vector2(-5, -5), new Vector2(-1, -1), new Vector2(1, 1)));
        pinballMachine.addElement(getPlacedElementMock(BaseElementType.NORMAL, new Vector2(5, 5), new Vector2(-1, -1), new Vector2(1, 1)));

        // Erstelle die BoundingBox
        RectangleDouble boundingBox = pinballMachine.getBoundingBox();

        // Prüfe ob die BoundingBox richtig ist
        assertThat(boundingBox.getOrigin(), matchesVector(-12, -12));
        assertThat(boundingBox.getSize(), matchesVector(24, 24));
    }

    /**
     * Überprüft Korrektheit der Methoden {@link PinballMachine#savePreviewImage},
     * {@link PinballMachine#relativePreviewImagePathProperty()} und {@link PinballMachine#absolutePreviewImagePathProperty()}.
     */
    @Test
    public void savePreviewImage()
    {
        // Erstelle leere PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), new ArrayList<>(), Mockito.mock(PinballMachineManager.class), true);

        assertThat("Es liegt kein Hintergrundbild vor", pinballMachine.relativePreviewImagePathProperty().get().isPresent(), is(false));

        // Hole das defaultImage
        String defaultImagePath = pinballMachine.absolutePreviewImagePathProperty().get();

        // Speichere ein Hintergrundbildmock
        pinballMachine.savePreviewImage(Mockito.mock(WritableImage.class));

        assertThat("Es liegt ein Hintergrundbild vor", pinballMachine.relativePreviewImagePathProperty().get().isPresent(), is(true));
        assertThat("Es ist nicht das DefaultHintergrundbild", pinballMachine.absolutePreviewImagePathProperty().get(), is(not(equalTo(defaultImagePath))));
    }

    /**
     * Überprüft Korrektheit der Methode {@link PinballMachine#removeElement}.
     */
    @Test
    public void removeElement()
    {
        // Erstelle leere PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Optional.empty(), new ArrayList<>(), Mockito.mock(PinballMachineManager.class), true);

        // Erstelle PlaceElementMocks
        PlacedElement placedElementNormalA = getPlacedElementMock(BaseElementType.NORMAL);
        PlacedElement placedElementNormalB = getPlacedElementMock(BaseElementType.NORMAL);
        PlacedElement placedElementBallA = getPlacedElementMock(BaseElementType.BALL);
        PlacedElement placedElementBallB = getPlacedElementMock(BaseElementType.BALL);
        pinballMachine.addElement(placedElementNormalA);
        pinballMachine.addElement(placedElementNormalB);
        pinballMachine.addElement(placedElementBallA);

        assertThat("placedElementNormalA ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementNormalA), is(true));
        assertThat("placedElementNormalB ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementNormalB), is(true));
        assertThat("placedElementBallA ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementBallA), is(true));

        pinballMachine.removeElement(placedElementNormalA);
        assertThat("placedElementNormalA wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalA), is(false));
        assertThat("placedElementNormalB ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementNormalB), is(true));
        assertThat("placedElementBallA ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementBallA), is(true));

        pinballMachine.removeElement(placedElementNormalB);
        assertThat("placedElementNormalA wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalA), is(false));
        assertThat("placedElementNormalB wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalB), is(false));
        assertThat("placedElementBallA ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementBallA), is(true));

        pinballMachine.removeElement(placedElementBallA);
        assertThat("placedElementNormalA wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalA), is(false));
        assertThat("placedElementNormalB wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalB), is(false));
        assertThat("placedElementBallA ist immernoch in der PinballMachine, da immer ein Ball vorhanden sein muss", pinballMachine.elementsProperty().contains(placedElementBallA), is(true));

        pinballMachine.addElement(placedElementBallB);
        assertThat("placedElementNormalA wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalA), is(false));
        assertThat("placedElementNormalB wurde entfernt", pinballMachine.elementsProperty().contains(placedElementNormalB), is(false));
        assertThat("placedElementBallA wurde entfernt", pinballMachine.elementsProperty().contains(placedElementBallA), is(false));
        assertThat("placedElementBallB ist in der PinballMachine", pinballMachine.elementsProperty().contains(placedElementBallB), is(true));
    }

    /**
     * Erstellt ein PlacedElementMock mit dem gegebenen BaseElementType.
     *
     * @param baseElementType Der BaseElementType des zu erstellende PlacedElementMocks.
     * @return Das erstellte PlacedElementMock mit dem gegebenen BaseElementType.
     */
    private PlacedElement getPlacedElementMock(BaseElementType baseElementType)
    {
        return getPlacedElementMock(baseElementType, new Vector2(), new Vector2(), new Vector2());
    }

    /**
     * Erstellt ein PlacedElementMock mit dem gegebenen Parametern.
     *
     * @param baseElementType Der BaseElementType des zu erstellenden PlacedElementMocks.
     * @param position        Die Position  des zu erstellenden PlacedElementMocks.
     * @param extremPosMin    Die minimale ExtremPosition des zu erstellenden PlacedElementMocks.
     * @param extremPosMax    Die maximale ExtremPosition des zu erstellenden PlacedElementMocks.
     * @return Das erstellte PlacedElementMock mit den gegebenen Parametern.
     */
    private PlacedElement getPlacedElementMock(BaseElementType baseElementType, Vector2 position, Vector2 extremPosMin, Vector2 extremPosMax)
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        Mockito.when(basePhysicsElement.getExtremePos(0, false)).thenReturn(extremPosMin);
        Mockito.when(basePhysicsElement.getExtremePos(0, true)).thenReturn(extremPosMax);

        BaseElement baseElementA = Mockito.mock(BaseElement.class);
        Mockito.when(baseElementA.getType()).thenReturn(baseElementType);
        Mockito.when(baseElementA.getPhysics()).thenReturn(basePhysicsElement);

        PlacedElement placedElementMock = Mockito.mock(PlacedElement.class);
        Mockito.when(placedElementMock.getBaseElement()).thenReturn(baseElementA);
        Mockito.when(placedElementMock.positionProperty()).thenReturn(new SimpleObjectProperty<>(position));
        Mockito.when(placedElementMock.rotationProperty()).thenReturn(new SimpleDoubleProperty(0));
        return placedElementMock;
    }
}