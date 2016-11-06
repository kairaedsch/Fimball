package sep.fimball.view.pinball;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.viewmodel.pinball.PinballCanvasViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PinballCanvasSubView
{
    @FXML
    private Canvas canvas;

    private ListProperty<SpriteSubView> sprites;

    public PinballCanvasSubView()
    {
        PinballCanvasViewModel pinballCanvasViewModel = new PinballCanvasViewModel();

        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), SpriteSubView::new);
    }

    private void draw()
    {
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(canvas.getGraphicsContext2D());
        }
    }
}
