package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sep.fimball.viewmodel.mainmenu.TableBlueprintDetailedPreviewHighscoreEntry;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuDetailedPreviewHighscoreEntryFxController
{
    @FXML
    public Label playerName;
    @FXML
    public Label score;

    public void bindToViewModel(TableBlueprintDetailedPreviewHighscoreEntry highscoreEntry)
    {
        playerName.textProperty().bind(highscoreEntry.playerNameProperty());
        score.textProperty().bind(highscoreEntry.scoreProperty().asString());
    }
}
