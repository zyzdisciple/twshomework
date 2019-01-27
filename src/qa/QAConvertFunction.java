package qa;

import number.GalaxyNumbers;
import number.GalaxyUnits;
import number.NumberFormalErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class QAConvertFunction extends QAFunction {

    public QAConvertFunction(QAInfo qa) {
        super(qa);
    }

    @Override
    public String answer(String line) {
        Map<String, String> map = extractQuestionParameters(line);
        if (map == null || map.isEmpty()) {
            return qa.getDefaultAnswer();
        }
        Map<String, String> fillMap = new HashMap<>(map.size());
        //数值 单位转换都有
        String value;
        for (String key : map.keySet()) {
            switch (key) {
                case "baseUnit":
                    value = GalaxyUnits.BASE_UNIT.getUnitName();
                    break;
                case "galaxyNumber":
                    value = galaxyNumber2bigDecimal(map.get(key), map.get("galaxyUnit"));
                    break;
                case "galaxyUnit":
                    value = map.get(key);
                    break;
                default:
                    value = null;
                    break;
            }
            fillMap.put(key, value);
        }
        return fillAnswer(map, fillMap);
    }

    /**
     * 星际值转换为 十进制数, 如需一金 二十银 三十铁这种数值, 需要更改此方法即可
     * @param galaxyNumber
     * @param galaxyUnit
     * @return
     */
    private String galaxyNumber2bigDecimal(String galaxyNumber, String galaxyUnit) {
        //处理数据, 将单位 与 数值分离开来
        //如 一金 二十银 三十铁 暂时不允许存在
        for (String str : galaxyNumber.split(" ")) {
            if (!GalaxyNumbers.contains(str) && GalaxyUnits.contains(str)) {
                galaxyNumber = galaxyNumber.replace(str, "");
            }
        }
        int unitNumber = 0;
        for (String str : galaxyUnit.split(" ")) {
            if (GalaxyNumbers.contains(str) && !GalaxyUnits.contains(str)) {
                galaxyUnit = galaxyUnit.replace(str, "");
            } else if (GalaxyUnits.contains(str)) {
                unitNumber++;
            }
        }
        if (unitNumber > 1) {
            return null;
        }

        List<GalaxyNumbers.GalaxyNumber> numbers = GalaxyNumbers.getGalaxyNumberByName(galaxyNumber);
        double sum;
        if (numbers.size() == 0) {
            return null;
        } else {
            try {
                sum = GalaxyNumbers.getValue(numbers);
            } catch (NumberFormalErrorException e) {
                return null;
            }
        }
        sum = sum * GalaxyUnits.getGalaxyUnitByName(galaxyUnit.trim()).getProportion();
        return sum + "";
    }
}
