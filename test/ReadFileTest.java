import org.junit.Assert;
import org.junit.Test;

/**
 * ReadFile Tester.
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
                }, ReadFile.readFile("测试文件.txt").toArray());
    }


} 
