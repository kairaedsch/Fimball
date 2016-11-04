package sep.fimball.viewmodel;

import sep.fimball.model.GameSession;

import java.util.List;

/**
 * Created by TheAsuro on 04.11.2016.
 */
public class GameViewModel
{
    private List<SpriteViewModel> spriteViewModels;

    public GameViewModel()
    {
        // GameSession has been created and filled in the PlayerNameDialog
        // As the view has finished loading (TODO has it?), we can now start the game


        GameSession.getSingletonInstance().startNewGame();
    }
}
