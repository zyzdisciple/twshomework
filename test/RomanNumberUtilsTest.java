import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

/**
 * RomanNumberUtils Tester.
 *
 * @author zyzdisciple
 * @version 1.0
 * @since <pre>一月 26, 2019</pre>
 */
@RunWith(Parameterized.class)
public class RomanNumberUtilsTest {

    private String strNumber;

    private RomanNumber[] rns;

    private Boolean validate;

    private Object sum;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        "IVX",
                        new RomanNumber[]{RomanNumber.ONE, RomanNumber.FIVE, RomanNumber.TEN},
                        true,
                        14
                },
                {
                        "IXII",
                        new RomanNumber[]{RomanNumber.ONE, RomanNumber.TEN, RomanNumber.ONE, RomanNumber.ONE},
                        true,
                        11
                },
                {
                        "VX",
                        new RomanNumber[]{RomanNumber.FIVE, RomanNumber.TEN},
                        true,
                        15

                },
                {
                        "VV",
                        new RomanNumber[]{RomanNumber.FIVE, RomanNumber.FIVE},
                        false,
                        null

                },
                {
                        " I X II II",
                        new RomanNumber[]{RomanNumber.ONE, RomanNumber.TEN, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE, RomanNumber.ONE,},
                        false,
                        null
                },
                {
                        null,
                        null,
                        false,
                        null
                },
                {
                        "  ",
                        null,
                        false,
                        null
                }
        });
    }

    public RomanNumberUtilsTest(String strNumber, RomanNumber[] rns, boolean validate, Object sum) {
        this.rns = rns;
        this.strNumber = strNumber;
        this.validate = validate;
        this.sum = sum;
    }

    /**
     * Method: getValue(String romanNumber)
     */
    @Test
    public void testGetValueRomanNumber() throws Exception {
        if (sum != null) {
            Assert.assertEquals(sum, RomanNumberUtils.getValue(strNumber));
        }
    }

    /**
     * Method: getValue(RomanNumber... rns)
     */
    @Test
    public void testGetValueRns() throws Exception {
        if (sum != null) {
            Assert.assertEquals(sum, RomanNumberUtils.getValue(rns));
        }
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
