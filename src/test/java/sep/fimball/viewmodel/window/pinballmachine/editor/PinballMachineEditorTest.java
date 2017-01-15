package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementJson;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse PinballMachineEditor.
 */
public class PinballMachineEditorTest
{

    /**
     * Die Elemente des Test-Flipperautomaten.
     */
    private List<PlacedElement> elementsInMachine;

    /**
     * Testet, ob das Hinzufügen von Elementen zur Auswahl funktioniert.
     */
    @Test
    public void addElementsToSelectionTest()
    {
        PinballMachine mockedMachine = mock(PinballMachine.class);
        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);
        BaseElement baseElement = getBaseElement("Test");

        test.addToSelection(baseElement);
        assertThat(test.getSelection().size(), is(1));
        assertThat(test.getSelection().get(0).getBaseElement(), equalTo(baseElement));

        test.addToSelection(baseElement);
        assertThat(test.getSelection().size(), is(1));

        PlacedElement element = new PlacedElement(getBaseElement("Test 2"), new Vector2(0, 0), 0, 1, 0);
        test.addToSelection(element);
        assertThat(test.getSelection().size(), is(2));

        ListProperty<PlacedElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        elements.add(new PlacedElement(getBaseElement("Test 3"), new Vector2(0, 0), 0, 1, 0));
        elements.add(new PlacedElement(getBaseElement("Test 4"), new Vector2(0, 0), 0, 1, 0));

        test.addToSelection(elements);
        assertThat(test.getSelection().size(), is(4));
    }

    /**
     * Testet, ob das Löschen der Auswahl funktioniert.
     */
    @Test
    public void clearTest()
    {
        PinballMachine mockedMachine = mock(PinballMachine.class);
        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);
        BaseElement baseElement = getBaseElement("Test");

        test.addToSelection(baseElement);
        assertThat(test.getSelection().size(), is(1));

        test.clearSelection();
        assertThat(test.getSelection().size(), is(0));
    }

    /**
     * Testet, ob das Platzieren von BaseElements funktioniert.
     */
    @Test
    public void placeSelectionTest()
    {
        elementsInMachine = new ArrayList<>();
        PinballMachine mockedMachine = mock(PinballMachine.class);
        doAnswer(invocationOnMock ->
        {
            elementsInMachine.add(invocationOnMock.getArgument(0));
            return null;
        }).when(mockedMachine).addElement(any(PlacedElement.class));

        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);
        BaseElement baseElement = getBaseElement("Test");

        test.addToSelection(baseElement);
        test.placeSelection();
        assertThat(elementsInMachine.size(), is(1));
    }

    /**
     * Testet ob das Verschieben von Elementen in der Auswahl funktioniert.
     */
    @Test
    public void moveSelectionTest()
    {
        PinballMachine mockedMachine = mock(PinballMachine.class);
        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);

        test.addToSelection(new PlacedElement(getBaseElement("Test 1"), new Vector2(0, 0), 0, 1, 0));
        test.addToSelection(new PlacedElement(getBaseElement("Test 2"), new Vector2(0, 0), 0, 1, 0));
        Vector2 moveVector = new Vector2(1, 1);
        test.moveSelectionBy(moveVector);
        test.moveSelectionBy(moveVector);
        assertThat(test.getSelection().get(0).positionProperty().get(), equalTo(new Vector2(2, 2)));
        assertThat(test.getSelection().get(1).positionProperty().get(), equalTo(new Vector2(2, 2)));

        test.moveSelectionTo(moveVector);
        assertThat(test.getSelection().get(0).positionProperty().get(), equalTo(new Vector2(1, 1)));
        assertThat(test.getSelection().get(1).positionProperty().get(), equalTo(new Vector2(1, 1)));
    }

    /**
     * Testet, ob das Löschen der Elemente in der Auswahl vom Automaten funktioniert.
     */
    @Test
    public void removeTest()
    {
        elementsInMachine = new ArrayList<>();
        PinballMachine mockedMachine = mock(PinballMachine.class);
        doAnswer(invocationOnMock ->
        {
            elementsInMachine.add(invocationOnMock.getArgument(0));
            return null;
        }).when(mockedMachine).addElement(any(PlacedElement.class));

        doAnswer(invocationOnMock ->
        {
            elementsInMachine.remove(invocationOnMock.getArgument(0));
            return null;
        }).when(mockedMachine).removeElement(any(PlacedElement.class));

        PlacedElement mockElement = mock(PlacedElement.class);
        when(mockElement.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2(0, 0)));

        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);

        mockedMachine.addElement(mockElement);
        test.addToSelection(mockElement);
        test.removeSelection();

        assertThat(elementsInMachine.size(), is(0));
    }

    /**
     * Prüft die Korrektheit von {@code getElementAt()}.
     */
    @Test
    public void getElementsAtTest()
    {
        elementsInMachine = new ArrayList<>();
        PinballMachine mockedMachine = mock(PinballMachine.class);
        doAnswer(invocationOnMock ->
        {
            elementsInMachine.add(invocationOnMock.getArgument(0));
            return null;
        }).when(mockedMachine).addElement(any(PlacedElement.class));
        doAnswer(invocationOnMock ->
        {
            for (PlacedElement element : elementsInMachine)
            {
                if (element.positionProperty().get().equals(invocationOnMock.getArgument(0)))
                {
                    return Optional.of(element);
                }

            }
            return Optional.empty();
        }).when(mockedMachine).getElementAt(any(Vector2.class));

        PinballMachineEditor test = new PinballMachineEditor(mockedMachine);

        test.addToSelection(new PlacedElement(getBaseElement("Test 1"), new Vector2(0, 0), 0, 1, 0));
        test.addToSelection(new PlacedElement(getBaseElement("Test 2"), new Vector2(1, 1), 0, 1, 0));
        test.placeSelection();

        ListProperty elements = new SimpleListProperty(test.getElementsAt(new Vector2(1, 1)));
        assertThat(elements.size(), is(1));
    }


    /**
     * Gibt ein Test-BaseElement zurück.
     *
     * @param id Die ID des BaseElements.
     * @return Ein Test-BaseElement.
     */
    private BaseElement getBaseElement(String id)
    {
        BaseElementJson baseElementJson = new BaseElementJson();
        baseElementJson.elementCategory = BaseElementCategory.BASIC;
        baseElementJson.elementType = BaseElementType.NORMAL;
        baseElementJson.physicElement = new BaseElementJson.PhysicElementJson();
        baseElementJson.physicElement.pivotPoint = new Vector2(0, 0);
        baseElementJson.physicElement.colliders = new BaseElementJson.PhysicElementJson.PhysicColliderJson[0];

        baseElementJson.mediaElement = new BaseElementJson.MediaElementJson();
        baseElementJson.mediaElement.general = new BaseElementJson.MediaElementJson.MediaElementGeneralJson();
        baseElementJson.mediaElement.descriptions = new BaseElementJson.MediaElementJson.MediaElementDescriptionJson[0];

        baseElementJson.ruleElement = new BaseElementJson.RuleElementJson();
        baseElementJson.ruleElement.general = new BaseElementJson.RuleElementJson.RuleElementGeneralJson();

        return new BaseElement(id, baseElementJson);
    }


}
