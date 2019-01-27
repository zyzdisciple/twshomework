package common;

import org.junit.Assert;
import org.junit.Test;

/**
 * common.ReadFile Tester.
 *
 * @author zyzdisciple
 * @version 1.0
 * @since <pre>一月 26, 2019</pre>
 */
public class ReadFileTest {

    @Test
    public void testReadFile() throws Exception {
        Assert.assertArrayEquals(
                new String[]{
                        "glob is I",
                        "prok is V",
                        "pish is X",
                        "tegj is L",
                        "glob glob Silver is 34 Credits",
                        "glob prok Gold is 57800 Credits",
                        "pish pish Iron is 3910 Credits",
                }, ReadFile.readFile("测试文件.txt").toArray());
    }

    @Test
    public void testReadQAProperties() throws Exception {
        ReadFile.readQAProperties();
    }


} 
