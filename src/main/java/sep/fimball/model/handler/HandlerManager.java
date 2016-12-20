package sep.fimball.model.handler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.input.manager.KeyEventArgs;

import java.util.ArrayList;
import java.util.List;

public class HandlerManager
{
    /**
     * Speichert die verwendeten Handler.
     */
    private List<Handler> handlers;

    /**
     * Gibt an, ob key events weiter geleitet werden.
     */
    private boolean keyEventsActivated;

    /**
     * Erstellt einen neuen HandlerManager.
     */
    public HandlerManager()
    {
        this.handlers = new ArrayList<>();
        keyEventsActivated = true;
    }

    /**
     * Fügt die gegebenen Handler hinzu.
     *
     * @param handlers Die Handler, die hinzugefügt werden sollen.
     */
    public void addHandlers(List<Handler> handlers)
    {
        this.handlers.addAll(handlers);
    }

    /**
     * Fügt den gegebenen Handler hinzu.
     *
     * @param handler Der Handler, der hinzugefügt werden soll.
     */
    public void addHandler(Handler handler)
    {
        this.handlers.add(handler);
    }

    /**
     * Benachrichtigt die Handler über eine Benutzeraktion.
     *
     * @param keyEventArgs Der Status des Tastendrucks, der die Aktion ausgelöst hat.
     */
    public void activateUserHandler(KeyEventArgs keyEventArgs)
    {
        if(keyEventsActivated)
        {
            for (Handler handler : handlers)
            {
                handler.activateUserHandler(keyEventArgs);
            }
        }
    }

    /**
     * Ist eine Kollision passiert, wird das an alle Handler weitergegeben.
     *
     * @param element    Das Element, das mit dem Ball kollidiert ist.
     * @param colliderId Die ID des Colliders des Elements, das mit dem Ball kollidiert ist.
     */
    public void activateElementHandler(GameElement element, int colliderId)
    {
        for (Handler handler : handlers)
        {
            handler.activateElementHandler(element, colliderId);
        }
    }

    /**
     * Benachrichtigt alle Handler über ein eingetretenes GameEvent.
     *
     * @param gameEvent Das GameEvent, welches aufgetreten ist.
     */
    public void activateGameHandler(GameEvent gameEvent)
    {
        for (Handler handler : handlers)
        {
            handler.activateGameHandler(gameEvent);
        }
    }

    public void setKeyEventsActivated(boolean keyEventsActivated)
    {
        this.keyEventsActivated = keyEventsActivated;
    }
}
