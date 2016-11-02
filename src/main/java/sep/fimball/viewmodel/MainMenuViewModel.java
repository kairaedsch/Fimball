package sep.fimball.viewmodel;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
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
        // TODO remove test code
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 1"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 2"), new SimpleStringProperty("/images/pic.jpg")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 3"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 4"), new SimpleStringProperty("/images/pic.jpg")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 5"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 6"), new SimpleStringProperty("/images/pic.jpg")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 7"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 8"), new SimpleStringProperty("/images/pic.jpg")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 9"), new SimpleStringProperty("/images/pinball-machine-test-v6.png")));
        tableBlueprintPreviewList.add(new TableBlueprintPreview(new SimpleStringProperty("Test 10"), new SimpleStringProperty("/images/pic.jpg")));
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
