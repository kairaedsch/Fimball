package sep.fimball.viewmodel.window.game;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.game.Player;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventConverter;
import sep.fimball.viewmodel.SoundManagerViewModel;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasGameViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.util.List;
import java.util.Optional;

/**
 * Das GameViewModel stellt der View Daten über die aktuelle Partie Pinball zu Verfügung, besonders über den aktiven
 * Spieler.
 */
public class GameViewModel extends WindowViewModel
{
    /**
     * Die aktuelle Punktzahl des aktiven Spielers.
     */
    private IntegerProperty playerPoints;

    /**
     * Der Name des aktiven Spielers.
     */
    private StringProperty playerName;

    /**
     * Die Anzahl der Reservekugeln des aktiven Spielers.
     */
    private IntegerProperty playerReserveBalls;

    /**
     * Die aktuelle Position der Kamera, die ihren Wert immer an die Kamera-Position im PinballCanvasViewModel sendet.
     * Dies geschieht durch Property-Binding.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Die aktuelle Stärke des Zooms der Kamera, die ihren Wert immer an die Kamera Stärke des Zooms in
     * PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
     */
    private DoubleProperty cameraZoom;

    /**
     * Das PinballCanvasViewModel, das vom GameViewModel initialisiert und gesteuert wird.
     */
    private PinballCanvasGameViewModel pinballCanvasViewModel;

    /**
     * Die zu diesem GameViewModel gehörende GameSession.
     */
    private GameSession gameSession;

    /**
     * Der KeyEventConverter des GameViewModels.
     */
    private KeyEventConverter keyEventConverter;

    /**
     * Erzeugt ein neues GameViewModel.
     *
     * @param gameSession Die GameSession, die vom GameViewModel benutzt wird.
     */
    public GameViewModel(GameSession gameSession)
    {
        super(WindowType.GAME);

        keyEventConverter = new KeyEventConverter();

        this.gameSession = gameSession;
        playerPoints = new SimpleIntegerProperty();
        playerName = new SimpleStringProperty();
        playerReserveBalls = new SimpleIntegerProperty();

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(gameSession.gameBallProperty().get().positionProperty());
        cameraZoom = new SimpleDoubleProperty(Config.DEFAULT_ZOOM);

        gameSession.currentPlayer().addListener((observable, oldValue, newValue) ->
        {
            playerPoints.bind(newValue.pointsProperty());
            playerName.bind(newValue.nameProperty());
            playerReserveBalls.bind(newValue.ballsProperty());
        });

        playerPoints.bind(gameSession.getCurrentPlayer().pointsProperty());
        playerName.bind(gameSession.getCurrentPlayer().nameProperty());
        playerReserveBalls.bind(gameSession.getCurrentPlayer().ballsProperty());

        pinballCanvasViewModel = new PinballCanvasGameViewModel(gameSession, this);

        gameSession.isOverProperty().addListener((observable, oldIsGameOver, newIsGameOver) ->
        {
            if (newIsGameOver)
            {
                List<Player> players = this.gameSession.getPlayers();
                String[] playerNames = new String[players.size()];
                for (int i = 0; i < playerNames.length; i++)
                {
                    playerNames[i] = players.get(i).nameProperty().get();
                }
                if (this.gameSession.isStartedFromEditor())
                {
                    sceneManager.setWindow(new PinballMachineEditorViewModel(this.gameSession.getPinballMachine()));
                }
                else
                {
                    sceneManager.pushDialog(new GameOverViewModel(this.gameSession.getPinballMachine(), getScores(), playerNames));
                }
            }
        });
    }

    /**
     * Stellt der View die aktuelle Punktzahl zur Verfügung.
     *
     * @return Die Punktzahl des aktiven Spielers.
     */
    public ReadOnlyIntegerProperty playerPointsProperty()
    {
        return playerPoints;
    }

    /**
     * Stellt der View den Namen des aktiven Spielers zur Verfügung.
     *
     * @return Der Name des aktiven Spielers.
     */
    public ReadOnlyStringProperty playerNameProperty()
    {
        return playerName;
    }

    /**
     * Stellt der View die Anzahl der Reservekugeln des aktiven Spielers zur Verfügung.
     *
     * @return Die Anzahl der Reservekugeln des aktiven Spielers.
     */
    public ReadOnlyIntegerProperty playerReserveBallsProperty()
    {
        return playerReserveBalls;
    }

    /**
     * Stellt der View die aktuelle Position der Kamera zur Verfügung.
     *
     * @return Die aktuelle Position der Kamera.
     */
    public ReadOnlyProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt der View die aktuelle Zoom-Stärke der Kamera zur Verfügung.
     *
     * @return Die aktuelle Zoom-Stärke der Kamera.
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Stellt der View das PinballCanvasViewModel zur Verfügung, damit die View den Automaten mit all seinen Sprites
     * zeichnen kann.
     *
     * @return Das PinballCanvasViewModel.
     */
    public PinballCanvasGameViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        Optional<KeyBinding> bindingOptional = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode());
        if (bindingOptional.isPresent())
        {
            KeyBinding binding = bindingOptional.get();
            if (binding == KeyBinding.PAUSE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
            {
                gameSession.pauseAll();
                if (gameSession.isStartedFromEditor())
                {
                    sceneManager.setWindow(new PinballMachineEditorViewModel(gameSession.getPinballMachine()));
                }
                else
                {
                    sceneManager.pushDialog(new PauseViewModel(this));
                }
            }
            else
            {
                keyEventConverter.createKeyEventArgs(keyEvent).ifPresent(gameSession::activateUserHandler);
            }
        }
    }

    /**
     * Setzt das Spiel im Model fort.
     */
    public void resume()
    {
        gameSession.startAll();
    }

    /**
     * Gibt die Scores der Spieler der aktuellen Partie zurück.
     *
     * @return Die Scores der Spieler der aktuellen Partie.
     */
    public ReadOnlyListProperty<Highscore> getScores()
    {
        ListProperty<Highscore> scores = new SimpleListProperty<>();
        scores.set(FXCollections.observableArrayList());
        for (Player player : gameSession.getPlayers())
        {
            Highscore score = new Highscore(player.pointsProperty().get(), player.nameProperty().get());
            scores.add(score);
        }
        return scores;
    }

    @Override
    public void changeBackgroundMusic()
    {
        SoundManagerViewModel.getInstance().playMusic(Sounds.GAME);
    }

    public void setCameraZoom(double cameraZoom)
    {
        this.cameraZoom.set(cameraZoom);
    }
}
