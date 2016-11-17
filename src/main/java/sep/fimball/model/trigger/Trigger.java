package sep.fimball.model.trigger;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class Trigger
{
    private ElementTrigger elementTrigger;
    private GameTrigger gameTrigger;
    private UserTrigger userTrigger;

    public void setElementTrigger(ElementTrigger elementTrigger)
    {
        this.elementTrigger = elementTrigger;
    }

    public void setGameTrigger(GameTrigger gameTrigger)
    {
        this.gameTrigger = gameTrigger;
    }

    public void setUserTrigger(UserTrigger userTrigger)
    {
        this.userTrigger = userTrigger;
    }
}
