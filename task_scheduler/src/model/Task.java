package model;
import java.time.LocalDate;

public class Task {
    private String title;
    private LocalDate dueDate;
    private int difficulty;

    public Task(String title, LocalDate dueDate, int difficulty) {
// Input validation
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be empty");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        if (difficulty < 1 || difficulty > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }
        this.title       = title;
        this.dueDate     = dueDate;
        this.difficulty  = difficulty;
    }
//getters
    public LocalDate getDueDate() {
        return dueDate;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
        return String.format(
            "%s (due %s, difficulty %d)",
            title,
            dueDate,
            difficulty
        );
    }
}
