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
     * Zeigt das Vorschaubild des aktuell ausgewählten Automaten an.
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

    @FXML
    private Button settingsButton;

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

        setToolTips();
    }

    /**
     * Benachrichtigt das PlayerNameEntrySubViewModel, dass der Nutzer den aktuell ausgewählten Automaten bearbeiten möchte.
     */
    @FXML
    private void editClicked()
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().startEditor();
    }

    /**
     * Benachrichtigt das PlayerNameEntrySubViewModel, dass der Nutzer den aktuell ausgewählten Automaten spielen möchte.
     */
    @FXML
    private void playClicked()
    {
        mainMenuViewModel.getPinballMachineInfoSubViewModel().showPlayerNameDialog();
    }

    /**
     * Benachrichtigt das PlayerNameEntrySubViewModel, dass der Nutzer die Einstellungen des Spiels einsehen und unter Umständen ändern möchte.
     */
    @FXML
    private void settingsClicked()
    {
        mainMenuViewModel.showSettingsDialog();
        /*
        final long[] lv = {System.currentTimeMillis()};
        final long[] lm = {System.currentTimeMillis()};


        {
            Timeline timeline  = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(new Duration(16.66), "", event -> {
                long s = System.currentTimeMillis();
                System.out.println("View Timeline fängt an:  Last run was " + (s - lv[0]) + "ms ago.");
                try
                {
                    Thread.sleep(5);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("View Timeline hört auf:  dauer                 " + (System.currentTimeMillis() - s) + "ms     ");
                lv[0] = s;
            }));
            timeline.play();
        }
        {
            Timeline timeline  = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(new Duration(16.66), "", event -> {
                long s = System.currentTimeMillis();
                System.out.println("Model Timeline fängt an. Last run was " + (s - lm[0]) + "ms ago.");
                try
                {
                    Thread.sleep(33);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Model Timeline hört auf: dauer                 " + (System.currentTimeMillis() - s) + "ms     ");
                lm[0] = s;
            }));
            timeline.play();
        }
        */
    }

    /**
     * Benachrichtigt das PlayerNameEntrySubViewModel, dass der Nutzer einen neuen Automaten erstellen und der Liste der verfügaberen Automaten hinzufügen möchte.
     */
    @FXML
    private void addClicked()
    {
        mainMenuViewModel.addNewAutomaton();
    }

    private void setToolTips() {
        Tooltip settingsToolTip = new Tooltip();
        settingsToolTip.setText("Show settings");
        settingsButton.setTooltip(settingsToolTip);
    }
}
