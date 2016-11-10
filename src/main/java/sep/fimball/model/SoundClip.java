package sep.fimball.model;

/**
 * SoundClip ist eine Aufzählung von Ereignissen bei denen Soundeffekte abgespielt werden.
 */
public enum SoundClip
{
    /**
     * Der Sound, der beim Starten eines Automaten abgespielt wird.
     */
    PINBALL_TABLE_INTRO,

    /**
     * Der Sound, der beim Treffen einer Wand durch die Kugel abgespielt wird.
     */
    WALL_COLLISION,

    /**
     * Der Sound, der beim Treffen einer  Slingshot durch die Kugel abgespielt wird.
     */
    BUMPER_OR_SLINGSHOT_HIT,

    /**
     * Der Sound der bei Bewegung der Flipper abgespielt wird.
     */
    FLIPPER_MOVE,

    /**
     * Der Sound, der beim Aufziehen des Plungers abgespielt wird.
     */
    PLUNGER_PULL
    //TODO more
}