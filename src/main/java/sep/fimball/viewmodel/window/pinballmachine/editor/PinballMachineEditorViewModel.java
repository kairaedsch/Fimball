package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.ElementTypeManager;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen FlipperAutomaten zu Verfügung und ermöglicht es, diesen zu editieren.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    /**
     * Der FlipperAutomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;

    /**
     * Eine Liste, welche alle FlipperAutomat-Elemente enthält, die von dem Nutzer platziert werden können.
     */
    private ListProperty<AvailableElementSubViewModel> availableElements;

    /**
     * Erstellt ein neues PinballMachineEditorViewModel.
     * @param pinballMachine
     */
    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_EDITOR);
        this.pinballMachine = pinballMachine;

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(availableElements, ElementTypeManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(element));
    }

    /**
     * Stellt die Liste, welche alle FlipperAutomat-Elemente enthält, die von dem Nutzer platziert werden können, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyListProperty<AvailableElementSubViewModel> availableElementsProperty()
    {
        return availableElements;
    }

    /**
     * Vergrößert die Ansicht des FlipperAutomaten für den Nutzer.
     */
    public void zoomIn()
    {

    }

    /**
     * Verkleinert die Ansicht des FlipperAutomaten für den Nutzer.
     */
    public void zoomOut()
    {

    }

    /**
     * Führt den Benutzer zu dem Spiel-Window, wo der gerade von dem Nutzer editierte FlipperAutomat getestet werden kann.
     */
    public void startMachine()
    {
        sceneManager.setWindow(new GameViewModel(new GameSession()));
    }

    /**
     * Führt den Benutzer zu dem Automat-Einstellungs-Window, wo der gerade von dem Nutzer editierte FlipperAutomat u.a. gespeichert werden kann.
     */
    public void showSettingsDialog()
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }
}
