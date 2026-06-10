package javalearner.data;

import javalearner.model.CodingExercise;
import java.util.*;

/**
 * 章节实操练习数据
 */
public class ExerciseData {

    /**
     * 章节列表 - 每个章节对应一组课程
     */
    public static final List<String> CHAPTER_KEYS = Arrays.asList("CH01", "CH02", "CH03", "CH04");

    /**
     * 获取章节包含的课程ID列表
     */
    public static List<String> getChapterLessonIds(String chapterKey) {
        return switch (chapterKey) {
            case "CH01" -> Arrays.asList("L01", "L02");
            case "CH02" -> Arrays.asList("L03", "L04", "L05", "L06", "L07");
            case "CH03" -> Arrays.asList("L08", "L09", "L10", "L11", "L12");
            case "CH04" -> Arrays.asList("L13", "L14");
            default -> List.of();
        };
    }

    /**
     * 创建所有章节实操题
     */
    public static Map<String, CodingExercise> createExercises() {
        Map<String, CodingExercise> map = new LinkedHashMap<>();

        // ==================== 第一章：Java入门 ====================
        CodingExercise ex1 = new CodingExercise("CH01", "第一章：Java入门", "🔰 实操题：你的第一个Java程序");
        ex1.setDescription(
            "<h3>📋 题目要求</h3>" +
            "<p>请编写一个Java程序，完成以下任务：</p>" +
            "<ol>" +
            "<li>在屏幕上打印 <b>「我学会了Java！」</b></li>" +
            "<li>计算 <b>2024 + 1</b> 的结果并打印出来（格式：<code>今年是：2025</code>）</li>" +
            "<li>再打印一句 <b>「继续加油！」</b></li>" +
            "</ol>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 知识点：</b>本题考查 <code>System.out.println()</code> 输出语句和基本运算。" +
            "</div>"
        );
        ex1.setHints(Arrays.asList(
            "💡 提示1：用 System.out.println() 来打印内容",
            "💡 提示2：字符串要放在双引号 \"\" 里面",
            "💡 提示3：计算 2024 + 1 可以直接在括号里写，像这样 println(2024 + 1)"
        ));
        ex1.setStarterCode(
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        // 1. 在这里打印「我学会了Java！」\n" +
            "        \n" +
            "        // 2. 在这里计算并打印 2024 + 1 的结果\n" +
            "        \n" +
            "        // 3. 在这里打印「继续加油！」\n" +
            "        \n" +
            "    }\n" +
            "}"
        );
        ex1.setExpectedKeywords(Arrays.asList("我学会了Java", "2025", "继续加油"));
        ex1.setExpectedOutputExample("我学会了Java！\n今年是：2025\n继续加油！");
        map.put("CH01", ex1);

        // ==================== 第二章：基础语法 ====================
        CodingExercise ex2 = new CodingExercise("CH02", "第二章：基础语法", "🧮 实操题：计算偶数和");
        ex2.setDescription(
            "<h3>📋 题目要求</h3>" +
            "<p>请编写程序完成以下任务：</p>" +
            "<ol>" +
            "<li>使用 <b>for循环</b> 计算 <b>1到100之间所有偶数</b> 的和</li>" +
            "<li>打印每一步的结果格式：<code>1到100之间所有偶数的和是：XXXX</code></li>" +
            "<li>使用 <b>if判断</b> 检查这个和是否大于2000，并打印结果：<br>" +
            "如果大于2000 → 打印 <code>这个和大于2000！</code><br>" +
            "否则 → 打印 <code>这个和不超过2000</code></li>" +
            "</ol>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 知识点：</b>本题考查 for循环、if条件判断、变量累加。判断偶数的条件：<code>num % 2 == 0</code>" +
            "</div>"
        );
        ex2.setHints(Arrays.asList(
            "💡 提示1：用 for (int i = 1; i <= 100; i++) 来遍历1到100",
            "💡 提示2：用 if (i % 2 == 0) 判断是否为偶数",
            "💡 提示3：定义一个 sum 变量来累加，初始值为0",
            "💡 提示4：循环结束后，用 if (sum > 2000) 来判断和的大小"
        ));
        ex2.setStarterCode(
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        // 1. 定义一个变量存储总和\n" +
            "        \n" +
            "        // 2. 用for循环遍历1到100\n" +
            "        \n" +
            "        // 3. 在循环内判断是否为偶数，是则累加\n" +
            "        \n" +
            "        // 4. 打印结果\n" +
            "        \n" +
            "        // 5. 用if判断和是否大于2000\n" +
            "        \n" +
            "    }\n" +
            "}"
        );
        ex2.setExpectedKeywords(Arrays.asList("偶数和", "2550", "大于2000"));
        ex2.setExpectedOutputExample("1到100之间所有偶数的和是：2550\n这个和大于2000！");
        map.put("CH02", ex2);

        // ==================== 第三章：面向对象入门 ====================
        CodingExercise ex3 = new CodingExercise("CH03", "第三章：面向对象入门", "🏗️ 实操题：创建计算器类");
        ex3.setDescription(
            "<h3>📋 题目要求</h3>" +
            "<p>请定义一个<b>计算器类（Calculator）</b>并创建对象使用它：</p>" +
            "<ol>" +
            "<li>定义 <b>Calculator 类</b>，包含三个方法：<br>" +
            "• <code>int add(int a, int b)</code> — 返回两数之和<br>" +
            "• <code>int subtract(int a, int b)</code> — 返回两数之差<br>" +
            "• <code>int multiply(int a, int b)</code> — 返回两数之积</li>" +
            "<li>在 main 方法中<b>创建 Calculator 对象</b></li>" +
            "<li>调用三个方法，分别计算 <b>10和5</b> 的加减乘结果并打印</li>" +
            "</ol>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 知识点：</b>本题考查类的定义、方法定义和调用、对象的创建（new关键字）。" +
            "</div>"
        );
        ex3.setHints(Arrays.asList(
            "💡 提示1：类定义格式：class Calculator { ... }",
            "💡 提示2：方法定义格式：int add(int a, int b) { return a + b; }",
            "💡 提示3：创建对象：Calculator calc = new Calculator();",
            "💡 提示4：调用方法：calc.add(10, 5)"
        ));
        ex3.setStarterCode(
            "// 在这里定义 Calculator 类\n" +
            "class Calculator {\n" +
            "    // 定义 add 方法\n" +
            "    \n" +
            "    // 定义 subtract 方法\n" +
            "    \n" +
            "    // 定义 multiply 方法\n" +
            "    \n" +
            "}\n" +
            "\n" +
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        // 创建 Calculator 对象\n" +
            "        \n" +
            "        // 调用三个方法并打印结果\n" +
            "        \n" +
            "    }\n" +
            "}"
        );
        ex3.setExpectedKeywords(Arrays.asList("10 + 5", "15", "10 - 5", "5", "10 × 5", "50"));
        ex3.setExpectedOutputExample("10 + 5 = 15\n10 - 5 = 5\n10 × 5 = 50");
        map.put("CH03", ex3);

        // ==================== 第四章：常用工具 ====================
        CodingExercise ex4 = new CodingExercise("CH04", "第四章：常用工具", "📋 实操题：学生名单管理");
        ex4.setDescription(
            "<h3>📋 题目要求</h3>" +
            "<p>使用 <b>ArrayList</b> 来管理一个学生名单：</p>" +
            "<ol>" +
            "<li>创建一个 <code>ArrayList&lt;String&gt;</code> 来存储学生姓名</li>" +
            "<li>添加5个学生：<b>张三、李四、王五、赵六、孙七</b></li>" +
            "<li>删除第3个学生（王五）</li>" +
            "<li>修改第1个学生（张三）的名字为 <b>张三丰</b></li>" +
            "<li>使用 <b>增强for循环</b> 遍历打印最终名单，格式：<code>第X个学生：XXX</code></li>" +
            "</ol>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 知识点：</b>本题考查 ArrayList 的增删改查操作和增强for循环遍历。" +
            "</div>"
        );
        ex4.setHints(Arrays.asList(
            "💡 提示1：记得导入 import java.util.ArrayList;",
            "💡 提示2：创建：ArrayList<String> list = new ArrayList<>();",
            "💡 提示3：添加：list.add(\"张三\");",
            "💡 提示4：删除：list.remove(2); （下标从0开始，王五是第3个）",
            "💡 提示5：修改：list.set(0, \"张三丰\");",
            "💡 提示6：遍历：for (String name : list) { ... }"
        ));
        ex4.setStarterCode(
            "import java.util.ArrayList;\n" +
            "\n" +
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        // 1. 创建ArrayList\n" +
            "        \n" +
            "        // 2. 添加5个学生\n" +
            "        \n" +
            "        // 3. 删除第3个学生（王五）\n" +
            "        \n" +
            "        // 4. 修改第1个学生为「张三丰」\n" +
            "        \n" +
            "        // 5. 遍历打印最终名单\n" +
            "        \n" +
            "    }\n" +
            "}"
        );
        ex4.setExpectedKeywords(Arrays.asList("张三丰", "李四", "赵六", "孙七"));
        ex4.setExpectedOutputExample("第1个学生：张三丰\n第2个学生：李四\n第3个学生：赵六\n第4个学生：孙七");
        map.put("CH04", ex4);

        return map;
    }
}
