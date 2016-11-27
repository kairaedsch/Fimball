package sep.fimball.model.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by felix on 27.11.16.
 */
@Ignore
public class GameSessionTest
{
    private PinballMachine pinballMachine;

    @Before
    public void initialize()
    {
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
    }

    @Test
    public void generateGameSessionTest()
    {
        String[] playerNames = {"TestPlayer1"};
        Player[] players = {new Player(playerNames[0])};

        GameSession gameSession = GameSession.generateGameSession(pinballMachine, playerNames);

        assertEquals(pinballMachine, gameSession.getPinballMachine());
        assertEquals(players[0], gameSession.getPlayers()[0]);
    }

    @Test
    public void generateEditorSessionTest()
    {

        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        GameSession gameSession = GameSession.generateEditorSession(pinballMachine);

        assertEquals(pinballMachine, gameSession.getPinballMachine());
        assertEquals(new Player("Editor-Player"), gameSession.getPlayers()[0]);
    }

    @After
    public void cleanup()
    {
        pinballMachine.deleteFromDisk();
    }
}
