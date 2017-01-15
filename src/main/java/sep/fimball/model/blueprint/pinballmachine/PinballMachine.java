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
    private ListProperty<PlacedElement> sortedElements;

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
        sortedElements = new SimpleListProperty<>(new SortedList<>(elements, PlacedElement::compare));
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

            Vector2 maxExtremePos = element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), true);
            Vector2 minExtremePos = element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), false);

            if (relToOrigin.plus(maxExtremePos).getX() > 0 && relToOrigin.plus(maxExtremePos).getY() > 0 && relToEnd.plus(minExtremePos).getX() < 0 && relToEnd.plus(minExtremePos).getY() < 0)
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
        Vector2 max = elements.stream().map(element -> element.positionProperty().get().plus(element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), true))).reduce(Vector2.NEGATIVE_INFINITY, Vector2::maxComponents);
        Vector2 origin = elements.stream().map(element -> element.positionProperty().get().plus(element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), false))).reduce(Vector2.POSITIVE_INFINITY, Vector2::minComponents);

        double width = Math.abs(max.getX() - origin.getX());
        double height = Math.abs(max.getY() - origin.getY());
        return new RectangleDouble(origin.minus(new Vector2(MACHINE_BOX_MARGIN, MACHINE_BOX_MARGIN)), width + MACHINE_BOX_MARGIN * 2, height + MACHINE_BOX_MARGIN * 2);
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
     * @return Ob die PinballMachine gespeichert werden konnte.
     */
    public boolean saveToDisk()
    {
        checkElementsLoaded();
        boolean success = pinballMachineManager.savePinballMachine(this);
        if (success)
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
        return pinballMachineManager.deleteMachine(this);
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
     * Fügt die gegebenen PlacedElements zur Liste der Bahnelemente {@code elements} hinzu. Falls ein zweiter Ball hinzugefügt werden soll wird der alte gelöscht.
     *
     * @param placedElements Die einzufügenden Elemente.
     */
    public void addElement(PlacedElement... placedElements)
    {
        checkElementsLoaded();

        List<PlacedElement> placedElementList = new ArrayList<>();
        placedElementList.addAll(Arrays.asList(placedElements));

        for (Iterator<PlacedElement> iterator = placedElementList.iterator(); iterator.hasNext(); )
        {
            PlacedElement placedElement = iterator.next();

            if (placedElement.getBaseElement().getType() == BaseElementType.BALL)
            {
                elements.removeIf((element -> element.getBaseElement().getType() == BaseElementType.BALL));
                elements.add(placedElement);
                iterator.remove();
            }
            else if (elements.contains(placedElement))
            {
                iterator.remove();
            }
        }

        elements.addAll(placedElementList);
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
            elements.clear();
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
        return sortedElements;
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
     * @return Der Speicherpfad zu einem Hintergrundbilde.
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
}
