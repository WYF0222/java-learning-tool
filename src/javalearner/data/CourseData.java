package javalearner.data;

import javalearner.model.Lesson;
import javalearner.model.QuizQuestion;
import java.util.*;

/**
 * 课程数据 - 包含所有课程内容和测验题目
 */
public class CourseData {

    public static List<Lesson> createLessons() {
        List<Lesson> lessons = new ArrayList<>();

        // ==================== 第一章：Java入门 ====================
        Lesson l1 = new Lesson("L01", "认识Java——什么是Java？", "第一章：Java入门", 1);
        l1.setContent(
            "<h2>☕ 什么是Java？</h2>" +
            "<p>Java是一种<strong>高级编程语言</strong>，由Sun Microsystems公司于1995年发布。" +
            "Java的设计理念是<strong>「一次编写，到处运行」</strong>（Write Once, Run Anywhere）。</p>" +
            "<br>" +
            "<h3>🌟 Java的特点</h3>" +
            "<ul>" +
            "<li>✅ <strong>跨平台</strong>：编译后的代码可以在Windows、Mac、Linux等任何安装了JVM的系统上运行</li>" +
            "<li>✅ <strong>面向对象</strong>：一切都是对象，符合人类思维习惯</li>" +
            "<li>✅ <strong>安全可靠</strong>：没有指针，自动内存管理（垃圾回收），不容易出错</li>" +
            "<li>✅ <strong>生态丰富</strong>：海量的第三方库和框架，企业级开发首选</li>" +
            "<li>✅ <strong>高薪就业</strong>：Java开发岗位多，薪资待遇好</li>" +
            "</ul>" +
            "<br>" +
            "<h3>🏗️ Java程序是怎么运行的？</h3>" +
            "<p>Java程序的运行分为两步：</p>" +
            "<p><b>1. 编译（Compile）：</b>将 .java 源文件编译成 .class 字节码文件</p>" +
            "<p><b>2. 运行（Run）：</b>JVM（Java虚拟机）读取字节码并执行</p>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 小提示：</b>JVM就像是一个「翻译官」，它把Java字节码翻译成你的操作系统能理解的指令。" +
            "</div>"
        );
        l1.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("Java程序的典型结构",
                "// 这是一个最简单的Java程序\n" +
                "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, Java!\");\n" +
                "    }\n" +
                "}",
                "Hello, Java!")
        ));
        lessons.add(l1);

        Lesson l2 = new Lesson("L02", "环境搭建——安装JDK和写第一个程序", "第一章：Java入门", 1);
        l2.setContent(
            "<h2>🛠️ 环境搭建</h2>" +
            "<h3>第一步：安装JDK</h3>" +
            "<p>JDK（Java Development Kit）是Java开发工具包，包含了编译器和JVM等工具。</p>" +
            "<ol>" +
            "<li>打开浏览器，搜索「Oracle JDK 下载」或「OpenJDK 下载」</li>" +
            "<li>选择适合你操作系统的版本（Windows选.exe，Mac选.dmg）</li>" +
            "<li>下载后双击安装，一路点击「下一步」即可</li>" +
            "<li>记住安装路径（例如：<code>C:\\Program Files\\Java\\jdk-17</code>）</li>" +
            "</ol>" +
            "<br>" +
            "<h3>第二步：配置环境变量（Windows）</h3>" +
            "<ol>" +
            "<li>右键「此电脑」→ 「属性」→ 「高级系统设置」</li>" +
            "<li>点击「环境变量」</li>" +
            "<li>新建系统变量 <code>JAVA_HOME</code>，值为JDK安装路径</li>" +
            "<li>在 <code>Path</code> 变量中添加 <code>%JAVA_HOME%\\bin</code></li>" +
            "<li>打开命令提示符，输入 <code>java -version</code> 验证安装</li>" +
            "</ol>" +
            "<br>" +
            "<h3>第三步：写第一个程序</h3>" +
            "<p>用记事本新建一个文件，命名为 <code>HelloWorld.java</code>，内容如下：</p>"
        );
        l2.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("你的第一个Java程序",
                "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 在屏幕上打印一句话\n" +
                "        System.out.println(\"这是我的第一个Java程序！\");\n" +
                "        System.out.println(\"你好，世界！\");\n" +
                "    }\n" +
                "}",
                "这是我的第一个Java程序！\n你好，世界！"),
            new Lesson.CodeExample("编译和运行命令",
                "// 在命令行中输入以下命令：\n" +
                "// 1. 编译（生成 HelloWorld.class 文件）\n" +
                "javac HelloWorld.java\n" +
                "\n" +
                "// 2. 运行（注意不要加 .class 后缀）\n" +
                "java HelloWorld",
                "这是我的第一个Java程序！\n你好，世界！")
        ));
        lessons.add(l2);

        // ==================== 第二章：基础语法 ====================
        Lesson l3 = new Lesson("L03", "变量与数据类型——存储数据的容器", "第二章：基础语法", 1);
        l3.setContent(
            "<h2>📦 变量是什么？</h2>" +
            "<p>变量就像是一个<strong>贴着标签的盒子</strong>，你可以把数据放进去，需要的时候再取出来。</p>" +
            "<p>声明变量的格式：<code>数据类型 变量名 = 初始值;</code></p>" +
            "<br>" +
            "<h3>📊 Java八大基本数据类型</h3>" +
            "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse:collapse;width:100%'>" +
            "<tr style='background:#5896ff;color:white;'><th>类型</th><th>含义</th><th>大小</th><th>示例</th></tr>" +
            "<tr><td><code>byte</code></td><td>字节型</td><td>1字节</td><td><code>byte b = 127;</code></td></tr>" +
            "<tr><td><code>short</code></td><td>短整型</td><td>2字节</td><td><code>short s = 32000;</code></td></tr>" +
            "<tr><td><code>int</code></td><td>整型⭐常用</td><td>4字节</td><td><code>int age = 25;</code></td></tr>" +
            "<tr><td><code>long</code></td><td>长整型</td><td>8字节</td><td><code>long l = 100000L;</code></td></tr>" +
            "<tr><td><code>float</code></td><td>单精度浮点</td><td>4字节</td><td><code>float f = 3.14f;</code></td></tr>" +
            "<tr><td><code>double</code></td><td>双精度浮点⭐常用</td><td>8字节</td><td><code>double d = 3.14159;</code></td></tr>" +
            "<tr><td><code>char</code></td><td>字符型</td><td>2字节</td><td><code>char c = 'A';</code></td></tr>" +
            "<tr><td><code>boolean</code></td><td>布尔型</td><td>1位</td><td><code>boolean ok = true;</code></td></tr>" +
            "</table>" +
            "<br>" +
            "<div style='background:#fff8e0;padding:12px;border-radius:8px;border-left:4px solid #ffc800;'>" +
            "<b>⚠️ 注意：</b>String（字符串）不是基本类型，是引用类型！但它非常常用：<code>String name = \"小明\";</code>" +
            "</div>"
        );
        l3.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("变量声明和使用",
                "public class VariableDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 声明变量\n" +
                "        int age = 20;\n" +
                "        double score = 95.5;\n" +
                "        char grade = 'A';\n" +
                "        boolean isPassed = true;\n" +
                "        String name = \"张三\";\n" +
                "        \n" +
                "        // 使用变量\n" +
                "        System.out.println(\"姓名：\" + name);\n" +
                "        System.out.println(\"年龄：\" + age);\n" +
                "        System.out.println(\"成绩：\" + score);\n" +
                "        System.out.println(\"等级：\" + grade);\n" +
                "        System.out.println(\"是否通过：\" + isPassed);\n" +
                "    }\n" +
                "}",
                "姓名：张三\n年龄：20\n成绩：95.5\n等级：A\n是否通过：true")
        ));
        lessons.add(l3);

        Lesson l4 = new Lesson("L04", "运算符——数据的运算与比较", "第二章：基础语法", 2);
        l4.setContent(
            "<h2>🔢 Java运算符</h2>" +
            "<h3>1. 算术运算符</h3>" +
            "<table border='1' cellpadding='6' cellspacing='0' style='border-collapse:collapse;'>" +
            "<tr style='background:#5896ff;color:white;'><th>运算符</th><th>含义</th><th>示例</th><th>结果</th></tr>" +
            "<tr><td><code>+</code></td><td>加</td><td><code>5 + 3</code></td><td>8</td></tr>" +
            "<tr><td><code>-</code></td><td>减</td><td><code>5 - 3</code></td><td>2</td></tr>" +
            "<tr><td><code>*</code></td><td>乘</td><td><code>5 * 3</code></td><td>15</td></tr>" +
            "<tr><td><code>/</code></td><td>除</td><td><code>5 / 2</code></td><td>2（整数除法）</td></tr>" +
            "<tr><td><code>%</code></td><td>取余（模）</td><td><code>5 % 2</code></td><td>1</td></tr>" +
            "</table>" +
            "<br>" +
            "<h3>2. 比较运算符（结果是boolean）</h3>" +
            "<p><code>>  >=  <  <=  ==  !=</code></p>" +
            "<p>例如：<code>5 > 3</code> 结果是 <code>true</code>；<code>5 == 3</code> 结果是 <code>false</code></p>" +
            "<br>" +
            "<h3>3. 逻辑运算符</h3>" +
            "<p><code>&&</code>（与）：两边都为true结果才为true</p>" +
            "<p><code>||</code>（或）：一边为true结果就为true</p>" +
            "<p><code>!</code>（非）：取反，true变false，false变true</p>" +
            "<br>" +
            "<h3>4. 自增自减</h3>" +
            "<p><code>++</code> 自增1：<code>i++</code> 或 <code>++i</code></p>" +
            "<p><code>--</code> 自减1：<code>j--</code> 或 <code>--j</code></p>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 关键区别：</b><code>i++</code> 先用后加；<code>++i</code> 先加后用。" +
            "</div>"
        );
        l4.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("运算符综合示例",
                "public class OperatorDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = 10, b = 3;\n" +
                "        \n" +
                "        // 算术运算\n" +
                "        System.out.println(\"a + b = \" + (a + b));\n" +
                "        System.out.println(\"a - b = \" + (a - b));\n" +
                "        System.out.println(\"a * b = \" + (a * b));\n" +
                "        System.out.println(\"a / b = \" + (a / b));\n" +
                "        System.out.println(\"a % b = \" + (a % b));\n" +
                "        \n" +
                "        // 比较运算\n" +
                "        System.out.println(\"a > b 吗？\" + (a > b));\n" +
                "        \n" +
                "        // 逻辑运算\n" +
                "        boolean result = (a > 5) && (b < 10);\n" +
                "        System.out.println(\"两条件都满足：\" + result);\n" +
                "        \n" +
                "        // 自增自减\n" +
                "        int x = 5;\n" +
                "        System.out.println(\"x++ = \" + (x++)); // 先用后加\n" +
                "        System.out.println(\"现在 x = \" + x);\n" +
                "    }\n" +
                "}",
                "a + b = 13\na - b = 7\na * b = 30\na / b = 3\na % b = 1\na > b 吗？true\n两条件都满足：true\nx++ = 5\n现在 x = 6")
        ));
        lessons.add(l4);

        Lesson l5 = new Lesson("L05", "条件语句——让程序学会判断", "第二章：基础语法", 2);
        l5.setContent(
            "<h2>🔀 条件语句</h2>" +
            "<p>条件语句让程序可以根据不同的情况执行不同的代码，就像人类做决策一样。</p>" +
            "<br>" +
            "<h3>1. if 语句（如果...就...）</h3>" +
            "<p>最基本的条件判断：</p>" +
            "<pre><code>if (条件) {\n    // 条件为true时执行\n}</code></pre>" +
            "<br>" +
            "<h3>2. if-else 语句（如果...就...否则...）</h3>" +
            "<p>二选一的情况：</p>" +
            "<pre><code>if (条件) {\n    // 条件为true时执行\n} else {\n    // 条件为false时执行\n}</code></pre>" +
            "<br>" +
            "<h3>3. if-else if-else 语句（多选一）</h3>" +
            "<p>多种情况判断：</p>" +
            "<pre><code>if (条件1) {\n    // ...\n} else if (条件2) {\n    // ...\n} else {\n    // 以上都不满足时执行\n}</code></pre>" +
            "<br>" +
            "<h3>4. switch 语句</h3>" +
            "<p>当一个变量有多个固定取值时，用switch更清晰：</p>"
        );
        l5.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("if-else 示例：成绩等级判断",
                "public class IfDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        int score = 85;\n" +
                "        \n" +
                "        if (score >= 90) {\n" +
                "            System.out.println(\"优秀！\");\n" +
                "        } else if (score >= 80) {\n" +
                "            System.out.println(\"良好！\");\n" +
                "        } else if (score >= 60) {\n" +
                "            System.out.println(\"及格\");\n" +
                "        } else {\n" +
                "            System.out.println(\"加油，需要努力哦！\");\n" +
                "        }\n" +
                "    }\n" +
                "}",
                "良好！"),
            new Lesson.CodeExample("switch 示例：星期几",
                "public class SwitchDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        int day = 3;\n" +
                "        \n" +
                "        switch (day) {\n" +
                "            case 1:\n" +
                "                System.out.println(\"星期一：新的一周开始了！\");\n" +
                "                break;\n" +
                "            case 2:\n" +
                "                System.out.println(\"星期二：继续加油！\");\n" +
                "                break;\n" +
                "            case 3:\n" +
                "                System.out.println(\"星期三：一周过半了！\");\n" +
                "                break;\n" +
                "            case 4:\n" +
                "                System.out.println(\"星期四：快要周末了！\");\n" +
                "                break;\n" +
                "            case 5:\n" +
                "                System.out.println(\"星期五：TGIF！\");\n" +
                "                break;\n" +
                "            default:\n" +
                "                System.out.println(\"周末愉快！\");\n" +
                "        }\n" +
                "    }\n" +
                "}",
                "星期三：一周过半了！")
        ));
        lessons.add(l5);

        Lesson l6 = new Lesson("L06", "循环语句——让程序重复执行", "第二章：基础语法", 2);
        l6.setContent(
            "<h2>🔄 循环语句</h2>" +
            "<p>循环让你可以重复执行一段代码，而不需要写很多重复的语句。</p>" +
            "<br>" +
            "<h3>1. for 循环（最常用）</h3>" +
            "<p><strong>适用场景：</strong>知道循环次数的情况</p>" +
            "<pre><code>for (初始化; 条件; 更新) {\n    // 循环体\n}\n\n// 例如：打印1到5\nfor (int i = 1; i <= 5; i++) {\n    System.out.println(i);\n}</code></pre>" +
            "<br>" +
            "<h3>2. while 循环</h3>" +
            "<p><strong>适用场景：</strong>不知道循环次数，只知道条件</p>" +
            "<pre><code>while (条件) {\n    // 循环体\n}</code></pre>" +
            "<br>" +
            "<h3>3. do-while 循环</h3>" +
            "<p>至少执行一次，然后判断条件：</p>" +
            "<pre><code>do {\n    // 循环体（至少执行一次）\n} while (条件);</code></pre>" +
            "<br>" +
            "<h3>4. break 和 continue</h3>" +
            "<p><code>break</code>：直接跳出整个循环</p>" +
            "<p><code>continue</code>：跳过本次循环，继续下一次</p>"
        );
        l6.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("for 循环：打印九九乘法表",
                "public class ForDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        for (int i = 1; i <= 9; i++) {\n" +
                "            for (int j = 1; j <= i; j++) {\n" +
                "                System.out.print(j + \"×\" + i + \"=\" + (i*j) + \"\\t\");\n" +
                "            }\n" +
                "            System.out.println(); // 换行\n" +
                "        }\n" +
                "    }\n" +
                "}",
                "1×1=1\t\n1×2=2\t2×2=4\t\n1×3=3\t2×3=6\t3×3=9\t\n...(完整99乘法表)"),
            new Lesson.CodeExample("while 循环：计算1到100的和",
                "public class WhileDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        int sum = 0;\n" +
                "        int i = 1;\n" +
                "        \n" +
                "        while (i <= 100) {\n" +
                "            sum += i;  // 等价于 sum = sum + i\n" +
                "            i++;\n" +
                "        }\n" +
                "        \n" +
                "        System.out.println(\"1到100的和是：\" + sum);\n" +
                "    }\n" +
                "}",
                "1到100的和是：5050")
        ));
        lessons.add(l6);

        Lesson l7 = new Lesson("L07", "数组——存储一组相同类型的数据", "第二章：基础语法", 2);
        l7.setContent(
            "<h2>📚 数组</h2>" +
            "<p>数组是一个<strong>容器</strong>，可以存放<strong>多个相同类型</strong>的数据。</p>" +
            "<p>想象一个停车场，有一排固定数量的停车位，每个车位只能停同一类型的车。</p>" +
            "<br>" +
            "<h3>1. 声明和创建数组</h3>" +
            "<p>方式一：先声明，再分配空间</p>" +
            "<pre><code>int[] scores = new int[5]; // 可以存5个int</code></pre>" +
            "<p>方式二：声明并初始化</p>" +
            "<pre><code>int[] scores = {85, 90, 78, 92, 88};</code></pre>" +
            "<br>" +
            "<h3>2. 访问数组元素</h3>" +
            "<p>通过<strong>下标（索引）</strong>访问，从 <strong>0</strong> 开始！</p>" +
            "<pre><code>int first = scores[0];  // 第一个元素\nint last = scores[4];   // 最后一个元素\nscores[2] = 95;         // 修改第三个元素</code></pre>" +
            "<br>" +
            "<h3>3. 数组的长度</h3>" +
            "<pre><code>int len = scores.length; // 获取数组长度</code></pre>" +
            "<br>" +
            "<div style='background:#ffe8e8;padding:12px;border-radius:8px;border-left:4px solid #ff6464;'>" +
            "<b>🚨 常见错误：</b>数组下标从0开始，最后一个元素的下标是 <code>length - 1</code>。" +
            "访问 <code>scores[5]</code> 会报错 <code>ArrayIndexOutOfBoundsException</code>（数组越界）！" +
            "</div>"
        );
        l7.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("数组基本操作",
                "public class ArrayDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 创建并初始化数组\n" +
                "        int[] scores = {85, 90, 78, 92, 88};\n" +
                "        \n" +
                "        // 遍历数组\n" +
                "        int sum = 0;\n" +
                "        for (int i = 0; i < scores.length; i++) {\n" +
                "            System.out.println(\"第\" + (i+1) + \"个成绩：\" + scores[i]);\n" +
                "            sum += scores[i];\n" +
                "        }\n" +
                "        \n" +
                "        double avg = (double) sum / scores.length;\n" +
                "        System.out.println(\"平均分：\" + avg);\n" +
                "    }\n" +
                "}",
                "第1个成绩：85\n第2个成绩：90\n第3个成绩：78\n第4个成绩：92\n第5个成绩：88\n平均分：86.6")
        ));
        lessons.add(l7);

        // ==================== 第三章：面向对象 ====================
        Lesson l8 = new Lesson("L08", "方法与函数——把代码组织起来", "第三章：面向对象入门", 3);
        l8.setContent(
            "<h2>🔧 方法（Method）</h2>" +
            "<p>方法就是<strong>一段可以重复使用的代码块</strong>，像一个做特定工作的「工具箱」。</p>" +
            "<br>" +
            "<h3>方法的定义格式</h3>" +
            "<pre><code>修饰符 返回值类型 方法名(参数列表) {\n    // 方法体\n    return 返回值;\n}</code></pre>" +
            "<br>" +
            "<h3>各部分说明：</h3>" +
            "<ul>" +
            "<li><code>修饰符</code>：如 public、static、private 等</li>" +
            "<li><code>返回值类型</code>：方法返回什么类型的数据，没有返回用 <code>void</code></li>" +
            "<li><code>方法名</code>：自己取的名字，采用小驼峰命名，如 <code>calculateSum</code></li>" +
            "<li><code>参数列表</code>：传入方法的数据，可以有0个或多个</li>" +
            "</ul>" +
            "<br>" +
            "<h3>为什么用方法？</h3>" +
            "<ul>" +
            "<li>✅ 避免重复代码（DRY原则：Don't Repeat Yourself）</li>" +
            "<li>✅ 让代码结构更清晰，便于维护</li>" +
            "<li>✅ 一次编写，多处调用</li>" +
            "</ul>"
        );
        l8.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("定义和调用方法",
                "public class MethodDemo {\n" +
                "    \n" +
                "    // 定义一个方法：求两个数的最大值\n" +
                "    public static int max(int a, int b) {\n" +
                "        if (a > b) {\n" +
                "            return a;\n" +
                "        } else {\n" +
                "            return b;\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    // 定义一个方法：打印问候语（无返回值）\n" +
                "    public static void greet(String name) {\n" +
                "        System.out.println(\"你好，\" + name + \"！欢迎学习Java！\");\n" +
                "    }\n" +
                "    \n" +
                "    public static void main(String[] args) {\n" +
                "        // 调用方法\n" +
                "        int bigger = max(10, 25);\n" +
                "        System.out.println(\"较大的数是：\" + bigger);\n" +
                "        \n" +
                "        greet(\"小明\");\n" +
                "        greet(\"小红\");\n" +
                "    }\n" +
                "}",
                "较大的数是：25\n你好，小明！欢迎学习Java！\n你好，小红！欢迎学习Java！"),
            new Lesson.CodeExample("方法重载——同名不同参",
                "public class OverloadDemo {\n" +
                "    // 方法名相同，参数不同 → 方法重载\n" +
                "    public static int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "    \n" +
                "    public static double add(double a, double b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "    \n" +
                "    public static int add(int a, int b, int c) {\n" +
                "        return a + b + c;\n" +
                "    }\n" +
                "    \n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(add(3, 5));       // 调用第一个\n" +
                "        System.out.println(add(3.5, 2.5));   // 调用第二个\n" +
                "        System.out.println(add(1, 2, 3));    // 调用第三个\n" +
                "    }\n" +
                "}",
                "8\n6.0\n6")
        ));
        lessons.add(l8);

        Lesson l9 = new Lesson("L09", "类与对象——面向对象的核心", "第三章：面向对象入门", 3);
        l9.setContent(
            "<h2>🏛️ 类与对象</h2>" +
            "<p><strong>类（Class）</strong>是对象的<strong>蓝图/模板</strong>，<strong>对象（Object）</strong>是类的<strong>具体实例</strong>。</p>" +
            "<p>类比：<strong>类 = 汽车设计图纸</strong>，<strong>对象 = 根据图纸造出来的真车</strong></p>" +
            "<br>" +
            "<h3>1. 定义类</h3>" +
            "<p>一个类包含：</p>" +
            "<ul>" +
            "<li><strong>属性（字段）</strong>：描述对象的状态/特征</li>" +
            "<li><strong>方法</strong>：描述对象的行为/功能</li>" +
            "</ul>" +
            "<br>" +
            "<h3>2. 创建对象</h3>" +
            "<pre><code>类名 对象名 = new 类名();</code></pre>" +
            "<p><code>new</code> 关键字用来「实例化」一个对象。</p>" +
            "<br>" +
            "<h3>3. 构造方法（Constructor）</h3>" +
            "<p>构造方法用于<strong>初始化对象</strong>，方法名和类名相同，没有返回值：</p>" +
            "<pre><code>public Student(String name, int age) {\n    this.name = name;\n    this.age = age;\n}</code></pre>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 this 关键字：</b><code>this</code> 代表当前对象本身，用来区分成员变量和局部变量。" +
            "</div>"
        );
        l9.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("定义一个学生类并创建对象",
                "// 定义 Student 类\n" +
                "class Student {\n" +
                "    // 属性（成员变量）\n" +
                "    String name;\n" +
                "    int age;\n" +
                "    double score;\n" +
                "    \n" +
                "    // 构造方法\n" +
                "    public Student(String name, int age, double score) {\n" +
                "        this.name = name;\n" +
                "        this.age = age;\n" +
                "        this.score = score;\n" +
                "    }\n" +
                "    \n" +
                "    // 方法：自我介绍\n" +
                "    public void introduce() {\n" +
                "        System.out.println(\"我叫\" + name + \"，今年\" + age + \"岁，成绩是\" + score);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "public class ClassDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 创建对象（实例化）\n" +
                "        Student s1 = new Student(\"张三\", 20, 92.5);\n" +
                "        Student s2 = new Student(\"李四\", 21, 88.0);\n" +
                "        \n" +
                "        // 调用对象的方法\n" +
                "        s1.introduce();\n" +
                "        s2.introduce();\n" +
                "    }\n" +
                "}",
                "我叫张三，今年20岁，成绩是92.5\n我叫李四，今年21岁，成绩是88.0")
        ));
        lessons.add(l9);

        Lesson l10 = new Lesson("L10", "封装——保护数据的安全", "第三章：面向对象入门", 3);
        l10.setContent(
            "<h2>🔒 封装（Encapsulation）</h2>" +
            "<p>封装是面向对象的<strong>三大特性之一</strong>。核心思想是：<strong>隐藏内部细节，只暴露必要的接口</strong>。</p>" +
            "<br>" +
            "<h3>为什么要封装？</h3>" +
            "<p>把电视机的内部电路封装在机箱里，你只需要遥控器就能操作。如果所有电线都露在外面，不仅危险，还容易弄坏。</p>" +
            "<br>" +
            "<h3>如何实现封装？</h3>" +
            "<ol>" +
            "<li>用 <code>private</code> 修饰属性，禁止外部直接访问</li>" +
            "<li>提供 <code>public</code> 的 getter/setter 方法控制访问</li>" +
            "</ol>" +
            "<br>" +
            "<h3>访问修饰符</h3>" +
            "<table border='1' cellpadding='6' cellspacing='0' style='border-collapse:collapse;'>" +
            "<tr style='background:#5896ff;color:white;'><th>修饰符</th><th>同类</th><th>同包</th><th>子类</th><th>任何地方</th></tr>" +
            "<tr><td><code>private</code></td><td>✅</td><td>❌</td><td>❌</td><td>❌</td></tr>" +
            "<tr><td><code>默认</code></td><td>✅</td><td>✅</td><td>❌</td><td>❌</td></tr>" +
            "<tr><td><code>protected</code></td><td>✅</td><td>✅</td><td>✅</td><td>❌</td></tr>" +
            "<tr><td><code>public</code></td><td>✅</td><td>✅</td><td>✅</td><td>✅</td></tr>" +
            "</table>"
        );
        l10.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("封装示例：银行账户",
                "class BankAccount {\n" +
                "    private String owner;   // 私有属性\n" +
                "    private double balance; // 私有属性，不允许直接修改\n" +
                "    \n" +
                "    public BankAccount(String owner, double initialBalance) {\n" +
                "        this.owner = owner;\n" +
                "        if (initialBalance >= 0) {\n" +
                "            this.balance = initialBalance;\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    // Getter：获取余额（只读）\n" +
                "    public double getBalance() {\n" +
                "        return balance;\n" +
                "    }\n" +
                "    \n" +
                "    // 存款（通过方法控制修改）\n" +
                "    public void deposit(double amount) {\n" +
                "        if (amount > 0) {\n" +
                "            balance += amount;\n" +
                "            System.out.println(\"存入 \" + amount + \" 元，余额：\" + balance);\n" +
                "        } else {\n" +
                "            System.out.println(\"存款金额必须大于0！\");\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    // 取款\n" +
                "    public void withdraw(double amount) {\n" +
                "        if (amount > 0 && amount <= balance) {\n" +
                "            balance -= amount;\n" +
                "            System.out.println(\"取出 \" + amount + \" 元，余额：\" + balance);\n" +
                "        } else {\n" +
                "            System.out.println(\"余额不足或金额无效！\");\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "public class EncapsulationDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        BankAccount account = new BankAccount(\"张三\", 1000);\n" +
                "        System.out.println(\"初始余额：\" + account.getBalance());\n" +
                "        account.deposit(500);\n" +
                "        account.withdraw(200);\n" +
                "        account.withdraw(2000); // 余额不足\n" +
                "        // account.balance = 99999; ← 编译错误！private属性不能直接访问\n" +
                "    }\n" +
                "}",
                "初始余额：1000.0\n存入 500.0 元，余额：1500.0\n取出 200.0 元，余额：1300.0\n余额不足或金额无效！")
        ));
        lessons.add(l10);

        Lesson l11 = new Lesson("L11", "继承——让子类拥有父类的能力", "第三章：面向对象入门", 4);
        l11.setContent(
            "<h2>🧬 继承（Inheritance）</h2>" +
            "<p>继承是面向对象的<strong>第二个特性</strong>。子类可以<strong>继承父类的属性和方法</strong>，实现代码复用。</p>" +
            "<br>" +
            "<h3>继承的语法</h3>" +
            "<pre><code>class 子类 extends 父类 {\n    // 子类特有的属性和方法\n}</code></pre>" +
            "<br>" +
            "<h3>继承的特点</h3>" +
            "<ul>" +
            "<li>Java只支持<strong>单继承</strong>（一个类只能有一个父类）</li>" +
            "<li>子类可以<strong>重写（Override）</strong>父类的方法</li>" +
            "<li>子类通过 <code>super</code> 关键字调用父类的构造方法或方法</li>" +
            "<li>所有类默认继承自 <code>Object</code> 类</li>" +
            "</ul>" +
            "<br>" +
            "<h3>方法重写 vs 方法重载</h3>" +
            "<p><strong>重写（Override）：</strong>子类重新定义父类的方法，方法签名完全相同</p>" +
            "<p><strong>重载（Overload）：</strong>同一个类中，方法名相同但参数不同</p>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 一句话区分：</b>重写是子类覆盖父类方法，重载是同名方法不同参数。" +
            "</div>"
        );
        l11.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("继承示例：动物体系",
                "// 父类：动物\n" +
                "class Animal {\n" +
                "    protected String name;\n" +
                "    \n" +
                "    public Animal(String name) {\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "    \n" +
                "    public void eat() {\n" +
                "        System.out.println(name + \" 正在吃东西\");\n" +
                "    }\n" +
                "    \n" +
                "    public void sleep() {\n" +
                "        System.out.println(name + \" 在睡觉\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// 子类：狗（继承自动物）\n" +
                "class Dog extends Animal {\n" +
                "    public Dog(String name) {\n" +
                "        super(name); // 调用父类构造方法\n" +
                "    }\n" +
                "    \n" +
                "    // 重写父类的 eat 方法\n" +
                "    @Override\n" +
                "    public void eat() {\n" +
                "        System.out.println(name + \" 在吃骨头 🦴\");\n" +
                "    }\n" +
                "    \n" +
                "    // 子类特有的方法\n" +
                "    public void bark() {\n" +
                "        System.out.println(name + \"：汪汪汪！\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "public class InheritanceDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        Dog dog = new Dog(\"旺财\");\n" +
                "        dog.eat();    // 调用重写后的方法\n" +
                "        dog.sleep();  // 调用继承来的方法\n" +
                "        dog.bark();   // 调用子类特有的方法\n" +
                "    }\n" +
                "}",
                "旺财 在吃骨头 🦴\n旺财 在睡觉\n旺财：汪汪汪！")
        ));
        lessons.add(l11);

        Lesson l12 = new Lesson("L12", "多态——同一个接口，不同的实现", "第三章：面向对象入门", 4);
        l12.setContent(
            "<h2>🎭 多态（Polymorphism）</h2>" +
            "<p>多态是面向对象的<strong>第三个特性</strong>。简单说就是：<strong>父类引用指向子类对象，同一个方法表现出不同的行为</strong>。</p>" +
            "<br>" +
            "<h3>多态的前提条件</h3>" +
            "<ol>" +
            "<li>必须有<strong>继承</strong>或<strong>接口实现</strong>关系</li>" +
            "<li>必须有<strong>方法重写</strong></li>" +
            "<li><strong>父类引用</strong>指向<strong>子类对象</strong></li>" +
            "</ol>" +
            "<br>" +
            "<h3>多态的体现</h3>" +
            "<pre><code>Animal a = new Dog();  // 父类引用指向子类对象\na.makeSound();          // 实际调用的是 Dog 的方法</code></pre>" +
            "<br>" +
            "<h3>多态的好处</h3>" +
            "<ul>" +
            "<li>✅ 代码更灵活，扩展性更好</li>" +
            "<li>✅ 可以用统一的接口处理不同的子类型</li>" +
            "<li>✅ 新增子类时不需要修改已有代码（开闭原则）</li>" +
            "</ul>"
        );
        l12.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("多态经典示例",
                "// 父类\n" +
                "class Shape {\n" +
                "    public void draw() {\n" +
                "        System.out.println(\"画一个形状\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Circle extends Shape {\n" +
                "    @Override\n" +
                "    public void draw() {\n" +
                "        System.out.println(\"○ 画一个圆形\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Rectangle extends Shape {\n" +
                "    @Override\n" +
                "    public void draw() {\n" +
                "        System.out.println(\"□ 画一个矩形\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Triangle extends Shape {\n" +
                "    @Override\n" +
                "    public void draw() {\n" +
                "        System.out.println(\"△ 画一个三角形\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "public class PolymorphismDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 多态：父类引用指向不同的子类对象\n" +
                "        Shape[] shapes = {\n" +
                "            new Circle(),\n" +
                "            new Rectangle(),\n" +
                "            new Triangle()\n" +
                "        };\n" +
                "        \n" +
                "        // 同一个 draw() 调用，表现不同\n" +
                "        for (Shape s : shapes) {\n" +
                "            s.draw();\n" +
                "        }\n" +
                "    }\n" +
                "}",
                "○ 画一个圆形\n□ 画一个矩形\n△ 画一个三角形")
        ));
        lessons.add(l12);

        // ==================== 第四章：常用工具 ====================
        Lesson l13 = new Lesson("L13", "String类——字符串的常用操作", "第四章：常用工具", 2);
        l13.setContent(
            "<h2>📝 String 类</h2>" +
            "<p>String（字符串）是Java中最常用的类之一，用于存储和处理文本。</p>" +
            "<br>" +
            "<h3>String的特点</h3>" +
            "<ul>" +
            "<li>String是<strong>不可变</strong>的（一旦创建，内容不能改变）</li>" +
            "<li>String本质是<strong>字符数组</strong></li>" +
            "<li>字符串字面量存在<strong>字符串常量池</strong>中，可复用</li>" +
            "</ul>" +
            "<br>" +
            "<h3>String常用方法一览</h3>" +
            "<table border='1' cellpadding='6' cellspacing='0' style='border-collapse:collapse;'>" +
            "<tr style='background:#5896ff;color:white;'><th>方法</th><th>说明</th><th>示例</th></tr>" +
            "<tr><td><code>length()</code></td><td>获取长度</td><td><code>\"abc\".length() → 3</code></td></tr>" +
            "<tr><td><code>charAt(i)</code></td><td>获取第i个字符</td><td><code>\"abc\".charAt(0) → 'a'</code></td></tr>" +
            "<tr><td><code>substring(s,e)</code></td><td>截取子串</td><td><code>\"hello\".substring(1,3) → \"el\"</code></td></tr>" +
            "<tr><td><code>contains(s)</code></td><td>是否包含</td><td><code>\"hello\".contains(\"el\") → true</code></td></tr>" +
            "<tr><td><code>equals(s)</code></td><td>比较内容是否相等</td><td><code>\"a\".equals(\"a\") → true</code></td></tr>" +
            "<tr><td><code>replace(a,b)</code></td><td>替换</td><td><code>\"jaca\".replace('c','v') → \"java\"</code></td></tr>" +
            "<tr><td><code>split(regex)</code></td><td>分割</td><td><code>\"a,b,c\".split(\",\") → [\"a\",\"b\",\"c\"]</code></td></tr>" +
            "<tr><td><code>trim()</code></td><td>去除两端空格</td><td><code>\" hi \".trim() → \"hi\"</code></td></tr>" +
            "<tr><td><code>toLowerCase()</code></td><td>转小写</td><td><code>\"JAVA\".toLowerCase() → \"java\"</code></td></tr>" +
            "</table>" +
            "<br>" +
            "<div style='background:#ffe8e8;padding:12px;border-radius:8px;border-left:4px solid #ff6464;'>" +
            "<b>🚨 重要：</b>比较字符串内容一定要用 <code>equals()</code>，不能用 <code>==</code>！<br>" +
            "<code>==</code> 比较的是内存地址，<code>equals()</code> 比较的是内容。" +
            "</div>"
        );
        l13.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("String常用操作演示",
                "public class StringDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        String str = \"  Hello Java World  \";\n" +
                "        \n" +
                "        System.out.println(\"原始：[\" + str + \"]\");\n" +
                "        System.out.println(\"长度：\" + str.length());\n" +
                "        System.out.println(\"去空格：[\" + str.trim() + \"]\");\n" +
                "        System.out.println(\"转大写：\" + str.toUpperCase());\n" +
                "        System.out.println(\"包含Java？\" + str.contains(\"Java\"));\n" +
                "        System.out.println(\"第7个字符：\" + str.charAt(7));\n" +
                "        System.out.println(\"替换：\" + str.replace(\"World\", \"世界\"));\n" +
                "    }\n" +
                "}",
                "原始：[  Hello Java World  ]\n长度：19\n去空格：[Hello Java World]\n转大写：  HELLO JAVA WORLD  \n包含Java？true\n第7个字符：l\n替换：  Hello Java 世界  ")
        ));
        lessons.add(l13);

        Lesson l14 = new Lesson("L14", "ArrayList——动态数组，比数组更好用", "第四章：常用工具", 3);
        l14.setContent(
            "<h2>📋 ArrayList——可变长度的列表</h2>" +
            "<p>普通的数组长度是固定的，而 <code>ArrayList</code> 的长度<strong>可以动态变化</strong>，非常方便。</p>" +
            "<br>" +
            "<h3>ArrayList vs 普通数组</h3>" +
            "<table border='1' cellpadding='6' cellspacing='0' style='border-collapse:collapse;'>" +
            "<tr style='background:#5896ff;color:white;'><th>特性</th><th>数组</th><th>ArrayList</th></tr>" +
            "<tr><td>长度</td><td>固定，不能改</td><td>动态，自动扩容</td></tr>" +
            "<tr><td>存储类型</td><td>基本类型+引用类型</td><td>只能引用类型</td></tr>" +
            "<tr><td>增删</td><td>不方便</td><td>很方便</td></tr>" +
            "<tr><td>大小获取</td><td><code>.length</code></td><td><code>.size()</code></td></tr>" +
            "</table>" +
            "<br>" +
            "<h3>常用方法</h3>" +
            "<ul>" +
            "<li><code>add(e)</code> — 在末尾添加元素</li>" +
            "<li><code>get(i)</code> — 获取第i个元素</li>" +
            "<li><code>set(i, e)</code> — 修改第i个元素</li>" +
            "<li><code>remove(i)</code> — 删除第i个元素</li>" +
            "<li><code>size()</code> — 获取元素个数</li>" +
            "<li><code>clear()</code> — 清空所有元素</li>" +
            "<li><code>contains(e)</code> — 判断是否包含某元素</li>" +
            "</ul>" +
            "<br>" +
            "<div style='background:#f0f4ff;padding:12px;border-radius:8px;border-left:4px solid #5896ff;'>" +
            "<b>💡 使用提示：</b>需要 <code>import java.util.ArrayList;</code>。<br>" +
            "如果存int，要用它的包装类：<code>ArrayList&lt;Integer&gt;</code></div>"
        );
        l14.setCodeExamples(Arrays.asList(
            new Lesson.CodeExample("ArrayList基本操作",
                "import java.util.ArrayList;\n" +
                "\n" +
                "public class ArrayListDemo {\n" +
                "    public static void main(String[] args) {\n" +
                "        // 创建一个ArrayList（存储字符串）\n" +
                "        ArrayList<String> names = new ArrayList<>();\n" +
                "        \n" +
                "        // 添加元素\n" +
                "        names.add(\"张三\");\n" +
                "        names.add(\"李四\");\n" +
                "        names.add(\"王五\");\n" +
                "        System.out.println(\"列表：\" + names);\n" +
                "        System.out.println(\"共有 \" + names.size() + \" 人\");\n" +
                "        \n" +
                "        // 获取元素\n" +
                "        System.out.println(\"第2个人：\" + names.get(1));\n" +
                "        \n" +
                "        // 删除元素\n" +
                "        names.remove(0);\n" +
                "        System.out.println(\"删除第一个后：\" + names);\n" +
                "        \n" +
                "        // 遍历列表（增强for循环）\n" +
                "        System.out.println(\"=== 遍历列表 ===\");\n" +
                "        for (String name : names) {\n" +
                "            System.out.println(\"👤 \" + name);\n" +
                "        }\n" +
                "    }\n" +
                "}",
                "列表：[张三, 李四, 王五]\n共有 3 人\n第2个人：李四\n删除第一个后：[李四, 王五]\n=== 遍历列表 ===\n👤 李四\n👤 王五")
        ));
        lessons.add(l14);

        return lessons;
    }

    /**
     * 为每一课创建测验题目
     */
    public static Map<String, List<QuizQuestion>> createQuizzes() {
        Map<String, List<QuizQuestion>> quizMap = new LinkedHashMap<>();

        // L03: 变量与数据类型
        quizMap.put("L03", Arrays.asList(
            new QuizQuestion("以下哪个是Java的int类型能表示的最大值？",
                Arrays.asList("A. 127", "B. 32767", "C. 约21亿", "D. 无限大"), 2,
                "int是4字节（32位），能表示的范围是 -2^31 ~ 2^31-1，也就是约 ±21亿。"),
            new QuizQuestion("以下哪个不是Java的基本数据类型？",
                Arrays.asList("A. int", "B. String", "C. double", "D. boolean"), 1,
                "String是引用类型，不是基本类型！基本类型只有8种：byte, short, int, long, float, double, char, boolean。"),
            new QuizQuestion("以下变量命名哪个不符合Java规范？",
                Arrays.asList("A. userName", "B. _value", "C. 2name", "D. $price"), 2,
                "Java变量名不能以数字开头。可以包含字母、数字、下划线和$，但必须以字母、下划线或$开头。")
        ));

        // L05: 条件语句
        quizMap.put("L05", Arrays.asList(
            new QuizQuestion("以下代码输出什么？ int x = 10; if (x > 5) System.out.print(\"A\"); else if (x > 8) System.out.print(\"B\"); else System.out.print(\"C\");",
                Arrays.asList("A. A", "B. B", "C. C", "D. AB"), 0,
                "if-else if 结构中，一旦某个条件满足，后面的就不再判断。x>5 成立，所以输出A，后面的else if被跳过。"),
            new QuizQuestion("switch语句中，如果某个case后面没有break，会发生什么？",
                Arrays.asList("A. 编译错误", "B. 只执行该case", "C. 继续执行下一个case（穿透）", "D. 自动跳出"), 2,
                "没有break会导致「case穿透」，程序会继续执行下一个case的代码，直到遇到break或switch结束。有时候这是故意利用的特性。")
        ));

        // L06: 循环
        quizMap.put("L06", Arrays.asList(
            new QuizQuestion("for (int i = 0; i < 5; i++) {} 这个循环执行了多少次？",
                Arrays.asList("A. 4次", "B. 5次", "C. 6次", "D. 0次"), 1,
                "i的值分别是0,1,2,3,4，共5次。当i=5时，条件i<5不满足，循环结束。"),
            new QuizQuestion("while 和 do-while 的主要区别是什么？",
                Arrays.asList("A. 没有区别", "B. while先判断后执行，do-while先执行后判断", "C. while更快", "D. do-while只能执行一次"), 1,
                "do-while保证循环体至少执行一次（先执行后判断），而while可能一次都不执行（先判断后执行）。")
        ));

        // L09: 类与对象
        quizMap.put("L09", Arrays.asList(
            new QuizQuestion("在Java中，使用什么关键字创建对象？",
                Arrays.asList("A. create", "B. new", "C. malloc", "D. alloc"), 1,
                "Java使用 new 关键字来创建对象。注意不同于C++的new（分配在堆上需要手动delete），Java的new创建的对象由垃圾回收器自动管理。"),
            new QuizQuestion("构造方法的特点是什么？",
                Arrays.asList("A. 有返回值", "B. 方法名可以与类名不同", "C. 方法名必须与类名相同，没有返回值", "D. 每个类只能有一个构造方法"), 2,
                "构造方法的方法名必须和类名完全相同，且没有返回值类型（连void都不能写）。一个类可以有多个构造方法（重载）。")
        ));

        // L11: 继承
        quizMap.put("L11", Arrays.asList(
            new QuizQuestion("Java中一个类可以继承几个父类？",
                Arrays.asList("A. 1个", "B. 2个", "C. 多个", "D. 0个"), 0,
                "Java是单继承的，一个类只能有一个直接父类（使用extends关键字）。但可以通过实现多个接口来弥补这个限制。"),
            new QuizQuestion("super关键字的作用是什么？",
                Arrays.asList("A. 调用子类的方法", "B. 调用父类的构造方法或方法", "C. 创建新对象", "D. 没有这个关键字"), 1,
                "super用来引用父类。super()调用父类构造方法，super.method()调用父类的方法。")
        ));

        // L13: String
        quizMap.put("L13", Arrays.asList(
            new QuizQuestion("比较两个字符串的内容是否相等，应该使用什么？",
                Arrays.asList("A. ==", "B. equals()", "C. compare()", "D. isEqual()"), 1,
                "== 比较的是引用地址，equals() 比较的是内容。例如：\"hello\".equals(\"hello\") 返回true。"),
            new QuizQuestion("\"Java\".length() 的结果是多少？",
                Arrays.asList("A. 3", "B. 4", "C. 5", "D. 编译错误"), 1,
                "\"Java\" 有4个字符：J, a, v, a，所以length()返回4。注意空格也算一个字符：\"Ja va\".length() = 5。")
        ));

        return quizMap;
    }
}
