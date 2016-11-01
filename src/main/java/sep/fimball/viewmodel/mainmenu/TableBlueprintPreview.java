package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.*;
import javafx.scene.image.Image;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintPreview
{
    SimpleObjectProperty<Image> image;
    SimpleStringProperty name;

    public TableBlueprintPreview(SimpleObjectProperty<Image> image, SimpleStringProperty name)
    {
        this.image = image;
        this.name = name;
    }

    public ReadOnlyStringProperty getStringProperty()
    {
        return name;
    }

    public ReadOnlyObjectProperty<Image> getObjectProperty()
    {
        return image;
    }
}
