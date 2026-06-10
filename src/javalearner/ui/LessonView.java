package javalearner.ui;

import javalearner.model.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * 课程内容视图 —— 展示课程讲解和代码示例
 */
public class LessonView extends JPanel {

    private Lesson lesson;
    private List<QuizQuestion> quizQuestions;
    private LessonActionListener listener;

    public interface LessonActionListener {
        void onStartQuiz(Lesson lesson);
        void onComplete(Lesson lesson);
    }

    public LessonView(Lesson lesson, List<QuizQuestion> quizQuestions, LessonActionListener listener) {
        this.lesson = lesson;
        this.quizQuestions = quizQuestions;
        this.listener = listener;

        setBackground(Theme.BG_CONTENT);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // 主滚动面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Theme.BG_CONTENT);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // ===== 课程标题卡片 =====
        JPanel titleCard = createTitleCard();
        mainPanel.add(titleCard);
        mainPanel.add(Box.createVerticalStrut(16));

        // ===== 课程内容卡片 =====
        JPanel contentCard = createContentCard();
        mainPanel.add(contentCard);
        mainPanel.add(Box.createVerticalStrut(16));

        // ===== 代码示例区域 =====
        if (lesson.getCodeExamples() != null && !lesson.getCodeExamples().isEmpty()) {
            for (int i = 0; i < lesson.getCodeExamples().size(); i++) {
                Lesson.CodeExample example = lesson.getCodeExamples().get(i);
                JPanel codeCard = createCodeCard(example, i + 1);
                mainPanel.add(codeCard);
                mainPanel.add(Box.createVerticalStrut(12));
            }
        }

        // ===== 底部操作按钮 =====
        JPanel actionPanel = createActionPanel();
        mainPanel.add(actionPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // 放入滚动面板
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Theme.BG_CONTENT);
        scrollPane.getViewport().setBackground(Theme.BG_CONTENT);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 标题卡片
     */
    private JPanel createTitleCard() {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundedRoundedBorder(Theme.RADIUS_MEDIUM, 20, 24));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setPreferredSize(new Dimension(800, 80));

        // 左侧：课程编号 + 标题
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel idLabel = new JLabel("第" + lesson.getId().substring(1) + "课");
        idLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        idLabel.setForeground(Theme.ACCENT);

        JLabel titleLabel = new JLabel(lesson.getTitle());
        titleLabel.setFont(Theme.FONT_HEADING);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        leftPanel.add(idLabel);
        leftPanel.add(Box.createVerticalStrut(4));
        leftPanel.add(titleLabel);
        card.add(leftPanel, BorderLayout.WEST);

        // 右侧：难度 + 分类
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JLabel diffLabel = new JLabel("难度：" + lesson.getDifficultyStars());
        diffLabel.setFont(Theme.FONT_SMALL);
        diffLabel.setForeground(Theme.getDifficultyColor(lesson.getDifficulty()));
        diffLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel catLabel = new JLabel("📁 " + lesson.getCategory());
        catLabel.setFont(Theme.FONT_SMALL);
        catLabel.setForeground(Theme.TEXT_SECONDARY);
        catLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(diffLabel);
        rightPanel.add(Box.createVerticalStrut(4));
        rightPanel.add(catLabel);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    /**
     * 课程内容卡片（HTML渲染）
     */
    private JPanel createContentCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundedRoundedBorder(Theme.RADIUS_MEDIUM, 24, 24));

        // 使用JEditorPane渲染HTML
        JEditorPane editorPane = new JEditorPane("text/html",
            "<html><body style='font-family:Microsoft YaHei,sans-serif;font-size:14px;color:#212121;padding:8px;'>" +
            lesson.getContent() +
            "</body></html>");
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                // 忽略链接点击
            }
        });

        card.add(editorPane, BorderLayout.CENTER);
        return card;
    }

    /**
     * 代码示例卡片
     */
    private JPanel createCodeCard(Lesson.CodeExample example, int index) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundedRoundedBorder(Theme.RADIUS_MEDIUM, 16, 16));

        // 标题栏
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        titleBar.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("💻 代码示例 " + index + "：" + example.getTitle());
        titleLabel.setFont(Theme.FONT_SUBHEADING);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleBar.add(titleLabel, BorderLayout.WEST);
        card.add(titleBar, BorderLayout.NORTH);

        // 代码区域（使用JTextArea模拟语法高亮）
        JPanel codePanel = new JPanel(new BorderLayout());
        codePanel.setBackground(Theme.BG_CODE);
        codePanel.setBorder(new RoundedBorder(Theme.RADIUS_SMALL, Theme.BG_CODE));

        JTextArea codeArea = new JTextArea(example.getCode());
        codeArea.setFont(Theme.FONT_CODE);
        codeArea.setForeground(Theme.TEXT_CODE);
        codeArea.setBackground(Theme.BG_CODE);
        codeArea.setEditable(false);
        codeArea.setCaretColor(Theme.TEXT_CODE);
        codeArea.setBorder(new EmptyBorder(14, 16, 14, 16));
        codeArea.setTabSize(4);

        JScrollPane codeScroll = new JScrollPane(codeArea);
        codeScroll.setBorder(null);
        codeScroll.setBackground(Theme.BG_CODE);
        codeScroll.getViewport().setBackground(Theme.BG_CODE);
        codePanel.add(codeScroll, BorderLayout.CENTER);
        card.add(codePanel, BorderLayout.CENTER);

        // 预期输出（折叠显示）
        if (example.getExpectedOutput() != null && !example.getExpectedOutput().isEmpty()) {
            JPanel outputPanel = createOutputPanel(example.getExpectedOutput());
            outputPanel.setBorder(new EmptyBorder(12, 0, 0, 0));
            card.add(outputPanel, BorderLayout.SOUTH);
        }

        return card;
    }

    /**
     * 预期输出面板（可折叠）
     */
    private JPanel createOutputPanel(String output) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // 切换按钮
        JToggleButton toggleBtn = new JToggleButton("▶ 查看运行结果");
        toggleBtn.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
        toggleBtn.setForeground(Theme.SUCCESS);
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorder(null);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 输出内容面板
        JPanel outputContent = new JPanel(new BorderLayout());
        outputContent.setBackground(new Color(30, 35, 45));
        outputContent.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(Theme.RADIUS_SMALL, new Color(60, 70, 90)),
            new EmptyBorder(10, 14, 10, 14)
        ));
        outputContent.setVisible(false);

        JLabel outputLabel = new JLabel("<html><pre style='color:#c0e090;font-family:NSimSun,SimSun,monospace;font-size:12px;margin:0;'>" +
            "// 运行结果 ↓\n" +
            output.replace("\n", "<br>") +
            "</pre></html>");
        outputContent.add(outputLabel, BorderLayout.CENTER);

        toggleBtn.addActionListener(e -> {
            outputContent.setVisible(toggleBtn.isSelected());
            toggleBtn.setText(toggleBtn.isSelected() ? "▼ 隐藏运行结果" : "▶ 查看运行结果");
            panel.revalidate();
            panel.repaint();
        });

        panel.add(toggleBtn, BorderLayout.NORTH);
        panel.add(outputContent, BorderLayout.CENTER);

        return panel;
    }

    /**
     * 底部操作按钮
     */
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // 测验按钮
        boolean hasQuiz = quizQuestions != null && !quizQuestions.isEmpty();
        if (hasQuiz) {
            JButton quizBtn = createStyledButton("📝 开始测验", Theme.ACCENT, Theme.ACCENT_HOVER);
            quizBtn.addActionListener(e -> listener.onStartQuiz(lesson));
            panel.add(quizBtn);
        }

        // 标记完成按钮
        if (!lesson.isCompleted()) {
            JButton completeBtn = createStyledButton("✅ 标记完成", Theme.SUCCESS, new Color(100, 220, 140));
            completeBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "确定要标记「" + lesson.getTitle() + "」为已完成吗？\n你可以随时回来复习哦！",
                    "确认完成", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listener.onComplete(lesson);
                    // 更新UI
                    for (Component c : panel.getComponents()) {
                        if (c instanceof JButton && ((JButton) c).getText().contains("标记完成")) {
                            ((JButton) c).setText("✅ 已完成 ✓");
                            ((JButton) c).setBackground(new Color(200, 220, 200));
                            ((JButton) c).setEnabled(false);
                        }
                    }
                }
            });
            panel.add(completeBtn);
        } else {
            JLabel doneLabel = new JLabel("✅ 已完成 ✓");
            doneLabel.setFont(Theme.FONT_BUTTON);
            doneLabel.setForeground(Theme.SUCCESS);
            panel.add(doneLabel);
        }

        return panel;
    }

    private JButton createStyledButton(String text, Color bg, Color hoverBg) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setBorder(new RoundedBorder(Theme.RADIUS_SMALL, bg));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        // 自定义圆角绘制
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

        btn.setPreferredSize(new Dimension(140, 38));
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

    // ===== 边框工具类 =====

    /**
     * 圆角+内边距的组合边框
     */
    static class CompoundedRoundedBorder extends CompoundBorder {
        CompoundedRoundedBorder(int radius, int hPad, int vPad) {
            super(
                new MainWindow.RoundedBorder(radius, new Color(220, 225, 235)),
                new EmptyBorder(vPad, hPad, vPad, hPad)
            );
        }
    }

    /**
     * 简单圆角边框
     */
    static class RoundedBorder implements Border {
        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() { return false; }
    }
}
