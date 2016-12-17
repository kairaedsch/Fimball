package sep.fimball.model.handler;

/**
 * Das BallElement aus Sicht des Handlers.
 */
public interface HandlerBallGameElement extends HandlerGameElement
{
    /**
     * Simuliert das Bewegen der Kugel beim Sto0en an den Automaten.
     *
     * @param left Gibt an, ob von links angestoßen wurde.
     */
    void nudge(boolean left);
}
