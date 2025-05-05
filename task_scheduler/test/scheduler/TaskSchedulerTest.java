//testing TaskScheduler.java 
package scheduler;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

public class TaskSchedulerTest {
    private TaskScheduler sched;
    private Task a, b, c;
    @Before
    public void setUp() {
        sched = new TaskScheduler();
        LocalDate today = LocalDate.now();
        a = new Task("A", today.plusDays(1), 1);
        b = new Task("B", today.plusDays(1), 5);
        c = new Task("C", today.plusDays(2), 3);
    }
    @Test
    public void addAndPollNext() {
        sched.addTask(a);
        sched.addTask(b);
        // B is harder on same date → highest priority
        assertEquals(b, sched.pollNext());
        assertEquals(a, sched.pollNext());
        assertNull(sched.pollNext());
    }
    @Test
    public void completeAndUndo() {
        sched.addTask(a);
        sched.addTask(b);
        sched.complete(b);
        assertEquals(a, sched.pollNext());
        Optional<Task> undone = sched.undoLast();
        assertTrue(undone.isPresent());
        assertEquals(b, undone.get());
        assertEquals(b, sched.pollNext());
    }
    @Test
    public void listUpcomingReflectsQueue() {
        sched.addTask(c);
        sched.addTask(a);
        List<Task> upcoming = sched.listUpcoming();
        // Since listUpcoming may not be sorted by iteration,
        // sort it by title for comparison
        String[] titles = upcoming.stream()
                                  .map(Task::getTitle)
                                  .sorted()
                                  .toArray(String[]::new);
        assertArrayEquals(new String[]{"A", "C"}, titles);
    }
    @Test
    public void loadAndSortTasksBulk() {
        List<Task> batch = Arrays.asList(c, a, b);
        sched.loadAndSortTasks(batch);
        List<Task> up = sched.listUpcoming();
        // Enforce sorted order by due+difficulty
        up.sort(new util.TaskComparator());
        assertEquals(b, up.get(0));  // B (hardest on earliest)
        assertEquals(a, up.get(1));  // A
        assertEquals(c, up.get(2));  // C (latest)
    }
}
