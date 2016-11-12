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
     * @param stage Die Stage, die gesetzt werden soll.
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

    /**
     * TODO
     * @param event
     */
    @FXML //TODO write in fxml file
    private void onKeyEvent(KeyEvent event)
    {
        sceneManagerViewModel.onKeyEvent(event);
    }

    /**
     * Ersetzt das aktuelle Fenster durch das zum gegebenen WindowViewModel gehörende Fenster.
     * @param windowViewModel Das übergenene WindowVIewModel.
     */
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

    /**
     * Ersetzt den aktuellen Dialog durch den zum gegebenen DialogVIewModel gehörenden Dialog.
     * @param dialogViewModel Das übergebene DialogViewModel.
     */
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

    /**
     * Setzt das aktuelle Fenster auf den gegebenen WindowType und verbindet dieses mit dem gegebenen ViewModel.
     * @param windowType Der WindowType des zu setzenden Fensters.
     * @param viewModel Das zum windowType gehörende ViewModel.
     */
    private void setWindow(WindowType windowType, ViewModel viewModel)
    {
        Node windowNode = loadView(windowType, viewModel);
        replaceWindow(windowNode);
    }

    /**
     * Setzt den aktuellen Dialog auf den gegebenen DialogTyp und verbindet diesen mit dem gegebenen ViewModel.
     * @param dialogType Der DialogType des zu setzenden Dialogs.
     * @param viewModel Das zum dialogType gehörende ViewModel.
     */
    private void setDialog(DialogType dialogType, ViewModel viewModel)
    {
        Node dialogNode = loadView(dialogType, viewModel);
        replaceDialog(dialogNode);

        getWindow().setEffect(blurEffect);
        getWindow().setDisable(true);
        getGlass().setVisible(true);
    }

    /**
     * Lädt eine View aus dem gegebenen ViewType und verbindet diese mit dem gegebenen ViewModel.
     * @param viewType Der gegebene ViewType der zu ladenden View.
     * @param viewModel Das zur geladenen View gehörende ViewModel.
     * @return Eine Node, die die geladene View verbunden mit dem (@code viewmodel} enthält.
     */
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

    /**
     * Blendet den angezigten Dialog aus und entfernt diesen.
     */
    private void removeDialog()
    {
        replaceDialog(null);

        getWindow().setEffect(null);
        getWindow().setDisable(false);
        getGlass().setVisible(false);
    }

    /**
     * Gibt das aktuell angzeigte Fenster zurück.
     * @return Das aktuell angezeigte Fenster.
     */
    private Node getWindow()
    {
        return root.getChildren().get(0);
    }

    /**
     * TODO
     * @return
     */
    private Node getGlass()
    {
        return root.getChildren().get(1);
    }

    /**
     * Ersetzt das aktuell angzeigte Fenster mit dem in {@code node} gespeicherten Fenster.
     * @param node Eine Node, die eine Fenster-View enthält.
     */
    private void replaceWindow(Node node)
    {
        root.getChildren().remove(0);
        root.getChildren().add(0, node);
    }

    /**
     * Ersetzt den aktuell angzeigten Dialog mit dem in {@code node} gespeicherten Dialog.
     * @param node Ene Node, die eine Dialog-View enthält.
     */
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
