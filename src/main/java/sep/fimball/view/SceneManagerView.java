package sep.fimball.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.SceneManagerViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneManagerView
{
    private Stage stage;
    private Scene scene;
    private StackPane root;

    public SceneManagerView(Stage stage)
    {
        this.stage = stage;

        // Create Stackpane and add two Placeholder
        root = new StackPane();
        root.getChildren().add(new Group());
        root.getChildren().add(new Group());

        scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
        this.stage.setScene(scene);
        this.stage.show();

        SceneManagerViewModel sceneManagerViewModel = SceneManagerViewModel.getInstance();
        sceneManagerViewModel.getWindowTypeProperty().addListener((observableValue, oldWindowType, newWindowType) -> updateContent(newWindowType));
        sceneManagerViewModel.getDialogTypeProperty().addListener((observableValue, oldDialogType, newDialogType) -> updateContent(newDialogType));
        updateContent(sceneManagerViewModel.getWindowTypeProperty().get());
        updateContent(sceneManagerViewModel.getDialogTypeProperty().get());
    }

    @FXML //TODO write in fxml file
    protected void onKeyEvent(KeyEvent event)
    {
        SceneManagerViewModel.getInstance().onKeyEvent(event);
    }

    public void updateContent(sep.fimball.viewmodel.window.WindowType newWindowType)
    {
        switch (newWindowType)
        {
            case SPLASH_SCREEN:
                setWindow(WindowType.SPLASH_SCREEN_WINDOW);
                break;
            case MAIN_MENU:
                setWindow(WindowType.MAIN_MENU_WINDOW);
                break;
            case GAME:
                setWindow(WindowType.GAME_WINDOW);
                break;
            case TABLE_EDITOR:
                setWindow(WindowType.TABLE_EDITOR_WINDOW);
                break;
            case TABLE_SETTINGS:
                setWindow(WindowType.TABLE_SETTINGS_WINDOW);
                break;
        }
    }

    public void updateContent(sep.fimball.viewmodel.dialog.DialogType newDialogType)
    {
        switch (newDialogType)
        {
            case NONE:
                removeDialog();
                break;
            case GAME_OVER:
                setDialog(DialogType.GAME_OVER_DIALOG);
                break;
            case GAME_SETTINGS:
                setDialog(DialogType.GAME_SETTINGS_DIALOG);
                break;
            case PLAYER_NAMES:
                setDialog(DialogType.PLAYER_NAME_DIALOG);
                break;
        }
    }

    private void setWindow(WindowType windowType)
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(windowType);
        Node windowNode = simpleFxmlLoader.getRootNode();
        if (windowNode != null)
        {
            root.getChildren().add(0, windowNode);
            root.getChildren().remove(1);
        }
    }

    private void setDialog(DialogType dialogType)
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(dialogType);
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
