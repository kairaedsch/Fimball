package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das PauseViewModel stellt der View Daten über die aktuell gespielte Partie Pinnball zu Verfügung und ermöglicht deren Fortsetzung oder Abbrechung.
 */
public class PauseViewModel extends DialogViewModel
{
    /**
     * Eine Highscoreliste, welche alle Highscores der aktuellen Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Erstellt ein neues PauseViewModel.
     */
    public PauseViewModel()
    {
        super(DialogType.PAUSE);
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenu.
     */
    public void exitDialog()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer zurück ins Spiel.
     */
    public void resumeGame()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt eine Highscoreliste, welche alle Highscores der aktuellen Partie enthält, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
