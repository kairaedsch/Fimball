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
                setWindow(FxControllerType.SPLASH_SCREEN_WINDOW);
                break;
            case MAIN_MENU:
                setWindow(FxControllerType.MAIN_MENU_WINDOW);
                break;
            case GAME:
                setWindow(FxControllerType.GAME_WINDOW);
                break;
            case TABLE_EDITOR:
                setWindow(FxControllerType.TABLE_EDITOR_WINDOW);
                break;
            case TABLE_SETTINGS:
                setWindow(FxControllerType.TABLE_SETTINGS_WINDOW);
                break;
        }
    }

    public void updateContent(DialogType newDialogType)
    {
        switch (newDialogType)
        {
            case NONE:
                removeDialog();
                break;
            case GAME_OVER:
                setDialog(FxControllerType.GAME_OVER_DIALOG);
                break;
            case GAME_SETTINGS:
                setDialog(FxControllerType.GAME_SETTINGS_DIALOG);
                break;
            case PLAYER_NAMES:
                setDialog(FxControllerType.PLAYER_NAME_DIALOG);
                break;
        }
    }

    private void setWindow(FxControllerType fxControllerType)
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(fxControllerType);
        Node windowNode = simpleFxmlLoader.getRootNode();
        if (windowNode != null)
        {
            root.getChildren().add(0, windowNode);
            root.getChildren().remove(1);
        }
    }

    private void setDialog(FxControllerType fxControllerType)
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(fxControllerType);
        Node dialogNode = simpleFxmlLoader.getRootNode();
        if (dialogNode != null)
        {
            root.getChildren().remove(1);
            root.getChildren().add(1, dialogNode);
        }
    }

    private void removeDialog()
    {
        root.getChildren().remove(1);
        root.getChildren().add(1, new Group());
    }
}
