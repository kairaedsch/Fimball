package sep.fimball.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.ViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneManagerView
{
    private Stage stage;
    private Scene scene;
    private StackPane root;
    private GaussianBlur blurEffect;

    public SceneManagerView(Stage stage)
    {
        this.stage = stage;

        root = new StackPane();

        Rectangle box = new Rectangle();
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        box.setFill(new Color(219 / 255., 93 / 255., 93 / 255., 1));
        box.setOpacity(0.60);

        root.getChildren().add(new Group());
        root.getChildren().add(box);
        root.getChildren().add(new Group());

        scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
        this.stage.setScene(scene);
        this.stage.show();

        SceneManagerViewModel sceneManagerViewModel = SceneManagerViewModel.getInstance();
        sceneManagerViewModel.windowTypeProperty().addListener((observableValue, oldWindowType, newWindowType) -> updateContent(newWindowType, sceneManagerViewModel.getWindowViewModel()));
        sceneManagerViewModel.dialogTypeProperty().addListener((observableValue, oldDialogType, newDialogType) -> updateContent(newDialogType, sceneManagerViewModel.getDialogViewModel()));
        updateContent(sceneManagerViewModel.windowTypeProperty().get(), new MainMenuViewModel());
        updateContent(sceneManagerViewModel.dialogTypeProperty().get(), null);

        blurEffect = new GaussianBlur(13);
    }

    @FXML //TODO write in fxml file
    protected void onKeyEvent(KeyEvent event)
    {
        SceneManagerViewModel.getInstance().onKeyEvent(event);
    }

    public void updateContent(sep.fimball.viewmodel.window.WindowType newWindowType, ViewModel viewModel)
    {
        switch (newWindowType)
        {
            case SPLASH_SCREEN:
                setWindow(WindowType.SPLASH_SCREEN_WINDOW, viewModel);
                break;
            case MAIN_MENU:
                setWindow(WindowType.MAIN_MENU_WINDOW, viewModel);
                break;
            case GAME:
                setWindow(WindowType.GAME_WINDOW, viewModel);
                break;
            case TABLE_EDITOR:
                setWindow(WindowType.TABLE_EDITOR_WINDOW, viewModel);
                break;
            case TABLE_SETTINGS:
                setWindow(WindowType.TABLE_SETTINGS_WINDOW, viewModel);
                break;
        }
    }

    public void updateContent(sep.fimball.viewmodel.dialog.DialogType newDialogType, ViewModel viewModel)
    {
        switch (newDialogType)
        {
            case NONE:
                removeDialog();
                break;
            case GAME_OVER:
                setDialog(DialogType.GAME_OVER_DIALOG, viewModel);
                break;
            case GAME_SETTINGS:
                setDialog(DialogType.GAME_SETTINGS_DIALOG, viewModel);
                break;
            case PLAYER_NAMES:
                setDialog(DialogType.PLAYER_NAME_DIALOG, viewModel);
                break;
        }
    }

    private void setWindow(WindowType windowType, ViewModel viewModel)
    {
        Node windowNode = loadView(windowType, viewModel);
        replaceWindow(windowNode);
    }

    private void setDialog(DialogType dialogType, ViewModel viewModel)
    {
        Node dialogNode = loadView(dialogType, viewModel);
        replaceDialog(dialogNode);

        getWindow().setEffect(blurEffect);
        getWindow().setDisable(true);
        getGlass().setVisible(true);
    }

    private Node loadView(ViewType viewType, ViewModel viewModel)
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(viewType);
        try
        {
            BoundToViewModel<ViewModel> view = (BoundToViewModel<ViewModel>) simpleFxmlLoader.getFxController();
            view.bindToViewModel(viewModel);
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could inject viemodel into view");
        }

        return simpleFxmlLoader.getRootNode();
    }

    private void removeDialog()
    {
        replaceDialog(null);

        getWindow().setEffect(null);
        getWindow().setDisable(false);
        getGlass().setVisible(false);
    }

    private Node getWindow()
    {
        return root.getChildren().get(0);
    }

    private Node getGlass()
    {
        return root.getChildren().get(1);
    }

    private void replaceWindow(Node node)
    {
        root.getChildren().remove(0);
        root.getChildren().add(0, node);
    }

    private void replaceDialog(Node node)
    {
        root.getChildren().remove(2);
        if (node != null) root.getChildren().add(node);
        else root.getChildren().add(new Group());
    }
}
