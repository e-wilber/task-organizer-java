// testing TaskComparator
package util;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;
public class TaskComparatorTest {
    private TaskComparator cmp;
    private Task earlyEasy;
    private Task earlyHard;
    private Task lateEasy;
    @Before
    public void setUp() {
        cmp = new TaskComparator();
        earlyEasy = new Task("E1", LocalDate.now(),       1);
        earlyHard = new Task("E2", LocalDate.now(),       5);
        lateEasy  = new Task("L1", LocalDate.now().plusDays(1), 1);
    }
    @Test
    public void compareByDueDate() {
        assertTrue("Early should come before late",
                   cmp.compare(earlyEasy, lateEasy) < 0);
        assertTrue("Late should come after early",
                   cmp.compare(lateEasy, earlyEasy) > 0);
    }
    @Test
    public void compareByDifficultyWhenSameDate() {
        assertTrue("Harder should come before easier on same date",
                   cmp.compare(earlyHard, earlyEasy) < 0);
        assertTrue("Easier should come after harder on same date",
                   cmp.compare(earlyEasy, earlyHard) > 0);
    }
    @Test
    public void compareEqualTasks() {
        Task copy = new Task("Copy", earlyEasy.getDueDate(), earlyEasy.getDifficulty());
        assertEquals("Identical tasks should compare equal",
                     0, cmp.compare(earlyEasy, copy));
    }
}
