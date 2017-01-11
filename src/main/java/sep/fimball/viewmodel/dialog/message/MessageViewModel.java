package sep.fimball.viewmodel.dialog.message;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class MessageViewModel extends DialogViewModel
{
    private StringProperty title;

    private StringProperty message;

    public MessageViewModel(String title, String message)
    {
        super(DialogType.MESSAGE);

        this.title = new SimpleStringProperty();
        this.title.bind(LanguageManagerViewModel.getInstance().textProperty(title));

        this.message = new SimpleStringProperty();
        this.message.bind(LanguageManagerViewModel.getInstance().textProperty(message));
    }

    public void close()
    {
        sceneManager.popDialog();
    }

    public ReadOnlyStringProperty titleProperty()
    {
        return title;
    }

    public ReadOnlyStringProperty messageProperty()
    {
        return message;
    }
}
