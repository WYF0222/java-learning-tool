package javalearner;

import javalearner.ui.MainWindow;
import javax.swing.*;

/**
 * Java 小白学习乐园 —— 主入口
 *
 * 一个为Java初学者设计的交互式学习软件
 * 功能包括：
 *   - 📚 14节系统课程，从零基础到面向对象
 *   - 💻 代码示例展示，带预期输出
 *   - 📝 课后测验，检验学习效果
 *   - 🎮 自由练习区，支持在线编译运行Java代码
 *   - 📊 学习进度跟踪
 */
public class Main {

    public static void main(String[] args) {
        // 设置系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // 微调一些UI默认值
            UIManager.put("OptionPane.messageFont",
                new java.awt.Font("Microsoft YaHei", java.awt.Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont",
                new java.awt.Font("Microsoft YaHei", java.awt.Font.BOLD, 14));
            UIManager.put("Button.font",
                new java.awt.Font("Microsoft YaHei", java.awt.Font.PLAIN, 14));
        } catch (Exception e) {
            // 使用默认外观
        }

        // 启动主窗口
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
