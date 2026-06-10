package javalearner.ui;

import javalearner.model.CodingExercise;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 章节实操题视图 —— 带在线编译运行的编程练习
 */
public class CodingExerciseView extends JPanel {

    private CodingExercise exercise;
    private Runnable onCompleted;
    private Runnable onBack;

    private JTextArea codeEditor;
    private JTextArea outputArea;
    private JButton runButton;
    private JButton checkButton;
    private JButton hintButton;
    private JLabel statusLabel;
    private JLabel hintLabel;
    private int hintIndex = 0;
    private boolean passed = false;

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/javalearner/";

    public CodingExerciseView(CodingExercise exercise, Runnable onCompleted, Runnable onBack) {
        this.exercise = exercise;
        this.onCompleted = onCompleted;
        this.onBack = onBack;

        setBackground(Theme.BG_CONTENT);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Theme.BG_CONTENT);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // ===== 标题卡片 =====
        JPanel titleCard = new JPanel(new BorderLayout());
        titleCard.setBackground(Color.WHITE);
        titleCard.setBorder(new CompoundBorder(
            new MainWindow.RoundedBorder(Theme.RADIUS_MEDIUM, new Color(220, 225, 235)),
            new EmptyBorder(18, 24, 18, 24)
        ));
        titleCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JPanel titleLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        titleLeft.setOpaque(false);
        JLabel iconLabel = new JLabel("🏆");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        titleLeft.add(iconLabel);

        JLabel titleLabel = new JLabel(exercise.getTitle());
        titleLabel.setFont(Theme.FONT_HEADING);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLeft.add(titleLabel);
        titleCard.add(titleLeft, BorderLayout.WEST);

        JButton backBtn = new JButton("← 返回课程");
        backBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        backBtn.setForeground(Theme.TEXT_SECONDARY);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(new EmptyBorder(4, 12, 4, 12));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> onBack.run());
        titleCard.add(backBtn, BorderLayout.EAST);

        mainPanel.add(titleCard);
        mainPanel.add(Box.createVerticalStrut(14));

        // ===== 题目描述卡片 =====
        JPanel descCard = new JPanel(new BorderLayout());
        descCard.setBackground(Color.WHITE);
        descCard.setBorder(new CompoundBorder(
            new MainWindow.RoundedBorder(Theme.RADIUS_MEDIUM, new Color(220, 225, 235)),
            new EmptyBorder(20, 24, 16, 24)
        ));

        JEditorPane descPane = new JEditorPane("text/html",
            "<html><body style='font-family:Microsoft YaHei,sans-serif;font-size:14px;color:#212121;'>" +
            exercise.getDescription() +
            "</body></html>");
        descPane.setEditable(false);
        descPane.setOpaque(false);
        descCard.add(descPane, BorderLayout.CENTER);

        // 预期输出
        if (exercise.getExpectedOutputExample() != null) {
            JPanel expectedPanel = new JPanel(new BorderLayout());
            expectedPanel.setOpaque(false);
            expectedPanel.setBorder(new EmptyBorder(12, 0, 0, 0));

            JLabel expLabel = new JLabel("📤 预期输出示例：");
            expLabel.setFont(Theme.FONT_SUBHEADING);
            expLabel.setForeground(Theme.TEXT_SECONDARY);
            expectedPanel.add(expLabel, BorderLayout.NORTH);

            JTextArea expArea = new JTextArea(exercise.getExpectedOutputExample());
            expArea.setFont(new Font("NSimSun", Font.PLAIN, 12));
            expArea.setForeground(new Color(100, 120, 140));
            expArea.setBackground(new Color(245, 247, 250));
            expArea.setEditable(false);
            expArea.setBorder(new EmptyBorder(8, 12, 8, 12));
            expectedPanel.add(expArea, BorderLayout.CENTER);

            descCard.add(expectedPanel, BorderLayout.SOUTH);
        }

        mainPanel.add(descCard);
        mainPanel.add(Box.createVerticalStrut(14));

        // ===== 提示区域 =====
        hintLabel = new JLabel();
        hintLabel.setFont(Theme.FONT_SMALL);
        hintLabel.setForeground(Theme.WARNING);
        hintLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        hintLabel.setVisible(false);
        mainPanel.add(hintLabel);
        mainPanel.add(Box.createVerticalStrut(6));

        // ===== 代码编辑区 =====
        JPanel editorPanel = createEditorPanel();
        mainPanel.add(editorPanel);
        mainPanel.add(Box.createVerticalStrut(14));

        // ===== 输出区域 =====
        JPanel outputPanel = createMiniOutputPanel();
        mainPanel.add(outputPanel);
        mainPanel.add(Box.createVerticalStrut(14));

        // ===== 操作按钮 =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        actionPanel.setOpaque(false);

        hintButton = createActionBtn("💡 提示 (" + exercise.getHints().size() + ")", new Color(255, 180, 50), new Color(255, 200, 80));
        hintButton.addActionListener(e -> showNextHint());
        actionPanel.add(hintButton);

        runButton = createActionBtn("▶ 运行代码", Theme.ACCENT, Theme.ACCENT_HOVER);
        runButton.addActionListener(e -> runCode());
        actionPanel.add(runButton);

        checkButton = createActionBtn("✅ 检查结果", Theme.SUCCESS, new Color(100, 220, 140));
        checkButton.addActionListener(e -> runAndCheck());
        checkButton.setEnabled(false);
        actionPanel.add(checkButton);

        mainPanel.add(actionPanel);

        // 状态标签
        statusLabel = new JLabel("  👆 先编写代码，然后点击「运行代码」查看效果");
        statusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        statusLabel.setForeground(Theme.TEXT_SECONDARY);
        statusLabel.setBorder(new EmptyBorder(8, 0, 0, 0));
        mainPanel.add(statusLabel);

        mainPanel.add(Box.createVerticalStrut(20));

        // 滚动
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Theme.BG_CONTENT);
        scrollPane.getViewport().setBackground(Theme.BG_CONTENT);
        add(scrollPane, BorderLayout.CENTER);

        // Ctrl+Enter
        codeEditor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), "runCode");
        codeEditor.getActionMap().put("runCode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { runCode(); }
        });
    }

    private JPanel createEditorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_CODE);
        panel.setBorder(new CompoundBorder(
            new MainWindow.RoundedBorder(Theme.RADIUS_MEDIUM, new Color(180, 185, 195)),
            new EmptyBorder(0, 0, 0, 0)
        ));

        // 标题栏
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(50, 55, 65));
        bar.setBorder(new EmptyBorder(8, 16, 8, 16));

        JLabel barLabel = new JLabel("📝 你的代码（类名保持 Main，Ctrl+Enter 运行）");
        barLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        barLabel.setForeground(new Color(200, 210, 225));
        bar.add(barLabel, BorderLayout.WEST);

        JButton resetBtn = new JButton("🔄 重置代码");
        resetBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        resetBtn.setForeground(new Color(200, 210, 225));
        resetBtn.setBackground(new Color(60, 65, 80));
        resetBtn.setBorder(new EmptyBorder(3, 10, 3, 10));
        resetBtn.setFocusPainted(false);
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetBtn.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                "确定要重置代码吗？你写的代码会丢失！",
                "确认重置", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                codeEditor.setText(exercise.getStarterCode());
                outputArea.setText("");
                statusLabel.setText("  代码已重置，重新开始吧！");
                checkButton.setEnabled(false);
                passed = false;
            }
        });
        bar.add(resetBtn, BorderLayout.EAST);
        panel.add(bar, BorderLayout.NORTH);

        // 编辑区
        codeEditor = new JTextArea(exercise.getStarterCode());
        codeEditor.setFont(Theme.FONT_CODE);
        codeEditor.setForeground(new Color(220, 230, 240));
        codeEditor.setBackground(Theme.BG_CODE);
        codeEditor.setCaretColor(Color.WHITE);
        codeEditor.setTabSize(4);
        codeEditor.setBorder(new EmptyBorder(12, 16, 12, 16));

        JScrollPane scroll = new JScrollPane(codeEditor);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Theme.BG_CODE);
        scroll.setPreferredSize(new Dimension(0, 220));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMiniOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 35, 45));
        panel.setBorder(new CompoundBorder(
            new MainWindow.RoundedBorder(Theme.RADIUS_MEDIUM, new Color(150, 155, 165)),
            new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(40, 45, 55));
        bar.setBorder(new EmptyBorder(6, 16, 6, 16));
        JLabel barLabel = new JLabel("📤 程序输出");
        barLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        barLabel.setForeground(new Color(200, 210, 225));
        bar.add(barLabel, BorderLayout.WEST);
        panel.add(bar, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("NSimSun", Font.PLAIN, 13));
        outputArea.setForeground(new Color(192, 224, 144));
        outputArea.setBackground(new Color(30, 35, 45));
        outputArea.setEditable(false);
        outputArea.setBorder(new EmptyBorder(8, 14, 8, 14));
        outputArea.setText("（程序输出将显示在这里）");

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(30, 35, 45));
        scroll.setPreferredSize(new Dimension(0, 120));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionBtn(String text, Color bg, Color hoverBg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, Theme.RADIUS_SMALL, Theme.RADIUS_SMALL);
                super.paint(g2, c);
                g2.dispose();
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(hoverBg); }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(bg);
            }
        });

        return btn;
    }

    private void showNextHint() {
        List<String> hints = exercise.getHints();
        if (hints == null || hints.isEmpty()) return;

        if (hintIndex < hints.size()) {
            hintLabel.setText("<html>" + hints.get(hintIndex) + "</html>");
            hintLabel.setVisible(true);
            hintIndex++;
            if (hintIndex < hints.size()) {
                hintButton.setText("💡 下一个提示 (" + (hints.size() - hintIndex) + ")");
            } else {
                hintButton.setText("💡 提示已全部显示");
                hintButton.setEnabled(false);
            }
        }
    }

    private void runCode() {
        checkButton.setEnabled(false);
        passed = false;
        runCodeInternal(false);
    }

    private void runAndCheck() {
        runCodeInternal(true);
    }

    private void runCodeInternal(boolean doCheck) {
        final String rawCode = codeEditor.getText().trim();
        if (rawCode.isEmpty()) {
            outputArea.setText("请先输入代码！");
            return;
        }

        // 预处理
        String processed = rawCode;
        if (!processed.contains("class Main")) {
            processed = processed.replaceFirst("class\\s+\\w+", "class Main");
        }
        if (!processed.contains("public class Main") && !processed.contains("class Main")) {
            processed = "public class Main {\n    public static void main(String[] args) {\n" +
                       processed + "\n    }\n}";
        }
        final String code = processed;
        final boolean check = doCheck;

        runButton.setEnabled(false);
        checkButton.setEnabled(false);
        runButton.setText("运行中...");
        statusLabel.setText("  正在编译运行，请稍候...");

        new Thread(() -> {
            try {
                Path tempDir = Paths.get(TEMP_DIR);
                Files.createDirectories(tempDir);
                Path sourceFile = tempDir.resolve("Main.java");
                Files.writeString(sourceFile, code);

                // 编译
                ProcessBuilder compilePb = new ProcessBuilder("javac", "-encoding", "UTF-8", sourceFile.toString());
                compilePb.directory(tempDir.toFile());
                compilePb.redirectErrorStream(true);
                Process cp = compilePb.start();
                String compileOutput = new String(cp.getInputStream().readAllBytes(), "UTF-8");
                int cr = cp.waitFor();

                if (cr != 0) {
                    final String err = compileOutput;
                    SwingUtilities.invokeLater(() -> {
                        outputArea.setText("编译失败！\n\n" + err + "\n\n提示：检查拼写、分号、大括号是否配对。");
                        runButton.setEnabled(true);
                        runButton.setText("▶ 运行代码");
                        statusLabel.setText("  ❌ 编译失败，请修改代码");
                    });
                    return;
                }

                // 运行
                ProcessBuilder runPb = new ProcessBuilder("java", "-Dfile.encoding=UTF-8", "Main");
                runPb.directory(tempDir.toFile());
                runPb.redirectErrorStream(true);
                Process rp = runPb.start();

                ExecutorService exe = Executors.newSingleThreadExecutor();
                java.util.concurrent.Future<String> future = exe.submit(() -> {
                    try {
                        return new String(rp.getInputStream().readAllBytes(), "UTF-8");
                    } catch (IOException e) {
                        return "读取输出失败: " + e.getMessage();
                    }
                });

                String output;
                try {
                    output = future.get(10, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    rp.destroyForcibly();
                    output = "运行超时（超过10秒）！请检查是否有死循环。";
                }
                rp.waitFor();
                exe.shutdownNow();

                final String finalOutput = output;

                SwingUtilities.invokeLater(() -> {
                    outputArea.setText("--- 你的程序输出 ---\n" + (finalOutput.isEmpty() ? "(无输出)" : finalOutput));
                    runButton.setEnabled(true);
                    runButton.setText("▶ 运行代码");
                    checkButton.setEnabled(true);

                    if (check) {
                        doValidate(finalOutput);
                    } else {
                        statusLabel.setText("  运行成功！点击「检查结果」验证是否正确");
                    }
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.setText("系统错误：" + e.getMessage() + "\n请确认已安装JDK，且javac/java命令可用。");
                    runButton.setEnabled(true);
                    runButton.setText("▶ 运行代码");
                    statusLabel.setText("  ❌ 运行失败");
                });
            }
        }).start();
    }

    private void doValidate(String output) {
        if (passed) return;

        if (exercise.validateOutput(output)) {
            passed = true;
            statusLabel.setText("  🎉 太棒了！所有关键内容都正确！");
            checkButton.setEnabled(false);
            runButton.setEnabled(false);

            int choice = JOptionPane.showOptionDialog(this,
                "🎉 恭喜！你通过了「" + exercise.getTitle() + "」！\n\n" +
                "你的输出包含了所有关键内容。\n" +
                "动手实践是学习编程最好的方式！\n\n" +
                "是否继续学习下一章？",
                "练习通过！",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"✅ 继续学习", "📝 再看看代码"},
                "✅ 继续学习");

            if (choice == JOptionPane.YES_OPTION) {
                onCompleted.run();
            } else {
                runButton.setEnabled(true);
                statusLabel.setText("  已通过！你可以继续修改代码练习，或点击返回。");
            }
        } else {
            List<String> missing = exercise.getMissingKeywords(output);
            StringBuilder sb = new StringBuilder();
            sb.append("❌ 还差一点！\n\n");
            sb.append("你的输出中缺少以下关键内容：\n");
            for (String m : missing) {
                sb.append("  • 缺少：").append(m).append("\n");
            }
            sb.append("\n请修改代码后再次运行并检查。");
            outputArea.append("\n\n--- 检查结果 ---\n" + sb.toString());
            statusLabel.setText("  ❌ 还缺少关键内容，看看提示再试试！");
        }
    }

    public boolean isPassed() {
        return passed;
    }
}
