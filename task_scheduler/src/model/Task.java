package model;
import java.time.LocalDate;

public class Task {
    private String title;
    private LocalDate dueDate;
    private int difficulty;
    private String description;

    public Task(String title, LocalDate dueDate, int difficulty, String description) {
        this.title       = title;
        this.dueDate     = dueDate;
        this.difficulty  = difficulty;
        this.description = description;
    }
//these must exist 
    public LocalDate getDueDate() {
        return dueDate;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return String.format(
            "%s (due %s, difficulty %d)%s",
            title,
            dueDate,
            difficulty,
            (description.isEmpty() ? "" : " – " + description)
        );
    }
}
