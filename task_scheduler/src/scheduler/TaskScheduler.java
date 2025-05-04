package scheduler;

import model.Task;
import util.TaskComparator;
import util.HeapSort;
import data.CompletedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class TaskScheduler {
//BINARY HEAP DATA STRUCTURE
    private final PriorityQueue<Task> upcoming;
    private final CompletedList completed;

    public TaskScheduler() {
// Heap ordered by due date then difficulty
        this.upcoming  = new PriorityQueue<>(new TaskComparator());
        this.completed = new CompletedList();
    }
/** Adding single task into the priority queue. */
    public void addTask(Task t) {
        upcoming.add(t);
    }
/** Removing and returning the highest priority (earliest/hardest) task */
    public Task pollNext() {
        return upcoming.poll();
    }
/** Marking specific task as completed (removing from upcoming, record in completed list) */
    public void complete(Task t) {
        upcoming.remove(t);
        completed.markCompleted(t);
    }
    /**
     * Undo last completion
     * @return the task that was undone, if it was
     */
    public Optional<Task> undoLast() {
        Optional<Task> o = completed.undoLast();
        o.ifPresent(upcoming::add);
        return o;
    }
/** Returning snapshot list of all upcoming tasks (unsorted iteration of heap) */
    public List<Task> listUpcoming() {
        return new ArrayList<>(upcoming);
    }
/** Returning history of completed tasks in order of completion. */
    public List<Task> listCompleted() {
        return completed.getHistory();
    }
    /**
     * HEAPSORT HERE
     * 
     * Bulk loading a list of tasks, sort them in place with HeapSort,
     * then adding them into the priority queue in sorted order
     */
    public void loadAndSortTasks(List<Task> taskList) {
        Task[] arr = taskList.toArray(new Task[0]);
        HeapSort.sort(arr, new TaskComparator());
        for (Task t : arr) {
            addTask(t);
        }
    }
}
