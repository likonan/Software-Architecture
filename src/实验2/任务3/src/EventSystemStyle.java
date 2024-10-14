package 实验2.任务3.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventSystemStyle extends FileProcessor{

    @Override
    public String processFile(String filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        EventSystemObserver input=new EventSystemInput();
        EventSystemObserver output=new EventSystemOutput();
        EventSystemObserver process=new EventSystemProcess();
        String s = input.toDo2(filePath);
        result.append("EventSystemStyle处理前：").append("\n").append(s).append("\n\n");
        String s1 = process.toDo2(s);
        output.toDo3(filePath,s1);
        result.append("EventSystemStyle处理后：").append("\n").append(s1).append("\n");
        return result.toString();
    }
}
class EventSystemInput implements EventSystemObserver{
    @Override
    public void toDo1() {
    }

    @Override
    public void toDo3(String filePath, String content) throws IOException {
    }

    @Override
    public String toDo2(String filePath) throws IOException {
        return input(filePath);
    }

    public String input(String filePath) throws IOException {
        // 输入文件路径
        String inputFilePath = filePath;
        // 读取文件内容
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)), StandardCharsets.UTF_8);
        return content;
    }
}
class EventSystemOutput implements EventSystemObserver{
    @Override
    public void toDo3(String filePath, String content) throws IOException {
        output(filePath,content);
    }

    @Override
    public String toDo2(String filePath) throws IOException {
        return "";
    }

    @Override
    public void toDo1() {
    }

    public void output(String filePath, String content) throws IOException {
        // 输出文件路径（可以覆盖原文件或写入新文件）
        String outputFilePath = filePath;

        // 将修改后的内容写回到文件中
        Files.write(Paths.get(outputFilePath), content.getBytes(StandardCharsets.UTF_8));

        System.out.println("处理完成，结果已保存到 " + outputFilePath);
    }
}
class EventSystemProcess implements EventSystemObserver{
    @Override
    public void toDo1() {
    }

    @Override
    public String toDo2(String text) {
        return process(text);
    }

    @Override
    public void toDo3(String filePath, String content) throws IOException {
    }

    public String process(String text) {

        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {

            matcher.appendReplacement(result, "");
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
