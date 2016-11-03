package sep.fimball.viewmodel;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.AutoListPropertyBinder;
import sep.fimball.model.tableblueprint.TableBlueprintManager;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    private SimpleListProperty<TableBlueprintPreview> tableBlueprintPreviewList;

    public MainMenuViewModel()
    {
        tableBlueprintPreviewList = new SimpleListProperty<>(FXCollections.observableArrayList());

        AutoListPropertyBinder.bind(tableBlueprintPreviewList, TableBlueprintManager.getInstance().tableBlueprintsProperty(), tableBlueprint -> new TableBlueprintPreview(tableBlueprint));
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
        // TODO remove test code
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test play"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
    }

    public void editClicked()
    {
        // TODO remove test code
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test edit"), new SimpleStringProperty("/images/pic.jpg")));
    }
}
