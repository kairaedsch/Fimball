package sep.fimball.view.pinballcanvas;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasViewModel;

import java.util.Observer;

/**
 * Die PinBallCanvasSubView ist für das Zeichnen eines Flipperautomaten mit all seinen Elementen zuständig.
 */
public class PinballCanvasSubView implements ViewBoundToViewModel<PinballCanvasViewModel>
{
    /**
     * Das Canvas, in welches die Sprites gezeichnet werden.
     */
    @FXML
    private Canvas canvas;

    /**
     * Alle Sprites der zu zeichnenenden Flipperautomaten-Elemente.
     */
    private ListProperty<SpriteSubView> sprites;

    /**
     * Die Position der Kamera, welcher den Ausschnitt des Flipperautomaten angibt, der gezeichnet werden soll.
     */
    private SimpleObjectProperty<Vector2> cameraPosition;

    /**
     * Die Stärke des Zooms der Kamera, welche die Größe der Flipperautomaten-Elemente bestimme.
     */
    private SimpleDoubleProperty cameraZoom;

    /**
     * Das zum PinballCanvasSubView gehörende PinballCanvasViewModel.
     */
    private PinballCanvasViewModel pinballCanvasViewModel;

    /**
     * Setzt das zur PinballCanvasSubView gehörende PinballCanvasViewModel.
     * @param pinballCanvasViewModel Das zu setzende PinballCanvasViewModel.
     */
    @Override
    public void setViewModel(PinballCanvasViewModel pinballCanvasViewModel)
    {
        sprites = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(sprites, pinballCanvasViewModel.spriteSubViewModelsProperty(), SpriteSubView::new);

        cameraPosition = new SimpleObjectProperty<>();
        cameraPosition.bind(pinballCanvasViewModel.cameraPositionProperty());

        cameraZoom = new SimpleDoubleProperty();
        cameraZoom.bind(pinballCanvasViewModel.cameraZoomProperty());

        Observer redrawObserver = (o, arg) -> redraw();
        pinballCanvasViewModel.notifyToRedraw(redrawObserver);
    }

    /**
     * Leert das canvas und zeichnet dann alle Sprites darauf, indem der GraphicsContext den Sprites zum bezeichnen übergeben wird.
     */
    private void redraw()
    {
        for (SpriteSubView sprite : sprites)
        {
            sprite.draw(canvas.getGraphicsContext2D());
        }
    }
}
