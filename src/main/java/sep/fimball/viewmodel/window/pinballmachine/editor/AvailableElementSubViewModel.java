package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.ElementType;

/**
 * Created by kaira on 03.11.2016.
 */
public class AvailableElementSubViewModel
{
    private ElementType elementType;

    private IntegerProperty elementId;
    private StringProperty imagePath;
    private StringProperty name;

    public AvailableElementSubViewModel(ElementType elementType)
    {
        this.elementType = elementType;
        elementId = new SimpleIntegerProperty();
        imagePath = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }

    public void dropOverPinballCanvas(Vector2 position)
    {

    }

    public ReadOnlyIntegerProperty elementIdProperty()
    {
        return elementId;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }
}
