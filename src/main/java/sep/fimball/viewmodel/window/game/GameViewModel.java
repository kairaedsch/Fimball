package sep.fimball.viewmodel.window.game;

import javafx.beans.property.*;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.InputManager;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Das GameViewModel stellt der View Daten über die aktuelle Partie Pinball zu Verfügung, besonders über den aktiven Spieler.
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
     * Die Anzahl der Reservebälle des aktiven Spielers.
     */
    private IntegerProperty playerReserveBalls;

    /**
     * Die aktuelle Position der Kamera, die ihren Wert immer an die Kamera-Position im PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Die aktuelle Zoomstärke der Kamera, die ihren Wert immer an die Kamera Zoomstärke in PinballCanvasViewModel sendet. Dies geschieht durch Property-Binding.
     */
    private DoubleProperty cameraZoom;

    /**
     * Das PinballCanvasViewModel, das vom GameViewModel initialisiert und gesteuert wird.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Die zu diesem GameViewModel gehörende GameSession.
     */
    private GameSession gameSession;

    /**
     * Gibt an, ob die Partie aus dem Editor gestartet wurde.
     */
    private boolean startedFromEditor;

    /**
     * Erstellt ein neues GameViewModel.
     */
    public GameViewModel(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor)
    {
        super(WindowType.GAME);
        gameSession = GameSession.generateGameSession(pinballMachine, playerNames);

        playerPoints = new SimpleIntegerProperty();
        playerName = new SimpleStringProperty();
        playerReserveBalls = new SimpleIntegerProperty();

        // TODO lost bind on ball change
        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(gameSession.gameBallProperty().get().positionProperty());

        cameraZoom = new SimpleDoubleProperty(0.75);

        playerPoints.bind(gameSession.getCurrentPlayer().pointsProperty());
        playerName.bind(gameSession.getCurrentPlayer().nameProperty());
        playerReserveBalls.bind(gameSession.getCurrentPlayer().ballsProperty());

        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, this);

        this.startedFromEditor = startedFromEditor;
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
     * Stellt der View die Anzahl der Reservebälle des aktiven Spielers zur Verfügung.
     *
     * @return Die Anzahl der Reservebälle des aktiven Spielers.
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
     * Stellt der View das PinballCanvasViewModel zur Verfügung, damit die View den Automaten mit all seinen Sprites zeichnen kann.
     *
     * @return Das PinballCanvasViewModel.
     */
    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            return;
        }
        if (Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode()).toString()=="PAUSE") {
            gameSession.pauseAll();
            sceneManager.setDialog(new PauseViewModel(this));
        } else {
            InputManager.getSingletonInstance().addKeyEvent(keyEvent);
        }

    }

    /**
     * Setzt das Spiel im Model fort.
     */
    public void resume()
    {
        gameSession.startAll();
    }

    public PinballMachine getPinballMachine() {
        return gameSession.getPinballMachine();
    }

    public boolean startedFromEditor() {
        return startedFromEditor;
    }
}
