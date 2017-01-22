package sep.fimball.viewmodel.dialog.message;

import javafx.beans.property.*;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

/**
 * Das NormalMessageViewModel stellt der View Daten über eine Nachricht zur Verfügung sowie zwei Buttons.
 */
public abstract class MessageViewModel extends DialogViewModel
{
    /**
     * Der Titel der Nachricht.
     */
    private StringProperty title;

    /**
     * Der Text der Nachricht.
     */
    private StringProperty message;

    /**
     * Der Text des linken Buttons.
     */
    private StringProperty leftButton;

    /**
     * Der Text des rechten Buttons.
     */
    private StringProperty rightButton;

    /**
     * Gibt an, ob der linke Button sichtbar ist.
     */
    private BooleanProperty showleftButton;

    /**
     * Gibt an, ob der rechte Button sichtbar ist.
     */
    private BooleanProperty showRightButton;

    /**
     * Erzeugt ein MessageViewModel.
     *
     * @param dialogKey       Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     * @param bindLeftButton  Gibt an, ob der linke Button gezeigt und mit Text befüllt werden soll.
     * @param bindRightButton Gibt an, ob der rechte Button gezeigt und mit Text befüllt werden soll.
     */
    public MessageViewModel(String dialogKey, boolean bindLeftButton, boolean bindRightButton)
    {
        super(DialogType.MESSAGE);

        this.title = new SimpleStringProperty();
        this.title.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".title.key"));

        this.message = new SimpleStringProperty();
        this.message.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".message.key"));

        this.leftButton = new SimpleStringProperty();
        this.rightButton = new SimpleStringProperty();

        showleftButton = new SimpleBooleanProperty(false);
        showRightButton = new SimpleBooleanProperty(false);

        if (bindLeftButton)
        {
            this.leftButton.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".button.left.key"));
            showleftButton.set(true);
        }

        if (bindRightButton)
        {
            this.rightButton.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".button.right.key"));
            showRightButton.set(true);
        }
    }

    /**
     * Wird ausgeführt, wenn der Nutzer den rechten Button klickt.
     */
    public abstract void leftButtonClicked();

    /**
     * Wird ausgeführt, wenn der Nutzer den linken Button klickt.
     */
    public abstract void rightButtonClicked();

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

    /**
     * Gibt den Text des linken Buttons zurück.
     *
     * @return Der Text des linken Buttons.
     */
    public StringProperty leftButtonProperty()
    {
        return leftButton;
    }

    /**
     * Gibt den Text des rechten Buttons zurück.
     *
     * @return Der Text des rechten Buttons.
     */
    public StringProperty rightButtonProperty()
    {
        return rightButton;
    }

    /**
     * Gibt zurück, ob der linke Button sichtbar ist.
     *
     * @return Ob der linke Button sichtbar ist.
     */
    public BooleanProperty showleftButtonProperty()
    {
        return showleftButton;
    }

    /**
     * Gibt zurück, ob der rechte Button sichtbar ist.
     *
     * @return Ob der rechte Button sichtbar ist.
     */
    public BooleanProperty showRightButtonProperty()
    {
        return showRightButton;
    }
}
