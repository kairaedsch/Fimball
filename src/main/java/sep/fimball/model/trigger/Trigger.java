package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyEventType;

/**
 * Wird bei verschiedenen Geschehnissen im Spiel informiert, und kann auf diese Reagieren.
 */
public class Trigger implements ElementTrigger, UserTrigger, GameTrigger
{
    /**
     * Reagiert auf Kollisionen zwischen Ball und Spielelementen.
     */
    private ElementTrigger elementTrigger = null;

    /**
     * TODO
     */
    private GameTrigger gameTrigger = null;

    /**
     * Reagiert auf Aktionen des Spielers.
     */
    private UserTrigger userTrigger = null;

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

    @Override
    public void activateElementTrigger(GameElement element, int colliderId)
    {
        elementTrigger.activateElementTrigger(element, colliderId);
    }

    @Override
    public void activateGameTrigger()
    {
        gameTrigger.activateGameTrigger();
    }

    @Override
    public void activateUserTrigger(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        userTrigger.activateUserTrigger(keyBinding, keyEventType);
    }
}
