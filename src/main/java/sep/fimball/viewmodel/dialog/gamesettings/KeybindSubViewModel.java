package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.dialog.message.normal.NormalMessageViewModel;

/**
 * Das KeybindSubViewModel stellt der View Daten über eine Tastenbelegung und das dazu gehörende {@link sep.fimball.model.game.GameElement} zur Verfügung und ermöglicht die Änderung der zugeteilten Taste.
 */
public class KeybindSubViewModel
{
    /**
     * Der Name der Aktion, die durch die Taste ausgeführt wird.
     */
    private StringProperty bindingName;

    /**
     * Der Name der Taste, die das {@link sep.fimball.model.game.GameElement} bedient.
     */
    private StringProperty keyName;

    /**
     * Die Funktion, für die die Tastenbelegung festgelegt wird.
     */
    private KeyBinding keyBinding;

    /**
     * Die Einstellungen, die die Tastenbelegungen speichert.
     */
    private Settings settings;

    /**
     * Das zugehörige GameSettingsViewModel.
     */
    private GameSettingsViewModel gameSettingsViewModel;

    /**
     * Erzeugt ein neues KeyBindSubViewModel.
     *
     * @param settings   Die aktuellen Einstellungen.
     * @param keyBinding Die Funktion des Automaten, an die die Taste gebunden wird.
     * @param keyCode    Die Taste, an die die Funktion gebunden wird.
     * @param gameSettingsViewModel Das zugehörige GameSettingsViewModel.
     */
    public KeybindSubViewModel(Settings settings, KeyBinding keyBinding, KeyCode keyCode, GameSettingsViewModel gameSettingsViewModel)
    {
        this.keyBinding = keyBinding;
        this.settings = settings;
        this.gameSettingsViewModel = gameSettingsViewModel;

        keyName = new SimpleStringProperty();
        keyName.setValue(keyCode != null ? keyCode.getName() : "No Key set");

        bindingName = new SimpleStringProperty();
        bindingName.bind(Bindings.concat(keyBinding.getName()));
    }

    /**
     * Erteilt dem Model den Befehl, die Taste, die das {@link sep.fimball.model.game.GameElement} bedient, auf die Übergebene zu ändern.
     *
     * @param keyCode Der Code der neuen Taste.
     */
    public void changeKeyBinding(KeyCode keyCode)
    {
        if (keyCode == KeyCode.UNDEFINED)
        {
            gameSettingsViewModel.getSceneManagerViewModel().pushDialog(new NormalMessageViewModel("gamesettings.undefinedKey"));
        } else
        {
            settings.setKeyBinding(keyCode, keyBinding);
        }
    }

    /**
     * Stellt der View den Namen der Aktion, die bedient werden soll, zur Verfügung.
     *
     * @return Der Name des bedienbaren Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty bindingNameProperty()
    {
        return bindingName;
    }

    /**
     * Stellt der View die Taste, die das Flipperautomaten-Element bedienen soll, zur Verfügung.
     *
     * @return Die Taste, die das Flipperautomaten-Element bedienen soll.
     */
    public ReadOnlyStringProperty keyNameProperty()
    {
        return keyName;
    }

    /**
     * Vergleicht zwei KeyBindSubViewModels nach den Namen der Aktionen, die sie auslösen sollen.
     *
     * @param subViewModelOne Ein KeyBindSubViewModel, mit dem das {@code subViewModelTwo} verglichen werden soll.
     * @param subViewModelTwo Ein KeyBindSubViewModel, mit dem das {@code subViewModelOne} verglichen werden soll.
     * @return {@code 0}, falls die Namen gleich sind; ein Wert kleiner 0, falls {@code subViewModelOne} lexikographisch kleiner ist als {@code subViewModelTwo}; und ein Wert größer 0, falls {@code subViewModelOne} lexikographisch größer ist als {@code subViewModelTwo}
     */
    public static int compare(KeybindSubViewModel subViewModelOne, KeybindSubViewModel subViewModelTwo)
    {
        return subViewModelOne.bindingNameProperty().get().compareTo(subViewModelTwo.bindingNameProperty().get());
    }
}
