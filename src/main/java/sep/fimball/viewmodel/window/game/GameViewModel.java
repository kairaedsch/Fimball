package sep.fimball.viewmodel.window.game;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.GameSession;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Das GameViewModel stellt der View Daten über eine aktuelle Partie Pinball zu Verfügung (Besonders über den aktiven Spieler).
 */
public class GameViewModel extends WindowViewModel
{
    /**
     * Die aktuelle Punktzahl(Score) des aktiven Spielers.
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
     * Die aktuelle Position der Kamera, welche seinen Wert immer an die Kamera Position in PinballCanvasViewModel sendet. Dies geschieht durch Propertie Binding.
     */
    private ObjectProperty<Vector2> cameraPosition;

    /**
     * Die aktuelle Zoom Stärke der Kamera, welche seinen Wert immer an die Kamera Zoom Stärke in PinballCanvasViewModel sendet. Dies geschieht durch Propertie Binding.
     */
    private DoubleProperty cameraZoom;

    /**
     * Das PinballCanvasViewModel welches vom GameViewModel initialisiert/gesteuert wird.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Erstellt ein neues GameViewModel.
     * @param gameSession
     */
    public GameViewModel(GameSession gameSession)
    {
        super(WindowType.GAME);
        playerPoints = new SimpleIntegerProperty();
        playerName = new SimpleStringProperty();
        playerReserveBalls = new SimpleIntegerProperty();
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        playerPoints.bind(gameSession.getCurrentPlayer().pointsProperty());
        playerName.bind(gameSession.getCurrentPlayer().nameProperty());
        playerReserveBalls.bind(gameSession.getCurrentPlayer().ballsProperty());

        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession.getWorld(), this);

        // GameSession has been created and filled in the PlayerNameView TODO move this to controller?
        // As the view has finished loading (TODO has it?), we can now start the game
        gameSession.startAll();
    }

    /**
     * Stellt die aktuelle Punktzahl(Score) für die View zu Verfügung.
     * @return
     */
    public IntegerProperty playerPointsProperty()
    {
        return playerPoints;
    }

    /**
     * Stellt den Name des aktiven Spielers für die View zu Verfügung.
     * @return
     */
    public StringProperty playerNameProperty()
    {
        return playerName;
    }

    /**
     * Stellt die Anzahl der Reservebälle des aktiven Spielers für die View zu Verfügung.
     * @return
     */
    public IntegerProperty playerReserveBallsProperty()
    {
        return playerReserveBalls;
    }

    /**
     * Stellt die aktuelle Position der Kamera für die View zu Verfügung.
     * @return
     */
    public ReadOnlyProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    /**
     * Stellt die aktuelle Zoom Stärke der Kamera für die View zu Verfügung.
     * @return
     */
    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    /**
     * Stellt das PinballCanvasViewModel für die View zu Verfügung, damit die View den Automaten mit all seinen Sprites zeichnen kann.
     * @return
     */
    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }
}
