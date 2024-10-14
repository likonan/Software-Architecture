package 实验2.任务3.src;
  
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UI {  
    private JFrame frame;  
    private JComboBox<String> styleComboBox;  
    private JTextArea resultArea;  
    private String inputFilePath = "E:\\IdeaProjects\\JavaSE_Code\\SA\\src\\实验2\\任务3\\resources\\sample.txt";
    // 声明JFileChooser实例
    private JFileChooser fileChooser = new JFileChooser();
    class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(Image image) {
            this.image = image;
            setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this);
            }
        }
    }

    public UI() {  
        frame = new JFrame("经典软件体系结构教学软件");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(800, 600);  
        frame.setLayout(new BorderLayout());  
  
        JPanel topPanel = new JPanel();  
        topPanel.add(new JLabel("选择体系结构风格:"));  
        String[] styles = {"主程序-子程序", "面向对象", "事件系统", "管道-过滤器"};  
        styleComboBox = new JComboBox<>(styles);  
        topPanel.add(styleComboBox);
        JButton processButton = new JButton("处理文件");
        topPanel.add(processButton);

        JButton showButton = new JButton("查看原理图");
        topPanel.add(showButton);

        resultArea = new JTextArea();  
        resultArea.setEditable(false);  
        JScrollPane scrollPane = new JScrollPane(resultArea);  
  
        frame.add(topPanel, BorderLayout.NORTH);  
        frame.add(scrollPane, BorderLayout.CENTER);  
  
        processButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {
                processFile();
            }  
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 根据所选体系结构风格获取图片路径的方法
                String imagePath = getImagePathForSelectedStyle();

                // 加载图片
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage();

                // 创建ImagePanel
                ImagePanel imagePanel = new ImagePanel(image);

                // 创建新的面板或容器来放置ImagePanel
                JFrame imageFrame = new JFrame("体系结构风格原理图");
                imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                imageFrame.add(imagePanel);
                imageFrame.pack();
                imageFrame.setLocationRelativeTo(frame); // 将新窗口置于主窗口中央
                imageFrame.setVisible(true);
            }
        });
    }

    private void processFile() {
        // 重置文件选择器
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // 设置文件过滤器（可选）
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        // 显示文件选择对话框
        int returnValue = fileChooser.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // 用户选择了文件
            File selectedFile = fileChooser.getSelectedFile();
            inputFilePath = selectedFile.getAbsolutePath();

            // 处理文件
            try {
                String selectedStyle = (String) styleComboBox.getSelectedItem();
                FileProcessor processor = getFileProcessorForStyle(selectedStyle);

                if (processor != null) {
                    String result = processor.processFile(inputFilePath);
                    resultArea.setText(result);
                }
            } catch (IOException ex) {
                // 处理异常，例如显示错误消息
                JOptionPane.showMessageDialog(frame, "Error processing file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 辅助方法，根据所选风格返回相应的FileProcessor实例
    private FileProcessor getFileProcessorForStyle(String style) {
        switch (style) {
            case "主程序-子程序":
                return new SubProgramStyle();
            case "面向对象":
                return new ObjectOrientedStyle();
            case "事件系统":
                return new EventSystemStyle();
            case "管道-过滤器":
                return new PipeFilterStyle();
            default:
                return null;
        }
    }
    private String getImagePathForSelectedStyle() {
        String selectedStyle = (String) styleComboBox.getSelectedItem();
        switch (selectedStyle) {
            case "主程序-子程序":
                return "E:\\IdeaProjects\\JavaSE_Code\\SA\\src\\实验2\\任务3\\resources\\diagrams\\subprogram.png";
            case "面向对象":
                return "E:\\IdeaProjects\\JavaSE_Code\\SA\\src\\实验2\\任务3\\resources\\diagrams\\objectoriented.png";
            case "事件系统":
                return "E:\\IdeaProjects\\JavaSE_Code\\SA\\src\\实验2\\任务3\\resources\\diagrams\\eventsystem.png";
            case "管道-过滤器":
                return "E:\\IdeaProjects\\JavaSE_Code\\SA\\src\\实验2\\任务3\\resources\\diagrams\\pipefilter.png";
            default:
                return null;
        }
    }
  
    public void display() {  
        frame.setVisible(true);  
    }  
}