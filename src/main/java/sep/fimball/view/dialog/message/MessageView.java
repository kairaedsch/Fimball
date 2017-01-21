package sep.fimball.view.dialog.message;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Die MessageView ist für die Darstellung einer Nachricht zuständig.
 */
public class MessageView extends DialogView<MessageViewModel>
{
    /**
     * Der Titel der Nachricht.
     */
    @FXML
    private TitledPane title;

    /**
     * Der Text der Nachricht.
     */
    @FXML
    private Label message;

    @FXML
    public Button leftButton;

    @FXML
    public Button rightButton;

    /**
     * Das zur View dazugehörige ViewModel.
     */
    private MessageViewModel messageViewModel;

    @Override
    public void setViewModel(MessageViewModel messageViewModel)
    {
        this.messageViewModel = messageViewModel;
        title.textProperty().bind(messageViewModel.titleProperty());
        message.textProperty().bind(messageViewModel.messageProperty());

        leftButton.textProperty().bind(messageViewModel.leftButtonProperty());
        leftButton.visibleProperty().bind(messageViewModel.showleftButtonProperty());
        rightButton.textProperty().bind(messageViewModel.rightButtonProperty());
        rightButton.visibleProperty().bind(messageViewModel.showRightButtonProperty());
    }

    /**
     * Benachrichtigt das {@code messageViewModel}, dass die Nachricht geschlossen werden kann.
     */
    @FXML
    private void leftButtonClicked()
    {
        messageViewModel.leftButtonClicked();
    }

    /**
     * Benachrichtigt das {@code messageViewModel}, dass die Nachricht geschlossen werden kann.
     */
    @FXML
    private void rightButtonClicked()
    {
        messageViewModel.rightButtonClicked();
    }
}
