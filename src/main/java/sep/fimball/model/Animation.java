package sep.fimball.model;

public class Animation implements Renderable
{
    private Sprite[] frames;
    private int currentFrame;

    public Animation(Sprite[] frames)
    {
        if (frames == null || frames.length == 0)
            throw new IllegalArgumentException("Animation can't be null and must have at least one frame!");

        this.frames = frames;
    }

    public Sprite getFrame()
    {
        if (currentFrame >= frames.length || currentFrame < 0)
            throw new IllegalStateException();

        return frames[currentFrame];
    }
}