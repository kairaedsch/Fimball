package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import sep.fimball.model.elements.PinballMachine;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSelectorSubViewModel
{
    StringProperty name;
    StringProperty imagePath;
    IntegerProperty blueprintTableId;

    public PinballMachineSelectorSubViewModel(PinballMachine pinballMachine)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        blueprintTableId = new SimpleIntegerProperty();

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        blueprintTableId.bind(pinballMachine.blueprintTableIdProperty());
    }

    public ReadOnlyStringProperty getNameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty getImagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyIntegerProperty blueprintTableIdProperty()
    {
        return blueprintTableId;
    }
}
