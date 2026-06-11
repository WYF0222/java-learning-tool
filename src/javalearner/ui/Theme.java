package javalearner.ui;

import java.awt.*;

/**
 * 统一主题和样式管理
 */
public class Theme {

    // ========== 颜色方案 —— "Java咖啡乐园"暖色系 ==========
    // 灵感来源：Java咖啡豆的深烘色泽 + 温暖的学习环境
    public static final Color BG_DARK = new Color(44, 34, 27);       // 深烘咖啡 — 顶栏/底栏
    public static final Color BG_SIDEBAR = new Color(61, 48, 40);    // 暖棕 — 侧边栏
    public static final Color BG_CONTENT = new Color(251, 248, 244); // 暖羊皮纸 — 内容区
    public static final Color BG_CARD = new Color(255, 253, 250);    // 暖白卡片
    public static final Color BG_CODE = new Color(43, 36, 30);       // 深烘色 — 代码区
    public static final Color BG_INPUT = new Color(253, 250, 246);   // 暖白输入

    public static final Color TEXT_PRIMARY = new Color(44, 34, 27);  // 深棕 — 主文字
    public static final Color TEXT_SECONDARY = new Color(139, 115, 85); // 暖灰棕 — 辅助文字
    public static final Color TEXT_CODE = new Color(200, 214, 160);  // 暖鼠尾草绿 — 代码文字
    public static final Color TEXT_WHITE = new Color(255, 248, 240); // 暖白 — 深色区文字
    public static final Color TEXT_LINK = new Color(180, 110, 50);   // 琥珀棕 — 链接色

    public static final Color ACCENT = new Color(212, 121, 43);      // 琥珀咖 — 强调色
    public static final Color ACCENT_HOVER = new Color(232, 145, 58); // 浅琥珀 — hover
    public static final Color SUCCESS = new Color(107, 163, 104);    // 暖苔绿 — 成功
    public static final Color ERROR = new Color(212, 104, 90);       // 暖陶土红 — 错误
    public static final Color WARNING = new Color(232, 180, 79);     // 金盏黄 — 警告
    public static final Color GOLD = new Color(232, 168, 64);        // 暖金 — 全部完成

    // 难度颜色
    public static final Color DIFF_EASY = new Color(124, 182, 104);  // 暖绿
    public static final Color DIFF_MEDIUM = new Color(232, 180, 79); // 暖琥珀
    public static final Color DIFF_HARD = new Color(212, 104, 90);   // 暖红

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
