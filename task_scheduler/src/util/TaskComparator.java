//This is used both by the PriorityQueue (TaskScheduler.java) and by custom heap sort (HeapSort.java)
package util;
import model.Task;
import java.util.Comparator;
public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
//if one task is due earlier, that comparison returns a negative number
//if one is due later it returns a positive number (so it sorts after)
        int dateCmp = a.getDueDate().compareTo(b.getDueDate());
        if (dateCmp != 0) return dateCmp;
//If the due dates are the same(dateCmp = 0) it compares difficulty values
        return Integer.compare(b.getDifficulty(), a.getDifficulty());
    }
}
