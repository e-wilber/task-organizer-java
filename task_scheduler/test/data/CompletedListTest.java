//testing CompletedList.java
package data;
import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CompletedListTest {

    private CompletedList completedList;
    private Task t1, t2;
    @Before
    public void setUp() {
        completedList = new CompletedList();
        t1 = new Task("T1", LocalDate.now(), 1);
        t2 = new Task("T2", LocalDate.now(), 2);
    }
    @Test
    public void testMarkCompletedAndHistory() {
        completedList.markCompleted(t1);
        completedList.markCompleted(t2);
        List<Task> history = completedList.getHistory();
        assertEquals(2, history.size());
        assertSame(t1, history.get(0));
        assertSame(t2, history.get(1));
    }
    @Test
    public void testUndoLast() {
        completedList.markCompleted(t1);
        completedList.markCompleted(t2);
        Optional<Task> undone = completedList.undoLast();
        assertTrue(undone.isPresent());
        assertSame(t2, undone.get());
        List<Task> history = completedList.getHistory();
        assertEquals(1, history.size());
        assertSame(t1, history.get(0));
    }
}
