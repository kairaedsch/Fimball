package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.elementtype.ElementTypeManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen Flipper-Automaten zur Verfügung und ermöglicht es, diesen zu bearbeiten.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    /**
     * Der Flipperautomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;

    /**
     * Eine Liste, die alle Flipperautomat-Elemente enthält, die vom Nutzer platziert werden können.
     */
    private ListProperty<AvailableElementSubViewModel> availableElements;

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der editiert werden soll.
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_EDITOR);
        this.pinballMachine = pinballMachine;

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(availableElements, ElementTypeManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(element));
    }

    /**
     * Stellt der View die Liste, die alle Flipperautomat-Elemente enthält, die vom Nutzer platziert werden können, zur Verfügung.
     *
     * @return Eine Liste aller platzierbaren Flipperautomat-Elemente.
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableElementsProperty()
    {
        return availableElements;
    }

    /**
     * Vergrößert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomIn()
    {

    }

    /**
     * Verkleinert die Ansicht des Flipper-Automaten für den Nutzer.
     */
    public void zoomOut()
    {

    }

    /**
     * Führt den Benutzer zu dem Spielfenster, wo der gerade vom Nutzer bearbeitete Flipper-Automat getestet werden kann.
     */
    public void startPinballMachine()
    {
        sceneManager.setWindow(new GameViewModel(new GameSession(pinballMachine, new String[]{new String(new byte[]{71, 111, 116, 116, 105, 109, 112, 101, 114, 97, 116, 111, 114, 32, 84, 114, 117, 109, 112})})));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, wo der gerade vom Nutzer bearbeitete Flipperautomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }
}
