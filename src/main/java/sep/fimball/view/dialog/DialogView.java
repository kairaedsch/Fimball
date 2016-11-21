package sep.fimball.view.dialog;

import sep.fimball.view.ViewBoundToViewModel;

/**
 * Die DialogView ist die Oberklasse aller Haupt-DialogViews und stellt sicher, dass diese das ViewBoundToViewModel-Interface implementieren.
 *
 * @param <ViewModelT> Die Klasse des ViewModels.
 */
public abstract class DialogView<ViewModelT> implements ViewBoundToViewModel<ViewModelT>
{

}
