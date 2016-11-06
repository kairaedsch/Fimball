package sep.fimball.viewmodel.window.game;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.GameSession;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;

/**
 * Created by TheAsuro on 04.11.2016.
 */
public class GameViewModel
{
    private IntegerProperty playerPoints;
    private StringProperty playerName;
    private IntegerProperty playerReserveBalls;
    private ObjectProperty<Vector2> cameraPosition;
    private DoubleProperty cameraZoom;

    private PinballCanvasViewModel pinballCanvasViewModel;

    public GameViewModel(GameSession gameSession)
    {
        playerPoints = new SimpleIntegerProperty();
        playerName = new SimpleStringProperty();
        playerReserveBalls = new SimpleIntegerProperty();
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        playerPoints.bind(gameSession.getCurrentPlayer().pointsProperty());
        playerName.bind(gameSession.getCurrentPlayer().nameProperty());
        playerReserveBalls.bind(gameSession.getCurrentPlayer().ballsProperty());

        pinballCanvasViewModel = new PinballCanvasViewModel(gameSession.getTable().getWorld(), this);

        // GameSession has been created and filled in the PlayerNameView TODO move this to controller?
        // As the view has finished loading (TODO has it?), we can now start the game
        gameSession.startNewGame();
    }

    public IntegerProperty playerPointsProperty()
    {
        return playerPoints;
    }

    public StringProperty playerNameProperty()
    {
        return playerName;
    }

    public IntegerProperty playerReserveBallsProperty()
    {
        return playerReserveBalls;
    }

    public PinballCanvasViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }

    public ReadOnlyProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }
}
