package sep.fimball.model.trigger;

/**
 * Wird bei verschiedenen Geschehnissen im Spiel informiert, und kann auf diese Reagieren.
 */
public class Trigger
{
    /**
     * Reagiert auf Kollisionen zwischen Ball und Spielelementen.
     */
    private ElementTrigger elementTrigger;

    /**
     * TODO
     */
    private GameTrigger gameTrigger;

    /**
     * Reagiert auf Aktionen des Spielers.
     */
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
