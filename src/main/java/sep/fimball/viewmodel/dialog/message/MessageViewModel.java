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

    /**
     * Erzeugt ein MessageViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     */
    public MessageViewModel(String dialogKey)
    {
        super(DialogType.MESSAGE);

        this.title = new SimpleStringProperty();
        this.title.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".title.key"));

        this.message = new SimpleStringProperty();
        this.message.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".message.key"));
    }

    /**
     * Schließt diesen Dialog.
     */
    public void close()
    {
        sceneManager.popDialog();
    }

    /**
     * Gibt den Titel der MessageBox in der aktuellen Sprache zurück.
     *
     * @return Der Titel der MessageBox in der aktuellen Sprache.
     */
    public ReadOnlyStringProperty titleProperty()
    {
        return title;
    }

    /**
     * Gibt die Nachricht der MessageBox in der aktuellen Sprache zurück.
     *
     * @return Die Nachricht der MessageBox in der aktuellen Sprache.
     */
    public ReadOnlyStringProperty messageProperty()
    {
        return message;
    }
}
