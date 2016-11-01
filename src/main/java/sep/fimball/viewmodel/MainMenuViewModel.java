package sep.fimball.viewmodel;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    private SimpleListProperty<TableBlueprintPreview> tableBlueprintPreviewList;

    public MainMenuViewModel()
    {
        tableBlueprintPreviewList = new SimpleListProperty(FXCollections.observableArrayList());
    }

    public ReadOnlyListProperty<TableBlueprintPreview> getTableBlueprintPreviewListProperty()
    {
        return tableBlueprintPreviewList;
    }

    public void settingsClicked()
    {
        ViewModelSceneManager.getInstance().setWindow(WindowType.TABLE_SETTINGS);
    }

    public void playClicked()
    {

    }

    public void editClicked()
    {

    }
}
