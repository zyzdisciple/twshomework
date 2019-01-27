package qa;

/**
 *
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class QAFunctionFactory {

    public static QAFunction getQAFunction(String type, QAInfo qaInfo) {
        switch (type) {
            case "calculate":
                return new QACalculateFunction(qaInfo);
            case "convert":
                return new QAConvertFunction(qaInfo);
            default:
                return null;
        }

    }
}
