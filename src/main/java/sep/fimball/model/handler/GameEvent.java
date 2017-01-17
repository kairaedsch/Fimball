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
     * Spielereignis, das ausgelöst wird, wenn der Ball gespawned wird.
     */
    BALL_SPAWNED,

    /**
     * Spielereignis, das ausgelöst wird, wenn das Spiel vorbei ist.
     */
    GAME_OVER,

    /**
     * Spielereignis, wenn das Spiel gestartet wird.
     */
    START,

    /**
     * Spielereignis, wenn das Spiel beendet wird.
     */
    PAUSE
}
