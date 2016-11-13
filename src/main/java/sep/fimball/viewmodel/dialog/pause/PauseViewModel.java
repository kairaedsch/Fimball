package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das PauseViewModel stellt der View Daten über die aktuell gespielte Partie Pinball zur Verfügung und ermöglicht deren Fortsetzung oder Abbrechung.
 */
public class PauseViewModel extends DialogViewModel
{
    /**
     * Eine Highscoreliste, die alle Highscores der aktuellen Partie enthält.
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
     * Führt den Benutzer in's Hauptmenü zurück.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer in's Spiel zurück.
     */
    public void resumeGame()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt der View eine Liste, die alle Highscores der aktuellen Partie enthält, zur Verfügung.
     *
     * @return Eine Liste der Highscores der aktuellen Partie.
     */
    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
