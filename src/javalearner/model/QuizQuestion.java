package javalearner.model;

import java.util.List;

/**
 * 测验题目模型
 */
public class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctIndex; // 0-based
    private String explanation;

    public QuizQuestion(String question, List<String> options, int correctIndex, String explanation) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
    }

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public String getExplanation() { return explanation; }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctIndex;
    }
}
