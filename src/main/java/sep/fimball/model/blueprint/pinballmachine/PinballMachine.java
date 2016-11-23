package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;

import java.util.List;
import java.util.Optional;

/**
 * Eine PinballMachine stellt einen Flipperautomaten dar und enthält allgemeine Informationen über den Automaten sowie über die im Automaten platzierten Elemente.
 */
public class PinballMachine
{
    /**
     * Der Name des Automaten.
     */
    private StringProperty name;

    /**
     * ID zur Identifizierung des Automaten.
     */
    private StringProperty pinballMachineId;

    /**
     * Liste mit den auf dem Automaten erreichten Highscores.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Liste der auf dem Automaten gesetzten Elemente.
     */
    private ListProperty<PlacedElement> elements;

    /**
     * Gibt an, ob die PinballMachine komplett aus der PinballMachineJson geladen wurde.
     */
    private boolean elementsLoaded;

    /**
     * Speicherpfad des Hintergrundbildes des Automaten.
     */
    private StringProperty imagePath;

    /**
     * Erstellt einen leeren Automaten mit gegebenen Namen, ID und bisher erreichten Highscores.
     *
     * @param name             Name des Automaten
     * @param pinballMachineId Id des Automaten
     * @param highscores       Die auf diesem Automaten bisher erreichten Highscores.
     */
    PinballMachine(String name, String pinballMachineId, List<Highscore> highscores)
    {
        this.name = new SimpleStringProperty(name);
        this.pinballMachineId = new SimpleStringProperty(pinballMachineId);

        this.elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementsLoaded = false;

        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        if (highscores != null)
            this.highscoreList.addAll(highscores);

        this.imagePath = new SimpleStringProperty(Config.pathToPinballMachineImagePreview(pinballMachineId));
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
            for (Collider collider : elements.get(i).getBaseElement().getPhysics().getColliders())
            {
                for (ColliderShape shape : collider.getShapes())
                {
                    RectangleDouble boundingBox = shape.getBoundingBox(elements.get(i).rotationProperty().get(), elements.get(i).getBaseElement().getPhysics().getPivotPoint());
                    Vector2 globalPosition = elements.get(i).positionProperty().get();
                    double minX = boundingBox.getOrigin().getX() + globalPosition.getX();
                    double minY = boundingBox.getOrigin().getY() + globalPosition.getY();
                    double maxX = minX + boundingBox.getWidth();
                    double maxY = minY + boundingBox.getHeight();

                    if (point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY)
                    {
                        return Optional.of(elements.get(i));
                    }
                }
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
        PinballMachineManager.getInstance().savePinballMachine(this);
        unloadElements();
    }

    /**
     * Löscht die gespeicherte und geladene Version dieses Automaten.
     */
    public void deleteFromDisk()
    {
        PinballMachineManager.getInstance().deleteMachine(this);
    }

    /**
     * Fügt den gegebenen Highscore zur Liste der Highscores des Automaten hinzu.
     *
     * @param highscore Der Highscore, der hinzugefügt werden soll.
     */
    public void addHighscore(Highscore highscore)
    {
        highscoreList.add(highscore);
        if (highscoreList.size() > Config.maxHighscores)
            highscoreList.remove(0);
    }

    /**
     * Fügt das gegebene Element zur Liste der Bahnelemente hinzu.
     *
     * @param placedElement Das einzufügende Element.
     */
    public void addElement(PlacedElement placedElement)
    {
        checkElementsLoaded();
        elements.add(placedElement);
    }

    /**
     * Erstellt ein PlacedElement aus den gegebenen Werten und fügt es zur Liste der Bahnelemente hinzu.
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
     * Lädt, falls nötig, Elemente aus der gespeicherten Form des Automaten.
     */
    private void checkElementsLoaded()
    {
        if (!elementsLoaded)
        {
            elementsLoaded = true;
            PinballMachineManager.getInstance().loadMachineElements(this);
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
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    /**
     * Setzt den Namen des Flipperautomaten auf den übergebenen Wert.
     *
     * @param name Der neue Name des Flipperautomaten.
     */
    public void setName(String name)
    {
        this.name.set(name);
    }

    /**
     * Gibt die ID zur Identifizierung des Automaten zurück.
     * @return Die ID zur Identifizierung des Automaten.
     */
    public String getID()
    {
        return pinballMachineId.getValue();
    }
}
