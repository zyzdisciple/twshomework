package number;

import java.util.ArrayList;
import java.util.List;

/**
 * 罗马数相关工具类
 *
 * @author zyzdisciple
 * @date 2019/1/26
 */
public class RomanNumberUtils {

    private RomanNumberUtils(){}

    /**
     * 将罗马数字符串转换为十进制数
     *
     * @param romanNumber
     * @return
     * @throws NumberFormalErrorException
     */
    public static int getDecimalValue(String romanNumber) throws NumberFormalErrorException {
        return getDecimalValue(string2Numbers(romanNumber));
    }

    /**
     * 通过传入一串罗马数, 计算相应值.
     * 如果传入数据不符合规则抛出异常
     *
     * @param rns
     * @return
     */
    public static int getDecimalValue(RomanNumber... rns) throws NumberFormalErrorException {
        if (!validate(rns)) {
            throw new NumberFormalErrorException();
        }
        //数据格式正确, 仅需进行运算
        if (rns.length == 1) {
            return rns[0].getValue();
        }
        //这里计算采取二元数组进行处理.
        RomanNumber[] subtractArray = new RomanNumber[2];
        //减数
        Integer temp;
        int sum = 0;
        for (int i = 0, L = rns.length; i < L;) {
            temp = subtract(subtractArray[0], subtractArray[1]);
            //表示不能相减
            if (temp == null) {
                temp = subtractArray[0].getValue();
                subtractArray[0] = subtractArray[1];
                subtractArray[1] = i < L ? rns[i++] : null;
            } else {
                subtractArray[0] = rns[i++];
                subtractArray[1] = i < L ? rns[i++] : null;
            }
            sum += temp;
        }
        temp = subtract(subtractArray[0], subtractArray[1]);
        //如果不能相减, 且小值不为null, 返回结果为null
        temp = temp == null ? subtractArray[0].getValue() +
                (subtractArray[1] == null ? 0 : subtractArray[1].getValue()) : temp;
        sum += temp;
        return sum;
    }

    /**
     * 将两个罗马数相减, 如果不能减返回null;
     * 不能减包括两种情况, 小减大, 或 大数 其值不在小数的 被减数中.
     * 如果大值不存在, 返回小值的value;
     *
     *
     * @param smaller
     * @param larger
     * @return
     */
    private static Integer subtract(RomanNumber smaller, RomanNumber larger) {

        return larger == null ?
                smaller == null ?
                        0 : smaller.getValue()
                : smaller.getSubtractStr().contains(larger.getName()) ? larger.getValue() - smaller.getValue() : null;
    }

    /**
     * 数值校验, false表示校验不通过.
     * 支持传入null/空数组, 返回false
     *
     * @param rns
     * @return
     */
    public static boolean validate(RomanNumber... rns) {
        if (rns == null || rns.length == 0) {
            return false;
        }
        //最大可允许重复出现次数, 当前出现次数
        int maxRepeatTimes = rns[0].getRepeatTimes(), repeatTimes = 1;
        RomanNumber preNumber = rns[0], currentNumber;
        for (int i = 1, L = rns.length; i < L; i++) {
            currentNumber = rns[i];
            if (preNumber.equals(currentNumber)) {
                repeatTimes++;
            } else {
                maxRepeatTimes = currentNumber.getRepeatTimes();
                repeatTimes = 1;
            }
            if (repeatTimes > maxRepeatTimes) {
                return false;
            }
            preNumber = currentNumber;
        }
        return true;
    }

    /**
     * 通过传入字符串进行数值校验, 将字符串转换为RomanNumber数组进行校验
     * 如果存在空格会自动截取空格进行校验
     * 传入空字符串/null, 返回false, 校验失败.
     *
     * @param numberStr
     * @return
     */
    public static boolean validate(String numberStr) {
        return validate(string2Numbers(numberStr));
    }

    /**
     * 将字符串转为 RomanNumber数组.
     * 如果传入值为 null/空数组/格式错误, 均返回null
     *
     * @param numberStr
     * @return
     */
    public static RomanNumber[] string2Numbers(String numberStr) {
        if (numberStr == null || (numberStr = numberStr.trim()).length() == 0) {
            return null;
        }
        List<RomanNumber> rns = new ArrayList<>(numberStr.length());
        RomanNumber rn;
        String str;
        //空字符直接跳过, 如果字符无法匹配表示当前数据格式有问题, 返回null
        for (Character c : numberStr.toCharArray()) {
            str = c.toString();
            if (!str.trim().isEmpty()) {
                rn = RomanNumber.getInstance(str);
            } else {
                continue;
            }
            if (rn == null) {
                return null;
            } else {
                rns.add(rn);
            }
        }
        return rns.toArray(new RomanNumber[rns.size()]);
    }
}
