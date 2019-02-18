package qa;

import common.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyzdisciple
 * @date 2019/1/27
 */
public abstract class QAFunction {

    protected QAInfo qa;

    public QAFunction(QAInfo qa) {
        this.qa = qa;
    }

    public abstract String answer(String question);

    /**
     * 提取问题中的 参数列表
     * @param line 实际文本
     * @return
     */
    protected Map<String, String> extractQuestionParameters(String line) {
        Map<String, String> map = new HashMap<>();
        String question = qa.getQuestion();
        //校验不通过
        if (!validate(question, line)) {
            return null;
        }
        String[] qa = StringUtil.removeBlankString(question);
        String[] lw = StringUtil.removeBlankString(line);
        boolean hasParameter = false;
        //存储顺序为 参数位置, 非参数位置
        //e.g. is #a# b #c# 存储值应为 1 2 3
        //e.g. is #a# #b# cd 存储应为 11 23
        List<Integer> extractQaIndex = new ArrayList<>(4);
        //存储参数的 起止位置.
        List<Integer> extractLwIndex = new ArrayList<>(4);
        //按规则填充 qaIndex
        for (int i = 0, L = qa.length; i < L; i++) {
            if (qa[i].charAt(0) == '#') {
                //表示两个连续参数
                if (hasParameter) {
                    extractQaIndex.add(i - 1);
                }
                extractQaIndex.add(i);
                hasParameter = true;
            } else if (hasParameter) {
                extractQaIndex.add(i);
                hasParameter = false;
            }
        }
        if (extractQaIndex.size() == 0) {
            return null;
        }
        //填充 lwIndex
        //j用来指示extractQaIndex 的索引
        for (int j = 0, i = extractQaIndex.get(j), L = lw.length, LL = extractQaIndex.size(); i < L && j < LL; i++) {
            //表示参数的起始索引位置
            if (j % 2 == 1) {
                //如果是奇数, 表示需要对字符串进行匹配
                if (lw[i].equals(qa[extractQaIndex.get(j)])) {
                    //当前参数索引已完善.
                    extractLwIndex.add(i - 1);
                    //切换到下一个参数索引
                    j++;
                    //表示为连续参数
                } else if (extractQaIndex.get(j - 1) == extractQaIndex.get(j)) {
                    //需要连续跳过当前的结束为止, 下一个参数的起始位置
                    j += 2;
                    //表示已经到字符串末尾
                } else if (i == L - 1) {
                    extractLwIndex.add(i);
                }
            } else {
                extractLwIndex.add(i);
                //向前移动一位
                j++;
                //表示已经移动到末位
                if (j >= LL) {
                    extractLwIndex.add(L - 1);
                }
            }
        }
        map = getParameterList(qa, extractQaIndex, lw, extractLwIndex);
        return map;
    }

    /**
     * 将模板, 模板参数索引, 实际语句, 实际语句参数索引 生成 参数 map表
     * @param template 模板字符串
     * @param tempIndex 模板参数索引
     * @param realWords 实际语句
     * @param realIndex 实际语句索引
     * @return
     */
    private Map<String, String> getParameterList(String[] template, List<Integer> tempIndex, String[] realWords, List<Integer> realIndex) {
        Map<String, String> map = new HashMap<>();
        String key;
        StringBuilder sb;
        for (int i = 0, j = 0, L = tempIndex.size(); i < L; i++) {
            //只取偶数位, 即起始位置
            if (i % 2 != 0) {
                continue;
            }

            key = template[tempIndex.get(i)].replace("#", "");
            //表示为连续参数
            if (i > 0 && tempIndex.get(i) - 1 == tempIndex.get(i - 2)) {
                //将前一个参数的value值赋值给当前key
                sb = new StringBuilder(map.get(template[tempIndex.get(i - 2)].replace("#", "")));
            } else {
                //非连续参数, 或连续参数的第一个
                sb = new StringBuilder();
                for (int k = realIndex.get(j), endIndex = realIndex.get(j + 1); k <= endIndex; k++) {
                    sb.append(realWords[k]).append(" ");
                }
                j += 2;
            }
            map.put(key, sb.toString());
        }
        return map;
    }

    /**
     * 检验问题与传入值是否符合问题模板要求
     *
     * @param line
     * @return
     */
    public boolean validate(String target, String line) {
        if (target == null || line == null) {
            return false;
        }
        String[] qWords = StringUtil.removeBlankString(target);
        String[] lWords = StringUtil.removeBlankString(line);
        String qWord, lWord;
        //表示 当前单词/前一个单词 是否为参数单词
        int parameterNumber = 0;
        for (int i = 0, j = 0, L = qWords.length, LL = lWords.length; i < L && j < LL;) {
            qWord = qWords[i];
            //仅校验语法的合法性.
            if ('#' == qWord.charAt(0)) {
                i++;
                parameterNumber++;
                continue;
            }
            //不为空 且不是 参数单词时
            for (; j < LL;) {
                lWord = lWords[j];
                //如果是参数单词
                if (qWord.equals(lWord)) {
                    i++;
                    j++;
                    parameterNumber = 0;
                    break;
                    //不相等, 且 非 参数情况下, 表示校验不通过
                } else if (parameterNumber == 0) {
                    return false;
                } else {
                    //如果有参数, 且不等, 正常
                    j++;
                }
            }
        }
        return true;
    }

    /**
     * 根据答案来填充模板
     * parameterMap 和 fillMap 的key从数量到值要完全一致
     * 只要有一条无法填充, 即返回错误信息
     *
     * @param parameterMap 可以通过 extractQuestionParameters 获取
     * @param fillMap 解答之后的参数列表
     * @return
     */
    protected String fillAnswer(Map<String, String> parameterMap, Map<String, String> fillMap) {
        String answer = qa.getAnswer();
        for (String key : fillMap.keySet()) {
            if (fillMap.get(key) == null) {
                return qa.getDefaultAnswer();
            }
            answer = answer.replace("#" + key + "#", parameterMap.get(key));
            answer = answer.replace("${" + key + "}", fillMap.get(key));
        }
        return answer;
    }
}
