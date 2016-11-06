package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.PlacedElement;

/**
 * Created by kaira on 03.11.2016.
 */
public class SelectedElementSubViewModel
{
    PlacedElement placedElement;

    private StringProperty name;
    private StringProperty description;
    private IntegerProperty points;
    private DoubleProperty multiplier;

    public SelectedElementSubViewModel(PlacedElement placedElement)
    {
        this.placedElement = placedElement;

        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
        points = new SimpleIntegerProperty();
        multiplier = new SimpleDoubleProperty();
    }

    public void rotateRightClicked()
    {

    }

    public void rotateLeftClicked()
    {

    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty descriptionProperty()
    {
        return description;
    }

    // TODO bind bidirect
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    // TODO bind bidirect
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }
}
