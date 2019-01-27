import common.ReadFile;
import number.GalaxyNumbers;
import number.GalaxyUnits;
import qa.QAInfo;

import java.util.List;

/**
 * 主程序
 */
public class Main {

    public static void main(String[] args) {
        //必须首先加载 星际数
        GalaxyNumbers.generateGalaxyNumber(ReadFile.readFile("测试文件.txt"));
        //加载星际单位
        GalaxyUnits.generateGalaxyUnits(ReadFile.readFile("测试文件.txt"));
        //加载 问答配置文件
        List<QAInfo> qaInfos = ReadFile.readQAProperties();
        List<String> questions = ReadFile.readFile("questionsTest.txt");
        questions.forEach(q -> {
            System.out.println(q);
            boolean correctWord = false;
            for (QAInfo qa : qaInfos) {
                if (qa.getQaFunction().validate(qa.getQuestion(), q)) {
                    System.out.println(qa.getQaFunction().answer(q));
                    correctWord = true;
                }
            }
            if (!correctWord) {
                System.out.println("I have no idea what you are talking about");
            }
        });
    }
}
