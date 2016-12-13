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
        addElement(BaseElementManager.getInstance().getElement("ball"), new Vector2());
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

            if (placedElement.getBaseElement().getPhysics().checkIfPointIsInElement(placedElement.rotationProperty().get(), point, placedElement.positionProperty().get()))
            {
                return Optional.of(elements.get(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Gibt eine Box, die das Spielfeld umschließt, zurück.
     * @return Eine Box, die das Spielfeld umschließt.
     */
    public RectangleDouble getBoundingBox()
    {
        Vector2 max = Vector2.getExtremeVector(elements, true, placedElement -> placedElement.positionProperty().get().plus(placedElement.getBaseElement().getPhysics().getExtremePos(placedElement.rotationProperty().get(), true)));
        Vector2 origin = Vector2.getExtremeVector(elements, false, placedElement -> placedElement.positionProperty().get().plus(placedElement.getBaseElement().getPhysics().getExtremePos(placedElement.rotationProperty().get(), false)));

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
        if (highscoreList.size() >= Config.maxHighscores)
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
     * Fügt das gegebene PlacedElement zur Liste der Bahnelemente {@code elements} hinzu.
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

        if (baseElement.getType() == BaseElementType.BALL)
        {
            elements.removeIf((element -> element.getBaseElement().getType() == BaseElementType.BALL));
        }
        PlacedElement placedElement = new PlacedElement(baseElement, position, 0, 0, 0);
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

        if (placedElement.getBaseElement().getType() != BaseElementType.BALL)
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
