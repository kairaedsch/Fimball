package sep.fimball.model;

import javafx.beans.property.StringProperty;

/**
 * Created by theasuro on 07.11.16.
 */
public class Animation
{
    private String[] framePaths;
    private double duration;
    private StringProperty currentFrame;

    public Animation(String directoryPath)
    {
        // load frames and stuff
    }

    public void update()
    {
        // move to next frame logic etc.
    }

    public StringProperty currentFrameProperty()
    {
        return currentFrame;
    }
}
