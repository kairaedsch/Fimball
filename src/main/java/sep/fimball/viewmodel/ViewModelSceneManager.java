package sep.fimball.viewmodel;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by kaira on 01.11.2016.
 */
public class ViewModelSceneManager
{
    private static ViewModelSceneManager singletonInstance;

    public static ViewModelSceneManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new ViewModelSceneManager();
        return singletonInstance;
    }

    private SimpleObjectProperty<WindowType> windowType;
    private SimpleObjectProperty<DialogType> dialogType;

    private ViewModelSceneManager()
    {
        windowType = new SimpleObjectProperty<WindowType>(WindowType.SPLASH_SCREEN);
        dialogType = new SimpleObjectProperty<DialogType>(DialogType.NONE);
    }

    public ReadOnlyObjectProperty<WindowType> getWindowTypeProperty()
    {
        return windowType;
    }

    public ReadOnlyObjectProperty<DialogType> getDialogTypeProperty()
    {
        return dialogType;
    }

    void setWindow(WindowType WindowTypeNew)
    {
        windowType.set(WindowTypeNew);
    }

    void setDialog(DialogType dialogtypeNew)
    {
        dialogType.set(dialogtypeNew);
    }
}
