package sep.fimball.view.window.game;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import sep.fimball.view.ViewLoader;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Die GameView ist für die Darstellung des Spiels während einer laufenden Partie zuständig.
 */
public class GameView extends WindowView<GameViewModel>
{
    /**
     * Die StackPane, die den Pinball-Canvas enthält.
     */
    @FXML
    public StackPane pinballCanvasContainer;

    /**
     * Zeigt den Namen des aktuellen Spielers an.
     */
    @FXML
    private Label playername;

    /**
     * Zeigt den aktuellen Punktestand des aktiven Spielers an.
     */
    @FXML
    private Label score;

    /**
     * Zeigt die Anzahl der Reservekugeln des aktuellen Spielers an.
     */
    @FXML
    private HBox reserveBalls;

    /**
     * Das zur GameView gehörende GameViewModel.
     */
    private GameViewModel gameViewModel;

    @Override
    public void setViewModel(GameViewModel gameViewModel)
    {
        this.gameViewModel = gameViewModel;

        playername.textProperty().bind(Bindings.concat("Player: ", gameViewModel.playerNameProperty()));
        score.textProperty().bind(Bindings.concat("Score: ", gameViewModel.playerPointsProperty().asString()));

        ViewLoader<PinballCanvasSubView> viewLoader = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(gameViewModel.getPinballCanvasViewModel());
    }
}
