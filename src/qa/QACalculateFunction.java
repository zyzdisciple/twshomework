package qa;

import number.GalaxyNumber;
import number.GalaxyNumbers;
import number.NumberFormalErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class QACalculateFunction extends QAFunction {

    public QACalculateFunction(QAInfo qa) {
        super(qa);
    }

    @Override
    public String answer(String line) {
        Map<String, String> map = extractQuestionParameters(line);
        if (map == null || map.isEmpty()) {
            return qa.getDefaultAnswer();
        }
        Map<String, String> fillMap = new HashMap<>(map.size());
        //仅包含数值转换
        for (String key : map.keySet()) {
            int sum = 0;
            List<GalaxyNumber> numbers = GalaxyNumbers.getGalaxyNumberByName(map.get(key));
            if (numbers.size() == 0) {
                return qa.getDefaultAnswer();
            } else {
                try {
                    sum = GalaxyNumbers.getValue(numbers);
                } catch (NumberFormalErrorException e) {
                    return qa.getDefaultAnswer();
                }
            }
            fillMap.put(key, sum + "");
        }
        return fillAnswer(map, fillMap);
    }
}
