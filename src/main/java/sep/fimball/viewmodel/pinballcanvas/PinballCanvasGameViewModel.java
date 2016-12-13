package sep.fimball.viewmodel.pinballcanvas;

import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Das PinballCanvasViewModel stellt der View Daten für die Anzeige des Flipperautomaten mit all seinen Elementen zur Verfügung und dient als Observable, um die View bei Änderungen zum erneuten Zeichnen auffordern zu können.
 */
public class PinballCanvasGameViewModel extends PinballCanvasViewModel
{
    /**
     * Erstellt ein neues PinballCanvasViewModel.
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
