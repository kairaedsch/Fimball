package sep.fimball.model.handler;

/**
 * Aufzählung verschiedener Spielereignisse.
 */
public enum GameEvent
{
    /**
     * Spielereignis, das ausgelöst wird, wenn der Ball als verloren gilt.
     */
    BALL_LOST,

    /**
     * Spielereignis, das ausgelöst wird, wenn der Ball gespawnet wird.
     */
    BALL_SPAWNED,

    /**
     * Spielereignis, das ausgelöst wird, wenn am Automaten gestoßen wurde.
     */
    NUDGE
}
