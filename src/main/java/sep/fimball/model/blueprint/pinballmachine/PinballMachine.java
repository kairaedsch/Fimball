package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.image.WritableImage;
import sep.fimball.general.data.*;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.base.BaseElementType;

import java.util.*;

import static sep.fimball.general.data.Config.MACHINE_BOX_MARGIN;
import static sep.fimball.general.data.Config.MACHINE_BOX_MIN_WIDTH;

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
     * Pfad zum Vorschaubild des Flipperautomaten.
     */
    private ObjectProperty<Optional<String>> previewImagePath;

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
     * Die sortierte Liste der platzierten Elemente. Diese werden sortiert um in der korrekten Reihenfolge gezeichnet zu werden.
     */
    private Optional<ListProperty<PlacedElement>> sortedElements;

    /**
     * Gibt an, ob die Flipperautomaten-Elemente {@code elements} geladen wurde.
     */
    private boolean elementsLoaded;

    /**
     * Erstellt einen leeren Automaten mit gegebenen Namen, ID und bisher erreichten Highscores.
     *
     * @param name                  Name des Automaten.
     * @param pinballMachineId      Id des Automaten.
     * @param previewImagePath      Namen der Vorschaubildes des Automaten.
     * @param highscores            Die auf diesem Automaten bisher erreichten Highscores.
     * @param pinballMachineManager Der PinballMachineManager, welcher diese PinballMachine verwaltet.
     * @param loaded                Gibt an ob die Elemente schon geladen sind.
     */
    PinballMachine(String name, String pinballMachineId, Optional<String> previewImagePath, List<Highscore> highscores, PinballMachineManager pinballMachineManager, boolean loaded)
    {
        this.name = new SimpleStringProperty(name);
        this.previewImagePath = new SimpleObjectProperty<>();
        this.previewImagePath.set(previewImagePath);
        this.id = new SimpleStringProperty(pinballMachineId);
        this.pinballMachineManager = pinballMachineManager;

        // Set up element list
        elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        sortedElements = Optional.empty();
        elementsLoaded = loaded;
        if (loaded)
            addElement(BaseElementManager.getInstance().getElement("ball"), new Vector2());

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
        return PinballMachineUtil.getElementAt(point, elementsProperty());
    }

    /**
     * Gibt die Elemente zurück, die sich innerhalb der Vierecks befinden.
     *
     * @param rect Das Viereck.
     * @return Die Elemente.
     */
    public List<PlacedElement> getElementsAt(RectangleDoubleByPoints rect)
    {
        return PinballMachineUtil.getElementsAt(rect, elements);
    }

    /**
     * Gibt eine Box, die das Spielfeld umschließt, zurück.
     *
     * @return Eine Box, die das Spielfeld umschließt.
     */
    public RectangleDouble getBoundingBox()
    {
        RectangleDouble boundingBox = PinballMachineUtil.getBoundingBox(elements);
        Vector2 newOrigin = boundingBox.getOrigin().minus(MACHINE_BOX_MARGIN);
        Vector2 newSize = boundingBox.getSize().plus(MACHINE_BOX_MARGIN.scale(2));
        if (newSize.getX() < MACHINE_BOX_MIN_WIDTH)
        {
            double missingWidth = MACHINE_BOX_MIN_WIDTH - newSize.getX();
            newOrigin = new Vector2(newOrigin.getX() - missingWidth / 2D, newOrigin.getY());
            newSize = new Vector2(newSize.getX() + missingWidth, newSize.getY());
        }
        return new RectangleDouble(newOrigin, newSize);
    }

    /**
     * Gibt die unterste Position das Automaten zurück.
     *
     * @return Die unterste Position das Automaten.
     */
    public double getMaximumYPosition()
    {
        RectangleDouble machineBox = getBoundingBox();
        return machineBox.getOrigin().getY() + machineBox.getHeight();
    }

    /**
     * Serialisiert und speichert diesen Automaten.
     * Falls erfolgreich, werden die Elemente der PinballMachine wieder aus dem Ram entladen.
     *
     * @param unloadElements Gibt an, ob die geladenen Elemente des Automaten verworfen werden sollen.
     * @param saveElements   Gibt an, ob die Elemente des Automaten gespeichert werden sollen.
     * @return Ob die PinballMachine gespeichert werden konnte.
     */
    public boolean saveToDisk(boolean unloadElements, boolean saveElements)
    {
        checkElementsLoaded();
        boolean success = pinballMachineManager.savePinballMachine(this, saveElements ? PinballMachineManager.SaveMode.ALL : PinballMachineManager.SaveMode.WITHOUT_ELEMENTS);
        if (success && unloadElements)
            unloadElements();
        return success;
    }

    /**
     * Speichert das gegebene Vorschaubild.
     *
     * @param image Das Vorschaubild, das gespeichert werden soll.
     */
    public void savePreviewImage(WritableImage image)
    {
        String newPreviewImagePath = getID() + System.currentTimeMillis();
        pinballMachineManager.savePreviewImage(this, image, newPreviewImagePath);
        previewImagePath.set(Optional.of(newPreviewImagePath));
    }

    /**
     * Löscht die gespeicherte und geladene Version dieses Automaten.
     *
     * @return Gibt zurück ob diese erfolgreich gelöscht wurde.
     */
    public boolean deleteFromDisk()
    {
        return pinballMachineManager.deleteMachine(this, false);
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

        if (!elements.contains(placedElement)) elements.add(placedElement);
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

        PlacedElement placedElement = new PlacedElement(baseElement, position, 0, 1, 0);
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
            sortedElements = Optional.empty();
            elements = new SimpleListProperty<>(FXCollections.observableArrayList());
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
        if (!sortedElements.isPresent())
        {
            sortedElements = Optional.of(new SimpleListProperty<>(new SortedList<>(elements, PlacedElement::compare)));
        }
        return sortedElements.get();
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

    /**
     * Gibt den Speicherpfad zum Hintergrundbildes des Automaten zurück.
     * Falls kein Bild vorliegt wird der Pfad zum default-Hintergrundbild zurückgegeben.
     *
     * @return Der Speicherpfad zu einem Hintergrundbild.
     */
    public ReadOnlyStringProperty absolutePreviewImagePathProperty()
    {
        StringProperty stringProperty = new SimpleStringProperty();
        stringProperty.bind(Bindings.createStringBinding(() -> previewImagePath.get().isPresent() ? DataPath.generatePathToNewImagePreview(getID(), previewImagePath.get().get()) : DataPath.pathToDefaultPreview(), previewImagePath));
        return stringProperty;
    }

    /**
     * Gibt den relativen Speicherpfad des Hintergrundbildes des Automaten zurück.
     * Der Pfad ist relativ zu dem Ordner des Automaten.
     * Falls kein Bild vorliegt ist das Optional leer.
     *
     * @return Der relativen Speicherpfad des Hintergrundbildes des Automaten oder ein leeres Optional.
     */
    public ReadOnlyObjectProperty<Optional<String>> relativePreviewImagePathProperty()
    {
        return previewImagePath;
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
                    saveToDisk(false, false);
            }
        }
        else
        {
            highscoreList.add(highscore);
            if (save)
                saveToDisk(false, false);
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
            if (!elements.stream().anyMatch((element -> element.getBaseElement().getType() == BaseElementType.BALL)))
            {
                addElement(BaseElementManager.getInstance().getElement("ball"), new Vector2());
            }
        }
    }

    /**
     * Erstellt einen neue, leere PinballMachine.
     *
     * @param pinballMachineManager Der PinballMachineManager, welcher diese PinballMachine verwaltet.
     */
    private PinballMachine(PinballMachineManager pinballMachineManager)
    {
        this.pinballMachineManager = pinballMachineManager;
    }
}
