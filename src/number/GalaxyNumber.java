package number;

/**
 * 星际数实体类
 * 其内部各项属性均为 可获得， 但不可更改属性， 因此用 public final 修饰更合适， 且没有必要进行私有处理
 * @author zyzdisciple
 * @date 2019/2/17
 */
public final class GalaxyNumber {
    public final RomanNumber romanNumber;

    public final String galaxyName;

    public GalaxyNumber(String galaxyName, RomanNumber romanNumber) {
        this.galaxyName = galaxyName;
        this.romanNumber = romanNumber;
    }
}
