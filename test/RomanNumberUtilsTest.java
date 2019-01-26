import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * RomanNumberUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>一月 26, 2019</pre>
 */
@RunWith(Parameterized.class)
public class RomanNumberUtilsTest {

    private String strNumber;

    private RomanNumber[] rns;

    private Boolean validate;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                    "IVX", new RomanNumber[]{RomanNumber.ONE, RomanNumber.FIVE, RomanNumber.TEN}, true
                },
                {
                    "IXIIII", new RomanNumber[]{RomanNumber.ONE, RomanNumber.TEN, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE,}, false
                },
                {
                    "VV", new RomanNumber[]{RomanNumber.FIVE, RomanNumber.FIVE}, false
                },
                {
                    " I X II II", new RomanNumber[]{RomanNumber.ONE, RomanNumber.TEN, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE,}, false
                },
                {
                    null, null, false
                },
                {
                    "  ", null, false
                }
        });
    }

    public RomanNumberUtilsTest(String strNumber, RomanNumber[] rns, boolean validate) {
        this.rns = rns;
        this.strNumber = strNumber;
        this.validate = validate;
    }


    /**
     * Method: getValue(RomanNumber... rns)
     */
    @Test
    public void testGetValue() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: validate(RomanNumber... rns)
     */
    @Test
    public void testValidateRns() throws Exception {
        Assert.assertEquals(validate, RomanNumberUtils.validate(rns));
    }

    /**
     * Method: validate(String numberStr)
     */
    @Test
    public void testValidateNumberStr() throws Exception {
        Assert.assertEquals(validate, RomanNumberUtils.validate(strNumber));
    }

    /**
     * Method: string2Numbers(String numberStr)
     */
    @Test
    public void testString2Numbers() throws Exception {
        Assert.assertArrayEquals(rns, RomanNumberUtils.string2Numbers(strNumber));
    }


} 
