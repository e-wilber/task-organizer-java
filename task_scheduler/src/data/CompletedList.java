//DOUBLY-LINKED LIST DATA STRUCTURE
package data;

import model.Task;
import java.util.*;

public class CompletedList {
//Each inner Node holds a reference to its task,
//plus pointers to prev and next
    private static class Node {
        Task task; Node prev, next;
        Node(Task task) { this.task = task; }
    }
    private Node head, tail;
//Appending (markCompleted) puts a new node onto the tail in O(1)
    public void markCompleted(Task t) {
        Node n = new Node(t);
//keeping track of both the head and tail
        if (tail != null) {
            tail.next = n; n.prev = tail; tail = n;
        } else {
            head = tail = n;
        }
    }
//undoing (undoLast) takes off the tail in O(1) by stepping back with prev
    public Optional<Task> undoLast() {
        if (tail == null) return Optional.empty();
        Task t = tail.task;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null;
        return Optional.of(t);
    }     
//goes from head through next pointers in getHistory()
//to collect the completed task list in order
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        for (Node n = head; n != null; n = n.next) {
            list.add(n.task);
        }
        return list;
    }
}
