package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

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
     * Erstellt ein neues SelectedElementSubViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der das ausgewählte Element enthält.
     */
    public SelectedElementSubViewModel(ReadOnlyListProperty<PlacedElement> selection)
    {
        isSomethingSelected = new SimpleBooleanProperty();
        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
        points = new SimpleIntegerProperty();
        multiplier = new SimpleDoubleProperty();
        placedElement = Optional.empty();

        setPlacedElement(Optional.empty());

        selection.addListener((observableValue, placedElements, t1) ->
        {
            if (selection.size() == 1)
            {
                setPlacedElement(Optional.of(selection.get(0)));
            } else {
                setPlacedElement(Optional.empty());
            }
        });
    }

    /**
     * Setzt das Flipperautomat-Element, das aktuell ausgewählt ist, auf das gegebene Element.
     *
     * @param newPlacedElement Das neue Flipperautomat-Element, das aktuell ausgewählt ist.
     */
    public void setPlacedElement(Optional<PlacedElement> newPlacedElement)
    {
        if (placedElement.isPresent())
        {
            points.unbindBidirectional(placedElement.get().pointsProperty());
            multiplier.unbindBidirectional(placedElement.get().multiplierProperty());
        }

        if (newPlacedElement.isPresent())
        {
            name.set(newPlacedElement.get().getBaseElement().getMedia().getName());
            description.set(newPlacedElement.get().getBaseElement().getMedia().getDescription());

            points.bindBidirectional(newPlacedElement.get().pointsProperty());

            multiplier.bindBidirectional(newPlacedElement.get().multiplierProperty());
            isSomethingSelected.set(true);
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
     * Gibt an, ob aktuell ein Element auf dem Spielfeld ausgewählt ist.
     *
     * @return {@code true} falls ein Element ausgewählt ist, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty isSomethingSelectedProperty()
    {
        return isSomethingSelected;
    }
}
