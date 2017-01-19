package sep.fimball.viewmodel.dialog.question;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.Action;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

/**
 * Das PauseViewModel stellt der View Daten über eine Frage zur Verfügung und ermöglicht die Ausführung der Aktion, falls sich der Nutzer dafür entscheidet.
 */
public class QuestionViewModel extends DialogViewModel
{
    /**
     * Der Titel der Frage.
     */
    private StringProperty title;

    /**
     * Der Text der Frage.
     */
    private StringProperty message;

    /**
     * Die Aktion, die bei Zustimmung ausgeführt werden soll.
     */
    private Action action;

    /**
     * Erzeugt ein neues QuestionViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
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
