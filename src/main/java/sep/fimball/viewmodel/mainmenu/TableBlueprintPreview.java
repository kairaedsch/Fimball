package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.*;
import javafx.scene.image.Image;

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

    public ReadOnlyStringProperty getNameProperty()
    {
        return name;
    }

    public SimpleStringProperty getImagePathProperty()
    {
        return imagePath;
    }
}
