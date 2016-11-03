package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.tableblueprint.TableBlueprint;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintPreview
{
    SimpleStringProperty name;
    SimpleStringProperty imagePath;
    SimpleIntegerProperty blueprintTableId;

    public TableBlueprintPreview(TableBlueprint tableBlueprint)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        blueprintTableId = new SimpleIntegerProperty();

        name.bind(tableBlueprint.nameProperty());
        imagePath.bind(tableBlueprint.imagePathProperty());
        blueprintTableId.bind(tableBlueprint.blueprintTableIdProperty());
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
