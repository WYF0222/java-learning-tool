package javalearner.model;

import java.util.List;

/**
 * 课程数据模型
 */
public class Lesson {
    private String id;
    private String title;
    private String category;
    private int difficulty; // 1-5
    private String content; // HTML格式的课程内容
    private List<CodeExample> codeExamples;
    private boolean completed;

    public Lesson(String id, String title, String category, int difficulty) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.completed = false;
    }

    public static class CodeExample {
        private String title;
        private String code;
        private String expectedOutput;

        public CodeExample(String title, String code, String expectedOutput) {
            this.title = title;
            this.code = code;
            this.expectedOutput = expectedOutput;
        }

        public String getTitle() { return title; }
        public String getCode() { return code; }
        public String getExpectedOutput() { return expectedOutput; }
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public int getDifficulty() { return difficulty; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public List<CodeExample> getCodeExamples() { return codeExamples; }
    public void setCodeExamples(List<CodeExample> codeExamples) { this.codeExamples = codeExamples; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getDifficultyStars() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < difficulty; i++) sb.append("⭐");
        return sb.toString();
    }
}
