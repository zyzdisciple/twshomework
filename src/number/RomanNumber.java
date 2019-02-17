package number;

/**
 * 罗马数基础类， 完成 罗马数 到基础数的映射。
 * 提供 通过名称/值 获取对应对象。
 *
 * @author zyzdisciple
 * @date 2019/1/25
 */
public enum RomanNumber{

    ONE("I", 1, 3, "VX"),
    FIVE("V", 5, 1, ""),
    TEN("X", 10, 3, "LC"),
    FIFTY("L", 50, 1, ""),
    ONE_HUNDRED("C", 100, 3, "DM"),
    FIVE_HUNDRED("D", 500, 1, ""),
    ONE_THOUSAND("M", 1000, 3, "");

    private String name;

    private int value;

    /**
     * 可连续重复最大次数, -1表示无限, 0表示不允许重复
     */
    private int repeatTimes;

    /**
     * 当做减法运算, 被减数的列表, 不在列表中表示无法做减法, 且只能由大减小
     *
     * 在这里存储的是其name值, 直接传入 RomanNumber可能会导致初始化存在问题.
     */
    private String subtractStr;

    RomanNumber(String name, int value, int repeatTimes, String subtractStr) {
        this.name = name;
        this.repeatTimes = repeatTimes;
        this.value = value;
        this.subtractStr = subtractStr;
    }

    /**
     * 通过名称获取RomanNumber
     *
     * @param name
     * @return
     */
    public static RomanNumber getInstance(String name) {
        for (RomanNumber rn : RomanNumber.values()) {
            if (rn.name.equals(name)) {
                return rn;
            }
        }
        return null;
    }

    /**
     * 通过值获取实体类
     *
     * @param value
     * @return
     */
    public static RomanNumber getInstance(int value) {
        for (RomanNumber rn : RomanNumber.values()) {
            if (value == rn.value) {
                return rn;
            }
        }
        return null;
    }

    /**
     * 如果传入为null表示保持原值
     * @param rn
     * @return
     */
    public int add(RomanNumber rn) {
        return this.value + (rn == null ? 0 : rn.value);
    }

    /**
     * 如果传入为null表示保持原值
     *
     * @param rn
     * @return
     */
    public int subtract(RomanNumber rn) {
        //TODO
        //在这里同样需要考虑数值校验的问题， 也可以将数值校验问题， 放在外部进行
        return this.value - (rn == null ? 0 : rn.value);
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public String getSubtractStr() {
        return subtractStr;
    }
}
