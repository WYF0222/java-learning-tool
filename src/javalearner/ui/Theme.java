package javalearner.ui;

import java.awt.*;

/**
 * 统一主题和样式管理
 */
public class Theme {

    // ========== 颜色方案 ==========
    public static final Color BG_DARK = new Color(30, 30, 40);
    public static final Color BG_SIDEBAR = new Color(40, 40, 55);
    public static final Color BG_CONTENT = new Color(245, 245, 250);
    public static final Color BG_CARD = Color.WHITE;
    public static final Color BG_CODE = new Color(40, 44, 52);
    public static final Color BG_INPUT = new Color(250, 250, 252);

    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    public static final Color TEXT_SECONDARY = new Color(100, 100, 115);
    public static final Color TEXT_CODE = new Color(171, 227, 129);
    public static final Color TEXT_WHITE = Color.WHITE;
    public static final Color TEXT_LINK = new Color(70, 130, 220);

    public static final Color ACCENT = new Color(88, 150, 255);
    public static final Color ACCENT_HOVER = new Color(120, 170, 255);
    public static final Color SUCCESS = new Color(80, 200, 120);
    public static final Color ERROR = new Color(255, 100, 100);
    public static final Color WARNING = new Color(255, 200, 80);
    public static final Color GOLD = new Color(255, 200, 0);

    // 难度颜色
    public static final Color DIFF_EASY = new Color(80, 200, 120);
    public static final Color DIFF_MEDIUM = new Color(255, 180, 50);
    public static final Color DIFF_HARD = new Color(255, 100, 100);

    // ========== 字体 ==========
    public static final Font FONT_TITLE = new Font("Microsoft YaHei", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Microsoft YaHei", Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font("Microsoft YaHei", Font.BOLD, 14);
    public static final Font FONT_BODY = new Font("Microsoft YaHei", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Microsoft YaHei", Font.PLAIN, 12);
    // 使用支持中文的等宽字体（Consolas不支持中文，中文代码注释会乱码）
    // Windows优先用SimSun(宋体)或NSimSun(新宋体)，都是等宽且支持中文
    public static final Font FONT_CODE = new Font("NSimSun", Font.PLAIN, 13);
    public static final Font FONT_MONO = new Font("NSimSun", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Microsoft YaHei", Font.BOLD, 14);

    // ========== 圆角边框 ==========
    public static final int RADIUS_SMALL = 6;
    public static final int RADIUS_MEDIUM = 10;
    public static final int RADIUS_LARGE = 15;

    // ========== 工具方法 ==========
    public static Color getDifficultyColor(int level) {
        switch (level) {
            case 1: return DIFF_EASY;
            case 2: return DIFF_EASY;
            case 3: return DIFF_MEDIUM;
            case 4: return DIFF_HARD;
            case 5: return DIFF_HARD;
            default: return TEXT_SECONDARY;
        }
    }
}
