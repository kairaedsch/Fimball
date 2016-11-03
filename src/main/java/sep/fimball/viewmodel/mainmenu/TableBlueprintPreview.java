package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.tableblueprint.TableBlueprint;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintPreview
{
    SimpleStringProperty name;
    SimpleStringProperty imagePath;

    public TableBlueprintPreview(SimpleStringProperty nameProperty, SimpleStringProperty imagePathProperty)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();

        name.bind(nameProperty);
        imagePath.bind(imagePathProperty);
    }

    public TableBlueprintPreview(TableBlueprint tableBlueprint)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();

        name.bind(tableBlueprint.nameProperty());
        imagePath.bind(tableBlueprint.imagePathProperty());
    }

    public ReadOnlyStringProperty getNameProperty()
    {
        return name;
    }

    public SimpleStringProperty getImagePathProperty()
    {
        return imagePath;
    }
}
