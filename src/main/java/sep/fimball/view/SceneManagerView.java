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
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Der SceneManagerView verwaltet die aktuelle WindowView und DialogView, und reagiert bei ViewModel Änderungen im ViewModel damit, dass sie das richtige WindowView und Dialogview erstellt und in die Stage einbindet.
 */
public class SceneManagerView
{
    /**
     * Das zum SceneManagerView gehörende ViewModel.
     */
    private SceneManagerViewModel sceneManagerViewModel;

    /**
     * Der oberste Container, in den die gesamte View geladen wird.
     */
    private StackPane root;

    /**
     * Ein Blur-Effekt, welcher bei einem aktiven DialogView das darunter liegende WindowView ausblendet.
     */
    private GaussianBlur blurEffect;

    /**
     * Erzeugt eine neue SceneManagerView mit der gegebenen Stage, in welche der oberste Container gelegt wird. Auch legt es die Startgröße des Fenster fest und initialisiert das SceneManagerViewModel an welches es sich dann auch gleich bindet, um bei ViewModel Änderungen reagieren zu können.
     * @param stage
     */
    public SceneManagerView(Stage stage)
    {
        stage.setWidth(1280);
        stage.setHeight(720);

        root = new StackPane();

        Rectangle box = new Rectangle();
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        box.setFill(new Color(219 / 255., 93 / 255., 93 / 255., 1));
        box.setOpacity(0.60);

        root.getChildren().add(new Group());
        root.getChildren().add(box);
        root.getChildren().add(new Group());

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();

        sceneManagerViewModel = new SceneManagerViewModel();
        sceneManagerViewModel.windowViewModelProperty().addListener((observableValue, oldWindowViewModel, newWindowViewModel) -> updateContent(newWindowViewModel));
        sceneManagerViewModel.dialogViewModelProperty().addListener((observableValue, oldDialogViewModel, newDialogViewModel) -> updateContent(newDialogViewModel));
        updateContent(sceneManagerViewModel.windowViewModelProperty().get());
        updateContent(sceneManagerViewModel.dialogViewModelProperty().get());

        blurEffect = new GaussianBlur(13);
    }

    @FXML //TODO write in fxml file
    private void onKeyEvent(KeyEvent event)
    {
        sceneManagerViewModel.onKeyEvent(event);
    }

    private void updateContent(WindowViewModel windowViewModel)
    {
        switch (windowViewModel.getWindowType())
        {
            case SPLASH_SCREEN:
                setWindow(WindowType.SPLASH_SCREEN_WINDOW, windowViewModel);
                break;
            case MAIN_MENU:
                setWindow(WindowType.MAIN_MENU_WINDOW, windowViewModel);
                break;
            case GAME:
                setWindow(WindowType.GAME_WINDOW, windowViewModel);
                break;
            case TABLE_EDITOR:
                setWindow(WindowType.TABLE_EDITOR_WINDOW, windowViewModel);
                break;
            case TABLE_SETTINGS:
                setWindow(WindowType.TABLE_SETTINGS_WINDOW, windowViewModel);
                break;
        }
    }

    private void updateContent(DialogViewModel dialogViewModel)
    {
        switch (dialogViewModel.getDialogType())
        {
            case NONE:
                removeDialog();
                break;
            case GAME_OVER:
                setDialog(DialogType.GAME_OVER_DIALOG, dialogViewModel);
                break;
            case GAME_SETTINGS:
                setDialog(DialogType.GAME_SETTINGS_DIALOG, dialogViewModel);
                break;
            case PLAYER_NAMES:
                setDialog(DialogType.PLAYER_NAME_DIALOG, dialogViewModel);
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
        ViewLoader<ViewBoundToViewModel<ViewModel>> viewLoader = new ViewLoader<>(viewType);
        try
        {
            ViewBoundToViewModel<ViewModel> view = viewLoader.getView();
            view.setViewModel(viewModel);
        }
        catch (ClassCastException e)
        {
            System.err.println("Could not inject viemodel into view");
            throw e;
        }

        return viewLoader.getRootNode();
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
        if (node != null)
        {
            root.getChildren().add(node);
        }
        else root.getChildren().add(new Group());
    }
}
