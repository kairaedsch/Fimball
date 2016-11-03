package sep.fimball.general;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Created by kaira on 03.11.2016.
 */
public class ListPropertyBinder
{
    public static <A, B> void bind(ListProperty<A> listPropertyA, ObservableList<B> listPropertyB, Converter<A, B> converter)
    {
        ListChangeListener listChangeListener = new ListChangeListener<B>()
        {
            @Override
            public void onChanged(Change<? extends B> change)
            {
                listPropertyA.clear();

                for(B b : listPropertyB)
                {
                    listPropertyA.add(converter.convert(b));
                }
            }
        };

        listPropertyB.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface Converter<A, B>
    {
        A convert(B b);
    }
}
