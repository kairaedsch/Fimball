package sep.fimball.viewmodel.pinballcanvas;

import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Das PinballCanvasGameViewModel dient der View als PinballCanvasViewModel und wird speziell für das Spielen eines Flipperautomaten eingesetzt.
 */
public class PinballCanvasGameViewModel extends PinballCanvasViewModel
{
    /**
     * Erstellt ein neues PinballCanvasGameViewModel.
     *
     * @param gameSession   Die Spielsitzung.
     * @param gameViewModel Das korrespondierende GameViewModel.
     */
    public PinballCanvasGameViewModel(GameSession gameSession, GameViewModel gameViewModel)
    {
        super(gameSession, DrawMode.GAME);
        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, gameSession.getWorld().gameElementsProperty(), SpriteSubViewModel::new);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());
    }
}
