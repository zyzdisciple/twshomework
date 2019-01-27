package common;

import qa.QAInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 读取文件, 提取需要信息转换为对应字符串
 *
 * @author zyzdisciple
 * @date 2019/1/26
 */
public class ReadFile {

    /**
     * 读取文件 返回文件所有行
     *
     * @param fileName 文件路径
     * @return
     */
    public static List<String> readFile(String fileName) {
        File file = new File(fileName);
        List<String> content = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String line;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return content;
    }

    /**
     * 读取问答配置模板
     */
    public static List<QAInfo> readQAProperties() {
        Properties pp = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(new File("aqconfig.properties"));
            pp.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return QAInfo.analyzeProperties(pp);
    }
}
