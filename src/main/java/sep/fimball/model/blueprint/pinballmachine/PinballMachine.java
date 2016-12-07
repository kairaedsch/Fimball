package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElement;

import java.util.List;
import java.util.Optional;

/**
 * Eine PinballMachine stellt einen Flipperautomaten dar und enthält allgemeine Informationen über den Automaten sowie über die im Automaten platzierten Elemente.
 */
public class PinballMachine
{
    /**
     * Der zu dieser PinballMachine gehörige PinballMachineManager.
     */
    private final PinballMachineManager pinballMachineManager;

    /**
     * Der Name des Automaten.
     */
    private StringProperty name;

    /**
     * ID zur Identifizierung des Automaten.
     */
    private StringProperty id;

    /**
     * Liste mit den auf dem Automaten erreichten Highscores.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Liste der auf dem Automaten gesetzten Elemente.
     */
    private ListProperty<PlacedElement> elements;

    /**
     * Gibt an, ob die Flipperautomatenelemente {@code elements} geladen wurde.
     */
    private boolean elementsLoaded;

    /**
     * Erstellt einen leeren Automaten mit gegebenen Namen, ID und bisher erreichten Highscores.
     *
     * @param name                  Name des Automaten.
     * @param pinballMachineId      Id des Automaten.
     * @param highscores            Die auf diesem Automaten bisher erreichten Highscores.
     * @param pinballMachineManager Der PinballMachineManager, welcher diese PinballMachine verwaltet.
     */
    PinballMachine(String name, String pinballMachineId, List<Highscore> highscores, PinballMachineManager pinballMachineManager)
    {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(pinballMachineId);
        this.pinballMachineManager = pinballMachineManager;

        // Set up element list
        elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        //ListPropertyConverter.autoSort(elements, PlacedElement::compare);
        elementsLoaded = false;

        // Fügt die Highscores zu highscoreList hinzu und lässt sie automatisch sortieren, wenn sie sich ändert
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        if (highscores != null)
        {
            highscoreList.addAll(highscores);
        }
        ListPropertyConverter.autoSort(highscoreList, (o1, o2) -> (int) (o2.scoreProperty().get() - o1.scoreProperty().get()));

        // Entfernt Highscore Einträge, solange zuviele existieren
        while (highscoreList.size() > Config.maxHighscores) highscoreList.remove(highscoreList.size() - 1);
    }

    /**
     * Überprüft, ob sich ein Bahnelement an der gegebenen Position befindet.
     *
     * @param point Die Position.
     * @return Das Bahnelement an der gegebenen Position.
     */
    public Optional<PlacedElement> getElementAt(Vector2 point)
    {
        checkElementsLoaded();
        for (int i = elements.size() - 1; i >= 0; i--)
        {
            PlacedElement placedElement = elements.get(i);
            Vector2 relativPoint = point.minus(placedElement.positionProperty().get());
            if (placedElement.getBaseElement().getPhysics().checkIfPointIsInElement(placedElement.rotationProperty().get(), relativPoint))
            {
                return Optional.of(elements.get(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Serialisiert und speichert diesen Automaten.
     */
    public void saveToDisk()
    {
        checkElementsLoaded();
        pinballMachineManager.savePinballMachine(this);
        unloadElements();
    }

    /**
     * Löscht die gespeicherte und geladene Version dieses Automaten.
     */
    public void deleteFromDisk()
    {
        pinballMachineManager.deleteMachine(this);
    }

    /**
     * Fügt den gegebenen Highscore zur Liste der Highscores des Automaten hinzu, falls der Highscore gutgenug ist.
     *
     * @param highscore Der Highscore, der hinzugefügt werden soll.
     */
    public void addHighscore(Highscore highscore)
    {
        if (highscoreList.size() >= Config.maxHighscores)
        {
            Highscore worstHigscore = highscoreList.get(highscoreList.size() - 1);
            if (worstHigscore.scoreProperty().get() < highscore.scoreProperty().get())
            {
                highscoreList.remove(worstHigscore);
                highscoreList.add(highscore);
                saveToDisk();
            }
        }
        else
        {
            highscoreList.add(highscore);
            saveToDisk();
        }
    }

    /**
     * Fügt das gegebene PlacedElement zur Liste der Bahnelemente {@code elements} hinzu.
     *
     * @param placedElement Das einzufügende Element.
     */
    public void addElement(PlacedElement placedElement)
    {
        checkElementsLoaded();
        elements.add(placedElement);
    }

    /**
     * Erstellt ein PlacedElement aus den gegebenen Werten und fügt es zur Liste der Bahnelemente {@code elements} hinzu.
     *
     * @param baseElement Der Bauplan des einzufügenden Elements.
     * @param position    Die Position des einzufügenden Elements.
     * @return Das erstellte und hinzugefügte Bahnelement.
     */
    public PlacedElement addElement(BaseElement baseElement, Vector2 position)
    {
        checkElementsLoaded();
        PlacedElement placedElement = new PlacedElement(baseElement, position, 1, 1, 0);
        addElement(placedElement);
        return placedElement;
    }

    /**
     * Entfernt das gegebene Element von dem Automaten.
     *
     * @param placedElement Das zu entfernende Element.
     */
    public void removeElement(PlacedElement placedElement)
    {
        checkElementsLoaded();
        elements.remove(placedElement);
    }

    /**
     * Verwirft den geladenen Automaten.
     */
    public void unloadElements()
    {
        if (elementsLoaded)
        {
            elementsLoaded = false;
            elements.clear();
        }
    }

    /**
     * Lädt, falls noch nicht geladen, die Elemente aus der gespeicherten Form des Automaten.
     */
    private void checkElementsLoaded()
    {
        if (!elementsLoaded)
        {
            elementsLoaded = true;
            pinballMachineManager.loadMachineElements(this);
        }
    }

    /**
     * Gibt den Namen des Flipperautomaten zurück.
     *
     * @return Der Name des Flipperautomaten.
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * Gibt die Liste der Highscores des Flipperautomaten zurück.
     *
     * @return Die Liste der Highscores des Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    /**
     * Gibt die Liste der Elemente des Flipperautomaten zurück.
     *
     * @return Die Liste der Elemente des Flipperautomaten zurück.
     */
    public ReadOnlyListProperty<PlacedElement> elementsProperty()
    {
        checkElementsLoaded();
        return elements;
    }

    /**
     * Gibt den Speicherpfad des Hintergrundbildes des Automaten zurück.
     *
     * @return Der Speicherpfad des Hintergrundbildes des Automaten.
     */
    public String getImagePath()
    {
        return DataPath.pathToPinballMachineImagePreview(id.get());
    }

    /**
     * Gibt die ID zur Identifizierung des Automaten zurück.
     *
     * @return Die ID zur Identifizierung des Automaten.
     */
    public String getID()
    {
        return id.getValue();
    }
}
