import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 星际数
 *
 * 星际数与罗马数并不要求一一对应, 为多对一. 可以存在多个星际数 对应同一个罗马数.
 *
 * @author zyzdisciple
 * @date 2019/1/26
 */
public class GalaxyNumbers {

    /**
     * 通过文件内容生成 星际数
     * 其中各个定义语句需要进行换行操作
     *
     * @param fileContents
     * @return
     */
    public static List<GalaxyNumber> generateGalaxyNumber(List<String> fileContents) {
        GalaxyNumber gn;
        List<GalaxyNumber> list = new ArrayList<>(7);
        for (String line : fileContents) {
            //字符串根据空格分隔成n个单词
            gn = getGalaxyNumberByDefinition(Arrays.asList(line.split(" ")));
            if (gn != null) {
                list.add(gn);
            }
        }
        return null;
    }

    /**
     * 根据定义语句获取 GalaxyNumber
     *
     * 如果获取不到返回null
     *
     * @param words
     * @return
     */
    private static GalaxyNumber getGalaxyNumberByDefinition(List<String> words) {
        //删除所有的空格, 空字符串
        words = words.stream().filter(word -> word.trim().length() > 0).collect(Collectors.toList());
        //判断是否包含 is 关键字
        boolean notMatch = !words.stream().anyMatch(word -> "is".equals(word.toLowerCase()));
        if (notMatch) {
            return null;
        }
        String word;
        RomanNumber rn;
        for (int i = 0, L = words.size(); i < L; i++) {
            word = words.get(i);
            //要求 is的前一个单词必须存在, 且后一个单词能与 RomanNumber对应上
            if ("is".equals(word.toLowerCase())) {
                if (i == 0
                        || i == L - 1
                        || (rn = RomanNumber.getInstance(words.get(i + 1).toUpperCase())) == null) {
                    continue;
                } else {
                    return new GalaxyNumber(words.get(i - 1), rn);
                }
            } else {
                continue;
            }
        }
        return null;
    }


    /**
     * 星际数实体类
     */
    static class GalaxyNumber {

        private RomanNumber romanNumber;

        private String galaxyName;

        GalaxyNumber(String galaxyName, RomanNumber romanNumber) {
            this.galaxyName = galaxyName;
            this.romanNumber = romanNumber;
        }



        public RomanNumber getRomanNumber() {
            return romanNumber;
        }

        public void setRomanNumber(RomanNumber romanNumber) {
            this.romanNumber = romanNumber;
        }

        public String getGalaxyName() {
            return galaxyName;
        }

        public void setGalaxyName(String galaxyName) {
            this.galaxyName = galaxyName;
        }
    }

}

