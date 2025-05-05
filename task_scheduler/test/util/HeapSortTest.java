//testing HeapSort.java
package util;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Comparator;
import static org.junit.Assert.*;
public class HeapSortTest {
    private Task[] tasks;
    private Comparator<Task> cmp;
    @Before
    public void setUp() {
        tasks = new Task[] {
            new Task("T1", LocalDate.now(), 2),
            new Task("T2", LocalDate.now(), 1),
            new Task("T3", LocalDate.now(), 5),
            new Task("T4", LocalDate.now(), 3)
        };
        cmp = new TaskComparator();
    }
    @Test
    public void sortOrdersCorrectlyByComparator() {
        HeapSort.sort(tasks, cmp);
        for (int i = 0; i < tasks.length - 1; i++) {
            int comparison = cmp.compare(tasks[i], tasks[i + 1]);
            assertTrue(
                String.format("Array not sorted at index %d: %s vs %s", 
                              i, tasks[i].getTitle(), tasks[i+1].getTitle()),
                comparison <= 0
            );
        }
    }
    @Test
    public void sortEmptyArray() {
        Task[] empty = {};
        HeapSort.sort(empty, cmp);
        assertEquals(0, empty.length);
    }
}
