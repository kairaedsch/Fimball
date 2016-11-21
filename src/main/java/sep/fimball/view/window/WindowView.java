package sep.fimball.view.window;

import sep.fimball.view.ViewBoundToViewModel;

/**
 * Die WindowView ist die Oberklasse aller Haupt-WindowViews und stellt sicher, dass diese das ViewBoundToViewModel-Interface implementieren.
 * @param <ViewModelT> Die Klasse des ViewModels.
 */
public abstract class WindowView<ViewModelT> implements ViewBoundToViewModel<ViewModelT>
{

}
