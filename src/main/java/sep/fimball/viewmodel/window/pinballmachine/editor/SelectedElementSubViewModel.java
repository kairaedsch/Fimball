package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;

import java.util.Optional;

/**
 * Das SelectedElementSubViewModel stellt der View Daten über das aktuell ausgewählte Flipperautomat-Element im Flipperautomat bereit und ermöglicht dem Nutzer, die Eigenschaften dieses Elements anzupassen.
 */
public class SelectedElementSubViewModel
{
    /**
     * Das Flipperautomat-Element, das aktuell ausgewählt ist.
     */
    private Optional<PlacedElement> placedElement;

    /**
     * Der Name des Flipperautomat-Elements.
     */
    private StringProperty name;

    /**
     * Die Beschreibung des Flipperautomat-Elements.
     */
    private StringProperty description;

    /**
     * Die Anzahl der Punkte, die dem Spieler bei einer Kollision mit diesem Element hinzugefügt werden.
     */
    private IntegerProperty points;

    /**
     * Ein Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert.
     */
    private DoubleProperty multiplier;

    /**
     * Gibt an, ob aktuell ein Element auf dem Spielfeld ausgewählt ist.
     */
    private BooleanProperty isSomethingSelected;

    /**
     * Der zugehörige PinballMachineEditor.
     */
    private PinballMachineEditor pinballMachineEditor;

    /**
     * Gibt an, ob die Punkte, die dieses Element vergibt, verändert werden dürfen.
     */
    private BooleanProperty pointsCanBeChanged;

    /**
     * Gibt an ob die Auswahl rotiert werden kann.
     */
    private BooleanProperty rotateAvailable;

    /**
     * Gibt an ob die Auswahl dupliziert werden kann.
     */
    private BooleanProperty duplicateAvailable;

    /**
     * Gibt an ob die Auswahl gelöscht werden kann.
     */
    private BooleanProperty removeAvailable;

    /**
     * Erstellt ein neues SelectedElementSubViewModel.
     *
     * @param pinballMachineEditor Der zugehörige PinballMachineEditor
     */
    SelectedElementSubViewModel(PinballMachineEditor pinballMachineEditor)
    {
        isSomethingSelected = new SimpleBooleanProperty();
        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
        points = new SimpleIntegerProperty();
        multiplier = new SimpleDoubleProperty();
        placedElement = Optional.empty();
        this.pinballMachineEditor = pinballMachineEditor;
        pointsCanBeChanged = new SimpleBooleanProperty(false);
        rotateAvailable = new SimpleBooleanProperty();
        duplicateAvailable = new SimpleBooleanProperty();
        removeAvailable = new SimpleBooleanProperty();

        setPlacedElement(Optional.empty());

        ListProperty<PlacedElement> selection = new SimpleListProperty<>(pinballMachineEditor.getSelection());

        selection.addListener((observableValue, placedElements, t1) ->
        {
            if (selection.size() == 1)
            {
                setPlacedElement(Optional.of(selection.get(0)));
            }
            else
            {
                setPlacedElement(Optional.empty());
            }

            rotateAvailable.set(selection.size() > 0 && selection.stream().allMatch(placedElement1 -> placedElement1.getBaseElement().getMedia().canRotate()));
            duplicateAvailable.set(selection.stream().anyMatch(placedElement1 -> !placedElement1.getBaseElement().getId().equals("ball")));
            removeAvailable.set(!selection.stream().allMatch(placedElement1 -> placedElement1.getBaseElement().getId().equals("ball")));
        });

    }

    /**
     * Setzt das Flipperautomat-Element, das aktuell ausgewählt ist, auf das gegebene Element.
     *
     * @param newPlacedElement Das neue Flipperautomat-Element, das aktuell ausgewählt ist.
     */
    private void setPlacedElement(Optional<PlacedElement> newPlacedElement)
    {
        if (placedElement.isPresent())
        {
            points.unbindBidirectional(placedElement.get().pointsProperty());
            multiplier.unbindBidirectional(placedElement.get().multiplierProperty());
        }

        if (newPlacedElement.isPresent())
        {
            name.set(newPlacedElement.get().getBaseElement().getMedia().getName(Settings.getSingletonInstance().languageProperty().get()));
            description.set(newPlacedElement.get().getBaseElement().getMedia().getDescription(Settings.getSingletonInstance().languageProperty().get()));

            points.bindBidirectional(newPlacedElement.get().pointsProperty());

            multiplier.bindBidirectional(newPlacedElement.get().multiplierProperty());
            isSomethingSelected.set(true);
            pointsCanBeChanged.set(newPlacedElement.get().getBaseElement().getRule().getEventMap().entrySet().stream().anyMatch((r) -> r.getValue().givesPoints()));
        }
        else
        {
            name.set("");
            description.set("");
            isSomethingSelected.set(false);
        }

        this.placedElement = newPlacedElement;
    }

    /**
     * Stellt der View den Namen des Flipperautomat-Elements zur Verfügung.
     *
     * @return Der Name des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt der View die Beschreibung des Flipperautomat-Elements zur Verfügung.
     *
     * @return Die Beschreibung des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty descriptionProperty()
    {
        return description;
    }

    /**
     * Stellt der View die Anzahl der Punkte des Flipperautomat-Elements, die dem Spieler bei einer Kollision mit diesem hinzugefügt werden, zur Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Die Anzahl der Punkte des Flipperautomat-Elements, die dem Spieler bei einer Kollision mit diesem hinzugefügt werden.
     */
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Stellt der View den Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert, zur Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Der Multiplier, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert,
     */
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    /**
     * Entfernt die ausgewählten Elemente aus dem Flipper-Automaten.
     */
    public void remove()
    {
        pinballMachineEditor.removeSelection();
        pinballMachineEditor.clearSelection();
    }

    /**
     * Dupliziert die ausgewählten Elemente in dem Flipper-Automaten.
     */
    public void duplicate()
    {
        pinballMachineEditor.duplicateSelection();
    }


    /**
     * Entfernt die ausgewählten Flipper-Automaten.
     */
    public void rotate()
    {
        pinballMachineEditor.rotateSelection();
    }

    /**
     * Gibt zurück, ob die Punkte, die dieses Element vergibt, verändert werden dürfen.
     *
     * @return {@code true}, falls die Punkte verändert werden dürfen, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty pointsCanBeChanged()
    {
        return pointsCanBeChanged;
    }

    /**
     * Gibt zurück, ob ein einzelnes Element ausgewählt ist.
     *
     * @return {@code true}, falls ein einzelnes Element ausgewählt ist, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty isSomethingSelected()
    {
        return isSomethingSelected;
    }

    /**
     * Gibt zurück, ob die Auswahl rotiert werden kann.
     *
     * @return Ob die Auswahl rotiert werden kann.
     */
    public ReadOnlyBooleanProperty isRotateAvailable()
    {
        return rotateAvailable;
    }

    /**
     * Gibt zurück, ob die Auswahl dupliziert werden kann.
     *
     * @return Ob die Auswahl dupliziert werden kann.
     */
    public ReadOnlyBooleanProperty isDuplicateAvailable()
    {
        return duplicateAvailable;
    }

    /**
     * Gibt zurück, ob die Auswahl gelöscht werden kann.
     *
     * @return Ob die Auswahl gelöscht werden kann.
     */
    public ReadOnlyBooleanProperty isRemoveAvailable()
    {
        return removeAvailable;
    }
}
