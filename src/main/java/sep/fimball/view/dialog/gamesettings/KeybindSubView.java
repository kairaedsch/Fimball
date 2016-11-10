package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.KeybindSubViewModel;


/**
 * Die KeybindSubView ist für die Darstellung einer Tastenbelegung zuständig und ermöglicht es dem Nutzer, die Taste beliebig anzupassen.
 */
public class KeybindSubView implements ViewBoundToViewModel<KeybindSubViewModel>
{
    /**
     * Zeigt die aktuell gebundene Taste und ermöglicht dessen Änderung.
     */
    @FXML
    private TextField keybindKey;

    /**
     * Zeigt den Name der belegten Funktion.
     */
    @FXML
    private Label keybindName;

    /**
     * Das zur KeyBindView gehörende KeybindSubViewModel.
     */
    private KeybindSubViewModel keybindSubViewModel;

    /**
     * Setzt das zur KeyBindView gehörende keybindSubViewModel.
     * @param keybindSubViewModel
     */
    @Override
    public void setViewModel(KeybindSubViewModel keybindSubViewModel) {
        this.keybindSubViewModel = keybindSubViewModel;
    }
}