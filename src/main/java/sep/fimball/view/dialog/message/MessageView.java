package sep.fimball.view.dialog.message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;


public class MessageView extends DialogView<MessageViewModel>
{
    @FXML
    private TitledPane title;

    @FXML
    private Label message;

    private MessageViewModel messageViewModel;

    @Override
    public void setViewModel(MessageViewModel messageViewModel)
    {
        this.messageViewModel = messageViewModel;
        title.textProperty().bind(messageViewModel.titleProperty());
        message.textProperty().bind(messageViewModel.messageProperty());
    }

    /**
     * Benachrichtigt das {@code ????}, dass ????? möchte.
     */
    @FXML
    private void okClicked()
    {
        messageViewModel.close();
    }
}
