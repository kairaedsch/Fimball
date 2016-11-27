package sep.fimball.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sep.fimball.general.data.Config;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.ViewModel;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Die SceneManagerView verwaltet die aktuelle WindowView und DialogView und reagiert bei Änderungen im ViewModel damit,
 * dass sie die richtige WindowView und DialogView erstellt und in die Stage einbindet.
 */
public class SceneManagerView
{
    /**
     * Das zum SceneManagerView gehörende ViewModel.
     */
    private SceneManagerViewModel sceneManagerViewModel;

    /**
     * Der oberste Container, in denen alle Nodes der aktiven Views geladen werden.
     */
    private StackPane root;

    /**
     * Ein Blur-Effekt, der bei einem aktiven DialogView das Node der WindowView ausblendet.
     */
    private GaussianBlur blurEffect;

    /**
     * Erzeugt eine neue SceneManagerView mit der gegebenen Stage, in welcher dann die Nodes der jeweils aktiven Views eingefügt werden.
     * Auch wird ein SceneManagerViewModel geholt, an das sich dieses SceneManagerView bindet, um bei Änderungen des ViewModels reagieren zu können,
     * um z.B. den Dialog zu wechseln.
     *
     * @param stage Die Stage, die gesetzt werden soll.
     */
    public SceneManagerView(Stage stage)
    {
        stage.setOnCloseRequest(t ->
        {
            Platform.exit();
            System.exit(0);
        });

        stage.setWidth(1280);
        stage.setHeight(720);

        root = new StackPane();

        Rectangle box = new Rectangle();
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        box.setFill(Config.primaryColor);
        box.setOpacity(0.60);

        root.getChildren().add(new Group());
        root.getChildren().add(box);
        root.getChildren().add(new Group());

        new SoundManagerView();

        sceneManagerViewModel = new SceneManagerViewModel();
        sceneManagerViewModel.windowViewModelProperty().addListener((observableValue, oldWindowViewModel, newWindowViewModel) -> updateContent(newWindowViewModel));
        sceneManagerViewModel.dialogViewModelProperty().addListener((observableValue, oldDialogViewModel, newDialogViewModel) -> updateContent(newDialogViewModel));

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        scene.setOnKeyPressed(sceneManagerViewModel::onKeyEvent);
        scene.setOnKeyReleased(sceneManagerViewModel::onKeyEvent);
        stage.setScene(scene);
        stage.show();

        updateContent(sceneManagerViewModel.windowViewModelProperty().get());
        updateContent(sceneManagerViewModel.dialogViewModelProperty().get());

        blurEffect = new GaussianBlur(13);
    }

    /**
     * Ersetzt die aktuelle aktive WindowView durch die zum übergebenen WindowViewModel gehörende WindowView.
     *
     * @param windowViewModel Das übergebene WindowViewModel.
     */
    private void updateContent(WindowViewModel windowViewModel)
    {
        WindowType windowType;
        switch (windowViewModel.getWindowType())
        {
            case SPLASH_SCREEN:
                windowType = WindowType.MAIN_MENU_WINDOW;
                break;
            case MAIN_MENU:
                windowType = WindowType.MAIN_MENU_WINDOW;
                break;
            case GAME:
                windowType = WindowType.GAME_WINDOW;
                break;
            case TABLE_EDITOR:
                windowType = WindowType.TABLE_EDITOR_WINDOW;
                break;
            case TABLE_SETTINGS:
                windowType = WindowType.TABLE_SETTINGS_WINDOW;
                break;
            default:
                throw new RuntimeException("Unkown WindowType");
        }
        setWindow(windowType, windowViewModel);
    }

    /**
     * Ersetzt die aktuelle aktive DialogView durch die zum übergebenen DialogViewModel gehörende DialogView.
     *
     * @param dialogViewModel Das übergebene DialogViewModel.
     */
    private void updateContent(DialogViewModel dialogViewModel)
    {
        DialogType dialogType = null;
        switch (dialogViewModel.getDialogType())
        {
            case NONE:
                break;
            case GAME_OVER:
                dialogType = DialogType.GAME_OVER_DIALOG;
                break;
            case GAME_SETTINGS:
                dialogType = DialogType.GAME_SETTINGS_DIALOG;
                break;
            case PLAYER_NAMES:
                dialogType = DialogType.PLAYER_NAME_DIALOG;
                break;
            case PAUSE:
                dialogType = DialogType.PAUSE;
                break;
            default:
                throw new RuntimeException("Unkown DialogType");
        }

        if(dialogType != null) setDialog(dialogType, dialogViewModel);
        else removeDialog();
    }

    /**
     * Erzeugt ein WindowView des übergebenden WindowType, setzt diese als aktive WindowView und verbindet diese mit dem gegebenen ViewModel.
     *
     * @param windowType Der WindowType des zu erzeugenden WindowView.
     * @param viewModel  Das zu bindende ViewModel.
     */
    private void setWindow(WindowType windowType, ViewModel viewModel)
    {
        Node windowNode = loadView(windowType, viewModel);
        replaceWindow(windowNode);
    }

    /**
     * Erzeugt ein DialogView des übergebenden DialogType, setzt diese als aktive DialogView und verbindet diese mit dem gegebenen ViewModel.
     *
     * @param dialogType Der DialogType des zu setzenden Dialogs.
     * @param viewModel  Das zu bindende ViewModel.
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
     * Lädt eine View mit RootNode aus dem gegebenen ViewType und verbindet diese mit dem gegebenen ViewModel.
     *
     * @param viewType  Der gegebene ViewType der zu ladenen View.
     * @param viewModel Das zur geladenen View gehörende ViewModel.
     * @return Das RootNode der geladenen View.
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
            System.err.println("Could not inject viewmodel into view");
            throw e;
        }

        return viewLoader.getRootNode();
    }

    /**
     * Entfernt den aktuell aktiven Dialog.
     */
    private void removeDialog()
    {
        replaceDialog(null);

        getWindow().setEffect(null);
        getWindow().setDisable(false);
        getGlass().setVisible(false);
    }

    /**
     * Gibt das RootNode der aktuell angezeigten WindowView zurück.
     *
     * @return Das RootNode der aktuell angezeigten WindowView.
     */
    private Node getWindow()
    {
        return root.getChildren().get(0);
    }

    /**
     * Gibt die transparente Zwischenebene zwischen dem Fenster und eventuell darüber liegenden Dialogen zurück.
     *
     * @return Die Zwischenebene zwischen WindowView und DialogView.
     */
    private Node getGlass()
    {
        return root.getChildren().get(1);
    }

    /**
     * Ersetzt das aktuell angezeigte RootNode einer WindowView mit einem anderen RootNode einer WindowView.
     *
     * @param node Das neue RootNode der aktiven WindowView.
     */
    private void replaceWindow(Node node)
    {
        root.getChildren().remove(0);
        root.getChildren().add(0, node);
    }

    /**
     * Ersetzt das aktuell angezeigte RootNode einer DialogView mit einem anderen RootNode einer DialogView.
     *
     * @param node Eine Node, die eine Dialog-View enthält.
     */
    private void replaceDialog(Node node)
    {
        root.getChildren().remove(2);
        if (node != null)
        {
            root.getChildren().add(node);
        }
        else
            root.getChildren().add(new Group());
    }
}
