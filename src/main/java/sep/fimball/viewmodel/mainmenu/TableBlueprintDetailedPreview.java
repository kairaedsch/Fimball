package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintDetailedPreview
{
    SimpleIntegerProperty id;
    SimpleStringProperty name;
    SimpleStringProperty imagePath;
    SimpleListProperty<TableBlueprintDetailedPreviewHighscoreEntry> highscore;

    public TableBlueprintDetailedPreview(SimpleIntegerProperty idProperty, SimpleStringProperty nameProperty, SimpleStringProperty imagePathProperty)
    {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();

        id.bind(idProperty);
        name.bind(nameProperty);
        imagePath.bind(imagePathProperty);
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyIntegerProperty idProperty()
    {
        return id;
    }


}
