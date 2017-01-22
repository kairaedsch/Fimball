package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class EditorPreviewSubViewModel
{
    private StringProperty topImagePath = new SimpleStringProperty();
    private StringProperty botImagePath = new SimpleStringProperty();

    public EditorPreviewSubViewModel(PlacedElement placedElement)
    {
        topImagePath.set(placedElement.getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, 0, 0));
        botImagePath.set(placedElement.getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.BOTTOM, 0, 0));
    }

    public StringProperty topImagePathProperty()
    {
        return topImagePath;
    }

    public StringProperty botImagePathProperty()
    {
        return botImagePath;
    }
}
