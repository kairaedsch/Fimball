package sep.fimball.view.window.game;

import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameView extends WindowView<GameViewModel>
{
    private GameViewModel gameViewModel;

    @Override
    public void setViewModel(GameViewModel gameViewModel)
    {
        this.gameViewModel = gameViewModel;
    }
}
