package number;

/**
 * @author zyzdisciple
 * @date 2019/2/17
 */
public final class GalaxyUnit {
    /**
     * 单位名称
     */
    public String unitName;

    /**
     * 与可识别单位的换算比例
     */
    public final Double proportion;

    public GalaxyUnit(String unitName, double proportion) {
        this.unitName = unitName;
        this.proportion = proportion;
    }
}
