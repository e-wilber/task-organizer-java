/** 
* DATA STRUCTURES: FINAL PROJECT
* @author E Wilber
* @version 1.0
* @since 1.0
*  
* OS: Windows11
* IDE: Eclipse
*  
* Copyright : This is my own original work 
* based on specifications issued by our instructor
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or
* unmodified, nor used generative AI as a final draft. 
* I have not given other fellow student(s) access to my program.
*/

//BINARY HEAP DATA STRUCTURE
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
/** Remove specific task from upcoming without marking it completed */
    public boolean removeTask(Task t) {
        return upcoming.remove(t);
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
