package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.World;
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

    public PinballCanvasViewModel(World world, GameViewModel gameViewModel)
    {
        init(world);

        cameraPosition.bind(gameViewModel.cameraPositionProperty());
        cameraZoom.bind(gameViewModel.cameraZoomProperty());

    }

    private void init(World world)
    {
        cameraPosition = new SimpleObjectProperty<>();
        cameraZoom = new SimpleDoubleProperty();

        spriteSubViewModels = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(spriteSubViewModels, world.getGameElements(), SpriteSubViewModel::new);

        redrawObservable = new Observable();
        Observer redrawObserver = (o, arg) -> redraw();
        world.notifyToRedraw(redrawObserver);
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
