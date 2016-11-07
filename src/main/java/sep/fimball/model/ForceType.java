package sep.fimball.model;

/**
 * Created by alexcekay on 03.11.16.
 */
public enum ForceType
{
    NORMAL, // just a wall
    BOUNCE, // bounces ball (e.g. Bumper)
    ACCELERATE, // accelerate thingy
    RAMP_FLOOR // keeps ball on top of ramp
}
