package sep.fimball.view.window.game;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import sep.fimball.view.pinballcanvas.PinballCanvasSubView;
import sep.fimball.view.tools.ViewLoader;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.LanguageManagerViewModel;
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
    private Label playerName;

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


    @Override
    public void setViewModel(GameViewModel gameViewModel)
    {
        StringExpression playerNameText = Bindings.concat(gameViewModel.playerNameProperty());
        StringExpression scoreText = Bindings.concat(gameViewModel.playerPointsProperty().asString());
        playerName.textProperty().bind(playerNameText);
        score.textProperty().bind(scoreText);

        ViewModelListToPaneBinder.bindAmountToViews(reserveBalls, gameViewModel.playerReserveBallsProperty(), WindowType.GAME_RESERVEBALL);
        ViewLoader<PinballCanvasSubView> viewLoader = new ViewLoader<>(WindowType.PINBALL_CANVAS);
        pinballCanvasContainer.getChildren().add(viewLoader.getRootNode());
        viewLoader.getView().setViewModel(gameViewModel.getPinballCanvasViewModel());
    }

    /**
     * Gibt den Text des gegebenen textKey in der aktuell eingestellten Sprache formatiert zurück.
     *
     * @param textKey Der Key zu dem der Text gesucht werden soll.
     * @return Der Text in der aktuell eingestellten Sprache.
     */
    private StringExpression getTranslation(String textKey)
    {
        return Bindings.concat(LanguageManagerViewModel.getInstance().textProperty(textKey), ": ");
    }
}
