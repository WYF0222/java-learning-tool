package javalearner.model;

import java.util.List;

/**
 * 章节实操编程题模型
 */
public class CodingExercise {
    private String chapterKey;        // 章节标识
    private String chapterName;       // 章节名称
    private String title;             // 题目名称
    private String description;       // 题目描述（HTML格式）
    private List<String> hints;       // 提示列表
    private String starterCode;       // 初始代码模板
    private List<String> expectedKeywords; // 输出中必须包含的关键词（用于验证）
    private String expectedOutputExample;  // 预期输出示例

    public CodingExercise(String chapterKey, String chapterName, String title) {
        this.chapterKey = chapterKey;
        this.chapterName = chapterName;
        this.title = title;
    }

    // Getters and Setters
    public String getChapterKey() { return chapterKey; }
    public String getChapterName() { return chapterName; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getHints() { return hints; }
    public void setHints(List<String> hints) { this.hints = hints; }
    public String getStarterCode() { return starterCode; }
    public void setStarterCode(String starterCode) { this.starterCode = starterCode; }
    public List<String> getExpectedKeywords() { return expectedKeywords; }
    public void setExpectedKeywords(List<String> expectedKeywords) { this.expectedKeywords = expectedKeywords; }
    public String getExpectedOutputExample() { return expectedOutputExample; }
    public void setExpectedOutputExample(String expectedOutputExample) { this.expectedOutputExample = expectedOutputExample; }

    /**
     * 验证输出是否包含所有关键词
     */
    public boolean validateOutput(String output) {
        if (expectedKeywords == null || expectedKeywords.isEmpty()) {
            return output != null && !output.trim().isEmpty();
        }
        for (String keyword : expectedKeywords) {
            if (!output.contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取缺失的关键词
     */
    public List<String> getMissingKeywords(String output) {
        if (expectedKeywords == null) return List.of();
        return expectedKeywords.stream()
            .filter(k -> !output.contains(k))
            .toList();
    }
}
