package sep.fimball.model.element;

import sep.fimball.model.Player;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class ScoreTrigger implements ElementTrigger
{
    private Player currentPlayer;

    public ScoreTrigger(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        currentPlayer.pointsProperty().set(currentPlayer.getPoints() + element.getPointReward());
    }
}
