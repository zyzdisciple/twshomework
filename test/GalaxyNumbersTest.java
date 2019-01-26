import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class GalaxyNumbersTest {

    public Map<String, GalaxyNumbers.GalaxyNumber> numbers;

    @Before
    public void setUp() throws Exception {
        numbers = GalaxyNumbers.generateGalaxyNumber(ReadFile.readFile("测试文件.txt"));
    }

    @Test
    public void generateGalaxyNumber() {
        Assert.assertArrayEquals(new String[]{
                "glob",
                "prok",
                "pish",
                "tegj"
        }, GalaxyNumbers.galaxyNumbers.keySet().toArray());
    }

    @Test
    public void getGalaxyNumberByName() {
        Assert.assertArrayEquals(new GalaxyNumbers.GalaxyNumber[]{
                numbers.get("prok"),
                numbers.get("pish")
        }, GalaxyNumbers.getGalaxyNumberByName("  prok   pish").toArray());
        Assert.assertEquals(null, GalaxyNumbers.getGalaxyNumberByName("  prok fish   pish"));
    }

    @Test
    public void contains() {
        Assert.assertFalse(GalaxyNumbers.contains("fish"));
        Assert.assertTrue(GalaxyNumbers.contains("pish"));
    }
}