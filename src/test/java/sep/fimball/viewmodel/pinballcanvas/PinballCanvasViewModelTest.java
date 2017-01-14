package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;

import java.util.Observer;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * Tests für die Klasse PinballCanvasViewModel.
 */
public class PinballCanvasViewModelTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link PinballCanvasViewModel#addRedrawObserver}.
     */
    @Test
    public void addRedrawObserver()
    {
        // Erstelle GameSession Mock mit PinballMachine Mock
        GameSession gameSession = Mockito.mock(GameSession.class);
        Mockito.when(gameSession.getPinballMachine()).thenReturn(Mockito.mock(PinballMachine.class));

        // Fange redrawObserver vom PinballCanvasViewModel ab
        final Observer[] redrawObserver = new Observer[1];
        Mockito.doAnswer(invocationOnMock ->
        {
            redrawObserver[0] = invocationOnMock.getArgument(0);
            return null;
        }).when(gameSession).addGameLoopObserver(any());

        // Erstelle PinballCanvasViewModel mit Mock
        PinballCanvasViewModel pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, DrawMode.GAME)
        {
            @Override
            public void mousePressedOnGame(Vector2 vector2, MouseEvent mouseEvent)
            {

            }

            @Override
            public ReadOnlyObjectProperty<Cursor> cursorProperty()
            {
                return new SimpleObjectProperty<>(Cursor.DEFAULT);
            }

            @Override
            public Optional<RectangleDoubleByPoints> selectingRectangleProperty()
            {
                return Optional.empty();
            }
        };

        // Fange draw Update ab
        final boolean[] drawUpdateRecieved = {false};
        pinballCanvasViewModel.addRedrawObserver((o, arg) -> drawUpdateRecieved[0] = true);

        // Für Draw update aus
        redrawObserver[0].update(null, null);

        // Prüfe ob das draw Update abgefangen werden konnte
        assertThat(drawUpdateRecieved[0], is(true));
    }
}