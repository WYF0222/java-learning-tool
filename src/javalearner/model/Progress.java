package javalearner.model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * 用户学习进度管理（持久化到文件）
 */
public class Progress {
    private static final String PROGRESS_FILE = System.getProperty("user.home") + "/.javalearner_progress.txt";
    private Set<String> completedLessons;
    private Set<String> completedExercises;  // 章节练习完成记录
    private Map<String, Integer> quizScores; // lessonId -> score percentage

    public Progress() {
        completedLessons = new HashSet<>();
        completedExercises = new HashSet<>();
        quizScores = new HashMap<>();
        load();
    }

    public void markLessonCompleted(String lessonId) {
        completedLessons.add(lessonId);
        save();
    }

    public boolean isLessonCompleted(String lessonId) {
        return completedLessons.contains(lessonId);
    }

    // ===== 章节练习相关 =====
    public void markExerciseCompleted(String chapterKey) {
        completedExercises.add(chapterKey);
        save();
    }

    public boolean isExerciseCompleted(String chapterKey) {
        return completedExercises.contains(chapterKey);
    }

    public void recordQuizScore(String lessonId, int scorePercent) {
        quizScores.merge(lessonId, scorePercent, Math::max);
        save();
    }

    public int getQuizScore(String lessonId) {
        return quizScores.getOrDefault(lessonId, -1);
    }

    public int getTotalCompleted() {
        return completedLessons.size();
    }

    public double getOverallProgress(int totalLessons) {
        if (totalLessons == 0) return 0;
        return (double) completedLessons.size() / totalLessons * 100;
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PROGRESS_FILE))) {
            for (String id : completedLessons) {
                pw.println("COMPLETED:" + id);
            }
            for (String ch : completedExercises) {
                pw.println("EXERCISE:" + ch);
            }
            for (Map.Entry<String, Integer> e : quizScores.entrySet()) {
                pw.println("QUIZ:" + e.getKey() + ":" + e.getValue());
            }
        } catch (IOException e) {
            // 静默失败，不影响使用
        }
    }

    private void load() {
        try {
            if (Files.exists(Paths.get(PROGRESS_FILE))) {
                List<String> lines = Files.readAllLines(Paths.get(PROGRESS_FILE));
                for (String line : lines) {
                    if (line.startsWith("COMPLETED:")) {
                        completedLessons.add(line.substring(10));
                    } else if (line.startsWith("EXERCISE:")) {
                        completedExercises.add(line.substring(9));
                    } else if (line.startsWith("QUIZ:")) {
                        String[] parts = line.substring(5).split(":");
                        if (parts.length == 2) {
                            quizScores.put(parts[0], Integer.parseInt(parts[1]));
                        }
                    }
                }
            }
        } catch (IOException e) {
            // 静默失败
        }
    }

    public void reset() {
        completedLessons.clear();
        completedExercises.clear();
        quizScores.clear();
        try {
            Files.deleteIfExists(Paths.get(PROGRESS_FILE));
        } catch (IOException e) {
            // 静默失败
        }
    }
}
