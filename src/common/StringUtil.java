package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 进行字符串常规处理
 *
 * @author zyzdisciple
 * @date 2019/1/27
 */
public class StringUtil {

    /**
     * 删除字符串中所有空格, 返回单词列表
     *
     * @param words
     * @return
     */
    public static String[] removeBlackString(String words) {
        if (words == null) {
            return new String[]{};
        }
        return removeBlackString(Arrays.asList(words.split(" ")));
    }

    /**
     * 删除字符串中所有空格, 返回单词列表
     *
     * @param words
     * @return
     */
    public static String[] removeBlackString(List<String> words) {
        if (words == null) {
            return new String[]{};
        }
        //防止是从 Arrays.asList变化过来, 导致移除时抛出异常.
        List<String> list = new ArrayList<>(words);
        return list.stream().
                filter(str -> str.trim().length() != 0).
                collect(Collectors.toList()).
                toArray(new String[]{});
    }
}
