package number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 星际单位, 与星际数类似
 * 定义语句格式为 ga ga ga Gold is 123 Credits
 * 即 星际数 + 单位(首字母需大写) + is + 十进制数 + 单位
 *
 * 基本单位需要保持统一,  不支持多基本单位
 *
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class GalaxyUnits {

    private GalaxyUnits(){}

    /**
     * 基础单位
     */
    public static final GalaxyUnit BASE_UNIT = new GalaxyUnit("", 1);

    /**
     * 星际单位保存位置, 同样是唯一, 无论从何种地方生成星际单位, 均需要指向统一位置.
     * 仅能够通过单位名称 换取星际单位, 不对外提供.
     */
    private static Map<String, GalaxyUnit> galaxyUnits = new HashMap<>(6);

    public static GalaxyUnit getGalaxyUnitByName(String unitName) {
        return galaxyUnits.get(unitName);
    }

    public static boolean contains(String unitName) {
        return galaxyUnits.containsKey(unitName);
    }

    /**
     * 通过文件内容解析生成 星际单位
     *
     * @param fileContents
     * @return
     */
    public static void generateGalaxyUnits(List<String> fileContents) {
        if (fileContents != null) {
            GalaxyUnit gu;
            for (String line : fileContents) {
                gu = getGalaxyUnitByDefinition(Arrays.asList(line.split(" ")));
                if (gu != null) {
                    galaxyUnits.put(gu.unitName, gu);
                }
            }
        }
    }

    /**
     * 根据定义生成星际单位 实体类
     * 定义语句格式为 ga ga ga Gold is 123 Credits
     * 即 星际数 + 单位(首字母需大写) + is + 十进制数 + 单位
     *
     * 其换算必须是星际单位与基础单位之间的换算.
     *
     * @param words
     * @return
     */
    private static GalaxyUnit getGalaxyUnitByDefinition(List<String> words) {
        //删除所有的空格, 空字符串
        words = words.stream().filter(word -> word.trim().length() > 0).collect(Collectors.toList());
        //判断是否包含 is 关键字
        boolean notMatch = !words.stream().anyMatch(word -> "is".equals(word.toLowerCase()));
        if (notMatch) {
            return null;
        }
        GalaxyUnit gu = null;
        //存储星际数
        StringBuilder galaxyNumbers = new StringBuilder();
        String unitName = null, baseUnitName = null, word;
        //十进制数
        Double value = null;
        //定义语句是否符合相应格式
        boolean flag = false;
        //提取星际单位定义转换语句
        for (int i = 0, L = words.size(); i < L; i++) {
            word = words.get(i);
            //是否是星际数
            if (GalaxyNumbers.contains(word)) {
                galaxyNumbers.append(" ").append(word);
                //如果星际数已经有值
            } else if (galaxyNumbers.length() > 0) {
                //且当前首字母为大写, 则表示为单位
                if ('A' <= word.charAt(0) && word.charAt(0) <= 'Z') {
                    unitName = word;
                    word = i < L - 1 ? words.get(++i).toLowerCase() : null;
                    //尝试转换下一位为数字
                    try {
                        value = i < L - 1 ? Double.valueOf(words.get(++i)) : null;
                    } catch (Exception e) {
                        value = null;
                    }
                    baseUnitName = i < L - 1 ? words.get(++i) : null;
                    if ("is".equals(word)
                            && value != null
                            && baseUnitName != null
                            && 'A' <= baseUnitName.charAt(0) && baseUnitName.charAt(0) <= 'Z') {
                        flag = true;
                        break;
                    }
                } else {
                    //无法识别, 清空星际数列表
                    galaxyNumbers = new StringBuilder();
                }
            }
        }

        if (flag) {
            int galaxyValue;
            List<RomanNumber> numbers = new ArrayList<>(6);
            for (GalaxyNumbers.GalaxyNumber number : GalaxyNumbers.getGalaxyNumberByName(galaxyNumbers.toString())) {
                numbers.add(number.getRomanNumber());
            }

            try {
                galaxyValue = RomanNumberUtils.getValue(numbers.toArray(new RomanNumber[]{}));
            } catch (NumberFormalErrorException e) {
                //当数据格式不符合要求, 则返回null
                return null;
            }
            gu = new GalaxyUnit(unitName, value / galaxyValue);
            BASE_UNIT.unitName = baseUnitName;
        }
        return gu;
    }

    public static class GalaxyUnit {
        /**
         * 单位名称
         */
        private String unitName;

        /**
         * 与可识别单位的换算比例
         */
        private Double proportion;

        private GalaxyUnit(String unitName, double proportion) {
            this.unitName = unitName;
            this.proportion = proportion;
        }

        public String getUnitName() {
            return unitName;
        }

        public Double getProportion() {
            return proportion;
        }
    }
}
