package sep.fimball.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.DataPath;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;
import sep.fimball.viewmodel.window.splashscreen.SplashScreenViewModel;

import java.nio.file.Paths;
import java.util.Optional;

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

    /**
     * Das aktuelle DialogViewModel, das angezeigt werden soll.
     */
    private ObjectProperty<DialogViewModel> dialogViewModel;

    /**
     * Erstellt ein neues SceneManagerViewModel, das ein MainMenuViewModel
     * verwaltet.
     */
    public SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        fullscreen = new SimpleBooleanProperty();

        dialogViewModel = new SimpleObjectProperty<>();
        dialogViewModel.bind(Bindings.createObjectBinding(() -> dialogViewModels.isEmpty() ? new EmptyViewModel() : dialogViewModels.get(0), dialogViewModels));

        if (Paths.get(DataPath.pathToAutoSave()).toFile().exists()) {
            Optional<PinballMachine> autoSavedMachine = PinballMachineManager.getInstance().loadAutoSavedMachine();
            if(autoSavedMachine.isPresent())
            {
                setWindow(new PinballMachineEditorViewModel(autoSavedMachine.get()));
            } else {
                setWindow(new SplashScreenViewModel());
            }
        } else
        {
            setWindow(new SplashScreenViewModel());
        }
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
     * Legt das übergebene DialogViewModel auf den obersten Platz des DialogViewModelStacks.
     *
     * @param dialogViewModel Das neue DialogViewModel.
     */
    public void pushDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
        dialogViewModels.add(0, dialogViewModel);
        dialogViewModels.get(0).changeBackgroundMusic();
    }

    /**
     * Entfernt das auf den obersten Platz des DialogViewModelStacks liegende DialogViewModel.
     */
    public void popDialog()
    {
        dialogViewModels.remove(0);
    }

    /**
     * Stellt das aktuelle WindowViewModel zur Verfügung.
     *
     * @return Das aktuelle WindowViewModel.
     */
    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    /**
     * Stellt das aktuelle DialogViewModel zur Verfügung.
     *
     * @return Das aktuelle DialogViewModel.
     */
    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
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
