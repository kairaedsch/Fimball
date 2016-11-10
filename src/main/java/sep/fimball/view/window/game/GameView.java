package sep.fimball.view.window.game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Die GameView ist für die Darstellung des Spiels während einer laufenden Partie zuständig.
 */
public class GameView extends WindowView<GameViewModel>
{
    /**
     *  Zeigt den Namen des aktuellen Spielers an.
     */
    @FXML
    private Label playername;

    /**
     * Zeigt den aktuellen Punktestand des aktuellen Spielers an.
     */
    @FXML
    private Label score;

    /**
     * Zeigt die Anzahl der Reservekugeln des aktuellen Spielers an.
     */
    @FXML
    private HBox reserveBalls;

    /**
     * Das Canvas, in welchen der Flipperautomat gezeichnet wird.
     */
    private Canvas canvas;

    /**
     * Das zur GameView gehörende GameViewModel.
     */
    private GameViewModel gameViewModel;

    /**
     * Setzt das zur GameView gehörende GameViewModel.
     * @param gameViewModel
     */
    @Override
    public void setViewModel(GameViewModel gameViewModel)
    {
        this.gameViewModel = gameViewModel;
    }
}
