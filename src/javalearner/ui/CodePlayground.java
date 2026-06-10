package javalearner.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * 自由代码练习区 —— 小白可以在这里写代码并运行
 */
public class CodePlayground extends JPanel {

    private JTextArea codeEditor;
    private JTextArea outputArea;
    private JButton runButton;
    private JButton clearButton;
    private JLabel statusLabel;
    private JComboBox<String> exampleSelector;
    private JLabel lineCountLabel;

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/javalearner/";
    private static final String[] TEMPLATES = {
        "空白模板",
        "Hello World",
        "变量与计算",
        "if/else 判断",
        "for 循环",
        "数组操作",
        "方法调用",
        "类和对象"
    };

    public CodePlayground() {
        setBackground(Theme.BG_CONTENT);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // 顶部标题栏
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // 中间：代码编辑器 + 输出面板（上下分割）
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(6);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);

        // 代码编辑区域
        JPanel editorPanel = createEditorPanel();
        splitPane.setTopComponent(editorPanel);

        // 输出区域
        JPanel outputPanel = createOutputPanel();
        splitPane.setBottomComponent(outputPanel);

        add(splitPane, BorderLayout.CENTER);

        // 设置初始模板
        loadTemplate(1); // Hello World
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(new EmptyBorder(14, 24, 14, 24));

        // 左侧
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        leftPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("💻 自由练习区");
        titleLabel.setFont(Theme.FONT_HEADING);
        titleLabel.setForeground(Theme.TEXT_WHITE);
        leftPanel.add(titleLabel);

        JLabel hintLabel = new JLabel("在这里自由编写Java代码，点击运行查看结果");
        hintLabel.setFont(Theme.FONT_SMALL);
        hintLabel.setForeground(new Color(170, 180, 200));
        leftPanel.add(hintLabel);

        panel.add(leftPanel, BorderLayout.WEST);

        // 右侧：模板选择器
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JLabel templateLabel = new JLabel("📋 快速模板：");
        templateLabel.setFont(Theme.FONT_SMALL);
        templateLabel.setForeground(new Color(200, 200, 220));
        rightPanel.add(templateLabel);

        exampleSelector = new JComboBox<>(TEMPLATES);
        exampleSelector.setFont(Theme.FONT_SMALL);
        exampleSelector.setPreferredSize(new Dimension(120, 28));
        exampleSelector.addActionListener(e -> {
            int idx = exampleSelector.getSelectedIndex();
            if (idx > 0) loadTemplate(idx);
        });
        rightPanel.add(exampleSelector);

        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createEditorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_CODE);

        // 编辑器标题栏
        JPanel editorBar = new JPanel(new BorderLayout());
        editorBar.setBackground(new Color(50, 55, 65));
        editorBar.setBorder(new EmptyBorder(6, 14, 6, 14));

        JLabel editorLabel = new JLabel("📝 代码编辑器 (Java)");
        editorLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        editorLabel.setForeground(new Color(200, 210, 225));
        editorBar.add(editorLabel, BorderLayout.WEST);

        lineCountLabel = new JLabel("行: 0");
        lineCountLabel.setFont(new Font("NSimSun", Font.PLAIN, 11));
        lineCountLabel.setForeground(new Color(140, 150, 170));
        editorBar.add(lineCountLabel, BorderLayout.EAST);

        panel.add(editorBar, BorderLayout.NORTH);

        // 代码编辑区
        codeEditor = new JTextArea();
        codeEditor.setFont(Theme.FONT_CODE);
        codeEditor.setForeground(new Color(220, 230, 240));
        codeEditor.setBackground(Theme.BG_CODE);
        codeEditor.setCaretColor(Color.WHITE);
        codeEditor.setTabSize(4);
        codeEditor.setBorder(new EmptyBorder(12, 16, 12, 16));
        codeEditor.setLineWrap(false);

        // 行号更新
        codeEditor.addCaretListener(e -> {
            try {
                int line = codeEditor.getLineOfOffset(codeEditor.getCaretPosition()) + 1;
                int total = codeEditor.getLineCount();
                lineCountLabel.setText("行: " + line + " / " + total);
            } catch (Exception ex) {
                // 忽略
            }
        });

        // Ctrl+Enter 运行
        codeEditor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), "runCode");
        codeEditor.getActionMap().put("runCode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCode();
            }
        });

        JScrollPane editorScroll = new JScrollPane(codeEditor);
        editorScroll.setBorder(null);
        editorScroll.getViewport().setBackground(Theme.BG_CODE);

        panel.add(editorScroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 35, 45));

        // 输出标题栏
        JPanel outputBar = new JPanel(new BorderLayout());
        outputBar.setBackground(new Color(40, 45, 55));
        outputBar.setBorder(new EmptyBorder(6, 14, 6, 14));

        JLabel outputLabel = new JLabel("📤 运行输出");
        outputLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        outputLabel.setForeground(new Color(200, 210, 225));
        outputBar.add(outputLabel, BorderLayout.WEST);

        // 按钮区
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnPanel.setOpaque(false);

        clearButton = createSmallButton("清空输出");
        clearButton.addActionListener(e -> outputArea.setText(""));
        btnPanel.add(clearButton);

        runButton = createSmallButton("▶ 运行代码 (Ctrl+Enter)");
        runButton.setBackground(Theme.SUCCESS);
        runButton.setForeground(Color.WHITE);
        runButton.addActionListener(e -> runCode());
        btnPanel.add(runButton);

        outputBar.add(btnPanel, BorderLayout.EAST);
        panel.add(outputBar, BorderLayout.NORTH);

        // 输出区域
        outputArea = new JTextArea();
        outputArea.setFont(new Font("NSimSun", Font.PLAIN, 13));
        outputArea.setForeground(new Color(192, 224, 144));
        outputArea.setBackground(new Color(30, 35, 45));
        outputArea.setEditable(false);
        outputArea.setBorder(new EmptyBorder(10, 14, 10, 14));
        outputArea.setText("// 欢迎来到自由练习区！\n" +
            "// 在这里编写Java代码，然后点击「运行代码」查看结果。\n" +
            "// 提示：\n" +
            "//   - 类名会自动设为 Main\n" +
            "//   - 需要包含 main 方法才能运行\n" +
            "//   - 快捷键 Ctrl+Enter 可以快速运行\n" +
            "//   - 支持打印中文哦！\n\n");

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(null);
        outputScroll.getViewport().setBackground(new Color(30, 35, 45));

        panel.add(outputScroll, BorderLayout.CENTER);

        // 底部状态栏
        statusLabel = new JLabel("  ✅ 准备就绪，开始写代码吧！");
        statusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(130, 140, 160));
        statusLabel.setBorder(new EmptyBorder(4, 12, 4, 12));
        statusLabel.setBackground(new Color(25, 30, 38));
        statusLabel.setOpaque(true);
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createSmallButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        btn.setForeground(new Color(200, 210, 225));
        btn.setBackground(new Color(55, 60, 75));
        btn.setBorder(new EmptyBorder(4, 10, 4, 10));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(70, 75, 90)); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(55, 60, 75)); }
        });

        return btn;
    }

    /**
     * 执行代码
     */
    private void runCode() {
        final String rawCode = codeEditor.getText().trim();
        if (rawCode.isEmpty()) {
            outputArea.append("请先输入代码！\n");
            return;
        }

        // 预处理代码（在UI线程完成，确保类名为 Main）
        String processed = rawCode;
        if (!processed.contains("class Main")) {
            processed = processed.replaceFirst("class\\s+\\w+", "class Main");
        }
        if (!processed.contains("public class Main") && !processed.contains("class Main")) {
            processed = "public class Main {\n    public static void main(String[] args) {\n" +
                       processed + "\n    }\n}";
        }
        final String code = processed;

        runButton.setEnabled(false);
        runButton.setText("运行中...");
        statusLabel.setText("  正在编译运行...");
        outputArea.setText(""); // 清空之前的输出

        // 在后台线程运行
        new Thread(() -> {
            try {
                // 创建临时目录
                Path tempDir = Paths.get(TEMP_DIR);
                Files.createDirectories(tempDir);

                // 写入源文件
                Path sourceFile = tempDir.resolve("Main.java");
                Files.writeString(sourceFile, code);

                // 编译
                ProcessBuilder compilePb = new ProcessBuilder(
                    "javac", "-encoding", "UTF-8", sourceFile.toString());
                compilePb.directory(tempDir.toFile());
                compilePb.redirectErrorStream(true);

                Process compileProcess = compilePb.start();
                String compileOutput = new String(compileProcess.getInputStream().readAllBytes(), "UTF-8");
                int compileResult = compileProcess.waitFor();

                if (compileResult != 0) {
                    SwingUtilities.invokeLater(() -> {
                        outputArea.append("❌ 编译失败！\n\n");
                        outputArea.append("--- 编译错误 ---\n");
                        outputArea.append(compileOutput.isEmpty() ? "未知编译错误" : compileOutput);
                        outputArea.append("\n--- 提示 ---\n");
                        outputArea.append("请检查：\n");
                        outputArea.append("  • 拼写是否正确（区分大小写）\n");
                        outputArea.append("  • 每行结尾的分号 (;) 是否写了\n");
                        outputArea.append("  • 大括号 {} 是否配对\n");
                        outputArea.append("  • 引号 \"\" 是否配对\n");
                        runButton.setEnabled(true);
                        runButton.setText("▶ 运行代码 (Ctrl+Enter)");
                        statusLabel.setText("  ❌ 编译失败，请修改代码后重试");
                    });
                    return;
                }

                // 运行
                ProcessBuilder runPb = new ProcessBuilder("java", "-Dfile.encoding=UTF-8", "Main");
                runPb.directory(tempDir.toFile());
                runPb.redirectErrorStream(true);

                Process runProcess = runPb.start();

                // 超时控制（10秒）
                ExecutorService executor = Executors.newSingleThreadExecutor();
                java.util.concurrent.Future<String> future = executor.submit(() -> {
                    try {
                        return new String(runProcess.getInputStream().readAllBytes(), "UTF-8");
                    } catch (IOException e) {
                        return "读取输出失败: " + e.getMessage();
                    }
                });

                String output;
                try {
                    output = future.get(10, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    runProcess.destroyForcibly();
                    output = "⏰ 运行超时（超过10秒）！请检查是否有死循环。";
                }

                int exitCode = runProcess.waitFor();
                executor.shutdownNow();

                final String finalOutput = output;
                final int finalExitCode = exitCode;

                SwingUtilities.invokeLater(() -> {
                    if (finalExitCode == 0) {
                        outputArea.append("✅ 运行成功！\n\n");
                        outputArea.append("--- 程序输出 ---\n");
                        outputArea.append(finalOutput.isEmpty() ? "(无输出)" : finalOutput);
                        statusLabel.setText("  ✅ 运行成功！");
                    } else {
                        outputArea.append("❌ 运行时出错！\n\n");
                        outputArea.append(finalOutput.isEmpty() ? "未知运行时错误" : finalOutput);
                        statusLabel.setText("  ❌ 运行时出错");
                    }
                    runButton.setEnabled(true);
                    runButton.setText("▶ 运行代码 (Ctrl+Enter)");
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("❌ 系统错误：" + e.getMessage() + "\n");
                    outputArea.append("\n💡 可能的原因：\n");
                    outputArea.append("  • 系统未安装JDK\n");
                    outputArea.append("  • javac/java 命令不在PATH中\n");
                    outputArea.append("  • 请确认可以在命令行中运行 java -version\n");
                    runButton.setEnabled(true);
                    runButton.setText("▶ 运行代码 (Ctrl+Enter)");
                    statusLabel.setText("  ❌ 运行失败（可能未安装JDK）");
                });
            }
        }).start();
    }

    /**
     * 加载代码模板
     */
    private void loadTemplate(int index) {
        String template;
        switch (index) {
            case 1: // Hello World
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello, Java!\");\n" +
                    "        System.out.println(\"你好，世界！\");\n" +
                    "    }\n" +
                    "}";
                break;
            case 2: // 变量与计算
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        int a = 100;\n" +
                    "        int b = 200;\n" +
                    "        int sum = a + b;\n" +
                    "        \n" +
                    "        System.out.println(\"a = \" + a);\n" +
                    "        System.out.println(\"b = \" + b);\n" +
                    "        System.out.println(\"a + b = \" + sum);\n" +
                    "        System.out.println(\"平均值 = \" + (sum / 2.0));\n" +
                    "    }\n" +
                    "}";
                break;
            case 3: // if/else
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        int score = 85;\n" +
                    "        \n" +
                    "        if (score >= 90) {\n" +
                    "            System.out.println(\"优秀！继续保持！\");\n" +
                    "        } else if (score >= 80) {\n" +
                    "            System.out.println(\"良好！再加把劲！\");\n" +
                    "        } else if (score >= 60) {\n" +
                    "            System.out.println(\"及格了，继续努力！\");\n" +
                    "        } else {\n" +
                    "            System.out.println(\"不及格，要加油哦！\");\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
                break;
            case 4: // for循环
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        // 打印九九乘法表\n" +
                    "        for (int i = 1; i <= 9; i++) {\n" +
                    "            for (int j = 1; j <= i; j++) {\n" +
                    "                System.out.print(j + \"×\" + i + \"=\" + (i*j) + \"\\t\");\n" +
                    "            }\n" +
                    "            System.out.println();\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
                break;
            case 5: // 数组
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        int[] numbers = {5, 2, 8, 1, 9, 3};\n" +
                    "        \n" +
                    "        // 找最大值\n" +
                    "        int max = numbers[0];\n" +
                    "        for (int i = 1; i < numbers.length; i++) {\n" +
                    "            if (numbers[i] > max) {\n" +
                    "                max = numbers[i];\n" +
                    "            }\n" +
                    "        }\n" +
                    "        System.out.println(\"最大值是：\" + max);\n" +
                    "        \n" +
                    "        // 计算总和\n" +
                    "        int sum = 0;\n" +
                    "        for (int n : numbers) {\n" +
                    "            sum += n;\n" +
                    "        }\n" +
                    "        System.out.println(\"总和是：\" + sum);\n" +
                    "    }\n" +
                    "}";
                break;
            case 6: // 方法
                template = "public class Main {\n" +
                    "    // 判断是否为偶数\n" +
                    "    public static boolean isEven(int n) {\n" +
                    "        return n % 2 == 0;\n" +
                    "    }\n" +
                    "    \n" +
                    "    // 计算阶乘\n" +
                    "    public static long factorial(int n) {\n" +
                    "        long result = 1;\n" +
                    "        for (int i = 1; i <= n; i++) {\n" +
                    "            result *= i;\n" +
                    "        }\n" +
                    "        return result;\n" +
                    "    }\n" +
                    "    \n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"5是偶数吗？\" + isEven(5));\n" +
                    "        System.out.println(\"8是偶数吗？\" + isEven(8));\n" +
                    "        System.out.println(\"5的阶乘 = \" + factorial(5));\n" +
                    "    }\n" +
                    "}";
                break;
            case 7: // 类和对象
                template = "class Dog {\n" +
                    "    String name;\n" +
                    "    int age;\n" +
                    "    \n" +
                    "    public Dog(String name, int age) {\n" +
                    "        this.name = name;\n" +
                    "        this.age = age;\n" +
                    "    }\n" +
                    "    \n" +
                    "    public void bark() {\n" +
                    "        System.out.println(name + \"（\" + age + \"岁）：汪汪汪！🐶\");\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        Dog dog1 = new Dog(\"旺财\", 3);\n" +
                    "        Dog dog2 = new Dog(\"大黄\", 1);\n" +
                    "        \n" +
                    "        dog1.bark();\n" +
                    "        dog2.bark();\n" +
                    "    }\n" +
                    "}";
                break;
            default:
                template = "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        // 在这里写你的代码...\n" +
                    "        \n" +
                    "    }\n" +
                    "}";
                break;
        }
        codeEditor.setText(template);
        codeEditor.setCaretPosition(0);
        exampleSelector.setSelectedIndex(0); // 恢复选择器
    }
}
