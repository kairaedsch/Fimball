package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;

import java.util.Observer;

/**
 * Created by kaira on 05.11.2016.
 */
public class PinballCanvasSubView implements ViewBoundToViewModel<PinballCanvasViewModel>
{
    @FXML
    private Canvas canvas;

    private ListProperty<SpriteSubView> sprites;

    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), SpriteSubView::new);

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.notifyToRedraw(redrawObserver);
    }

    private void redraw()
    {
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(canvas.getGraphicsContext2D());
        }
    }
}
