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
     * Der oberste Container, in den die gesamte View geladen wird.
     */
    private StackPane root;

    /**
     * Ein Blur-Effekt, der bei einem aktiven DialogView das darunter liegende WindowView ausblendet.
     */
    private GaussianBlur blurEffect;

    /**
     * Erzeugt eine neue SceneManagerView mit der gegebenen Stage, in welcher dann die jeweils aktiven Views eingefügt werden.
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

        new SoundManagerView();
    }

    /**
     * Ersetzt das aktuelle Fenster durch das zum übergebenen WindowViewModel gehörende Fenster.
     *
     * @param windowViewModel Das übergebene WindowViewModel.
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
     * Ersetzt den aktuellen Dialog durch den zum übergebenen DialogViewModel gehörenden Dialog.
     *
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
            case PAUSE:
                setDialog(DialogType.PAUSE, dialogViewModel);
        }
    }

    /**
     * Erzeugt ein Fenster des übergebenden WindowType, setzt dieses als aktuelles Fenster und verbindet es mit dem gegebenen ViewModel.
     *
     * @param windowType Der WindowType des zu setzenden Fensters.
     * @param viewModel  Das zum windowType gehörende ViewModel.
     */
    private void setWindow(WindowType windowType, ViewModel viewModel)
    {
        Node windowNode = loadView(windowType, viewModel);
        replaceWindow(windowNode);
    }

    /**
     * Erzeugt einen Dialog des übergebenden DialogType, setzt diesen als aktuelles Fenster und verbindet ihn mit dem gegebenen ViewModel.
     *
     * @param dialogType Der DialogType des zu setzenden Dialogs.
     * @param viewModel  Das zum dialogType gehörende ViewModel.
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
     *
     * @param viewType  Der gegebene ViewType der zu ladenden View.
     * @param viewModel Das zur geladenen View gehörende ViewModel.
     * @return Eine Node, die die geladene View verbunden mit dem (@code viewModel} enthält.
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
     * Blendet den angezeigten Dialog aus und entfernt diesen.
     */
    private void removeDialog()
    {
        replaceDialog(null);

        getWindow().setEffect(null);
        getWindow().setDisable(false);
        getGlass().setVisible(false);
    }

    /**
     * Gibt das aktuell angezeigte Fenster zurück.
     *
     * @return Das aktuell angezeigte Fenster.
     */
    private Node getWindow()
    {
        return root.getChildren().get(0);
    }

    /**
     * Gibt die transparente Zwischenebene zwischen dem Fenster und eventuell darüber liegenden Dialogen zurück.
     *
     * @return Die Zwischenebene zwischen Fenstern und Dialogen.
     */
    private Node getGlass()
    {
        return root.getChildren().get(1);
    }

    /**
     * Ersetzt das aktuell angezeigte Fenster mit dem in {@code node} gespeicherten Fenster.
     *
     * @param node Eine Node, die eine Fenster-View enthält.
     */
    private void replaceWindow(Node node)
    {
        root.getChildren().remove(0);
        root.getChildren().add(0, node);
    }

    /**
     * Ersetzt den aktuell angezeigten Dialog mit dem in {@code node} gespeicherten Dialog.
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
