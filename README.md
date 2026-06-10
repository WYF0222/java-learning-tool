# ☕ Java 小白学习乐园

一个为 **零基础初学者** 设计的 Java 交互式学习软件，带有图形界面。

## ✨ 功能特色

- 📚 **14节系统课程** — 从变量、循环到面向对象，循序渐进
- 💻 **代码示例展示** — 每课多个带语法高亮的示例代码，可折叠查看运行结果
- 📝 **课后测验** — 选择题测验，即时反馈 + 详细解析
- 🏆 **章节实操题** — 学完一章自动解锁编程练习，在线编译运行 + 自动验证
- 🎮 **自由练习区** — 7个代码模板，支持在线编译运行（Ctrl+Enter）
- 📊 **进度追踪** — 自动保存学习进度和成绩

## 🚀 快速开始

### 前提条件
- JDK 17 或更高版本（需要 `javac` 和 `java` 命令）

### 编译运行

**Windows（双击）：**
```
双击 compile.bat 编译
双击 run.bat 运行
```

**命令行：**
```bash
# 编译
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")

# 运行
java -cp out javalearner.Main
```

## 📖 课程目录

| 章节 | 内容 |
|------|------|
| 第一章：Java入门 | 认识Java、环境搭建 |
| 第二章：基础语法 | 变量与数据类型、运算符、条件语句、循环语句、数组 |
| 第三章：面向对象入门 | 方法、类与对象、封装、继承、多态 |
| 第四章：常用工具 | String类、ArrayList |

## 📁 项目结构

```
java-learning-tool/
├── src/javalearner/
│   ├── Main.java                 # 程序入口
│   ├── model/
│   │   ├── Lesson.java           # 课程模型
│   │   ├── QuizQuestion.java     # 测验题模型
│   │   ├── CodingExercise.java   # 实操题模型
│   │   └── Progress.java         # 进度管理
│   ├── data/
│   │   ├── CourseData.java       # 14节课+测验题库
│   │   └── ExerciseData.java     # 4章节实操题
│   └── ui/
│       ├── Theme.java            # 统一主题配色
│       ├── MainWindow.java       # 主窗口
│       ├── LessonView.java       # 课程展示
│       ├── QuizView.java         # 测验界面
│       ├── CodingExerciseView.java # 实操题界面
│       └── CodePlayground.java   # 自由练习区
├── compile.bat
├── run.bat
└── .gitignore
```

## 🛠️ 技术栈

- Java Swing（GUI）
- 纯 Java，无第三方依赖
- 在线编译运行通过 `javac`/`java` 子进程实现

## 📝 License

MIT
