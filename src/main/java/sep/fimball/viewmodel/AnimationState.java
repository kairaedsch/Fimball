package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.model.Animation;

/**
 * Created by alexcekay on 16.11.16.
 */
public class AnimationState
{
    private ObjectProperty<Animation> animation;

    private int currentFrameIndex;

    public AnimationState(ObjectProperty<Animation> animation)
    {
        this.animation = new SimpleObjectProperty<>(animation.get());
    }

    public int getAnimationSize()
    {
        return animation.get().getFramePaths().length;
    }

    public String getCurrentFrame()
    {
        return animation.get().getFramePaths()[currentFrameIndex];
    }

    public void setCurrentFrameIndex(int index)
    {
        currentFrameIndex = index;
    }
}
