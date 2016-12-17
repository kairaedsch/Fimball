package sep.fimball.viewmodel.pinballcanvas;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;

import java.util.Observable;
import java.util.Observer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class PinballCanvasViewModelTest
{
    private Observer redrawObserver;
    private boolean drawUpdateRecieved;

    @Test
    public void addRedrawObserver() throws Exception
    {
        PinballMachine pinballMachine = Mockito.mock(PinballMachine.class);

        GameSession gameSession = Mockito.mock(GameSession.class);
        Mockito.when(gameSession.getPinballMachine()).thenReturn(pinballMachine);
        Mockito.doAnswer(invocationOnMock ->
        {
            redrawObserver = invocationOnMock.getArgument(0);
            return null;
        }).when(gameSession).addGameLoopObserver(any());

        PinballCanvasViewModel pinballCanvasViewModel = new PinballCanvasViewModel(gameSession, DrawMode.GAME) {};

        drawUpdateRecieved = false;
        pinballCanvasViewModel.addRedrawObserver((o, arg) -> drawUpdateRecieved = true);

        redrawObserver.update(null, null);
        assertThat(drawUpdateRecieved, is(true));
    }
}