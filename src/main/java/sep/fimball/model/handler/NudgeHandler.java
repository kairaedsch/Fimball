package sep.fimball.model.handler;

import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;

public class NudgeHandler implements UserHandler
{
    private HandlerGameSession handlerGameSession;

    public NudgeHandler(HandlerGameSession handlerGameSession) {
        this.handlerGameSession = handlerGameSession;
    }

    @Override
    public void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        handlerGameSession.tilt();
    }
}
