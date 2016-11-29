package sep.fimball.view;

import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by kaira on 29.11.2016.
 */
public class ViewLoaderTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void test() throws Exception
    {
        ViewLoader viewLoader = new ViewLoader<>(() -> "label.fxml");
        assertThat(viewLoader.getRootNode(), is(not(nullValue())));
        assertThat(viewLoader.getView(), is(not(nullValue())));
    }
}