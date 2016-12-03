package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.model.handler.BaseRuleElement;

/**
 * Created by TheAsuro on 03.12.2016.
 */
public class BaseRuleElementFactoryTest
{
    @Test
    public void factoryTest()
    {
        BaseElementJson.RuleElementJson json = new BaseElementJson.RuleElementJson();
        //json.ev

        BaseRuleElement generatedElement = BaseRuleElementFactory.generate(json);
    }
}
