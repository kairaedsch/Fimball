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
    private SimpleObjectProperty<WindowType> windowType;
    private SimpleObjectProperty<DialogType> dialogType;

    public SceneContentManager(Stage stage) {
        primaryStage = stage;
        root = new Group();
        scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryNode = new StackPane();
        root.getChildren().add(primaryNode);

        ViewModelSceneManager viewModelSceneManager = ViewModelSceneManager.getInstance();

        windowType.bind(viewModelSceneManager.getWindowTypeProperty());
        viewModelSceneManager.getWindowTypeProperty().addListener(new ChangeListener<WindowType>() {
            public void changed(ObservableValue<? extends WindowType> observableValue, WindowType windowType, WindowType t1) {
                updateContent();
            }
        });

        dialogType.bind(viewModelSceneManager.getDialogTypeProperty());
        viewModelSceneManager.getDialogTypeProperty().addListener(new ChangeListener<DialogType>() {
            public void changed(ObservableValue<? extends DialogType> observableValue, DialogType dialogType, DialogType t1) {
                updateContent();
            }
        });
    }

    public void updateContent() {
        //TODO
    }

}
