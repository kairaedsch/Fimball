package sep.fimball.model.trigger;

import sep.fimball.model.game.GameElement;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und die Punkte des aktuellen Spielers erhöht.
 */
public class ScoreHandler implements ElementHandler
{
    /**
     * Aktuelle GameSession.
     */
    private HandlerGameSession session;

    /**
     * Erstellt einen neuen Handler.
     *
     * @param session Aktuelle GameSession.
     */
    public ScoreHandler(HandlerGameSession session)
    {
        this.session = session;
    }


    @Override
    public void activateElementHandler(GameElement element, int colliderID)
    {
        session.getCurrentPlayer().pointsProperty().set(session.getCurrentPlayer().getPoints() + element.getPointReward());
    }
}
