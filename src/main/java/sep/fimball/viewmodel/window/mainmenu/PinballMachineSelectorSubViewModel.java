package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.PinballMachine;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSelectorSubViewModel
{
    private StringProperty name;
    private StringProperty imagePath;
    private IntegerProperty blueprintTableId;

    PinballMachineSelectorSubViewModel(PinballMachine pinballMachine)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        blueprintTableId = new SimpleIntegerProperty();

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        blueprintTableId.bind(pinballMachine.blueprintTableIdProperty());
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyIntegerProperty blueprintTableIdProperty()
    {
        return blueprintTableId;
    }
}
