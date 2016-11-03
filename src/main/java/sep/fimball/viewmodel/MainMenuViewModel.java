package sep.fimball.viewmodel;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.model.tableblueprint.TableBlueprintManager;
import sep.fimball.viewmodel.mainmenu.TableBlueprintDetailedPreview;
import sep.fimball.viewmodel.mainmenu.TableBlueprintPreview;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    private SimpleListProperty<TableBlueprintPreview> tableBlueprintPreviewList;
    private SimpleObjectProperty<TableBlueprintDetailedPreview> tableBlueprintDetailedPreview;

    public MainMenuViewModel()
    {
        tableBlueprintPreviewList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bind(tableBlueprintPreviewList, TableBlueprintManager.getInstance().tableBlueprintsProperty(), TableBlueprintPreview::new);
        tableBlueprintDetailedPreview = new SimpleObjectProperty<>(new TableBlueprintDetailedPreview(TableBlueprintManager.getInstance().tableBlueprintsProperty().get(0)));
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
