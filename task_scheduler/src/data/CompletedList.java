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

//DOUBLY-LINKED LIST DATA STRUCTURE
package data;

import model.Task;
import java.util.*;

/**
 * Maintains a doubly linked list of completed tasks
 * 
 * This class allows tasks to be marked as completed, undone in reverse order,
 * and gotten in the order they were completed
 */
public class CompletedList {
	
/**
* node representing an entry in the list of completed tasks
* Each node holds a reference to a {@link Task}, and pointers to the
* previous and next nodes in the list
*/
    private static class Node {
        Task task; Node prev, next;
        /**
         * makes a new node for a completed task
         *
         * @param task the task to store in this node
         */
        Node(Task task) { this.task = task; }
    }
    private Node head, tail;
 
/**
* Marks task as completed by appending it to the end of list
* Runs in constant time O(1)
*
* @param t the task to mark as completed
*/
    public void markCompleted(Task t) {
        Node n = new Node(t);
//keeping track of both the head and tail
        if (tail != null) {
            tail.next = n; n.prev = tail; tail = n;
        } else {
            head = tail = n;
        }
    }
    
/**
* Undoes the last completed task, removing it from the end of the list
* Runs in constant time O(1)
*
* @return an {@code Optional} with undone task, or empty if list is empty
*/
    public Optional<Task> undoLast() {
        if (tail == null) return Optional.empty();
        Task t = tail.task;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null;
        return Optional.of(t);
    }     

/**
* Returns list of all completed tasks in the order they were marked as complete
*
* @return a {@code List} containing the completed history
*/
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        for (Node n = head; n != null; n = n.next) {
            list.add(n.task);
        }
        return list;
    }
}
