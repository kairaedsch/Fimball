package sep.fimball.view;

import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ViewLoaderTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testAll() throws Exception
    {
        ViewLoader viewLoader = new ViewLoader<>(() -> "label.fxml");

        assertThat("Es existiert ein Rootnode", viewLoader.getRootNode(), is(not(nullValue())));
        assertThat("Es existiert eine View", viewLoader.getView(), is(not(nullValue())));
    }
}