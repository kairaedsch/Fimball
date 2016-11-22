package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Trigger, der bei Kollision mit dem Ball ausgelöst wird, und die Punkte des aktuellen Spielers erhöht.
 */
public class ScoreTrigger implements ElementTrigger
{
    /**
     * Aktuelle GameSession.
     */
    private TriggerGameSession session;

    /**
     * Erstellt einen neuen Trigger.
     *
     * @param session Aktuelle GameSession.
     */
    public ScoreTrigger(TriggerGameSession session)
    {
        this.session = session;
    }


    @Override
    public void activateElementTrigger(GameElement element, int colliderID)
    {
        session.getCurrentPlayer().pointsProperty().set(session.getCurrentPlayer().getPoints() + element.getPointReward());
    }
}
