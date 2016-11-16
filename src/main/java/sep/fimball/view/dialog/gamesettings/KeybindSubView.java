package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.KeybindSubViewModel;


/**
 * Die KeybindSubView ist für die Darstellung einer Tastenbelegung zuständig und ermöglicht es dem Nutzer, die Taste beliebig anzupassen.
 */
public class KeybindSubView implements ViewBoundToViewModel<KeybindSubViewModel>
{
    /**
     * Zeigt die aktuell gebundene Taste und ermöglicht deren Änderung.
     */
    @FXML
    private TextField keybindKey;

    /**
     * Zeigt den Namen der belegten Funktion.
     */
    @FXML
    private Label keybindName;

    /**
     * Das zur KeyBindView gehörende KeybindSubViewModel.
     */
    private KeybindSubViewModel keybindSubViewModel;

    /**
     * Setzt das zur KeyBindView gehörende KeybindSubViewModel.
     *
     * @param keybindSubViewModel Das zu setzende KeybindSubViewModel.
     */
    @Override
    public void setViewModel(KeybindSubViewModel keybindSubViewModel) {
        this.keybindSubViewModel = keybindSubViewModel;

        keybindName.textProperty().bind(keybindSubViewModel.elementNameProperty());
        keybindKey.textProperty().bind(keybindSubViewModel.keyNameProperty());
    }

    /**
     *  Ändert die die Tastenbelegung für die von dieser KeybindSubView dargestellten Funktion.
     * @param keyEvent
     */
    @FXML
    private void changeKey(KeyEvent keyEvent) {
        keybindSubViewModel.changeKeyBinding(keyEvent.getCode());
    }
}
