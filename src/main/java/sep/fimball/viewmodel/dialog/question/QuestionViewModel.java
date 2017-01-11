package sep.fimball.viewmodel.dialog.question;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class QuestionViewModel extends DialogViewModel
{
    private StringProperty title;
    private StringProperty message;
    private Action action;

    /**
     * Erzeugt ein neues QuestionViewModel.
     *
     * @param action Die Aktion die beim Klicken des OK Button des Dialogs ausgeführt werden soll.
     */
    public QuestionViewModel(String dialogKey, Action action)
    {
        super(DialogType.QUESTION);

        this.title = new SimpleStringProperty();
        this.title.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".title.key"));

        this.message = new SimpleStringProperty();
        this.message.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".message.key"));

        this.action = action;
    }

    /**
     * Führt die Aktion die ausgeführt werden soll aus und schließt den Dialog.
     */
    public void performAction()
    {
        sceneManager.popDialog();
        action.perform();
    }

    /**
     * Beendet den Dialog.
     */
    public void abort()
    {
        sceneManager.popDialog();
    }

    /**
     * Gibt den Titel des QuestionDialog in der aktuell ausgewählten Sprache zurück.
     *
     * @return Der Titel des QuestionDialog in der aktuell ausgewählten Sprache.
     */
    public ReadOnlyStringProperty titleProperty()
    {
        return title;
    }

    /**
     * Gibt die Nachricht des QuestionDialog in der aktuell ausgewählten Sprache zurück.
     *
     * @return Die Nachricht des QuestionDialog in der aktuell ausgewählten Sprache.
     */
    public ReadOnlyStringProperty messageProperty()
    {
        return message;
    }
}
