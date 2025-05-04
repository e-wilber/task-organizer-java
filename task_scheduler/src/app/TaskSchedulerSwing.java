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
package app;

import model.Task;
import scheduler.TaskScheduler;
import util.HeapSort;
import util.TaskComparator;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;
/**
 * Swing based UI for Task Scheduler
 * 
 * Provides controls to add, complete, undo and delete tasks,
 * and displays upcoming vs completed task lists
 * 
 */
public class TaskSchedulerSwing {
	
    private final TaskScheduler scheduler = new TaskScheduler();
    private final DefaultListModel<Task> upcomingModel  = new DefaultListModel<>();
    private final DefaultListModel<Task> completedModel = new DefaultListModel<>();

    
/**
* Builds and shows the main app window
* 
* This sets up the input controls, the task lists, and the action buttons
* 
*/    
    public void initAndShow() {
//Building UI
        JFrame frame = new JFrame("TASK SCHEDULER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
//Hard-coded daily tasks, sorted by custom heap-sort (HeapSort.java)
        Task[] initial = {
            new Task("feed cat",LocalDate.now(), 1),
            new Task("water plants",LocalDate.now(), 1),
            new Task("drink full glass of water",LocalDate.now(), 2)
        };
        HeapSort.sort(initial, new TaskComparator());
//Seed the schedulers priority queue
        for (Task t : initial) {
            scheduler.addTask(t);
        }
//Populate the upcoming list model directly from the sorted array
        upcomingModel.clear();
        for (Task t : initial) {
            upcomingModel.addElement(t);
        }
//Clear completed list (empty at startup)
        completedModel.clear();        
               
// Top input panel
        JPanel input = new JPanel();
//jpanel backgorund color
        input.setBackground(new Color(180, 190, 210));                          
        JTextField titleField = new JTextField(10);
// Date dropdown
        String[] monthNames = {
            "Jan","Feb","Mar","Apr","May","Jun",
            "Jul","Aug","Sep","Oct","Nov","Dec"
        };
        JComboBox<String> monthCb = new JComboBox<>(monthNames);
        Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
        JComboBox<Integer> dayCb   = new JComboBox<>(days);
        int currentYear = LocalDate.now().getYear();
        Integer[] years = IntStream.rangeClosed(currentYear, currentYear + 5)
                                   .boxed().toArray(Integer[]::new);
        JComboBox<Integer> yearCb  = new JComboBox<>(years);

        JTextField diffField  = new JTextField("1-5", 3);
        JButton addBtn        = new JButton("Add");

        input.add(new JLabel("TASK:"));  input.add(titleField);
        input.add(new JLabel("DUE DATE:"));    input.add(monthCb);
                                          input.add(dayCb);
                                          input.add(yearCb);
        input.add(new JLabel("DIFFICULTY:"));   input.add(diffField);
        input.add(addBtn);
        input.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
// Center lists and controls
        JList<Task> upcomingList  = new JList<>(upcomingModel);
        JList<Task> completedList = new JList<>(completedModel);
//list colors
        upcomingList.setBackground(new Color(180, 190, 210));
        completedList.setBackground(new Color(180, 190, 210));
//buttons        
        JButton completeBtn = new JButton("COMPLETED →");
        JButton undoBtn     = new JButton("Undo");
        JButton deleteBtn = new JButton("Delete");
//button colors       
        completeBtn.setBackground(Color.DARK_GRAY);
        completeBtn.setForeground(Color.white);    

        undoBtn.setBackground(Color.DARK_GRAY); 
        undoBtn.setForeground(Color.white);               

        deleteBtn.setBackground(Color.DARK_GRAY); 
        deleteBtn.setForeground(Color.white);
        
        JPanel ctrls = new JPanel(new GridLayout(3,1,5,5));
        ctrls.add(completeBtn);
        ctrls.add(undoBtn);
        ctrls.add(deleteBtn);

        JPanel lists = new JPanel(new BorderLayout(10, 10));
        lists.add(new JScrollPane(upcomingList), BorderLayout.WEST);
        lists.add(ctrls, BorderLayout.CENTER);
        lists.add(new JScrollPane(completedList), BorderLayout.EAST);
        lists.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
// Assemble UI
        frame.getContentPane().add(input, BorderLayout.NORTH);
        frame.getContentPane().add(lists, BorderLayout.CENTER);
// Event handlers
        addBtn.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                int month = monthCb.getSelectedIndex() + 1;
                int day   = (Integer)dayCb.getSelectedItem();
                int year  = (Integer)yearCb.getSelectedItem();
                YearMonth ym = YearMonth.of(year, month);
//input validation for date
                if (day > ym.lengthOfMonth()) {
                    throw new IllegalArgumentException("Invalid day for " + monthNames[month-1]);
                }
                LocalDate due = LocalDate.of(year, month, day);
//input validation for date
                if (due.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Due date cant be in the past");
                }
                int diff = Integer.parseInt(diffField.getText().trim());
//input validation for difficulty
                if (diff < 1 || diff > 5) {
                    throw new IllegalArgumentException("Difficulty must be between 1 and 5");
                }
//input validation for title
                if (title.isEmpty()) {
                    throw new IllegalArgumentException("Title can't be empty");
                }

                Task t = new Task(title, due, diff);
                scheduler.addTask(t);
                refresh();
                titleField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                    "Input Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
//buttons
        completeBtn.addActionListener(e -> {
            Task sel = upcomingList.getSelectedValue();
            if (sel != null) {
                scheduler.complete(sel);
                refresh();
            }
        });
        undoBtn.addActionListener(e -> {
            scheduler.undoLast().ifPresent(x -> refresh());
        });        
        deleteBtn.addActionListener(e -> {
            Task sel = upcomingList.getSelectedValue();
            if (sel != null) {
                scheduler.removeTask(sel);
                refresh();
            }
        });        
//Finalize and show
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refresh();
    }
 
/**
* Refreshes the UI list models 
*/    
    private void refresh() {
        List<Task> up   = scheduler.listUpcoming();
        List<Task> comp = scheduler.listCompleted();

        upcomingModel.clear();
        up.forEach(upcomingModel::addElement);

        completedModel.clear();
        comp.forEach(completedModel::addElement);
    }

/**
* Launches Swing UI on the Event Dispatch Thread
*
* @param args ignored
*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskSchedulerSwing().initAndShow());
    }
}
