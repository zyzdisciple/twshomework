package number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 星际数
 *
 * 星际数与罗马数并不要求一一对应, 为多对一. 可以存在多个星际数 对应同一个罗马数.
 *
 * 无论通过何种方式添加星际数, 都需要将其指向统一引用, 与罗马数类似,
 * 将来需要为数添加更多功能就可以定义在实体类中
 *
 * 定义语句格式为 a is I 这种形式
 *
 * @author zyzdisciple
 * @date 2019/1/26
 */
public class GalaxyNumbers {

    private GalaxyNumbers() {}

    /**
     * 保证整个项目内只有这一个地方可以保存 GalaxyNumber, 无论通过何种方式生成星际数, 保证指向统一引用
     * 并且不提供get set方法, 仅能够通过名称换取 星际数实体
     */
    private static Map<String, GalaxyNumber> galaxyNumbers = new HashMap<>(7);

    /**
     * 通过文件内容生成 星际数
     * 其中各个定义语句需要进行换行操作
     *
     * @param fileContents
     * @return
     */
    public static void generateGalaxyNumber(List<String> fileContents) {
        if (fileContents != null) {
            GalaxyNumber gn;
            for (String line : fileContents) {
                //字符串根据空格分隔成n个单词
                gn = getGalaxyNumberByDefinition(Arrays.asList(line.split(" ")));
                if (gn != null) {
                    galaxyNumbers.put(gn.galaxyName, gn);
                }
            }
        }
    }

    /**
     * 根据定义语句获取 GalaxyNumber
     *
     * 定义语句格式为 a is I 这种形式
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
     * 将单词转换为 星际数, 如果存在不能够转换为 星际数的单词, 则整体返回null
     *
     * @param words
     * @return
     */
    public static List<GalaxyNumber> getGalaxyNumberByName(String words) {
        if (words == null || words.trim().length() == 0) {
            return new ArrayList<>(1);
        }
        List<GalaxyNumber> numbers = new ArrayList<>(6);
        GalaxyNumber gn;
        for (String word : words.split(" ")) {
            if (word.trim().length() == 0) {
                continue;
            }
            gn = galaxyNumbers.get(word);
            if (gn == null) {
                return new ArrayList<>(1);
            } else {
                numbers.add(gn);
            }
        }
        return numbers;
    }

    /**
     * 星际数中是否包含当前单词.
     *
     * @param word
     * @return
     */
    public static boolean contains(String word) {
        return galaxyNumbers.containsKey(word);
    }

    /**
     * 将星际数 转换为 十进制数
     *
     * @param numbers
     * @return
     * @throws NumberFormalErrorException
     */
    public static int getValue(List<GalaxyNumber> numbers) throws NumberFormalErrorException {
        List<RomanNumber> romanNumbers = new ArrayList<>(numbers.size());
        for (GalaxyNumber number : numbers) {
            romanNumbers.add(number.getRomanNumber());
        }
        return RomanNumberUtils.getValue(romanNumbers.toArray(new RomanNumber[]{}));
    }

    /**
     * 星际数实体类
     */
    public static class GalaxyNumber {

        private RomanNumber romanNumber;

        private String galaxyName;

        private GalaxyNumber(String galaxyName, RomanNumber romanNumber) {
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

