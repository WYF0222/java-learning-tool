package javalearner.ui;

import javalearner.model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * 测验视图 —— 选择题形式的课后测验
 */
public class QuizView extends JPanel {

    private Lesson lesson;
    private List<QuizQuestion> questions;
    private java.util.function.Consumer<QuizResult> onFinished;

    private int currentIndex = 0;
    private int correctCount = 0;
    private int[] userAnswers;
    private boolean[] answered;

    private JLabel progressLabel;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private JLabel feedbackLabel;
    private JButton nextButton;
    private JButton prevButton;
    private JPanel questionCard;

    public static class QuizResult {
        private int correctCount;
        private int total;

        public QuizResult(int correctCount, int total) {
            this.correctCount = correctCount;
            this.total = total;
        }

        public int getScorePercent() {
            return total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;
        }

        public boolean isAllCorrect() {
            return correctCount == total;
        }

        public int getCorrectCount() { return correctCount; }
        public int getTotal() { return total; }
    }

    public QuizView(Lesson lesson, List<QuizQuestion> questions,
                    java.util.function.Consumer<QuizResult> onFinished) {
        this.lesson = lesson;
        this.questions = questions;
        this.onFinished = onFinished;
        this.userAnswers = new int[questions.size()];
        this.answered = new boolean[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;

        setBackground(Theme.BG_CONTENT);
        setLayout(new BorderLayout());
        buildUI();
        showQuestion(0);
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Theme.BG_CONTENT);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // 测验标题
        JPanel headerCard = new JPanel(new BorderLayout());
        headerCard.setBackground(Color.WHITE);
        headerCard.setBorder(new CompoundedRoundedBorder(Theme.RADIUS_MEDIUM, 20, 16));
        headerCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel headerLabel = new JLabel("📝 课后测验 —— " + lesson.getTitle());
        headerLabel.setFont(Theme.FONT_HEADING);
        headerLabel.setForeground(Theme.TEXT_PRIMARY);
        headerCard.add(headerLabel, BorderLayout.WEST);

        progressLabel = new JLabel();
        progressLabel.setFont(Theme.FONT_SMALL);
        progressLabel.setForeground(Theme.TEXT_SECONDARY);
        headerCard.add(progressLabel, BorderLayout.EAST);

        mainPanel.add(headerCard);
        mainPanel.add(Box.createVerticalStrut(16));

        // 题目卡片
        questionCard = new JPanel();
        questionCard.setLayout(new BoxLayout(questionCard, BoxLayout.Y_AXIS));
        questionCard.setBackground(Color.WHITE);
        questionCard.setBorder(new CompoundedRoundedBorder(Theme.RADIUS_MEDIUM, 24, 24));
        mainPanel.add(questionCard);
        mainPanel.add(Box.createVerticalStrut(16));

        // 导航按钮
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        navPanel.setOpaque(false);

        prevButton = createStyledButton("◀ 上一题", new Color(140, 140, 160), new Color(160, 160, 180));
        prevButton.addActionListener(e -> {
            if (currentIndex > 0) {
                saveAnswer();
                showQuestion(currentIndex - 1);
            }
        });
        navPanel.add(prevButton);

        nextButton = createStyledButton("下一题 ▶", Theme.ACCENT, Theme.ACCENT_HOVER);
        nextButton.addActionListener(e -> {
            saveAnswer();
            if (currentIndex < questions.size() - 1) {
                showQuestion(currentIndex + 1);
            } else {
                finishQuiz();
            }
        });
        navPanel.add(nextButton);

        // 提交按钮
        JButton submitBtn = createStyledButton("📊 交卷查看成绩", Theme.SUCCESS, new Color(100, 220, 140));
        submitBtn.addActionListener(e -> {
            saveAnswer();
            finishQuiz();
        });
        navPanel.add(submitBtn);

        mainPanel.add(navPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Theme.BG_CONTENT);
        scrollPane.getViewport().setBackground(Theme.BG_CONTENT);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void showQuestion(int index) {
        currentIndex = index;
        QuizQuestion q = questions.get(index);
        questionCard.removeAll();

        // 进度
        progressLabel.setText((index + 1) + " / " + questions.size() +
            "  |  ✅ " + correctCount + " 题正确");

        // 问题文本
        JLabel qLabel = new JLabel("<html><div style='font-size:15px;padding:4px 0;'>" +
            "<b>第" + (index + 1) + "题：</b>" + q.getQuestion() +
            "</div></html>");
        qLabel.setFont(Theme.FONT_BODY);
        qLabel.setForeground(Theme.TEXT_PRIMARY);
        qLabel.setBorder(new EmptyBorder(0, 0, 16, 0));
        questionCard.add(qLabel);

        // 选项
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[q.getOptions().size()];
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);

        for (int i = 0; i < q.getOptions().size(); i++) {
            final int optIndex = i;
            JRadioButton rb = new JRadioButton(q.getOptions().get(i));
            rb.setFont(Theme.FONT_BODY);
            rb.setForeground(Theme.TEXT_PRIMARY);
            rb.setBackground(Color.WHITE);
            rb.setFocusPainted(false);
            rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rb.setBorder(new EmptyBorder(8, 12, 8, 12));

            // 自定义单选按钮样式
            rb.setIcon(new RadioIcon(false));
            rb.setSelectedIcon(new RadioIcon(true));

            rb.addActionListener(e -> {
                if (rb.isSelected()) {
                    userAnswers[currentIndex] = optIndex;
                    // 高亮选中项
                    for (Component c : optionsPanel.getComponents()) {
                        if (c instanceof JRadioButton) {
                            c.setBackground(Color.WHITE);
                        }
                    }
                    rb.setBackground(new Color(235, 245, 255));
                }
            });

            optionButtons[i] = rb;
            optionsGroup.add(rb);
            optionsPanel.add(rb);
            optionsPanel.add(Box.createVerticalStrut(6));
        }

        // 恢复之前的答案
        if (userAnswers[index] >= 0 && userAnswers[index] < optionButtons.length) {
            optionButtons[userAnswers[index]].setSelected(true);
            optionButtons[userAnswers[index]].setBackground(new Color(235, 245, 255));
        }

        questionCard.add(optionsPanel);

        // 反馈区域
        feedbackLabel = new JLabel();
        feedbackLabel.setFont(Theme.FONT_SMALL);
        feedbackLabel.setBorder(new EmptyBorder(12, 0, 0, 0));
        questionCard.add(feedbackLabel);

        // 如果已经回答过，显示反馈
        if (answered[index]) {
            showFeedback(index);
        }

        // 更新按钮状态
        prevButton.setEnabled(index > 0);
        if (index == questions.size() - 1) {
            nextButton.setText("完成 📋");
        } else {
            nextButton.setText("下一题 ▶");
        }

        questionCard.revalidate();
        questionCard.repaint();
    }

    private void saveAnswer() {
        // 答案在点击选项时已经通过 ActionListener 保存
    }

    private void showFeedback(int index) {
        QuizQuestion q = questions.get(index);
        int userAnswer = userAnswers[index];
        boolean correct = q.isCorrect(userAnswer);

        if (correct) {
            feedbackLabel.setText("<html><div style='color:#28a745;'>✅ 回答正确！" +
                (q.getExplanation() != null ? " " + q.getExplanation() : "") +
                "</div></html>");
        } else {
            feedbackLabel.setText("<html><div style='color:#dc3545;'>❌ 回答错误！正确答案是：<b>" +
                q.getOptions().get(q.getCorrectIndex()) + "</b>" +
                (q.getExplanation() != null ? "<br>💡 " + q.getExplanation() : "") +
                "</div></html>");
        }
    }

    private void checkAndShowFeedback() {
        saveAnswer();
        QuizQuestion q = questions.get(currentIndex);
        boolean correct = q.isCorrect(userAnswers[currentIndex]);

        if (correct && !answered[currentIndex]) {
            correctCount++;
        }
        answered[currentIndex] = true;
        showFeedback(currentIndex);

        // 禁用选项
        for (JRadioButton rb : optionButtons) {
            rb.setEnabled(false);
        }
    }

    private void finishQuiz() {
        // 统计所有已回答的题
        int answeredCount = 0;
        correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (answered[i] || userAnswers[i] >= 0) {
                answeredCount++;
                if (questions.get(i).isCorrect(userAnswers[i])) {
                    correctCount++;
                }
            }
        }

        int unanswered = questions.size() - answeredCount;
        String msg;
        if (unanswered > 0) {
            int option = JOptionPane.showConfirmDialog(this,
                "还有 " + unanswered + " 道题未作答，确定要交卷吗？\n未作答的题目将计为错误。",
                "确认交卷", JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) return;
        }

        QuizResult result = new QuizResult(correctCount, questions.size());

        // 显示成绩
        String gradeEmoji;
        if (result.getScorePercent() >= 80) gradeEmoji = "🌟";
        else if (result.getScorePercent() >= 60) gradeEmoji = "👍";
        else gradeEmoji = "💪";

        JOptionPane.showMessageDialog(this,
            gradeEmoji + " 测验成绩\n\n" +
            "正确：" + correctCount + " / " + questions.size() + "\n" +
            "得分：" + result.getScorePercent() + " 分\n\n" +
            (result.isAllCorrect() ? "🎉 太厉害了，全对！你已经完全掌握了本节内容！" :
             result.getScorePercent() >= 80 ? "👏 很棒！建议再看看错题的解释。继续加油！" :
             "📚 还需要复习一下哦，重新看看课程内容吧！"),
            "测验结果", JOptionPane.INFORMATION_MESSAGE);

        onFinished.accept(result);
    }

    private JButton createStyledButton(String text, Color bg, Color hoverBg) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setBorder(null);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        btn.setPreferredSize(new Dimension(150, 38));
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

    // ===== 自定义Radio图标 =====
    static class RadioIcon implements Icon {
        private boolean selected;
        private static final int SIZE = 18;

        RadioIcon(boolean selected) { this.selected = selected; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (selected) {
                g2.setColor(Theme.ACCENT);
                g2.fillOval(x, y, SIZE, SIZE);
                g2.setColor(Color.WHITE);
                g2.fillOval(x + 5, y + 5, SIZE - 10, SIZE - 10);
            } else {
                g2.setColor(new Color(180, 180, 190));
                g2.drawOval(x, y, SIZE - 1, SIZE - 1);
            }

            g2.dispose();
        }

        @Override
        public int getIconWidth() { return SIZE; }
        @Override
        public int getIconHeight() { return SIZE; }
    }

    static class CompoundedRoundedBorder extends CompoundBorder {
        CompoundedRoundedBorder(int radius, int hPad, int vPad) {
            super(
                new MainWindow.RoundedBorder(radius, new Color(220, 225, 235)),
                new EmptyBorder(vPad, hPad, vPad, hPad)
            );
        }
    }
}
