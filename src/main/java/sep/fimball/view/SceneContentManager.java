package sep.fimball.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sep.fimball.viewmodel.DialogType;
import sep.fimball.viewmodel.ViewModelSceneManager;
import sep.fimball.viewmodel.WindowType;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneContentManager
{

    private Stage stage;
    private Scene scene;
    private StackPane root;

    public SceneContentManager(Stage stage)
    {
        this.stage = stage;

        // Create Stackpane and add two Placeholder
        root = new StackPane();
        root.getChildren().add(new Group());
        root.getChildren().add(new Group());

        scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
        this.stage.setScene(scene);
        this.stage.show();

        ViewModelSceneManager viewModelSceneManager = ViewModelSceneManager.getInstance();
        viewModelSceneManager.getWindowTypeProperty().addListener((observableValue, oldWindowType, newWindowType) -> updateContent(newWindowType));
        viewModelSceneManager.getDialogTypeProperty().addListener((observableValue, oldDialogType, newDialogType) -> updateContent(newDialogType));
        updateContent(viewModelSceneManager.getWindowTypeProperty().get());
        updateContent(viewModelSceneManager.getDialogTypeProperty().get());
    }

    public void updateContent(WindowType newWindowType)
    {
        switch (newWindowType)
        {
            case SPLASH_SCREEN:
                //TODO
                break;
            case MAIN_MENU:
                setWindow(new SimpleFxmlLoader(FxControllerType.MAIN_MENU_WINDOW));
                break;
            case GAME:
                //TODO
                break;
            case TABLE_EDITOR:
                //TODO
                break;
            case TABLE_SETTINGS:
                //TODO
                break;
        }
    }

    public void updateContent(DialogType newDialogType)
    {
        switch (newDialogType)
        {
            case NONE:
                //TODO
                break;
            case GAME_OVER:
                //TODO
                break;
            case GAME_SETTINGS:
                //TODO
                break;
            case PLAYER_NAMES:
                //TODO
                break;
        }
    }

    private void setWindow(SimpleFxmlLoader simpleFxmlLoader)
    {
        Node windowNode = simpleFxmlLoader.getRootNode();
        if (windowNode != null)
        {
            root.getChildren().add(0, windowNode);
            root.getChildren().remove(1);
        }
    }

    private void setDialog(SimpleFxmlLoader simpleFxmlLoader)
    {
        Node dialogNode = simpleFxmlLoader.getRootNode();
        if (dialogNode != null)
        {
            root.getChildren().add(1, dialogNode);
            root.getChildren().remove(2);
        }
    }
}
