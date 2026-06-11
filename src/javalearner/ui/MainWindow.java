package javalearner.ui;

import javalearner.data.CourseData;
import javalearner.data.ExerciseData;
import javalearner.model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

/**
 * 主窗口 - 学习软件的主界面
 */
public class MainWindow extends JFrame {

    private List<Lesson> lessons;
    private Map<String, List<QuizQuestion>> quizzes;
    private Map<String, CodingExercise> exercises;
    private Progress progress;
    private int totalLessons;

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private JLabel progressLabel;
    private JProgressBar progressBar;

    private LessonView currentLessonView;
    private QuizView currentQuizView;
    private CodingExerciseView currentExerciseView;
    private CodePlayground codePlayground;

    private String currentLessonId = null;
    private String pendingExerciseChapter = null; // 章节全部完成后待做的练习

    public MainWindow() {
        lessons = CourseData.createLessons();
        quizzes = CourseData.createQuizzes();
        exercises = ExerciseData.createExercises();
        progress = new Progress();
        totalLessons = lessons.size();

        // 恢复进度
        for (Lesson l : lessons) {
            if (progress.isLessonCompleted(l.getId())) {
                l.setCompleted(true);
            }
        }

        initUI();
        setTitle("☕ Java 小白学习乐园");
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 默认显示第一课
        showLesson("L01");
    }

    private void initUI() {
        // 整体布局
        setLayout(new BorderLayout());

        // ===== 顶部标题栏 =====
        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        // ===== 左侧课程导航 =====
        sidebarPanel = createSidebar();
        JScrollPane sidebarScroll = new JScrollPane(sidebarPanel);
        sidebarScroll.setBorder(null);
        sidebarScroll.setPreferredSize(new Dimension(260, 0));
        sidebarScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(sidebarScroll, BorderLayout.WEST);

        // ===== 右侧内容区域 =====
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Theme.BG_CONTENT);

        // 预创建各个视图
        codePlayground = new CodePlayground();
        contentPanel.add(createWelcomePanel(), "WELCOME");
        contentPanel.add(codePlayground, "PLAYGROUND");

        add(contentPanel, BorderLayout.CENTER);

        // ===== 底部状态栏 =====
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * 顶部标题栏
     */
    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.BG_DARK);
        bar.setBorder(new EmptyBorder(12, 20, 12, 20));

        // 标题
        JLabel titleLabel = new JLabel("☕ Java 小白学习乐园");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_WHITE);
        bar.add(titleLabel, BorderLayout.WEST);

        // 右侧：进度信息
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        progressLabel = new JLabel();
        progressLabel.setFont(Theme.FONT_SMALL);
        progressLabel.setForeground(new Color(215, 200, 180));
        rightPanel.add(progressLabel);

        // 进度条
        progressBar = new JProgressBar(0, totalLessons);
        progressBar.setPreferredSize(new Dimension(150, 16));
        progressBar.setStringPainted(true);
        progressBar.setForeground(Theme.SUCCESS);
        progressBar.setBackground(new Color(70, 55, 45));
        progressBar.setFont(Theme.FONT_SMALL);
        rightPanel.add(progressBar);

        bar.add(rightPanel, BorderLayout.EAST);
        updateProgressDisplay();

        return bar;
    }

    /**
     * 左侧课程导航栏
     */
    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.BG_SIDEBAR);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));

        // Logo区域
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Theme.BG_SIDEBAR);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(15, 15, 10, 15));
        logoPanel.setMaximumSize(new Dimension(260, 60));

        JLabel logoLabel = new JLabel("📚 课程目录");
        logoLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        logoLabel.setForeground(Theme.TEXT_WHITE);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.add(logoLabel);
        panel.add(logoPanel);

        // 分隔线
        panel.add(createSidebarSeparator());

        // 课程列表（按分类分组，每章末尾插入实操题按钮）
        String currentCategory = "";
        String prevCategory = "";
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);

            // 分类标题 + 上一章的实操题按钮
            if (!lesson.getCategory().equals(currentCategory)) {
                // 在新分类开始前，插入上一章的实操题
                if (!prevCategory.isEmpty()) {
                    addExerciseButtonToSidebar(panel, prevCategory);
                }
                prevCategory = currentCategory;
                currentCategory = lesson.getCategory();

                JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                catPanel.setBackground(Theme.BG_SIDEBAR);
                catPanel.setBorder(new EmptyBorder(12, 18, 4, 0));
                catPanel.setMaximumSize(new Dimension(260, 30));
                catPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel catLabel = new JLabel("📁 " + currentCategory);
                catLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
                catLabel.setForeground(new Color(200, 175, 145));
                catPanel.add(catLabel);
                panel.add(catPanel);
            }

            // 课程按钮
            JButton lessonBtn = createLessonButton(lesson);
            panel.add(lessonBtn);
        }
        // 最后一章的实操题按钮
        if (!currentCategory.isEmpty()) {
            addExerciseButtonToSidebar(panel, currentCategory);
        }

        // 底部：代码练习按钮
        panel.add(Box.createVerticalStrut(10));
        panel.add(createSidebarSeparator());

        JButton playgroundBtn = new JButton("💻 自由练习区");
        styleSidebarSpecialButton(playgroundBtn);
        playgroundBtn.addActionListener(e -> showPlayground());
        playgroundBtn.setMaximumSize(new Dimension(260, 42));
        playgroundBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(playgroundBtn);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton createLessonButton(Lesson lesson) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 0));
        btn.setBorder(new EmptyBorder(8, 16, 8, 12));
        btn.setMaximumSize(new Dimension(260, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 左侧：序号和标题
        JPanel leftPanel = new JPanel(new BorderLayout(8, 0));
        leftPanel.setOpaque(false);

        JLabel indexLabel = new JLabel(lesson.getId().substring(1));
        indexLabel.setFont(new Font("NSimSun", Font.BOLD, 11));
        indexLabel.setForeground(Theme.ACCENT);
        indexLabel.setPreferredSize(new Dimension(24, 20));

        JLabel titleLabel = new JLabel(lesson.getTitle());
        titleLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));

        leftPanel.add(indexLabel, BorderLayout.WEST);
        leftPanel.add(titleLabel, BorderLayout.CENTER);

        // 右侧：难度和完成标记
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false);

        // 难度星星
        JLabel diffLabel = new JLabel(lesson.getDifficultyStars());
        diffLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));

        // 完成标记
        JLabel checkLabel = new JLabel(lesson.isCompleted() ? "✅" : "  ");
        checkLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        checkLabel.setName("check-" + lesson.getId());

        rightPanel.add(diffLabel);
        rightPanel.add(checkLabel);

        btn.add(leftPanel, BorderLayout.WEST);
        btn.add(rightPanel, BorderLayout.EAST);

        // 样式
        updateLessonButtonStyle(btn, lesson, false);

        // 事件
        btn.addActionListener(e -> showLesson(lesson.getId()));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!lesson.getId().equals(currentLessonId)) {
                    btn.setBackground(new Color(75, 58, 48));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!lesson.getId().equals(currentLessonId)) {
                    btn.setBackground(Theme.BG_SIDEBAR);
                }
            }
        });

        btn.setName("btn-" + lesson.getId());
        return btn;
    }

    private void updateLessonButtonStyle(JButton btn, Lesson lesson, boolean selected) {
        if (selected) {
            btn.setBackground(new Color(95, 72, 55));
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(8, 13, 8, 12),
                BorderFactory.createMatteBorder(0, 3, 0, 0, Theme.ACCENT)
            ));
        } else {
            btn.setBackground(Theme.BG_SIDEBAR);
            btn.setOpaque(true);
            btn.setBorder(new EmptyBorder(8, 16, 8, 12));
        }
    }

    private void styleSidebarSpecialButton(JButton btn) {
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        btn.setForeground(new Color(210, 190, 160));
        btn.setBackground(new Color(70, 55, 45));
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(85, 68, 55));
                btn.setForeground(Theme.TEXT_WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(70, 55, 45));
                btn.setForeground(new Color(210, 190, 160));
            }
        });
    }

    /**
     * 在侧边栏添加章节实操题按钮
     */
    private void addExerciseButtonToSidebar(JPanel panel, String category) {
        String chapterKey = getChapterKey(category);
        if (chapterKey == null || !exercises.containsKey(chapterKey)) return;

        CodingExercise ex = exercises.get(chapterKey);
        boolean completed = progress.isExerciseCompleted(chapterKey);

        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 0));
        btn.setBorder(new EmptyBorder(6, 22, 6, 12));
        btn.setMaximumSize(new Dimension(260, 36));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(new Color(65, 55, 42));
        btn.setOpaque(true);
        btn.setName("exercise-" + chapterKey);

        JLabel leftLabel = new JLabel(completed ? "✅ " : "🏆 " + "章节练习");
        leftLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        leftLabel.setForeground(completed ? Theme.SUCCESS : new Color(190, 170, 130));

        JLabel rightLabel = new JLabel(completed ? "已完成" : "→ 挑战");
        rightLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        rightLabel.setForeground(completed ? Theme.SUCCESS : new Color(232, 180, 79));

        btn.add(leftLabel, BorderLayout.WEST);
        btn.add(rightLabel, BorderLayout.EAST);

        btn.addActionListener(e -> showExercise(chapterKey));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(78, 66, 52));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(65, 55, 42));
            }
        });

        panel.add(btn);
    }

    /**
     * 根据分类名称获取章节key
     */
    private String getChapterKey(String category) {
        if (category.contains("第一章")) return "CH01";
        if (category.contains("第二章")) return "CH02";
        if (category.contains("第三章")) return "CH03";
        if (category.contains("第四章")) return "CH04";
        return null;
    }

    /**
     * 检查某一章的所有课程是否全部完成
     */
    private boolean isChapterFullyCompleted(String chapterKey) {
        List<String> lessonIds = ExerciseData.getChapterLessonIds(chapterKey);
        for (String id : lessonIds) {
            if (!progress.isLessonCompleted(id)) return false;
        }
        return true;
    }

    private JPanel createSidebarSeparator() {
        JPanel sep = new JPanel();
        sep.setBackground(new Color(90, 72, 60));
        sep.setMaximumSize(new Dimension(240, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setBorder(new EmptyBorder(0, 10, 0, 10));
        return sep;
    }

    /**
     * 内容区域 - 欢迎页
     */
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Theme.BG_CONTENT);
        panel.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(50, 50, 50, 50));
        card.setMaximumSize(new Dimension(550, 420));

        // 大树emoji作为欢迎图标
        JLabel iconLabel = new JLabel("☕");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("欢迎来到 Java 小白学习乐园！");
        welcomeLabel.setFont(Theme.FONT_TITLE);
        welcomeLabel.setForeground(Theme.TEXT_PRIMARY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("<html><div style='text-align:center;'>从零开始学习Java编程<br>从入门到精通，让我们开始吧！ 🚀</div></html>");
        subLabel.setFont(Theme.FONT_BODY);
        subLabel.setForeground(Theme.TEXT_SECONDARY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tipLabel = new JLabel("<html><div style='text-align:center;color:#888;'>从左测选择一节课开始学习<br>点击[自由练习区]可以自己动手写代码</div></html>");
        tipLabel.setFont(Theme.FONT_SMALL);
        tipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(welcomeLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(subLabel);
        card.add(Box.createVerticalStrut(30));
        card.add(tipLabel);

        // 圆角边框
        card.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(Theme.RADIUS_LARGE, new Color(210, 195, 175)),
            new EmptyBorder(50, 50, 50, 50)
        ));

        panel.add(card);
        return panel;
    }

    /**
     * 底部状态栏
     */
    private JPanel createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.BG_DARK);
        bar.setBorder(new EmptyBorder(6, 20, 6, 20));

        JLabel statusLabel = new JLabel("💡 学习小技巧：动手敲代码比光看效果好10倍！");
        statusLabel.setFont(Theme.FONT_SMALL);
        statusLabel.setForeground(new Color(200, 185, 165));
        bar.add(statusLabel, BorderLayout.WEST);

        return bar;
    }

    /**
     * 显示指定课程
     */
    private void showLesson(String lessonId) {
        Lesson lesson = findLesson(lessonId);
        if (lesson == null) return;

        // 更新侧边栏选中状态
        if (currentLessonId != null) {
            updateSidebarButtonStyle(currentLessonId, false);
        }
        currentLessonId = lessonId;
        updateSidebarButtonStyle(lessonId, true);

        // 移除旧视图
        if (currentLessonView != null) {
            contentPanel.remove(currentLessonView);
        }
        if (currentQuizView != null) {
            contentPanel.remove(currentQuizView);
        }

        // 创建课程视图
        List<QuizQuestion> lessonQuiz = quizzes.get(lessonId);
        currentLessonView = new LessonView(lesson, lessonQuiz, new LessonView.LessonActionListener() {
            @Override
            public void onStartQuiz(Lesson lesson) {
                showQuiz(lesson);
            }
            @Override
            public void onComplete(Lesson lesson) {
                lesson.setCompleted(true);
                progress.markLessonCompleted(lesson.getId());
                updateSidebarButtonStyle(lesson.getId(), true);
                updateProgressDisplay();
                JOptionPane.showMessageDialog(MainWindow.this,
                    "🎉 恭喜你完成了「" + lesson.getTitle() + "」！\n继续加油，你离Java高手越来越近了！",
                    "课程完成", JOptionPane.INFORMATION_MESSAGE);

                // 检查该章节是否全部完成，是则提示做实操题
                checkAndOfferExercise(lesson);
            }
        });
        contentPanel.add(currentLessonView, lessonId);

        contentLayout.show(contentPanel, lessonId);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * 显示测验
     */
    private void showQuiz(Lesson lesson) {
        List<QuizQuestion> questions = quizzes.get(lesson.getId());
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "本课程暂无测验题目，请继续学习下一课！");
            return;
        }

        if (currentQuizView != null) {
            contentPanel.remove(currentQuizView);
        }

        currentQuizView = new QuizView(lesson, questions, result -> {
            progress.recordQuizScore(lesson.getId(), result.getScorePercent());

            if (result.isAllCorrect()) {
                lesson.setCompleted(true);
                progress.markLessonCompleted(lesson.getId());
                updateSidebarButtonStyle(lesson.getId(), true);
                updateProgressDisplay();
            }

            // 返回课程页
            showLesson(lesson.getId());
        });

        contentPanel.add(currentQuizView, "QUIZ_" + lesson.getId());
        contentLayout.show(contentPanel, "QUIZ_" + lesson.getId());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * 显示自由练习区
     */
    private void showPlayground() {
        currentLessonId = null;
        updateAllSidebarButtons();
        contentLayout.show(contentPanel, "PLAYGROUND");
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * 检查章节是否全部完成，是则提示做实操题
     */
    private void checkAndOfferExercise(Lesson justCompleted) {
        String chapterKey = getChapterKey(justCompleted.getCategory());
        if (chapterKey == null) return;
        if (progress.isExerciseCompleted(chapterKey)) return; // 做过了
        if (!isChapterFullyCompleted(chapterKey)) return;     // 还有课没学完

        int choice = JOptionPane.showOptionDialog(this,
            "🎉 太棒了！你已经完成了「" + justCompleted.getCategory() + "」的全部课程！\n\n" +
            "现在来做一个 <b>实操编程题</b> 巩固一下吧？\n" +
            "动手写代码是学编程最快的方式！",
            "章节完成 —— 来挑战实操题吧！",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"✅ 开始挑战", "⏭ 稍后再说"},
            "✅ 开始挑战");

        if (choice == JOptionPane.YES_OPTION) {
            showExercise(chapterKey);
        }
    }

    /**
     * 显示章节实操题
     */
    private void showExercise(String chapterKey) {
        CodingExercise exercise = exercises.get(chapterKey);
        if (exercise == null) return;

        // 移除旧视图
        if (currentLessonView != null) {
            contentPanel.remove(currentLessonView);
            currentLessonView = null;
        }
        if (currentQuizView != null) {
            contentPanel.remove(currentQuizView);
            currentQuizView = null;
        }
        if (currentExerciseView != null) {
            contentPanel.remove(currentExerciseView);
        }

        currentLessonId = null;
        updateAllSidebarButtons();

        currentExerciseView = new CodingExerciseView(exercise,
            // 完成回调
            () -> {
                progress.markExerciseCompleted(chapterKey);
                // 刷新侧边栏（更新练习按钮状态）
                rebuildSidebar();
                JOptionPane.showMessageDialog(MainWindow.this,
                    "🌟 恭喜！你完成了「" + exercise.getTitle() + "」！\n\n" +
                    "你已掌握了「" + exercise.getChapterName() + "」的核心知识。\n" +
                    "继续挑战下一章吧！",
                    "练习完成", JOptionPane.INFORMATION_MESSAGE);
                showWelcome();
            },
            // 返回回调
            () -> {
                rebuildSidebar();
                showWelcome();
            }
        );

        contentPanel.add(currentExerciseView, "EXERCISE_" + chapterKey);
        contentLayout.show(contentPanel, "EXERCISE_" + chapterKey);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showWelcome() {
        currentLessonId = null;
        updateAllSidebarButtons();
        contentLayout.show(contentPanel, "WELCOME");
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * 重建侧边栏（刷新练习按钮状态）
     */
    private void rebuildSidebar() {
        sidebarPanel.removeAll();
        // 重建Logo区
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Theme.BG_SIDEBAR);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(15, 15, 10, 15));
        logoPanel.setMaximumSize(new Dimension(260, 60));

        JLabel logoLabel = new JLabel("📚 课程目录");
        logoLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        logoLabel.setForeground(Theme.TEXT_WHITE);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.add(logoLabel);
        sidebarPanel.add(logoPanel);
        sidebarPanel.add(createSidebarSeparator());

        // 重建课程列表
        String currentCategory = "";
        String prevCategory = "";
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            if (!lesson.getCategory().equals(currentCategory)) {
                if (!prevCategory.isEmpty()) {
                    addExerciseButtonToSidebar(sidebarPanel, prevCategory);
                }
                prevCategory = currentCategory;
                currentCategory = lesson.getCategory();

                JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                catPanel.setBackground(Theme.BG_SIDEBAR);
                catPanel.setBorder(new EmptyBorder(12, 18, 4, 0));
                catPanel.setMaximumSize(new Dimension(260, 30));
                catPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel catLabel = new JLabel("📁 " + currentCategory);
                catLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
                catLabel.setForeground(new Color(200, 175, 145));
                catPanel.add(catLabel);
                sidebarPanel.add(catPanel);
            }
            JButton lessonBtn = createLessonButton(lesson);
            sidebarPanel.add(lessonBtn);
        }
        if (!currentCategory.isEmpty()) {
            addExerciseButtonToSidebar(sidebarPanel, currentCategory);
        }

        // 底部分隔 + 自由练习
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(createSidebarSeparator());

        JButton playgroundBtn = new JButton("💻 自由练习区");
        styleSidebarSpecialButton(playgroundBtn);
        playgroundBtn.addActionListener(e -> showPlayground());
        playgroundBtn.setMaximumSize(new Dimension(260, 42));
        playgroundBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(playgroundBtn);

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.revalidate();
        sidebarPanel.repaint();
    }

    private Lesson findLesson(String id) {
        for (Lesson l : lessons) {
            if (l.getId().equals(id)) return l;
        }
        return null;
    }

    private void updateSidebarButtonStyle(String lessonId, boolean selected) {
        for (Component c : sidebarPanel.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals("btn-" + lessonId)) {
                updateLessonButtonStyle((JButton) c, findLesson(lessonId), selected);
                // 更新勾号
                for (Component inner : ((JButton) c).getComponents()) {
                    if (inner instanceof JPanel) {
                        for (Component ic : ((JPanel) inner).getComponents()) {
                            if (ic instanceof JLabel && ic.getName() != null && ic.getName().equals("check-" + lessonId)) {
                                Lesson l = findLesson(lessonId);
                                ((JLabel) ic).setText(l != null && l.isCompleted() ? "✅" : "  ");
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private void updateAllSidebarButtons() {
        for (Lesson l : lessons) {
            updateSidebarButtonStyle(l.getId(), false);
        }
    }

    private void updateProgressDisplay() {
        int completed = progress.getTotalCompleted();
        progressLabel.setText("学习进度：");
        progressBar.setValue(completed);
        progressBar.setString(completed + " / " + totalLessons);

        if (completed == totalLessons) {
            progressBar.setForeground(Theme.GOLD);
        }
    }

    /**
     * 圆角边框工具类
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
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}
