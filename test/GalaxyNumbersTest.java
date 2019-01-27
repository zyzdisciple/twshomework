import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class GalaxyNumbersTest {

    @Before
    public void setUp() throws Exception {
        GalaxyNumbers.generateGalaxyNumber(ReadFile.readFile("测试文件.txt"));
    }

    @Test
    public void getGalaxyNumberByName() {
        List<GalaxyNumbers.GalaxyNumber> numbers = GalaxyNumbers.getGalaxyNumberByName("  prok   pish");
        List<RomanNumber> romanNumbers = new ArrayList<>(2);
        for (GalaxyNumbers.GalaxyNumber number : numbers) {
            romanNumbers.add(number.getRomanNumber());
        }
        Assert.assertArrayEquals(new RomanNumber[]{
                RomanNumber.FIVE,
                RomanNumber.TEN,
        }, romanNumbers.toArray());
        Assert.assertArrayEquals(new GalaxyNumbers.GalaxyNumber[]{}, GalaxyNumbers.getGalaxyNumberByName("  prok fish   pish").toArray());
    }

    @Test
    public void contains() {
        Assert.assertFalse(GalaxyNumbers.contains("fish"));
        Assert.assertTrue(GalaxyNumbers.contains("pish"));
    }
}