package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.tableblueprint.PinballMachine;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSelectorSubViewModel
{
    SimpleStringProperty name;
    SimpleStringProperty imagePath;
    SimpleIntegerProperty blueprintTableId;

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

    public SimpleStringProperty getImagePathProperty()
    {
        return imagePath;
    }

    public SimpleIntegerProperty blueprintTableIdProperty()
    {
        return blueprintTableId;
    }
}
