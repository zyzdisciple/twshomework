import common.ReadFile;
import number.GalaxyNumbers;
import number.GalaxyUnits;
import qa.QAInfo;

/**
 * 主程序
 */
public class Main {

    public static void main(String[] args) {
        //必须首先加载 星际数
        GalaxyNumbers.generateGalaxyNumber(ReadFile.readFile("测试文件.txt"));
        //加载星际单位
        GalaxyUnits.generateGalaxyUnits(ReadFile.readFile("测试文件.txt"));
        for (QAInfo qa : ReadFile.readQAProperties()) {
            qa.getQaFunction().extractQuestionParameters("how many Credits is glob prok Silver ?");
        }
    }
}
