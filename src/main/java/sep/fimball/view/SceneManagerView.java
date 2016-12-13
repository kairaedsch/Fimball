package sep.fimball.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.tools.ViewLoader;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.pinballmachine.editor.PinballMachineEditorView;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.ViewModel;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

import java.io.File;

/**
 * Die SceneManagerView verwaltet die aktuelle WindowView und DialogView und reagiert bei Änderungen im ViewModel damit,
 * dass sie die richtige WindowView und DialogView erstellt und in die Stage einbindet.
 */
public class SceneManagerView
{
    /**
     * Der oberste Container, in denen alle Nodes der aktiven Views geladen werden.
     */
    private StackPane root;

    /**
     * Ein Blur-Effekt, der bei einem aktiven DialogView das Node der WindowView ausblendet.
     */
    private GaussianBlur blurEffect;

    private Stage stage;

    /**
     * Erzeugt eine neue SceneManagerView mit der gegebenen Stage, in welcher dann die Nodes der jeweils aktiven Views eingefügt werden.
     * Auch wird ein SceneManagerViewModel geholt, an das sich dieses SceneManagerView bindet, um bei Änderungen des ViewModels reagieren zu können,
     * um z.B. den Dialog zu wechseln.
     *
     * @param stage                 Die Stage, die gesetzt werden soll.
     * @param sceneManagerViewModel Das SceneManagerViewModel, welches das SceneManagerView steuert.
     */
    public SceneManagerView(Stage stage, SceneManagerViewModel sceneManagerViewModel)
    {
        this.stage = stage;

        // Blureffekt für das glass.
        blurEffect = new GaussianBlur(DesignConfig.stageDividerLayerBlur);

        // Initialisiere den SoundManager
        new SoundManagerView();

        // Setzt die Anfangsgröße des gesamten Spiels
        stage.setWidth(DesignConfig.defaultStageWidth);
        stage.setHeight(DesignConfig.defaultStageHeight);

        root = new StackPane();

        // Erstellt das Node, welches zwischen Window und Dialog platziert wird, um die Window auszublenden
        Rectangle glass = new Rectangle();
        glass.widthProperty().bind(root.widthProperty());
        glass.heightProperty().bind(root.heightProperty());
        glass.setFill(DesignConfig.primaryColor);
        glass.setOpacity(DesignConfig.stageDividerLayerOpacity);

        // Fügt die verschiedenen Schichten zum rootNode hinzu
        root.getChildren().add(new Group());
        root.getChildren().add(glass);
        root.getChildren().add(new Group());

        // Erstellt eine Scene aus dem rootNode und fügt sie zu der Stage hinzu
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        scene.setOnKeyPressed(sceneManagerViewModel::onKeyEvent);
        scene.setOnKeyReleased(sceneManagerViewModel::onKeyEvent);
        stage.setScene(scene);

        // Fügt einen Listener für das WindowViewModel hinzu
        sceneManagerViewModel.windowViewModelProperty().addListener((observableValue, oldWindowViewModel, newWindowViewModel) -> updateContent(newWindowViewModel));
        updateContent(sceneManagerViewModel.windowViewModelProperty().get());

        // Fügt einen Listener für das DialogViewModel hinzu
        sceneManagerViewModel.dialogViewModelProperty().addListener((observableValue, oldDialogViewModel, newDialogViewModel) -> updateContent(newDialogViewModel));
        updateContent(sceneManagerViewModel.dialogViewModelProperty().get());

        // Fügt einen Listener für Fullscreen hinzu und stellt die Fullscreen Keys ein
        sceneManagerViewModel.fullscreenProperty().addListener((observable, oldValue, newValue) -> stage.setFullScreen(newValue));
        stage.setFullScreen(sceneManagerViewModel.fullscreenProperty().get());
        stage.fullScreenExitKeyProperty().set(KeyCombination.NO_MATCH);

        //Setzt das angezeigte Icon.
        File imageFile = new File(DataPath.pathToLogo());
        Image loadedImage = new Image(imageFile.toURI().toString(), false);
        stage.getIcons().add(loadedImage);

        // Zeigt die Stage dem Benutzer
        stage.show();
    }

    /**
     * Ersetzt die aktuelle aktive WindowView durch die zum übergebenen WindowViewModel gehörende WindowView.
     *
     * @param windowViewModel Das übergebene WindowViewModel.
     */
    private void updateContent(WindowViewModel windowViewModel)
    {
        // Holt das zum ViewModelWindowType gehörige ViewWindowType
        WindowType windowType;
        switch (windowViewModel.getWindowType())
        {
            case SPLASH_SCREEN:
                windowType = WindowType.SPLASH_SCREEN_WINDOW;
                break;
            case MAIN_MENU:
                windowType = WindowType.MAIN_MENU_WINDOW;
                break;
            case GAME:
                windowType = WindowType.GAME_WINDOW;
                break;
            case MACHINE_EDITOR:
                windowType = WindowType.EDITOR_WINDOW;
                break;
            case MACHINE_SETTINGS:
                windowType = WindowType.EDITOR_SETTINGS_WINDOW;
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
        // Holt das zum ViewModelDialogType gehörige ViewDialogType
        DialogType dialogType;
        switch (dialogViewModel.getDialogType())
        {
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
            case NONE:
                // Entfernt Dialog und returnt falls DialogType.NONE
                removeDialog();
                return;
            default:
                throw new RuntimeException("Unkown DialogType");
        }

        setDialog(dialogType, dialogViewModel);
    }

    /**
     * Erzeugt ein WindowView des übergebenen WindowType, setzt diese als aktive WindowView und verbindet diese mit dem gegebenen ViewModel.
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

        // Deaktiviert das Window und blendet es aus
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
            // TODO - cancer
            if (viewType == WindowType.EDITOR_WINDOW)
                ((PinballMachineEditorView)((Object)view)).setStage(stage);
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

        // Aktiviert das Window und blendet es ein
        getWindow().setEffect(null);
        getWindow().setDisable(false);
        getGlass().setVisible(false);
    }

    /**
     * Gibt das RootNode der aktuell angezeigten WindowView zurück.
     *
     * @return Das RootNode der aktuell angezeigten WindowView.
     */
    Node getWindow()
    {
        return root.getChildren().get(0);
    }

    /**
     * Gibt das RootNode der aktuell angezeigten DialogView zurück.
     *
     * @return Das RootNode der aktuell angezeigten WindowView.
     */
    Node getDialog()
    {
        return root.getChildren().get(2);
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
