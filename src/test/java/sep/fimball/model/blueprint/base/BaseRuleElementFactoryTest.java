package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.model.handler.BaseRuleElement;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by TheAsuro on 03.12.2016.
 */
public class BaseRuleElementFactoryTest
{
    @Test
    public void factoryTest()
    {
        final boolean TEST_BOOL = true;
        final int TEST_INT = 42;

        BaseElementJson.RuleElementJson.RuleElementEventJson event = new BaseElementJson.RuleElementJson.RuleElementEventJson();
        event.colliderId = TEST_INT;

        BaseElementJson.RuleElementJson json = new BaseElementJson.RuleElementJson();
        json.events = new BaseElementJson.RuleElementJson.RuleElementEventJson[] { event };
        json.general = new BaseElementJson.RuleElementJson.RuleElementGeneralJson();
        json.general.givesPoints = TEST_BOOL;

        BaseRuleElement generatedElement = BaseRuleElementFactory.create(json);

        assertThat(generatedElement.getEventMap().size(), is(1));
        generatedElement.getEventMap().forEach((id, ev) -> assertThat(id, is(TEST_INT)));
        assertThat(generatedElement.givesPoints(), is(TEST_BOOL));
    }
}
