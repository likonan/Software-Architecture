package 实验2.任务3.src;

import java.io.IOException;

public interface EventSystemObserver {
    void toDo1();
    String toDo2(String filePath) throws IOException;
    public void toDo3(String filePath, String content) throws IOException;
}
