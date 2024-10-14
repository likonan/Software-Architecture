package 实验2.任务3.src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectOrientedStyle extends FileProcessor{
    public String processFile(String filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        Input input = new Input();
        String inputString = input.input(filePath);
        result.append("ObjectOrientedStyle处理前：").append("\n").append(inputString).append("\n\n");
        Process process = new Process();
        String content = process.process(inputString);
        result.append("ObjectOrientedStyle处理后：").append("\n").append(content).append("\n");
        Output output = new Output();
        output.output(filePath,content);
        return result.toString();
    }
}
class Input{
    public String input(String filePath) throws IOException {
        // 输入文件路径
        String inputFilePath = filePath;
        // 读取文件内容
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)), StandardCharsets.UTF_8);
        return content;
    }
}
class Output{
    public void output(String filePath,String content) throws IOException {
        // 输出文件路径（可以覆盖原文件或写入新文件）
        String outputFilePath = filePath;

        // 将修改后的内容写回到文件中
        Files.write(Paths.get(outputFilePath), content.getBytes(StandardCharsets.UTF_8));

        System.out.println("处理完成，结果已保存到 " + outputFilePath);
    }
}
class Process{
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
