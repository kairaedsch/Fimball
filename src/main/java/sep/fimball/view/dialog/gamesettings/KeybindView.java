package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.KeybindSubViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameEntrySubViewModel;


/**
 * Created by kaira on 01.11.2016.
 */
public class KeybindView implements ViewBoundToViewModel<KeybindSubViewModel>
{

    private KeybindSubViewModel keybindSubViewModel;

    @FXML
    private TextField keybindKey;

    @FXML
    private Label keybindName;


    @Override
    public void setViewModel(KeybindSubViewModel keybindSubViewModel) {
        this.keybindSubViewModel = keybindSubViewModel;
    }
}
