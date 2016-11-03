package sep.fimball.viewmodel;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.model.tableblueprint.TableBlueprint;
import sep.fimball.model.tableblueprint.TableBlueprintManager;
import sep.fimball.viewmodel.mainmenu.TableBlueprintDetailedPreview;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    private SimpleListProperty<TableBlueprintPreview> tableBlueprintPreviewList;
    private TableBlueprintDetailedPreview tableBlueprintDetailedPreview;

    public MainMenuViewModel()
    {
        tableBlueprintPreviewList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(tableBlueprintPreviewList, TableBlueprintManager.getInstance().tableBlueprintsProperty(), TableBlueprintPreview::new);
        tableBlueprintDetailedPreview = new TableBlueprintDetailedPreview(TableBlueprintManager.getInstance().tableBlueprintsProperty().get(0));
    }

    public ReadOnlyListProperty<TableBlueprintPreview> tableBlueprintPreviewListProperty()
    {
        return tableBlueprintPreviewList;
    }

    public void settingsClicked()
    {
        ViewModelSceneManager.getInstance().setDialog(DialogType.GAME_SETTINGS);
    }

    public void playClicked()
    {
        ViewModelSceneManager.getInstance().setDialog(DialogType.PLAYER_NAMES);
    }

    public void editClicked()
    {
        ViewModelSceneManager.getInstance().setWindow(WindowType.TABLE_SETTINGS);
    }

    public void blueprintPreviewClick(int blueprintTableId)
    {
        TableBlueprint tableBlueprint = TableBlueprintManager.getInstance().tableBlueprintsProperty().get(blueprintTableId);
        if (tableBlueprint != null)
        {
            tableBlueprintDetailedPreview.update(tableBlueprint);
        }
    }

    public TableBlueprintDetailedPreview getTableBlueprintDetailedPreview()
    {
        return tableBlueprintDetailedPreview;
    }
}
