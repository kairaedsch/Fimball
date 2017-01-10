package sep.fimball.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.splashscreen.SplashScreenViewModel;

/**
 * Das SceneManagerViewModel steuert die View und bestimmt, welches Fenster und
 * welcher Dialog angezeigt werden, indem das ViewModel an die View übergeben
 * wird. Es gewährleistet weiterhin, dass Nutzereingaben problemlos an das Model
 * weitergeleitet werden können.
 */
public class SceneManagerViewModel
{
    /**
     * Das aktuelle WindowViewModel.
     */
    private ObjectProperty<WindowViewModel> windowViewModel;

    /**
     * Die aktuellen DialogViewModels.
     */
    private ListProperty<DialogViewModel> dialogViewModels;

    /**
     * Gibt an, ob das Fenster im Vollbildmodus dargestellt werden soll.
     */
    private BooleanProperty fullscreen;

    private ObjectProperty<DialogViewModel> dialogViewModel;

    /**
     * Erstellt ein neues SceneManagerViewModel, das ein MainMenuViewModel
     * verwaltet.
     */
    public SceneManagerViewModel()
    {
        dialogViewModel = new SimpleObjectProperty<>();
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        fullscreen = new SimpleBooleanProperty();
        setWindow(new SplashScreenViewModel());
        fullscreen.bind(Settings.getSingletonInstance().fullscreenProperty());
    }

    /**
     * Leitet das Key-Event an das aktuell aktive WindowViewModel bzw.
     * DialogViewModel weiter.
     *
     * @param event Das Key-Event, das weitergeleitet werden soll.
     */
    public void onKeyEvent(KeyEvent event)
    {
        if (dialogViewModels.isEmpty())
        {
            windowViewModel.get().handleKeyEvent(event);
        }
        else
        {
            dialogViewModels.get(0).handleKeyEvent(event);
        }
    }

    /**
     * Ändert das aktuelle WindowViewModel auf das, das der Methode übergeben
     * wird und löscht den aktuell offenen Dialog, falls vorhanden.
     *
     * @param windowViewModel Das neue WindowViewModel.
     */
    public void setWindow(WindowViewModel windowViewModel)
    {
        windowViewModel.setSceneManager(this);
        this.windowViewModel.set(windowViewModel);
        this.windowViewModel.get().changeBackgroundMusic();
    }

    /**
     * @param dialogViewModel Das neue DialogViewModel.
     */
    public void pushDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
        dialogViewModels.add(0, dialogViewModel);
        dialogViewModels.get(0).changeBackgroundMusic();
    }

    /**
     *
     */
    public void popDialog()
    {
        dialogViewModels.remove(0);
    }

    /**
     * Stellt das aktuelle WindowViewModel als ObjectProperty zur Verfügung.
     *
     * @return Das aktuelle WindowViewModel.
     */
    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    /**
     * Stellt das aktuelle DialogViewModel als ObjectProperty zur Verfügung.
     *
     * @return Das aktuelle DialogViewModel.
     */
    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
        dialogViewModel.bind(Bindings.createObjectBinding(
                () -> dialogViewModels.isEmpty() ? new EmptyViewModel() : dialogViewModels.get(0),
                dialogViewModels));
        return dialogViewModel;
    }

    /**
     * Stellt der View die Information zu Verfügung, ob das Programm im Vollbildmodus dargestellt werden soll.
     *
     * @return {@code true}, wenn das Programm im Vollbildmodus gestartet werden soll, {@code false} sonst.
     */
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }
}
