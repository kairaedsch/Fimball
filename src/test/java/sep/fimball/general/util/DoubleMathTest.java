package sep.fimball.general.util;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by TheAsuro on 19.01.2017.
 */
public class DoubleMathTest
{
    @Test
    public void clamp()
    {
        double testValAbove = 1.3;
        double testValInside = 0.7;
        double testValBelow = -0.1;

        assertThat(DoubleMath.clamp(-0.05, 0.8, testValAbove), is(0.8));
        assertThat(DoubleMath.clamp(-0.6, 10e7, testValInside), is(testValInside));
        assertThat(DoubleMath.clamp(-0.05, -0.04, testValBelow), is(-0.05));
    }

    @Test
    public void lerp()
    {
        double from = 1.0;
        double to = 2.0;

        assertThat(DoubleMath.lerp(from, to, 0.5), is(1.5));
        assertThat(DoubleMath.lerp(from, to, -0.5), is(0.5));
    }
}