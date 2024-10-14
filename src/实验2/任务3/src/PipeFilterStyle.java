package 实验2.任务3.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PipeFilterStyle extends FileProcessor{
    @Override
    public String processFile(String filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        Input input = new Input();
        String inputString = input.input(filePath);
        result.append("PipeFilterStyle处理前：").append("\n").append(inputString).append("\n\n");
        Process process = new Process();
        String content = process.process(inputString);
        result.append("PipeFilterStyle处理后：").append("\n").append(content).append("\n");
        Output output = new Output();
        output.output(filePath,content);
        return result.toString();
    }
}
