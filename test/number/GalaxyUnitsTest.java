package number;

import common.ReadFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class GalaxyUnitsTest {

    @Before
    public void setUp() throws Exception {
        //必须首先加载 星际数
        GalaxyNumbers.generateGalaxyNumber(ReadFile.readFile("测试文件.txt"));
        //加载星际单位
        GalaxyUnits.generateGalaxyUnits(ReadFile.readFile("测试文件.txt"));
    }

    @Test
    public void getGalaxyUnitByName() {
        GalaxyUnits.GalaxyUnit unit = GalaxyUnits.getGalaxyUnitByName("Silver");
        Assert.assertEquals(17, unit.getProportion(), 0.000);
    }

    @Test
    public void contains() {
        Assert.assertFalse(GalaxyUnits.contains("gold"));
        Assert.assertTrue(GalaxyUnits.contains("Gold"));
    }
}