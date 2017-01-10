package sep.fimball.model.blueprint.pinballmachine;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Tests für die Klasse PlacedElementListFactory.
 */
public class PlacedElementListFactoryTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link PlacedElementListFactory#createPlacedElementListJson}.
     */
    @Test
    public void createPlacedElementList()
    {
        // Test mit valider placedElementListJson
        {
            // Erstelle eine valide placedElementListJson
            PlacedElementListJson placedElementListJson = new PlacedElementListJson();
            placedElementListJson.elements = new PlacedElementListJson.PlacedElementJson[3];

            placedElementListJson.elements[0] = new PlacedElementListJson.PlacedElementJson();
            placedElementListJson.elements[0].baseElementId = "ball";
            placedElementListJson.elements[0].multiplier = 0;
            placedElementListJson.elements[0].points = 0;
            placedElementListJson.elements[0].position = new Vector2();
            placedElementListJson.elements[0].rotation = 0;

            placedElementListJson.elements[1] = new PlacedElementListJson.PlacedElementJson();
            placedElementListJson.elements[1].baseElementId = "ball";
            placedElementListJson.elements[1].multiplier = 5;
            placedElementListJson.elements[1].points = 42;
            placedElementListJson.elements[1].position = new Vector2(2, -9.0);
            placedElementListJson.elements[1].rotation = 93;

            placedElementListJson.elements[2] = null;

            // Lasse eine placedElementList aus der validen PlacedElementListJson generieren
            List<PlacedElement> placedElementList = PlacedElementListFactory.createPlacedElementList(Optional.of(placedElementListJson)).get();

            // Prüfe ob die generierte placedElementList valide ist
            assertThat(placedElementList.size(), is(2));

            assertThat(placedElementList.get(0).getBaseElement().getId(), is(equalTo("ball")));
            assertThat(placedElementList.get(0).multiplierProperty().get(), is(0.0));
            assertThat(placedElementList.get(0).pointsProperty().get(), is(0));
            assertThat(placedElementList.get(0).positionProperty().get(), is(equalTo(new Vector2())));
            assertThat(placedElementList.get(0).rotationProperty().get(), is(0.0));

            assertThat(placedElementList.get(1).getBaseElement().getId(), is(equalTo("ball")));
            assertThat(placedElementList.get(1).multiplierProperty().get(), is(5.0));
            assertThat(placedElementList.get(1).pointsProperty().get(), is(42));
            assertThat(placedElementList.get(1).positionProperty().get(), is(equalTo(new Vector2(2, -9.0))));
            assertThat(placedElementList.get(1).rotationProperty().get(), is(93.0));
        }

        // Test mit ungültiger placedElementListJson
        {
            // Erstelle eine ungültige placedElementListJson
            PlacedElementListJson placedElementListJson = new PlacedElementListJson();
            placedElementListJson.elements = new PlacedElementListJson.PlacedElementJson[1];
            placedElementListJson.elements[0] = null;

            // Lasse eine PlacedElementList aus der ungültigen PlacedElementListJson generieren
            List<PlacedElement> placedElementList = PlacedElementListFactory.createPlacedElementList(Optional.of(placedElementListJson)).get();
            assertThat("Generierung der placedElementList sollte erfolgreich sein, die Liste allerdings leer", placedElementList.size(), is(0));
        }

        // Test mit ungültiger placedElementListJson
        {
            // Erstelle eine ungültige placedElementListJson
            PlacedElementListJson placedElementListJson = new PlacedElementListJson();
            placedElementListJson.elements = null;

            // Lasse eine PlacedElementList aus der ungültigen PlacedElementListJson generieren
            Optional<List<PlacedElement>> placedElementList = PlacedElementListFactory.createPlacedElementList(Optional.of(placedElementListJson));
            assertThat("Generierung der placedElementList sollte Fehlgeschlagen sein", placedElementList.isPresent(), is(false));
        }

        // Test mit ungültiger placedElementListJson
        {
            // Lasse eine PlacedElementList aus einer ungültigen PlacedElementListJson generieren
            Optional<List<PlacedElement>> placedElementList = PlacedElementListFactory.createPlacedElementList(Optional.empty());
            assertThat("Generierung der placedElementList sollte Fehlgeschlagen sein", placedElementList.isPresent(), is(false));
        }
    }

    /**
     * Überprüft die Korrektheit der Methode {@link PlacedElementListFactory#createPlacedElementListJson}.
     */
    @Test
    public void createPlacedElementListJson()
    {
        // Erstelle Mocks
        BaseElement baseElementBallMock = Mockito.mock(BaseElement.class);
        Mockito.when(baseElementBallMock.getId()).thenReturn("ball");

        BaseElement baseElementTestMock = Mockito.mock(BaseElement.class);
        Mockito.when(baseElementTestMock.getId()).thenReturn("testID");

        // Erstelle eine PlacedElementList
        List<PlacedElement> placedElementList = new ArrayList<>();
        placedElementList.add(new PlacedElement(baseElementBallMock, new Vector2(2, -2), 4, 20, 5));
        placedElementList.add(new PlacedElement(baseElementTestMock, new Vector2(-1, 9), 7, 1, 3));

        // Lasse eine PlacedElementListJson aus der PlacedElementList generieren
        PlacedElementListJson placedElementListJson = PlacedElementListFactory.createPlacedElementListJson(placedElementList);

        // Prüfe ob die generierte PlacedElementListJson valide ist
        assertThat(placedElementListJson.elements[0].baseElementId, is(equalTo("ball")));
        assertThat(placedElementListJson.elements[0].position, is(equalTo(new Vector2(2, -2))));
        assertThat(placedElementListJson.elements[0].points, is(4));
        assertThat(placedElementListJson.elements[0].multiplier, is(20.0));
        assertThat(placedElementListJson.elements[0].rotation, is(5.0));

        assertThat(placedElementListJson.elements[1].baseElementId, is(equalTo("testID")));
        assertThat(placedElementListJson.elements[1].position, is(equalTo(new Vector2(-1, 9))));
        assertThat(placedElementListJson.elements[1].points, is(7));
        assertThat(placedElementListJson.elements[1].multiplier, is(1.0));
        assertThat(placedElementListJson.elements[1].rotation, is(3.0));
    }
}