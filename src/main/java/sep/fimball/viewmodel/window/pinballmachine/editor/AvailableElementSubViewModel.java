package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.Element;

/**
 * Created by kaira on 03.11.2016.
 */
public class AvailableElementSubViewModel
{
    private IntegerProperty elementId;
    private StringProperty imagePath;
    private StringProperty name;

    public AvailableElementSubViewModel(Element element)
    {
        elementId = new SimpleIntegerProperty();
        imagePath = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }
}
