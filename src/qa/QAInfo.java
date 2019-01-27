package qa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 问答模板对应实体类
 *
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class QAInfo {

    private String question;

    private String answer;

    /**
     * 如果无法解析或找到问题答案, 则采用 defaultAnswer
     */
    private String defaultAnswer;

    /**
     * 问题类型
     */
    private QAFunction qaFunction;

    public static List<QAInfo> analyzeProperties(Properties pp) {
        Set<String> propertyNames = pp.stringPropertyNames();
        HashMap<String, QAInfo> map = new HashMap<>();
        String key, value;
        String[] spiltNames;
        QAInfo entity;
        for (String name : propertyNames) {
            spiltNames = name.split("\\.");
            key = name.substring(0, name.lastIndexOf("."));
            if ((entity = map.get(key)) == null) {
                entity = new QAInfo();
                entity.qaFunction = QAFunctionFactory.getQAFunction(spiltNames[0], entity);
                //找不到匹配类型, 则不进行存储
                if (entity.qaFunction == null) {
                    continue;
                }
                map.put(key, entity);
            }
            value = pp.getProperty(name);
            switch (spiltNames[spiltNames.length - 1]) {
                case "Q":
                    entity.question = value;
                    break;
                case "A":
                    entity.answer = value;
                    break;
                case "N":
                    entity.defaultAnswer = value;
                    break;
                default:
                    break;
            }
        }
        return Arrays.asList(map.values().toArray(new QAInfo[]{}));
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getDefaultAnswer() {
        return defaultAnswer;
    }

    public QAFunction getQaFunction() {
        return qaFunction;
    }
}
