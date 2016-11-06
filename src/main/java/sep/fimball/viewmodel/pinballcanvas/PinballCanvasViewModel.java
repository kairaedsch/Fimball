package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.GameSession;
import sep.fimball.viewmodel.window.game.GameViewModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kaira on 06.11.2016.
 */
public class PinballCanvasViewModel
{
    private ListProperty<SpriteSubViewModel> spriteSubViewModels;
    private ObjectProperty<Vector2> cameraPosition;
    private DoubleProperty cameraZoom;
    private Observable redrawObservable;

    public PinballCanvasViewModel(GameSession gameSession, GameViewModel gameViewModel)
    {
        initWithGameSession(gameSession);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());

    }

    private void initWithGameSession(GameSession gameSession)
    {
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(spriteSubViewModels, gameSession.getTable().getWorld().getWorldElements(), SpriteSubViewModel::new);

        redrawObservable = new Observable();
        Observer redrawObserver = (o, arg) -> redraw();
        // TODO give model the observer
    }

    public ReadOnlyListProperty<SpriteSubViewModel> spriteSubViewModelsProperty()
    {
        return spriteSubViewModels;
    }

    public ReadOnlyObjectProperty<Vector2> cameraPositionProperty()
    {
        return cameraPosition;
    }

    public ReadOnlyDoubleProperty cameraZoomProperty()
    {
        return cameraZoom;
    }

    public void notifyToRedraw(Observer observer)
    {
        redrawObservable.addObserver(observer);
    }

    public void redraw()
    {
        redrawObservable.hasChanged();
        redrawObservable.notifyObservers();
    }
}
