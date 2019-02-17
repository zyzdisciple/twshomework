package number;

import common.ReadFile;
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
        List<GalaxyNumber> numbers = GalaxyNumbers.getGalaxyNumberByName("  prok   pish");
        List<RomanNumber> romanNumbers = new ArrayList<>(2);
        for (GalaxyNumber number : numbers) {
            romanNumbers.add(number.romanNumber);
        }
        Assert.assertArrayEquals(new RomanNumber[]{
                RomanNumber.FIVE,
                RomanNumber.TEN,
        }, romanNumbers.toArray());
        Assert.assertArrayEquals(new GalaxyNumber[]{}, GalaxyNumbers.getGalaxyNumberByName("  prok fish   pish").toArray());
    }

    @Test
    public void contains() {
        Assert.assertFalse(GalaxyNumbers.contains("fish"));
        Assert.assertTrue(GalaxyNumbers.contains("pish"));
    }
}