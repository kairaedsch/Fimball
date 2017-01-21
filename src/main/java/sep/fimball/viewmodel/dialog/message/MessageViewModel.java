package sep.fimball.viewmodel.dialog.message;

import javafx.beans.property.*;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

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

    private StringProperty leftButton;

    private StringProperty rightButton;

    protected BooleanProperty showleftButton;

    protected BooleanProperty showRightButton;

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

        this.leftButton = new SimpleStringProperty();
        this.leftButton.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".button.left.key"));

        this.rightButton = new SimpleStringProperty();
        this.rightButton.bind(LanguageManagerViewModel.getInstance().textProperty(dialogKey + ".button.right.key"));

        showleftButton = new SimpleBooleanProperty(true);
        showRightButton = new SimpleBooleanProperty(true);
    }

    public abstract void leftButtonClicked();

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

    public StringProperty leftButtonProperty()
    {
        return leftButton;
    }

    public StringProperty rightButtonProperty()
    {
        return rightButton;
    }

    public BooleanProperty showleftButtonProperty()
    {
        return showleftButton;
    }

    public BooleanProperty showRightButtonProperty()
    {
        return showRightButton;
    }
}
