package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Die MainMenuView ist für die Darstellung des Hauptmenüs zuständig und ermöglicht dem Nutzer, sich einen Automaten auszusuchen und diesen dann zu bearbeiten oder zu spielen.
 */
public class MainMenuView extends WindowView<MainMenuViewModel>
{
    /**
     * Das Pane zur Anzeige aller verfügbaren Flipperautomaten, die auch auswählbar sind, wodurch eine größere Vorschau erscheint.
     */
    @FXML
    private VBox machineOverview;

    /**
     * Zeigt das Vorschau-Bild des aktuell ausgewählten Automaten an.
     */
    @FXML
    private Pane detailedPreviewImage;

    /**
     * Zeigt den Namen des ausgewählten Automaten an.
     */
    @FXML
    private Label detailedPreviewName;

    /**
     * Das Pane zur Anzeige der Highscores, die an dem Flipperautomaten erreicht wurden.
     */
    @FXML
    private VBox highscoreTable;

    /**
     * Das zum MainMenu gehörende MainMenuViewModel.
     */
    private MainMenuViewModel mainMenuViewModel;

    /**
     * Der Button, der zu den Einstellungen führt.
     */
    @FXML
    private Button settingsButton;

    /**
     * Der Button, der zur Erstellung eines neuen Automaten führt.
     */
    @FXML
    private Button addButton;

    /**
     * Der Tooltip zum settingsButton.
     */
    @FXML
    private Tooltip settingsTip;

    /**
     * Setzt das zur MainMenuView gehörende MainMenuViewModel.
     *
     * @param mainMenuViewModel Das zu setzende MainMenuViewModel.
     */
    @Override
    public void setViewModel(MainMenuViewModel mainMenuViewModel)
    {
        this.mainMenuViewModel = mainMenuViewModel;

        detailedPreviewName.textProperty().bind(mainMenuViewModel.getPinballMachineInfoSubViewModel().nameProperty());
        detailedPreviewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"file:///", mainMenuViewModel.getPinballMachineInfoSubViewModel().imagePathProperty(), "\");"));

        ViewModelListToPaneBinder.bindViewModelsToViews(machineOverview, mainMenuViewModel.pinballMachineSelectorSubViewModelListProperty(), WindowType.MAIN_MENU_PREVIEW);

        ViewModelListToPaneBinder.bindViewModelsToViews(highscoreTable, mainMenuViewModel.getPinballMachineInfoSubViewModel().highscoreListProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);

        settingsButton.textProperty().bind(LanguageManagerViewModel.getInstance().getText("mainmenu.settings.key"));

    }

    /**
     * Benachrichtigt das {@code mainMenuViewModel}, dass der Nutzer den aktuell ausgewählten Automaten bearbeiten möchte.
     */
    @FXML
    private void editClicked()
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().startEditor();
    }

    /**
     * Benachrichtigt das {@code mainMenuViewModel}, dass der Nutzer den aktuell ausgewählten Automaten spielen möchte.
     */
    @FXML
    private void playClicked()
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().showPlayerNameDialog();
    }

    /**
     * Benachrichtigt das {@code mainMenuViewModel}, dass der Nutzer die Einstellungen des Spiels einsehen und unter Umständen ändern möchte.
     */
    @FXML
    private void settingsClicked()
    {
        mainMenuViewModel.showSettingsDialog();
    }

    /**
     * Benachrichtigt das {@code mainMenuViewModel}, dass der Nutzer einen neuen Automaten erstellen und der Liste der verfügbaren Automaten hinzufügen möchte.
     */
    @FXML
    private void addClicked()
    {
        mainMenuViewModel.addNewAutomaton();
    }


}
