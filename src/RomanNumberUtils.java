import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 罗马数相关工具类
 *
 * @author zyzdisciple
 * @date 2019/1/26
 */
public class RomanNumberUtils {

    private RomanNumberUtils(){}

    /**
     * 通过传入一串罗马数, 计算相应值.
     * 如果传入数据不符合规则抛出异常
     *
     * @param rns
     * @return
     */
    public static int getValue(RomanNumber... rns) throws NumberFormalErrorException {
        if (!validate(rns)) {
            throw new NumberFormalErrorException();
        }
        //数据格式正确, 仅需进行运算
        return 0;
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
