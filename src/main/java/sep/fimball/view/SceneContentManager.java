package sep.fimball.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sep.fimball.viewmodel.DialogType;
import sep.fimball.viewmodel.ViewModelSceneManager;
import sep.fimball.viewmodel.WindowType;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneContentManager {

    private Stage primaryStage;
    private Scene scene;
    private Group root;
    private StackPane primaryNode;

    public SceneContentManager(Stage stage) {
        primaryStage = stage;
        root = new Group();
        scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryNode = new StackPane();
        root.getChildren().add(primaryNode);

        final ViewModelSceneManager viewModelSceneManager = ViewModelSceneManager.getInstance();

        viewModelSceneManager.getWindowTypeProperty().addListener(
                (observableValue, newValue, oldValue) -> updateContent(newValue));

        viewModelSceneManager.getDialogTypeProperty().addListener(
                (observableValue, newValue, oldValue) -> updateContent(newValue));
    }

    public void updateContent(WindowType windowType) {
        //TODO
    }

    public void updateContent(DialogType dialogType) {
        //TODO
    }

}
