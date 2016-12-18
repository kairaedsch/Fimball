package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.image.WritableImage;
import sep.fimball.general.data.*;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.base.BaseElementType;

import java.util.List;
import java.util.Optional;

/**
 * Eine PinballMachine stellt einen Flipperautomaten dar und enthält allgemeine Informationen über den Automaten sowie über die im Automaten platzierten Elemente.
 */
public class PinballMachine
{
    /**
     * Der PinballMachineManager welcher die PinballMachines verwaltet.
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
    private ObservableList<Highscore> highscoreList;

    /**
     * Liste mit den auf dem Automaten erreichten Highscores (Sortiert).
     */
    private ListProperty<Highscore> highscoreListSorted;

    /**
     * Liste der auf dem Automaten gesetzten Elemente.
     */
    private ListProperty<PlacedElement> elements;

    /**
     * Gibt an, ob die Flipperautomaten-Elemente {@code elements} geladen wurde.
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
        elementsLoaded = false;

        // Fügt die Highscores zu Highscore-liste hinzu und lässt sie automatisch sortieren, wenn sie sich ändert
        highscoreList = FXCollections.observableArrayList();
        highscoreListSorted = new SimpleListProperty<>(new SortedList<>(highscoreList, (o1, o2) -> (int) (o2.scoreProperty().get() - o1.scoreProperty().get())));

        for (Highscore highscore : highscores)
        {
            addHighscore(highscore, false);
        }
    }

    /**
     * Überprüft, ob sich ein Bahnelement an der gegebenen Position befindet. Falls dies der Fall ist wird es zurückgegeben.
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

            if (placedElement.getBaseElement().getPhysics().checkIfPointIsInElement(placedElement.rotationProperty().get(), point.minus(placedElement.positionProperty().get())))
            {
                return Optional.of(elements.get(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Gibt die Elemente zurück, die sich innerhalb der Vierecks befinden.
     *
     * @param rect Das Viereck.
     * @return Die Elemente.
     */
    public ListProperty<PlacedElement> getElementsAt(RectangleDoubleByPoints rect)
    {
        ListProperty<PlacedElement> matchingElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (PlacedElement element : elements)
        {
            Vector2 elemPos = element.positionProperty().get();

            Vector2 relToOrigin = elemPos.minus(rect.getOrigin());
            Vector2 relToEnd = elemPos.minus(rect.getEnd());

            if (relToOrigin.getX() > 0 && relToOrigin.getY() > 0 && relToEnd.getX() < 0 && relToEnd.getY() < 0)
            {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    /**
     * Gibt eine Box, die das Spielfeld umschließt, zurück.
     *
     * @return Eine Box, die das Spielfeld umschließt.
     */
    public RectangleDouble getBoundingBox()
    {
        Vector2 max = elements.stream().map(element -> element.positionProperty().get().plus(element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), true))).reduce(Vector2::maxComponents).get();
        Vector2 origin = elements.stream().map(element -> element.positionProperty().get().plus(element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), false))).reduce(Vector2::minComponents).get();

        double width = Math.abs(max.getX() - origin.getX());
        double height = Math.abs(max.getY() - origin.getY());
        return new RectangleDouble(origin.minus(new Vector2(2, 2)), width + 4, height + 4);
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
     * Speichert das gegebene Vorschaubild.
     *
     * @param image Das Vorschaubild, das gespeichert werden soll.
     */
    public void savePreviewImage(WritableImage image)
    {
        pinballMachineManager.savePreviewImage(this, image);
    }

    /**
     * Löscht die gespeicherte und geladene Version dieses Automaten.
     */
    public void deleteFromDisk()
    {
        pinballMachineManager.deleteMachine(this);
    }

    /**
     * Fügt den gegebenen Highscore zur Liste der Highscores des Automaten hinzu, falls der Highscore gut genug ist.
     *
     * @param highscore Der Highscore, der hinzugefügt werden soll.
     */
    public void addHighscore(Highscore highscore)
    {
        addHighscore(highscore, true);
    }

    /**
     * Fügt den gegebenen Highscore zur Liste der Highscores des Automaten hinzu, falls der Highscore gut genug ist.
     *
     * @param highscore Der Highscore, der hinzugefügt werden soll.
     * @param save      Ob gespeichert werden soll.
     */
    private void addHighscore(Highscore highscore, boolean save)
    {
        if (highscoreList.size() >= Config.MAX_HIGHSCORES)
        {
            Highscore worstHighscore = highscoreListSorted.get(highscoreListSorted.size() - 1);
            if (worstHighscore.scoreProperty().get() < highscore.scoreProperty().get())
            {
                highscoreList.remove(worstHighscore);
                highscoreList.add(highscore);
                if (save)
                    saveToDisk();
            }
        }
        else
        {
            highscoreList.add(highscore);
            if (save)
                saveToDisk();
        }
    }

    /**
     * Fügt das gegebene PlacedElement zur Liste der Bahnelemente {@code elements} hinzu. Falls ein zweiter Ball hinzugefügt werden soll wird der alte gelöscht.
     *
     * @param placedElement Das einzufügende Element.
     */
    public void addElement(PlacedElement placedElement)
    {
        checkElementsLoaded();

        if (placedElement.getBaseElement().getType() == BaseElementType.BALL)
        {
            elements.removeIf((element -> element.getBaseElement().getType() == BaseElementType.BALL));
        }

        if (!elements.contains(placedElement))
        {
            elements.add(placedElement);
        }
    }

    /**
     * Erstellt ein PlacedElement aus den gegebenen Werten und fügt es zur Liste der Bahnelemente {@code elements} hinzu. Falls ein zweiter Ball hinzugefügt werden soll wird der alte gelöscht.
     *
     * @param baseElement Der Bauplan des einzufügenden Elements.
     * @param position    Die Position des einzufügenden Elements.
     * @return Das erstellte und hinzugefügte Bahnelement.
     */
    public PlacedElement addElement(BaseElement baseElement, Vector2 position)
    {
        checkElementsLoaded();

        PlacedElement placedElement = new PlacedElement(baseElement, position, 0, 0, 0);
        addElement(placedElement);
        return placedElement;
    }

    /**
     * Entfernt das gegebene Element von dem Automaten. Der Ball kann nicht gelöscht werden.
     *
     * @param placedElement Das zu entfernende Element.
     */
    public void removeElement(PlacedElement placedElement)
    {
        checkElementsLoaded();

        if (placedElement.getBaseElement().getType() != BaseElementType.BALL)
        {
            elements.remove(placedElement);
        }
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
            addElement(BaseElementManager.getInstance().getElement("ball"), new Vector2());
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
        return highscoreListSorted;
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
        return pinballMachineManager.loadPreviewImage(this);
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
